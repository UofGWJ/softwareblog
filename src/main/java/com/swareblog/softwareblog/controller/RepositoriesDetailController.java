package com.swareblog.softwareblog.controller;

import com.alibaba.fastjson.JSONObject;
import com.swareblog.softwareblog.dto.GithubOwner;
import com.swareblog.softwareblog.dto.issues.UrlsPages;
import com.swareblog.softwareblog.dto.repositories.GithubRepositoriesDto;
import com.swareblog.softwareblog.dto.repositories.PageRepositoriesDetail;
import com.swareblog.softwareblog.dto.repositories.RepositoriesDetailDto;
import com.swareblog.softwareblog.provider.GithubCommonProvider;
import com.swareblog.softwareblog.provider.RepositoriesDetailProvider;

import com.swareblog.softwareblog.service.ContributeDetailService;
import com.swareblog.softwareblog.service.IssueService;
import com.swareblog.softwareblog.service.RepositoryService;
import com.swareblog.softwareblog.vo.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class RepositoriesDetailController {

    @Value("${per_page_detail}")
    private String per_page_detail;
    @Autowired
    public RepositoriesDetailProvider repositoriesDetailProvider;

    @Autowired
    public GithubCommonProvider githubCommonProvider;

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private ContributeDetailService contributeDetailService;

    /*
    myissues detail界面展示
    1. 如果前端未传值，设置默认值page1=1
    2. // 判断用户是否登陆  如果登陆可以获取到session中 存的accessToken
    3. 通过前端传递来的issue html_url 访问得到当前issue的JSONObject对象 if not login and the times is out to 404
    4. 解析JSONObject 对象 得到Owner详细信息 if not login and the times is out to 404
    5. 解析JSONObject 对象 得到GithubIssueDto详细信息 if not login and the times is out to 404
    6. 根据相似的语言 拼接查找相似url
    7. 根据url 查询推荐的issue
    8. 根据url 查询推荐的issue  获得ArrayList<UrlsPages>  页面链接 和 页数
    9.如果登陆了 写入history数据库
    10. 判断是否已经将该issue加入到 contribute里
   * */
    @GetMapping("/repositoriesdetail")
    public String myrepositoriesdetail(@RequestParam(name = "url") String url,
                                       @RequestParam(name = "page") String page1,
                                       @RequestParam(name = "q") String q,
                                       HttpServletRequest request,
                                       Model model) throws ParseException {
        // 1. 如果前端未传值，设置默认值page1=1
        if ("".equals(page1)) {
            page1 = "1";
        }
        //  2. // 判断用户是否登陆  如果登陆可以获取到session中 存的accessToken
        String accessToken = null;
        Object obj = request.getSession().getAttribute("accessToken");
        if(obj!=null){
            accessToken = obj.toString();
        }
        // 3. 通过前端传递来的repositories html_url 访问得到当前repositories的JSONObject对象 if not login and the times is out to 404
        JSONObject jsonObject = repositoriesDetailProvider.findRepositories(url,accessToken);
        // if not login and the times is out to 404
        if(jsonObject==null){
            return "404";
        }
        // 4. 解析JSONObject 对象 得到Owner详细信息 if not login and the times is out to 404
        GithubOwner githubOwner = repositoriesDetailProvider.findOwner(jsonObject,accessToken);
        // if not login and the times is out to 404
        if(githubOwner==null){
            return "404";
        }
        // 5. 解析JSONObject 对象 得到GithubIssueDto详细信息 if not login and the times is out to 404
        RepositoriesDetailDto repositoriesDetailDto = repositoriesDetailProvider.findRepositoriesDetail(jsonObject,accessToken);
        // if not login and the times is out to 404
        if(repositoriesDetailDto==null){
            return "404";
        }

        model.addAttribute("owner",githubOwner);
        model.addAttribute("repositoriesDetail",repositoriesDetailDto);
        int page = Integer.parseInt(page1);

        // 6. 根据name 和 language  拼接查找相似url
        String url1 = "https://api.github.com/search/repositories?q="+repositoriesDetailDto.getName()+"+language:"+repositoriesDetailDto.getLanguage()+"+in:readme,description+watchers>2+stars>1+forks>1+commits>10&sort=stars&order=desc&per_page="+per_page_detail+"&page="+page;
        // 7. 根据url 查询推荐的repositories
        ArrayList<GithubRepositoriesDto> reponsitories = repositoriesDetailProvider.findReponsitories(url1,accessToken);
        model.addAttribute("reponsitories", reponsitories);
        model.addAttribute("q",q);
        int totalPage = PageRepositoriesDetail.getPage();
        model.addAttribute("page", ""+page);
        String hasPre = null;
        if (page > 1) {
            hasPre = "/repositoriesdetail?url="+url+"&page="+(page-1)+"&q="+q;
        }
        String hasNext = null;
        if (page < totalPage) {
            hasNext = "/repositoriesdetail?url="+url+"&page="+(page+1)+"&q="+q;
        }
        model.addAttribute("hasPre",hasPre);
        model.addAttribute("hasNext",hasNext);

        // 8. 根据url 查询推荐的repositories  获得ArrayList<UrlsPages>  页面链接 和 页数
        ArrayList<UrlsPages> returnUrlsPages = repositoriesDetailProvider.getUrlListDetail("repositoriesdetail",url,  page, totalPage,q);
        model.addAttribute("urlsPages",returnUrlsPages);
        //    9.如果登陆了 写入数据库
        Repository repository = new Repository();
        String isContribute = "noLogin";
        String username = null;
        Object obju = request.getSession().getAttribute("user");
        if (obju != null) {
            isContribute = "false";
            username = obju.toString();
            repository.setUserid(username);
            repository.setHtml_url(url);
            repository.setLanguage(repositoriesDetailDto.getLanguage());
            repository.setQ(q);
            repository.setTitle(repositoriesDetailDto.getName());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
            Date time = df.parse(df.format(new Date())); // new Date()为获取当前系统时间
            repository.setTime(time);
            repositoryService.insertRepository(repository);
        }
        //    10. 判断是否已经将该issue加入到 contribute里

        if(isContribute=="false"&contributeDetailService.getContribute(username,repositoriesDetailDto.getFull_name(),"repository")>0){
            isContribute="true";
        }
        model.addAttribute("isContribute", isContribute);
        return "myrepositoriesdetail";
    }
}
