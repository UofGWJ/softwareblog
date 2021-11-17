package com.swareblog.softwareblog.controller;

import com.alibaba.fastjson.JSONObject;
import com.swareblog.softwareblog.dto.GithubOwner;
import com.swareblog.softwareblog.dto.issues.GithubIssueDto;
import com.swareblog.softwareblog.dto.issues.PageIssuesDetail;
import com.swareblog.softwareblog.dto.issues.StackOverFlowIssueDto;
import com.swareblog.softwareblog.dto.issues.UrlsPages;
import com.swareblog.softwareblog.provider.GithubCommonProvider;
import com.swareblog.softwareblog.provider.IssuesDetailProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
public class IssuesDetailController {
    @Value("${per_page_detail}")
    private String per_page_detail;
    @Autowired
    public IssuesDetailProvider issuesDetailProvider;

    @Autowired
    public GithubCommonProvider githubCommonProvider;

    /*
    myissues detail界面展示
    1. 如果前端未传值，设置默认值page1=1
    2. // 判断用户是否登陆  如果登陆可以获取到session中 存的accessToken
    3. 通过前端传递来的issue html_url 访问得到当前issue的JSONObject对象 if not login and the times is out to 404
    4. 解析JSONObject 对象 得到Owner详细信息 if not login and the times is out to 404
    5. 解析JSONObject 对象 得到GithubIssueDto详细信息 if not login and the times is out to 404
    6. 找到相同labels  作为推荐依据 拼接url用于查询推荐的 issue
    7. 根据url 查询推荐的issue
    8. 根据url 查询推荐的issue  获得ArrayList<UrlsPages>  页面链接 和 页数
    9. 根据url 查询推荐的StackOverFlow return ArrayList<GithubIssueDto>
   * */
    @GetMapping("/issuesdetail")
    public String myissuesdetail(@RequestParam(name = "url") String url,
                                 @RequestParam(name = "labels") String labels,
                                 @RequestParam(name = "page") String page1,
                                 @RequestParam(name = "q") String q,
                                 HttpServletRequest request,
                                 Model model) {
        //  1. 如果前端未传值，设置默认值page1=1
        if ("".equals(page1)) {
            page1 = "1";
        }
        //2. 判断用户是否登陆  如果登陆可以获取到session中 存的accessToken
        String accessToken = null;
        Object obj = request.getSession().getAttribute("accessToken");
        if (obj != null) {
            accessToken = obj.toString();
        }
        //  3. 通过前端传递来的issue html_url 访问得到当前issue的JSONObject对象
        JSONObject jsonObject = issuesDetailProvider.findissues(url, accessToken);
        // if not login and the times is out to 404
        if (jsonObject == null) {
            return "404";
        }
        //  4. 解析JSONObject 对象 得到Owner详细信息
        GithubOwner githubOwner = issuesDetailProvider.findOwner(jsonObject, accessToken);
        // if not login and the times is out to 404
        if (githubOwner == null) {
            return "404";
        }
        //  5. 解析JSONObject 对象 得到GithubIssueDto详细信息
        GithubIssueDto githubIssueDto = issuesDetailProvider.findIssuesDetail(jsonObject, accessToken);
        // if not login and the times is out to 404
        if (githubIssueDto == null) {
            return "404";
        }

        model.addAttribute("owner", githubOwner);
        model.addAttribute("issuesDetail", githubIssueDto);
        int page = Integer.parseInt(page1);

        // 6. 找到相同labels  作为推荐依据 拼接url用于查询推荐的 issue
        String url1 = "https://api.github.com/search/issues?q="+q+"+label:%22good%20first%20issue%22,good-first-issue,goodfirstissue";
        if(!"".equals(labels)){
            String[] labelsArr = labels.split(",");
            for(String label : labelsArr){
                url1 = url1 + "+label:"+label;
            }
        }
        url1 = url1+ "+state:open&sort=best match&order=desc&per_page="+per_page_detail+"&page="+page;
        model.addAttribute("q", q);
        // 7. 根据url 查询推荐的issue
        ArrayList<GithubIssueDto> githubIssueDtos = issuesDetailProvider.findIsssuesDetails(url1, accessToken);
        // 如果githubIssueDtos.size =1 表示只有他自己，则说明查询无效 因此置空
        // 如果githubIssueDtos.size =0 表示什么都没查到没有相同label的，则说明查询无效 因此置空
        if(githubIssueDtos.size()<=1){
            githubIssueDtos =null;
        }
        model.addAttribute("issues", githubIssueDtos);

        int totalPage = PageIssuesDetail.getPage();

        model.addAttribute("page", "" + page);
        String hasPre = null;
        if (page > 1) {
            hasPre = "/issuesdetail?url=" + url + "&page=" + (page - 1);
        }
        String hasNext = null;
        if (page < totalPage) {
            hasNext = "/issuesdetail?url=" + url + "&page=" + (page + 1);
        }
        model.addAttribute("hasPre", hasPre);
        model.addAttribute("hasNext", hasNext);

        // 8. 根据url 查询推荐的issue  获得ArrayList<UrlsPages>  页面链接 和 页数
        ArrayList<UrlsPages> returnUrlsPages = issuesDetailProvider.getUrlListDetail("issuesdetail", url, page, totalPage,labels,q);
        if(githubIssueDtos == null){
            model.addAttribute("urlsPages", null);
        }
       else{
            model.addAttribute("urlsPages", returnUrlsPages);
        }


       // 9. 根据url 查询推荐的StackOverFlow return ArrayList<GithubIssueDto>
        String qSof = githubIssueDto.getTitle();
        if(qSof.length()>10){
            qSof = qSof.substring(0,10);
        }
        String urlsof = "https://api.stackexchange.com//2.3/search/advanced?page=1&pagesize=10&order=desc&sort=activity&q="+qSof+"&site=stackoverflow";
        System.out.println(urlsof);
        ArrayList<StackOverFlowIssueDto> stackOverFlowIssueDtos = issuesDetailProvider.findStackOverflowIssueDtos(urlsof);
//        System.out.println(stackOverFlowIssueDtos);
        model.addAttribute("stackOverFlows",stackOverFlowIssueDtos);
        return "myissuesdetail";
    }
}


