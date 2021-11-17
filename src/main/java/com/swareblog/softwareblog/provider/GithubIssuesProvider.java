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

    /*
    访问url 查询Issues
    1. 利用公共Provider对象获得 访问链接对象 return request
    2. 得到response对象
    3. 设置当前为第几页   dealPageCount 为计算页码信息 记录totalpage  githubCommonProvider
    return  ArrayList<GithubIssueDto>
    * */
    public ArrayList<GithubIssueDto> findIssues(String url,String accessToken) {
        OkHttpClient client = new OkHttpClient();

        // 1. 利用公共Provider对象获得 访问链接对象 return request
        Request request = githubCommonProvider.getRequest(url,accessToken);
        // 2. 利用request对象 访问得到response对象
        try (Response response = client.newCall(request).execute()) {
            // 解析返回对象
            String string = response.body().string();
            if(string == null){
                return null;
            }
            // 如果返回为空 则total_count不会有值 直接返回null
            JSONObject jsonobj = JSON.parseObject(string);
            if(jsonobj.get("total_count")==null){
                return null;
            }
            // 设置当前为第几页
            PageIssues p = new PageIssues();
            // dealPageCount 为计算页码信息 记录totalpage
            p.setPage(githubCommonProvider.dealPageCount(jsonobj));
            JSONArray jsonArray = jsonobj.getJSONArray("items");
            // 返回 ArrayList<GithubIssueDto>
            return githubCommonProvider.getGithubIssueDtos(jsonArray);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    // 按照传入属性 拼装url
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

