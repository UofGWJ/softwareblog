package com.swareblog.softwareblog.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.swareblog.softwareblog.dto.issues.UrlsPages;
import com.swareblog.softwareblog.dto.repositories.GithubRepositoriesDto;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class GithubCommonProvider {

    @Value("${per_page}")
    private String per_page;

    @Value("${per_page_detail}")
    private String per_page_detail;

    // 这个是所有的dealpagecount
    public int dealPageCount(JSONObject jsonobj) {
        int total_page = 0;
        int total_count =  Integer.parseInt(jsonobj.get("total_count").toString());
        if(total_count>=1000){
            total_page = 1000/Integer.parseInt(per_page);
        }
        else{
            total_page = total_count/Integer.parseInt(per_page)+1;
        }
        return total_page;
    }


    // 这个是repositoriesProvider
    public ArrayList<UrlsPages> getUrlList(String curl, String q, String language, String sort, String order, int page, int totalPage) {
        ArrayList<UrlsPages> urlPages = new ArrayList<>();
        if (totalPage > 10) {  // 如果大于10页的时候 做复杂的分页处理
            if(page<5){
                for(int i =1;i<11;i++){
//                   System.out.println(i);
                    UrlsPages u = new UrlsPages("/"+curl+"?q="+q+"&language="+language+"&sort="+sort+"&page="+i+"&order="+order,""+i);
                    urlPages.add(u);
                }
            }
            else if(totalPage-page<=6){
                for(int i=totalPage-10;i<=totalPage;i++){
                    UrlsPages u = new UrlsPages("/"+curl+"?q="+q+"&language="+language+"&sort="+sort+"&page="+i+"&order="+order,""+i);
                    urlPages.add(u);
                }
            }
            else{
                for(int i=page-4;i<=page+6;i++){
                    UrlsPages u = new UrlsPages("/"+curl+"?q="+q+"&language="+language+"&sort="+sort+"&page="+i+"&order="+order,""+i);
                    urlPages.add(u);
                }
            }
        }
        else{
            for(int i = 1;i<totalPage+1;i++){
                UrlsPages u = new UrlsPages("/"+curl+"?q="+q+"&language="+language+"&sort="+sort+"&page="+i+"&order="+order,""+i);
                urlPages.add(u);
            }
        }
        System.out.println("size:"+urlPages.size());
        return urlPages;
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
        System.out.println("size:"+urlPages.size());
        return urlPages;
    }

    // 这个是repositoriesDetailProvider
    public int dealPageCountDetail(JSONObject jsonobj) {
        int total_page = 0;
        int total_count =  Integer.parseInt(jsonobj.get("total_count").toString());
        if(total_count>=1000){
            total_page = 1000/Integer.parseInt(per_page_detail);
        }
        else{
            total_page = total_count/Integer.parseInt(per_page_detail)+1;
        }
        return total_page;
    }



    // detail count
    public ArrayList<UrlsPages> getUrlList1(String curl, String q, int page, int totalPage) {
        ArrayList<UrlsPages> urlPages = new ArrayList<>();
        if (totalPage > 10) {  // 如果大于10页的时候 做复杂的分页处理
            if(page<5){
                for(int i =1;i<11;i++){
//                   System.out.println(i);
                    UrlsPages u = new UrlsPages("/"+curl+"?q="+q+"&page="+i,""+i);
                    urlPages.add(u);
                }
            }
            else if(totalPage-page<=6){
                for(int i=totalPage-10;i<=totalPage;i++){
                    UrlsPages u = new UrlsPages("/"+curl+"?q="+q+"&page="+i,""+i);
                    urlPages.add(u);
                }
            }
            else{
                for(int i=page-4;i<=page+6;i++){
                    UrlsPages u = new UrlsPages("/"+curl+"?q="+q+"&page="+i,""+i);
                    urlPages.add(u);
                }
            }
        }
        else{
            for(int i = 1;i<totalPage+1;i++){
                UrlsPages u = new UrlsPages("/"+curl+"?q="+q+"&page="+i,""+i);
                urlPages.add(u);
            }
        }
        System.out.println("size:"+urlPages.size());
        return urlPages;
    }


    public ArrayList<GithubRepositoriesDto> getGithubRepositoriesDtos(JSONArray jsonArray) {
        if(jsonArray==null){
            return null;
        }
        ArrayList<GithubRepositoriesDto> githubIssueDtos = new ArrayList<>();

        for (int i=0; i<jsonArray.size(); i++) {
            GithubRepositoriesDto githubRepositoriesDto = JSON.parseObject(jsonArray.get(i).toString(), GithubRepositoriesDto.class);

            githubIssueDtos.add(githubRepositoriesDto);
        }
        return githubIssueDtos;
    }



    // request action
    public Request getRequest(String url,String accessToken){
        if(accessToken !=null){
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Accept", "application/vnd.github.v3+json")
                    .addHeader("Authorization", "token "+accessToken)
                    .build();
            return request;
        }
        else{
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Accept", "application/vnd.github.v3+json")
                    .build();
            return request;
        }

    }
}
