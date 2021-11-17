package com.swareblog.softwareblog.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.swareblog.softwareblog.dto.GithubOwner;
import com.swareblog.softwareblog.dto.issues.UrlsPages;
import com.swareblog.softwareblog.dto.repositories.GithubRepositoriesDto;
import com.swareblog.softwareblog.dto.repositories.PageRepositoriesDetail;
import com.swareblog.softwareblog.dto.repositories.RepositoriesDetailDto;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class RepositoriesDetailProvider {

    @Autowired
    private GithubCommonProvider githubCommonProvider;

    public JSONObject findRepositories(String url,String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = githubCommonProvider.getRequest(url,accessToken);

        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
//            System.out.println(string);
            if (string == null) {
                return null;
            }
            JSONObject jsonobj = JSON.parseObject(string);

            return jsonobj;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public RepositoriesDetailDto findRepositoriesDetail(JSONObject jsonobj,String accessToken) {
        if (jsonobj == null) {
            return null;
        } else {
            RepositoriesDetailDto repositoriesDetailDto = JSON.parseObject(jsonobj.toString(), RepositoriesDetailDto.class);
            String url = repositoriesDetailDto.getTags_url();
            OkHttpClient client = new OkHttpClient();
            Request request = githubCommonProvider.getRequest(url,accessToken);
            try (Response response = client.newCall(request).execute()) {
                String string = response.body().string();
                System.out.println(string);
                if (!"[]".equals(string)) {
                    JSONObject object = (JSONObject)JSONObject.parseArray(string).get(0);
                    repositoriesDetailDto.setTags_url(object.getString("zipball_url"));
                }else{
                    repositoriesDetailDto.setTags_url(null);
                }

            } catch (IOException e) {
                repositoriesDetailDto.setTags_url(null);
                e.printStackTrace();
            }
            return repositoriesDetailDto;
        }
    }

    public GithubOwner findOwner(JSONObject jsonobj, String accessToken) {
        if(jsonobj.get("owner")==null){
            return null;
        }
        String url = JSON.parseObject(jsonobj.get("owner").toString()).get("url").toString();

        OkHttpClient client = new OkHttpClient();
        Request request = githubCommonProvider.getRequest(url,accessToken);
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            if (string == null) {
                return null;
            }
            GithubOwner githubOwner = JSON.parseObject(string, GithubOwner.class);

            return githubOwner;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

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
            PageRepositoriesDetail p = new PageRepositoriesDetail();
            p.setPage(githubCommonProvider.dealPageCountDetail(jsonobj));

            JSONArray jsonArray = jsonobj.getJSONArray("items");
            return githubCommonProvider.getGithubRepositoriesDtos(jsonArray);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    // 这个是repositoriesDetailProvider
    public ArrayList<UrlsPages> getUrlListDetail(String curl, String url, int page, int totalPage) {
        ArrayList<UrlsPages> urlPages = new ArrayList<>();
        if (totalPage > 10) {  // 如果大于10页的时候 做复杂的分页处理
            if(page<5){
                for(int i =1;i<11;i++){
//                   System.out.println(i);
                    UrlsPages u = new UrlsPages("/"+curl+"?url="+url+"&page="+i,""+i);
                    urlPages.add(u);
                }
            }
            else if(totalPage-page<=6){
                for(int i=totalPage-10;i<=totalPage;i++){
                    UrlsPages u = new UrlsPages("/"+curl+"?url="+url+"&page="+i,""+i);
                    urlPages.add(u);
                }
            }
            else{
                for(int i=page-4;i<=page+6;i++){
                    UrlsPages u = new UrlsPages("/"+curl+"?url="+url+"&page="+i,""+i);
                    urlPages.add(u);
                }
            }
        }
        else{
            for(int i = 1;i<totalPage+1;i++){
                UrlsPages u = new UrlsPages("/"+curl+"?url="+url+"&page="+i,""+i);
                urlPages.add(u);
            }
        }
//        System.out.println("size:"+urlPages.size());
        return urlPages;
    }


}
