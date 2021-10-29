package com.swareblog.softwareblog.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.swareblog.softwareblog.dto.Issues.GithubIssueDto;
import com.swareblog.softwareblog.dto.Issues.Page;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class GithubIssuesProvider {


    @Value("${per_page}")
    private String per_page;
    public ArrayList<GithubIssueDto> findIssues(String url) {
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
            p.setPage(dealPageCount(jsonobj));
            JSONArray jsonArray = jsonobj.getJSONArray("items");
            return getGithubIssueDtos(jsonArray);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int dealPageCount(JSONObject jsonobj) {
        int total_page = 0;
        int total_count =  Integer.parseInt(jsonobj.get("total_count").toString());
        if(total_count>=1000){
            total_page = 1000/Integer.parseInt(per_page);
        }
        else{
            total_page = total_count/Integer.parseInt(per_page)+1;
        }
//
//        System.out.println(total_page);
//        System.out.println(total_count);
        return total_page;
//            System.out.println(jsonobj.get("incomplete_results"));
    }

    private ArrayList<GithubIssueDto> getGithubIssueDtos(JSONArray jsonArray) {

        ArrayList<GithubIssueDto> githubIssueDtos = new ArrayList<>();

        for (int i=0; i<jsonArray.size(); i++) {
//                System.out.println(jsonArray.get(i));

            GithubIssueDto githubIssueDto = JSON.parseObject(jsonArray.get(i).toString(), GithubIssueDto.class);

            githubIssueDtos.add(githubIssueDto);
//                System.out.println(githubIssueDto.toString());
        }
        return githubIssueDtos;
    }
}
