package com.swareblog.softwareblog.controller;


import com.swareblog.softwareblog.dto.GithubOwner;
import com.swareblog.softwareblog.provider.GithubCommonProvider;
import com.swareblog.softwareblog.service.ContributeDetailService;
import com.swareblog.softwareblog.service.IssueService;
import com.swareblog.softwareblog.vo.ContributeDetail;
import com.swareblog.softwareblog.vo.Issue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class AddContributeDetail {

    @Resource
    private ContributeDetailService contributeDetailService;

    @Autowired
    public GithubCommonProvider githubCommonProvider;


    @RequestMapping("/addcontributedetail")
    @ResponseBody
    public String addContributeDetail(String issuetitle,
                                      String html_url,
                                      String keyword,
                                      String programming,
                                      String sort,
                                      String order,
                                      String github,
                                      String stackoverflow,
                                      String commits,
                                      String type,
                                      HttpServletRequest request,
                                      Model model) throws ParseException {
        Object obj = request.getSession().getAttribute("user");
        if (obj != null) {
            String user = obj.toString();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
            Date time = df.parse(df.format(new Date())); // new Date()为获取当前系统时间
            ContributeDetail contributeDetail = new ContributeDetail(user, issuetitle, html_url, keyword, programming, sort, order, github, stackoverflow, commits, type, time);
            contributeDetailService.insertContribute(contributeDetail);
            System.out.println(contributeDetail);
            return "success";
        } else {
            return "fail";
        }

    }


    @GetMapping("/mycontribute")
    public String userIssueHistory(HttpServletRequest request,
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
            List<ContributeDetail> contributeDetails = contributeDetailService.findContributeDetailList(username );
            model.addAttribute("ContributeHistories", contributeDetails);
            return "mycontribute";
        }
        else{
            return "redirect:/";
        }

    }

}
