package com.swareblog.softwareblog.controller;

import com.alibaba.fastjson.JSONObject;
import com.swareblog.softwareblog.dto.GithubOwner;
import com.swareblog.softwareblog.dto.issues.GithubIssueDto;
import com.swareblog.softwareblog.dto.issues.PageIssuesDetail;
import com.swareblog.softwareblog.dto.issues.UrlsPages;
import com.swareblog.softwareblog.dto.repositories.GithubRepositoriesDto;
import com.swareblog.softwareblog.dto.repositories.PageRepositoriesDetail;
import com.swareblog.softwareblog.dto.repositories.RepositoriesDetailDto;
import com.swareblog.softwareblog.provider.GithubCommonProvider;
import com.swareblog.softwareblog.provider.IssuesDetailProvider;
import com.swareblog.softwareblog.provider.RepositoriesDetailProvider;
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

    @GetMapping("/issuesdetail")
    public String myissuesdetail(@RequestParam(name = "url") String url,
                                 @RequestParam(name = "labels") String labels,
                                 @RequestParam(name = "page") String page1,
                                 @RequestParam(name = "q") String q,
                                 HttpServletRequest request,
                                 Model model) {

        if ("".equals(page1)) {
            page1 = "1";
        }
//        System.out.println(url);
        String accessToken = null;
        Object obj = request.getSession().getAttribute("accessToken");
        if (obj != null) {
            accessToken = obj.toString();
        }

        JSONObject jsonObject = issuesDetailProvider.findissues(url, accessToken);
        // if not login and the times is out to 404
        if (jsonObject == null) {
            return "404";
        }
        GithubOwner githubOwner = issuesDetailProvider.findOwner(jsonObject, accessToken);
        // if not login and the times is out to 404
        if (githubOwner == null) {
            return "404";
        }
        GithubIssueDto githubIssueDto = issuesDetailProvider.findIssuesDetail(jsonObject, accessToken);
        // if not login and the times is out to 404
        if (githubIssueDto == null) {
            return "404";
        }

        model.addAttribute("owner", githubOwner);
        model.addAttribute("issuesDetail", githubIssueDto);
        int page = Integer.parseInt(page1);


        System.out.println("labels:"+labels);
        String url1 = "https://api.github.com/search/issues?q="+q+"+label:%22good%20first%20issue%22,good-first-issue,goodfirstissue";
        if(!"".equals(labels)){
            String[] labelsArr = labels.split(",");
            for(String label : labelsArr){
                url1 = url1 + "+label:"+label;
            }
        }
        url1 = url1+ "+state:open&sort=best match&order=desc&per_page="+per_page_detail+"&page="+page;
        System.out.println(url1);
        model.addAttribute("q", q);


        ArrayList<GithubIssueDto> githubIssueDtos = issuesDetailProvider.findIsssuesDetails(url1, accessToken);



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

//        System.out.println("totlaPage" + totalPage);
        ArrayList<UrlsPages> returnUrlsPages = issuesDetailProvider.getUrlListDetail("issuesdetail", url, page, totalPage,labels,q);

        model.addAttribute("urlsPages", returnUrlsPages);

        return "myissuesdetail";
    }
}


