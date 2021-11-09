package com.swareblog.softwareblog.controller;

import com.swareblog.softwareblog.dto.issues.UrlsPages;
import com.swareblog.softwareblog.dto.repositories.GithubRepositoriesDto;
import com.swareblog.softwareblog.dto.repositories.PageRepositories;
import com.swareblog.softwareblog.provider.GithubCommonProvider;
import com.swareblog.softwareblog.provider.GithubRepositoriesProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
public class FindRepositoriesController {
    @Autowired
    public GithubRepositoriesProvider githubRepositoriesProvider;
    @Autowired
    public GithubCommonProvider githubCommonProvider;

    @GetMapping("/myreponsitories")
    public String index1(@RequestParam(name = "q") String q,
                         @RequestParam(name = "language") String language,
                         @RequestParam(name = "sort") String sort,
                         @RequestParam(name = "page") String page1,
                         @RequestParam(name = "order") String order,
                         HttpServletRequest request,
                         Model model) {

        // 因为这里后面可能判断是否有language 如果没有language 也没有q就会包错 不和issues一样 lable是必须的
        if("".equals(q)){
            q = "first";
        }
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

        String accessToken = null;
        Object obj = request.getSession().getAttribute("accessToken");
        if(obj!=null){
            accessToken = obj.toString();
        }

        String url = githubRepositoriesProvider.getUrl(q, language, sort, page, order);
        ArrayList<GithubRepositoriesDto> reponsitories = githubRepositoriesProvider.findReponsitories(url,accessToken);
        // if not login and the times is out to 404
        if(reponsitories==null){
            return "404";
        }
        int totalPage = PageRepositories.getPage();
        model.addAttribute("reponsitories", reponsitories);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("q", q);
        model.addAttribute("language", language);
        model.addAttribute("sort", sort);
        model.addAttribute("page", ""+page);
        model.addAttribute("order", order);
        String hasPre = null;
        if (page > 1) {
            hasPre = "/myreponsitories?q="+q+"&language="+language+"&sort="+sort+"&page="+(page-1)+"&order="+order;
        }
        String hasNext = null;
        if (page < totalPage) {
            hasNext = "/myreponsitories?q="+q+"&language="+language+"&sort="+sort+"&page="+(page+1)+"&order="+order;
        }
        model.addAttribute("hasPre",hasPre);
        model.addAttribute("hasNext",hasNext);

//        System.out.println("totlaPage"+totalPage);
        ArrayList<UrlsPages> returnUrlsPages = githubCommonProvider.getUrlList("myreponsitories",q, language, sort, order, page, totalPage);

        model.addAttribute("urlsPages",returnUrlsPages);
        return "myrepositories";
    }
}
