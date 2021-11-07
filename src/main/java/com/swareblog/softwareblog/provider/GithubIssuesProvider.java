package com.swareblog.softwareblog.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.swareblog.softwareblog.dto.Issues.GithubIssueDto;
import com.swareblog.softwareblog.dto.Issues.Page;
import com.swareblog.softwareblog.dto.Issues.UrlsPages;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@Component
public class GithubIssuesProvider {


    @Value("${per_page}")
    private String per_page;

    @Autowired
    private GithubCommonProvider githubCommonProvider;

    public ArrayList<GithubIssueDto> findIssues(String url) {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        HashMap<String,String> b = new HashMap<>();
//        b["spoken_language"] =
        String obj = "spoken_language:zh";
//        HashMap<String,String> map = new HashMap<>();
//        map.put("spoken_language","zh");
        Test t = new Test();
        t.setSpoken_language_code("zh");
        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(t));
        System.out.println(body);
//        RequestBody body = null;
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/vnd.github.v3+json")
//                .post(body)
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
            Page p = new Page();
            p.setPage(githubCommonProvider.dealPageCount(jsonobj));
            JSONArray jsonArray = jsonobj.getJSONArray("items");
            System.out.println(jsonArray);
            return getGithubIssueDtos(jsonArray);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private ArrayList<GithubIssueDto> getGithubIssueDtos(JSONArray jsonArray) {

        ArrayList<GithubIssueDto> githubIssueDtos = new ArrayList<>();

        for (int i=0; i<jsonArray.size(); i++) {
            GithubIssueDto githubIssueDto = JSON.parseObject(jsonArray.get(i).toString(), GithubIssueDto.class);

            githubIssueDtos.add(githubIssueDto);
        }
        return githubIssueDtos;
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
        System.out.println(url);
        return url;
    }

}
class Test{
    public String spoken_language_code;

    public String getSpoken_language_code() {
        return spoken_language_code;
    }

    public void setSpoken_language_code(String spoken_language_code) {
        this.spoken_language_code = spoken_language_code;
    }
}
