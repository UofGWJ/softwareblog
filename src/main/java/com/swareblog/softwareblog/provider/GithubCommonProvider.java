package com.swareblog.softwareblog.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.swareblog.softwareblog.dto.GithubOwner;
import com.swareblog.softwareblog.dto.issues.GithubIssueDto;
import com.swareblog.softwareblog.dto.issues.UrlsPages;
import com.swareblog.softwareblog.dto.issues.UserCommitHistoryDto;
import com.swareblog.softwareblog.dto.repositories.GithubRepositoriesDto;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class GithubCommonProvider {

    @Value("${per_page}")
    private String per_page;

    @Value("${per_page_detail}")
    private String per_page_detail;

    /*
    计算总页数
    处理页面页数 计算出一共有多少页， 因为插叙的最大条数为1000
    所以如果条数大于1000 则总页数为1000/per_page
    否则 用 总条数/per_page
    return total_page
    **/

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


    // 获取页面链接和页数 return ArrayList<UrlsPages>   FindIssuesController在用
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
//        System.out.println("size:"+urlPages.size());
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



    // 访问url 获得request对象
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



    // 解析JSONArray 返回ArrayList<GithubIssueDto>
    public ArrayList<GithubIssueDto> getGithubIssueDtos(JSONArray jsonArray) {

        ArrayList<GithubIssueDto> githubIssueDtos = new ArrayList<>();

        for (int i=0; i<jsonArray.size(); i++) {
            GithubIssueDto githubIssueDto = JSON.parseObject(jsonArray.get(i).toString(), GithubIssueDto.class);

            githubIssueDtos.add(githubIssueDto);
        }
        return githubIssueDtos;
    }


    // find User 在history中显示user的基本信息
    public GithubOwner gitGithubOwner(String html_url,String accessToken){
//        System.out.println(html_url);
        OkHttpClient client = new OkHttpClient();
        Request request = getRequest(html_url,accessToken);
//        System.out.println(request);
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

    // 解析JSONArray 返回ArrayList<UserCommitDto>
    public ArrayList<UserCommitHistoryDto> getUserCommitHistoryDto(JSONArray jsonArray) {
        ArrayList<UserCommitHistoryDto> userCommitHistoryDtos = new ArrayList<>();
        for (int i=0; i<jsonArray.size(); i++) {
            UserCommitHistoryDto userCommitHistoryDto = JSON.parseObject(jsonArray.get(i).toString(), UserCommitHistoryDto.class);
            System.out.println(userCommitHistoryDto);
            userCommitHistoryDtos.add(userCommitHistoryDto);
        }
        return userCommitHistoryDtos;
    }


}
