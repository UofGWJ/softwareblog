package com.swareblog.softwareblog.controller;

import com.swareblog.softwareblog.dto.issues.Page;
import com.swareblog.softwareblog.dto.issues.UrlsPages;
import com.swareblog.softwareblog.dto.repositories.GithubRepositoriesDto;
import com.swareblog.softwareblog.dto.topics.GithubTopicsDto;
import com.swareblog.softwareblog.provider.GithubCommonProvider;
import com.swareblog.softwareblog.provider.GithubRepositoriesProvider;
import com.swareblog.softwareblog.provider.GithubTopicsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class FindTopicController {
    @Autowired
    public GithubTopicsProvider githubTopicsProvider;
    @Autowired
    public GithubCommonProvider githubCommonProvider;

    @GetMapping("/mytopics")
    public String index1(@RequestParam(name = "q") String q,
                         @RequestParam(name = "page") String page1,
                         Model model) {


        if ("".equals(page1)) {
            page1 = "1";
        }

        if("".equals(q)){
            q = "ruby";
        }
        int page = Integer.parseInt(page1);
        String url = githubTopicsProvider.getUrl(q,  page);
        ArrayList<GithubTopicsDto> topics = githubTopicsProvider.findTopics(url);
        int totalPage = Page.getPage();
        model.addAttribute("topics", topics);
        model.addAttribute("totalPage", totalPage);

        model.addAttribute("q", q);
        model.addAttribute("page", ""+page);
        String hasPre = null;
        if (page > 1) {
            hasPre = "/mytopics?q="+q+"&page="+(page-1);
        }
        String hasNext = null;
        if (page < totalPage) {
            hasNext = "/mytopics?q="+q+"&page="+(page+1);
        }
        model.addAttribute("hasPre",hasPre);
        model.addAttribute("hasNext",hasNext);

        System.out.println("totlaPage"+totalPage);
        ArrayList<UrlsPages> returnUrlsPages = githubCommonProvider.getUrlList1("mytopics",q, page, totalPage);

        model.addAttribute("urlsPages",returnUrlsPages);
        return "mytopics";
    }

}
