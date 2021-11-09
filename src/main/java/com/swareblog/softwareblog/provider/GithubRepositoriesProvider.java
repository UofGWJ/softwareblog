package com.swareblog.softwareblog.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.swareblog.softwareblog.dto.issues.Page;
import com.swareblog.softwareblog.dto.repositories.GithubRepositoriesDto;
import com.swareblog.softwareblog.dto.repositories.PageRepositories;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class GithubRepositoriesProvider {

    @Value("${per_page}")
    private String per_page;

    @Autowired
    private GithubCommonProvider githubCommonProvider;

    public ArrayList<GithubRepositoriesDto> findReponsitories(String url,String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = githubCommonProvider.getRequest(url,accessToken);
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
//            System.out.println(string);
            if(string == null){
                return null;
            }
            JSONObject jsonobj = JSON.parseObject(string);
            if(jsonobj.get("total_count")==null){
                return null;
            }
            PageRepositories p = new PageRepositories();
            p.setPage(githubCommonProvider.dealPageCount(jsonobj));
            JSONArray jsonArray = jsonobj.getJSONArray("items");
            return githubCommonProvider.getGithubRepositoriesDtos(jsonArray);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }




    public String getUrl(String q, String language, String sort,int page, String order){
        String url = "https://api.github.com/search/repositories?q=" +q;
//        url = url+"+state:open";

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
