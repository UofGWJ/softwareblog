package com.swareblog.softwareblog.provider;

import com.alibaba.fastjson.JSON;
import com.swareblog.softwareblog.dto.AccessTokenDto;
import com.swareblog.softwareblog.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component // IOC  加上这个注解就不需要去 new对象  这个对象直接被调用就行 和Autowired配合使用
public class GithubProvider {
    public String getAccessToken(AccessTokenDto accessTokenDto){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDto));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String tokenstr = string.split("&")[0];
            String token = tokenstr.split("=")[1];
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public GithubUser getuser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
//                .url("https://api.github.com/user?access_token="+accessToken+"&scope=repo%2Cgist&token_type=bearer")
//                .url("https://api.github.com/user/repos") // 这个是获取到项目的信息
                .url("https://api.github.com/user")  //  这个是获取到项目用户的信息
                .addHeader("Authorization", "token "+accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
//            System.out.println(string);
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
