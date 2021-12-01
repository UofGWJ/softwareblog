package com.swareblog.softwareblog.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.swareblog.softwareblog.dto.issues.PageIssues;
import com.swareblog.softwareblog.dto.issues.UserCommitHistoryDto;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class UserCommitHistoryProvider {
    @Autowired
    private GithubCommonProvider githubCommonProvider;


    public ArrayList<UserCommitHistoryDto> findUserCommitHistory(String url,String accessToken){
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

            JSONArray jsonArray = jsonobj.getJSONArray("items");
            // 返回 ArrayList<GithubIssueDto>
            return githubCommonProvider.getUserCommitHistoryDto(jsonArray);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
