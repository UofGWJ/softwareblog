package com.swareblog.softwareblog.controller;

import com.swareblog.softwareblog.dto.issues.GithubIssueDto;
import com.swareblog.softwareblog.dto.issues.PageIssues;
import com.swareblog.softwareblog.dto.issues.UrlsPages;
import com.swareblog.softwareblog.provider.GithubCommonProvider;
import com.swareblog.softwareblog.provider.GithubIssuesProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
public class FindIssuesController {

    @Autowired
    public GithubIssuesProvider githubIssues;

    @Autowired
    public GithubCommonProvider githubCommonProvider;

    @GetMapping("/myissues")
    public String index1(@RequestParam(name = "q") String q,
                         @RequestParam(name = "language") String language,
                         @RequestParam(name = "sort") String sort,
                         @RequestParam(name = "page") String page1,
                         @RequestParam(name = "order") String order,
                         HttpServletRequest request,
                         Model model) {
        if ("".equals(sort)|| "default".equals(sort)) {
            sort = "best match";
        }
        if ("".equals(page1)) {
            page1 = "1";
        }
        if ("".equals(order)) {
            order = "desc";
        }
        int page = Integer.parseInt(page1);
        String url = githubIssues.getUrl(q, language, sort, page, order);

        String accessToken = null;
        Object obj = request.getSession().getAttribute("accessToken");
        if(obj!=null){
            accessToken = obj.toString();
        }

        ArrayList<GithubIssueDto> issues = githubIssues.findIssues(url,accessToken);
        int totalPage = PageIssues.getPage();
        model.addAttribute("issues", issues);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("q", q);
        model.addAttribute("language", language);
        model.addAttribute("sort", sort);
        model.addAttribute("page", ""+page);
        model.addAttribute("order", order);
        String hasPre = null;
        if (page > 1) {
            hasPre = "/myissues?q="+q+"&language="+language+"&sort="+sort+"&page="+(page-1)+"&order="+order;
        }
        String hasNext = null;
        if (page < totalPage) {
            hasNext = "/myissues?q="+q+"&language="+language+"&sort="+sort+"&page="+(page+1)+"&order="+order;
        }
        model.addAttribute("hasPre",hasPre);
        model.addAttribute("hasNext",hasNext);

//        System.out.println("totlaPage"+totalPage);
        ArrayList<UrlsPages> returnUrlsPages = githubCommonProvider.getUrlList("myissues",q, language, sort, order, page, totalPage);

        model.addAttribute("urlsPages",returnUrlsPages);
        return "myissues";
    }






}
