package com.swareblog.softwareblog.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.swareblog.softwareblog.dto.Issues.GithubIssueDto;
import com.swareblog.softwareblog.dto.Issues.Page;
import com.swareblog.softwareblog.dto.Issues.UrlsPages;
import com.swareblog.softwareblog.dto.repositories.GithubRepositoriesDto;
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

    public ArrayList<GithubRepositoriesDto> findReponsitories(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/vnd.github.v3+json")
                .build();
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
            Page p = new Page();
            p.setPage(githubCommonProvider.dealPageCount(jsonobj));
            JSONArray jsonArray = jsonobj.getJSONArray("items");
            return getGithubRepositoriesDtos(jsonArray);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private ArrayList<GithubRepositoriesDto> getGithubRepositoriesDtos(JSONArray jsonArray) {

        ArrayList<GithubRepositoriesDto> githubIssueDtos = new ArrayList<>();

        for (int i=0; i<jsonArray.size(); i++) {
            GithubRepositoriesDto githubRepositoriesDto = JSON.parseObject(jsonArray.get(i).toString(), GithubRepositoriesDto.class);

            githubIssueDtos.add(githubRepositoriesDto);
        }
        return githubIssueDtos;
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
