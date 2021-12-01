package com.swareblog.softwareblog.controller;

import com.swareblog.softwareblog.dto.GithubOwner;
import com.swareblog.softwareblog.dto.issues.UserCommitHistoryDto;
import com.swareblog.softwareblog.provider.GithubCommonProvider;
import com.swareblog.softwareblog.provider.UserCommitHistoryProvider;
import com.swareblog.softwareblog.vo.ContributeDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ShowCommintHistory {

    @Autowired // 自动装配 自动把已经写好的实例记载道容器里面
    private UserCommitHistoryProvider userCommitHistoryProvider;

    @Autowired
    public GithubCommonProvider githubCommonProvider;

    @GetMapping("/showcommithistory")
    public String userIssueHistory(HttpServletRequest request,
                                   Model model){
        try {
            Object obj = request.getSession().getAttribute("user");
            if (obj != null) {
                String username = obj.toString();
                if (username == null) {
                    return "404";
                }
                String accessToken = request.getSession().getAttribute("accessToken").toString();
                String url = "https://api.github.com/search/issues?q=is:issue+author:"+username;
                // if not login and the times is out to 404
                ArrayList<UserCommitHistoryDto> userCommitHistoryDtos = userCommitHistoryProvider.findUserCommitHistory(url,accessToken);
                model.addAttribute("UserCommitHistories", userCommitHistoryDtos);

                GithubOwner githubOwner = githubCommonProvider.gitGithubOwner(request.getSession().getAttribute("user_url").toString(), accessToken);
                // if not login and the times is out to 404
                if (githubOwner == null) {
                    return "404";
                }
                model.addAttribute("owner", githubOwner);
                return "mycommits";
            } else {
                return "redirect:/";
            }
        }
        catch(Exception ex){
            return "redirect:/";
        }
    }
}
