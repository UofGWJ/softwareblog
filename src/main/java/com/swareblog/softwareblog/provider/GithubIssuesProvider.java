package com.swareblog.softwareblog.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.swareblog.softwareblog.dto.issues.GithubIssueDto;
import com.swareblog.softwareblog.dto.issues.PageIssues;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class GithubIssuesProvider {


    @Value("${per_page}")
    private String per_page;

    @Autowired
    private GithubCommonProvider githubCommonProvider;

    public ArrayList<GithubIssueDto> findIssues(String url,String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = githubCommonProvider.getRequest(url,accessToken);
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            if(string == null){
                return null;
            }
            JSONObject jsonobj = JSON.parseObject(string);
            if(jsonobj.get("total_count")==null){
                return null;
            }
            PageIssues p = new PageIssues();
            p.setPage(githubCommonProvider.dealPageCount(jsonobj));
            JSONArray jsonArray = jsonobj.getJSONArray("items");
//            System.out.println(jsonArray);
            return githubCommonProvider.getGithubIssueDtos(jsonArray);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getUrl(String q, String language, String sort,int page, String order){
        String url = "https://api.github.com/search/issues?q=" +q;
        url = url+"+label:%22good%20first%20issue%22,good-first-issue,goodfirstissue+state:open";
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
//        System.out.println(url);
        return url;
    }

}

