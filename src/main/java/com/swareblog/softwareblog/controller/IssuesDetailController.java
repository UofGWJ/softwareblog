package com.swareblog.softwareblog.controller;

import com.alibaba.fastjson.JSONObject;
import com.swareblog.softwareblog.dto.GithubOwner;
import com.swareblog.softwareblog.dto.GithubUser;
import com.swareblog.softwareblog.dto.issues.*;
import com.swareblog.softwareblog.provider.GithubCommonProvider;
import com.swareblog.softwareblog.provider.IssuesDetailProvider;
import com.swareblog.softwareblog.service.ContributeDetailService;
import com.swareblog.softwareblog.service.IssueService;
import com.swareblog.softwareblog.vo.Issue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IssuesDetailController {
    @Value("${per_page_detail}")
    private String per_page_detail;
    @Autowired
    public IssuesDetailProvider issuesDetailProvider;

    @Autowired
    public GithubCommonProvider githubCommonProvider;

    @Resource
    private IssueService issueService;

    @Resource
    private ContributeDetailService contributeDetailService;

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
    10.如果登陆了 写入数据库
    11. 判断是否已经将该issue加入到 contribute里
   * */
    @GetMapping("/issuesdetail")
    public String myissuesdetail(@RequestParam(name = "url") String url,
                                 @RequestParam(name = "labels") String labels,
                                 @RequestParam(name = "page") String page1,
                                 @RequestParam(name = "q") String q,
                                 HttpServletRequest request,
                                 Model model) throws ParseException {
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
        String url1 = "https://api.github.com/search/issues?q=" + q + "+label:%22good%20first%20issue%22,good-first-issue,goodfirstissue";
        if (!"".equals(labels)) {
            String[] labelsArr = labels.split(",");
            for (String label : labelsArr) {
                url1 = url1 + "+label:" + label;
            }
        }
        url1 = url1 + "+state:open&sort=best match&order=desc&per_page=" + per_page_detail + "&page=" + page;
        model.addAttribute("q", q);
        // 7. 根据url 查询推荐的issue
        ArrayList<GithubIssueDto> githubIssueDtos = issuesDetailProvider.findIsssuesDetails(url1, accessToken);
        // 如果githubIssueDtos.size =1 表示只有他自己，则说明查询无效 因此置空
        // 如果githubIssueDtos.size =0 表示什么都没查到没有相同label的，则说明查询无效 因此置空
        if (githubIssueDtos.size() <= 1) {
            githubIssueDtos = null;
        }
        model.addAttribute("issues", githubIssueDtos);

        int totalPage = PageIssuesDetail.getPage();

        model.addAttribute("page", "" + page);
        String hasPre = null;
        if (page > 1) {
            hasPre = "/issuesdetail?url=" + url + "&page=" + (page - 1)+"&labels="+labels+"&q="+q;
        }
        String hasNext = null;
        if (page < totalPage) {
            hasNext = "/issuesdetail?url=" + url + "&page=" + (page + 1)+"&labels="+labels+"&q="+q;
        }
        model.addAttribute("hasPre", hasPre);
        model.addAttribute("hasNext", hasNext);

        // 8. 根据url 查询推荐的issue  获得ArrayList<UrlsPages>  页面链接 和 页数
        ArrayList<UrlsPages> returnUrlsPages = issuesDetailProvider.getUrlListDetail("issuesdetail", url, page, totalPage, labels, q);
        if (githubIssueDtos == null) {
            model.addAttribute("urlsPages", null);
        } else {
            model.addAttribute("urlsPages", returnUrlsPages);
        }


        // 9. 根据url 查询推荐的StackOverFlow return ArrayList<GithubIssueDto>
        String qSof = githubIssueDto.getTitle();
        if (qSof.length() > 10) {
            qSof = qSof.substring(0, 10);
        }

        String urlsof = "https://api.stackexchange.com//2.3/search/advanced?page=1&pagesize=10&order=desc&sort=activity&q=" + qSof + "&site=stackoverflow";
        ArrayList<StackOverFlowIssueDto> stackOverFlowIssueDtos = issuesDetailProvider.findStackOverflowIssueDtos(urlsof);
//        System.out.println(stackOverFlowIssueDtos);
        model.addAttribute("stackOverFlows", stackOverFlowIssueDtos);

        model.addAttribute("previouspage", 0);

        model.addAttribute("nextpage", 2);


        // 10. 如果登陆了 写入数据库
        String isContribute = "notLogin";
        Issue issue = new Issue();
        String username = null;
        Object obju = request.getSession().getAttribute("user");
        if (obju != null) {
            isContribute = "false";
            username = obju.toString();
            issue.setUserid(username);
            issue.setHtml_url(url);
            issue.setLables(labels);
            issue.setQ(q);
            issue.setTitle(githubIssueDto.getTitle());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
            Date time = df.parse(df.format(new Date())); // new Date()为获取当前系统时间
            issue.setTime(time);
            issueService.insertIssue(issue);
        }

        // 11. 判断是否已经将该issue加入到 contribute里

        if(isContribute=="false"&contributeDetailService.getContribute(username,githubIssueDto.getTitle(),"issue")>0){
            isContribute="true";
        }
        model.addAttribute("isContribute", isContribute);

        return "myissuesdetail";
    }


//    public String changeStackPage(@RequestParam(name = "page") String page) {
////        int stackPage = StackOverFlowIssuePageDto.getPage();
//////        if (stackPage == 0) {
//////            StackOverFlowIssuePageDto.setPage(1);
//////        }
////        int previouspage = stackPage - 1;
////        int nextpage = stackPage + 1;
//
//        String restle = "";
//        return restle;
//    }
}


