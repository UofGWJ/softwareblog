package com.swareblog.softwareblog.controller;

import com.swareblog.softwareblog.dto.GithubOwner;
import com.swareblog.softwareblog.provider.GithubCommonProvider;
import com.swareblog.softwareblog.service.IssueService;
import com.swareblog.softwareblog.service.RepositoryService;
import com.swareblog.softwareblog.vo.Issue;
import com.swareblog.softwareblog.vo.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UserRepositoryHistory {
    @Autowired
    public GithubCommonProvider githubCommonProvider;

    @Resource
    private RepositoryService repositoryService;

    @GetMapping("/repositoryhistory")
    public String userRepositoryHistory(HttpServletRequest request,
                                   Model model){

        Object obj = request.getSession().getAttribute("user_url");
        if(obj!=null){
            Object obja = request.getSession().getAttribute("accessToken");
            GithubOwner githubOwner = githubCommonProvider.gitGithubOwner(obj.toString(),obja.toString());
            // if not login and the times is out to 404
            if (githubOwner == null) {
                return "404";
            }
            model.addAttribute("owner", githubOwner);
            Object obju = request.getSession().getAttribute("user");
            String username = obju.toString();
            List<Repository> issueList = repositoryService.findRepositoryList(username);
            model.addAttribute("RepositoryHistories", issueList);
            return "repositoryhistory";
        }
        else{
            return "redirect:/";
        }

    }
}
