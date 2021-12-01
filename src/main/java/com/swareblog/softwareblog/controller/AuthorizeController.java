package com.swareblog.softwareblog.controller;

import com.swareblog.softwareblog.dto.AccessTokenDto;
import com.swareblog.softwareblog.dto.GithubUser;
import com.swareblog.softwareblog.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthorizeController {

    @Autowired // 自动装配 自动把已经写好的实例记载道容器里面
    private GithubProvider gitHubProvider;

    @Value("${github.client_id}")
    private String client_id;

    @Value("${github.client_secret}")
    private String client_secret;

    @Value("${github.redirect_url}")
    private String redirect_url;

//    @GetMapping("/callback")
    @RequestMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state, Model model,
                           HttpServletRequest request
                           ){
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_id(client_id);
        accessTokenDto.setClient_secret(client_secret);
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_url(redirect_url);
        accessTokenDto.setState(state);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDto);
        GithubUser getuser = gitHubProvider.getuser(accessToken);
//        System.out.println(getuser.toString());
//        model.addAttribute("username",getuser.getLogin());
        if(getuser != null){
            // login success
            request.getSession().setAttribute("user", getuser.getLogin());
            request.getSession().setAttribute("user_url", getuser.getUrl());
//            System.out.println(getuser.toString());
            request.getSession().setAttribute("accessToken", accessToken);
            return "redirect:/";
        } else{
            // login fail
            return "404";
        }
    }
}
