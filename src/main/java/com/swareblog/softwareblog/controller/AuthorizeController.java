package com.swareblog.softwareblog.controller;

import com.swareblog.softwareblog.dto.AccessTokenDto;
import com.swareblog.softwareblog.dto.GithubUser;
import com.swareblog.softwareblog.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired // 自动装配 自动把已经写好的实例记载道容器里面
    private GithubProvider gitHubProvider;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state
                           ){
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_id("d32754e05783c716a028");
        accessTokenDto.setClient_secret("860fe7f776640cfc3f932a5bf58f3e50bc2ec142");
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_url("http://localhost:8080/callback");
        accessTokenDto.setState(state);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDto);
        GithubUser getuser = gitHubProvider.getuser(accessToken);
        System.out.println(getuser.toString());
        return "index";
    }
}
