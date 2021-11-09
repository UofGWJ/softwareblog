package com.swareblog.softwareblog.controller;

import com.alibaba.fastjson.JSONObject;
import com.swareblog.softwareblog.dto.GithubOwner;
import com.swareblog.softwareblog.dto.issues.UrlsPages;
import com.swareblog.softwareblog.dto.repositories.GithubRepositoriesDto;
import com.swareblog.softwareblog.dto.repositories.PageRepositoriesDetail;
import com.swareblog.softwareblog.dto.repositories.RepositoriesDetailDto;
import com.swareblog.softwareblog.provider.GithubCommonProvider;
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
public class RepositoriesDetailController {

    @Value("${per_page_detail}")
    private String per_page_detail;
    @Autowired
    public RepositoriesDetailProvider repositoriesDetailProvider;

    @Autowired
    public GithubCommonProvider githubCommonProvider;

    @GetMapping("/repositoriesdetail")
    public String myrepositoriesdetail(@RequestParam(name = "url") String url,
                                       @RequestParam(name = "page") String page1,
                                       HttpServletRequest request,
                                       Model model){

        if ("".equals(page1)) {
            page1 = "1";
        }
//        System.out.println(url);
        String accessToken = null;
        Object obj = request.getSession().getAttribute("accessToken");
        if(obj!=null){
            accessToken = obj.toString();
        }

        JSONObject jsonObject = repositoriesDetailProvider.findRepositories(url,accessToken);
        // if not login and the times is out to 404
        if(jsonObject==null){
            return "404";
        }
        GithubOwner githubOwner = repositoriesDetailProvider.findOwner(jsonObject,accessToken);
        // if not login and the times is out to 404
        if(githubOwner==null){
            return "404";
        }
        RepositoriesDetailDto repositoriesDetailDto = repositoriesDetailProvider.findRepositoriesDetail(jsonObject,accessToken);
        // if not login and the times is out to 404
        if(repositoriesDetailDto==null){
            return "404";
        }

        model.addAttribute("owner",githubOwner);
        model.addAttribute("repositoriesDetail",repositoriesDetailDto);
        int page = Integer.parseInt(page1);

        String url1 = "https://api.github.com/search/repositories?q="+repositoriesDetailDto.getName()+"+language:"+repositoriesDetailDto.getLanguage()+"+in:readme,description+watchers>2+stars>1+forks>1+commits>10&sort=stars&order=desc&per_page="+per_page_detail+"&page="+page;
//        System.out.println(url1);

        ArrayList<GithubRepositoriesDto> reponsitories = repositoriesDetailProvider.findReponsitories(url1,accessToken);
        model.addAttribute("reponsitories", reponsitories);

        int totalPage = PageRepositoriesDetail.getPage();
        model.addAttribute("page", ""+page);
        String hasPre = null;
        if (page > 1) {
            hasPre = "/repositoriesdetail?url="+url+"&page="+(page-1);
        }
        String hasNext = null;
        if (page < totalPage) {
            hasNext = "/repositoriesdetail?url="+url+"&page="+(page+1);
        }
        model.addAttribute("hasPre",hasPre);
        model.addAttribute("hasNext",hasNext);

//        System.out.println("totlaPage"+totalPage);
        ArrayList<UrlsPages> returnUrlsPages = repositoriesDetailProvider.getUrlListDetail("repositoriesdetail",url,  page, totalPage);

        model.addAttribute("urlsPages",returnUrlsPages);


        return "myrepositoriesdetail";
    }
}
