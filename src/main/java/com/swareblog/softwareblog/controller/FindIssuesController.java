package com.swareblog.softwareblog.controller;

import com.swareblog.softwareblog.dto.Issues.GithubIssueDto;
import com.swareblog.softwareblog.dto.Issues.Page;
import com.swareblog.softwareblog.dto.Issues.UrlsPages;
import com.swareblog.softwareblog.provider.GithubIssuesProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class FindIssuesController {

    @Autowired
    public GithubIssuesProvider githubIssues;
    @Value("${per_page}")
    private String per_page;

    @GetMapping("/myissues")
    public String index1(@RequestParam(name = "q") String q,
                         @RequestParam(name = "language") String language,
                         @RequestParam(name = "sort") String sort,
                         @RequestParam(name = "page") String page1,
                         @RequestParam(name = "order") String order,
                         Model model) {

        System.out.println(q);
        System.out.println(language);
        System.out.println(sort);
        System.out.println(page1);
        System.out.println(order);

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
        String url = getUrl(q, language, sort, page, order);
        ArrayList<GithubIssueDto> issues = githubIssues.findIssues(url);
        int totalPage = Page.getPage();
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

        System.out.println("totlaPage"+totalPage);
        ArrayList<UrlsPages> returnUrlsPages = getUrlList(q, language, sort, order, page, totalPage);

        model.addAttribute("urlsPages",returnUrlsPages);
        return "myissues";
    }

    private ArrayList<UrlsPages> getUrlList(String q,String language,String sort, String order, int page, int totalPage) {
        ArrayList<UrlsPages> urlPages = new ArrayList<>();
        if (totalPage > 10) {  // 如果大于10页的时候 做复杂的分页处理
           if(page<5){
               for(int i =1;i<11;i++){
//                   System.out.println(i);
                   UrlsPages u = new UrlsPages("/myissues?q="+q+"&language="+language+"&sort="+sort+"&page="+i+"&order="+order,""+i);
                   urlPages.add(u);
               }
           }
           else if(totalPage-page<=6){
               for(int i=totalPage-10;i<=totalPage;i++){
                   UrlsPages u = new UrlsPages("/myissues?q="+q+"&language="+language+"&sort="+sort+"&page="+i+"&order="+order,""+i);
                   urlPages.add(u);
               }
           }
           else{
               for(int i=page-4;i<=page+6;i++){
                   UrlsPages u = new UrlsPages("/myissues?q="+q+"&language="+language+"&sort="+sort+"&page="+i+"&order="+order,""+i);
                   urlPages.add(u);
               }
           }
        }
        else{
            for(int i = 1;i<page+1;i++){
                UrlsPages u = new UrlsPages("/myissues?q="+q+"&language="+language+"&sort="+sort+"&page="+i+"&order="+order,""+i);
                urlPages.add(u);
            }
        }
        System.out.println("size:"+urlPages.size());
        return urlPages;
    }



    public String getUrl(String q, String language, String sort,int page, String order){
        String url = "https://api.github.com/search/issues?q=" +q;
        url = url+"label:%22good%20first%20issue%22,good-first-issue,goodfirstissue+state:open";
        if(!"".equals(language)&&!"default".equals(language)){
            url = url + "+language:"+ language;
        }
        if(!"".equals(sort)){
            url = url + "&sort="+ sort;
        }
        if(!"".equals(order)){
            url = url + "&order="+order;
        }
        url = url + "&per_page="+per_page + "&page="+page;
        System.out.println(url);
        return url;
    }

}
