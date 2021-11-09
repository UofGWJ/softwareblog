package com.swareblog.softwareblog.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.swareblog.softwareblog.dto.issues.PageIssues;
import com.swareblog.softwareblog.dto.topics.GithubTopicsDto;
import com.swareblog.softwareblog.dto.topics.PageTopics;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class GithubTopicsProvider {

    @Value("${per_page}")
    private String per_page;

    @Autowired
    private GithubCommonProvider githubCommonProvider;

    public ArrayList<GithubTopicsDto> findTopics(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/vnd.github.v3+json")
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            if(string == null){
                return null;
            }
            JSONObject jsonobj = JSON.parseObject(string);
            if(jsonobj.get("total_count")==null){
                return null;
            }
            PageTopics p = new PageTopics();
            p.setPage(githubCommonProvider.dealPageCount(jsonobj));
            JSONArray jsonArray = jsonobj.getJSONArray("items");
            return getGithubTopicsDtos(jsonArray);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private ArrayList<GithubTopicsDto> getGithubTopicsDtos(JSONArray jsonArray) {

        ArrayList<GithubTopicsDto> githubTopicsDtos = new ArrayList<>();

        for (int i=0; i<jsonArray.size(); i++) {
            GithubTopicsDto githubTopicsDto = JSON.parseObject(jsonArray.get(i).toString(), GithubTopicsDto.class);

            githubTopicsDtos.add(githubTopicsDto);
        }
        return githubTopicsDtos;
    }


    public String getUrl(String q,int page){
        String url = "https://api.github.com/search/topics?q=" +q;

        url = url + "&per_page="+per_page + "&page="+page;
//        System.out.println(url);
        return url;
    }

}
