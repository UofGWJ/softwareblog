package com.swareblog.softwareblog.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.swareblog.softwareblog.dto.GithubOwner;
import com.swareblog.softwareblog.dto.issues.GithubIssueDto;
import com.swareblog.softwareblog.dto.issues.PageIssuesDetail;
import com.swareblog.softwareblog.dto.issues.StackOverFlowIssueDto;
import com.swareblog.softwareblog.dto.issues.UrlsPages;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class IssuesDetailProvider {

    @Autowired
    private GithubCommonProvider githubCommonProvider;

    // 3. 通过前端传递来的issue html_url 访问得到当前issue的JSONObject对象
    public JSONObject findissues(String url, String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = githubCommonProvider.getRequest(url,accessToken);
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
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


    // 查询当前issue 的详细信息 return GithubIssueDto
    public GithubIssueDto findIssuesDetail(JSONObject jsonobj, String accessToken) {
        if (jsonobj == null) {
            return null;
        } else {
            GithubIssueDto githubIssueDto = JSON.parseObject(jsonobj.toString(), GithubIssueDto.class);
//            String url = repositoriesDetailDto.getTags_url();
//            OkHttpClient client = new OkHttpClient();
//            Request request = githubCommonProvider.getRequest(url,accessToken);
//            try (Response response = client.newCall(request).execute()) {
//                String string = response.body().string();
//                System.out.println(string);
//                if (!"[]".equals(string)) {
//                    JSONObject object = (JSONObject)JSONObject.parseArray(string).get(0);
//                    repositoriesDetailDto.setTags_url(object.getString("zipball_url"));
//                }else{
//                    repositoriesDetailDto.setTags_url(null);
//                }
//
//            } catch (IOException e) {
//                repositoriesDetailDto.setTags_url(null);
//                e.printStackTrace();
//            }
            return githubIssueDto;
        }
    }


    // 根据url 查询推荐的issue return ArrayList<GithubIssueDto>
    public ArrayList<GithubIssueDto> findIsssuesDetails(String url, String accessToken) {
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
            PageIssuesDetail p = new PageIssuesDetail();
            p.setPage(githubCommonProvider.dealPageCountDetail(jsonobj));

            JSONArray jsonArray = jsonobj.getJSONArray("items");
            return githubCommonProvider.getGithubIssueDtos(jsonArray);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    // 4. 解析JSONObject 对象 得到Owner详细信息
    public GithubOwner findOwner(JSONObject jsonobj, String accessToken) {
        if(jsonobj.get("user")==null){
            return null;
        }
        String url = JSON.parseObject(jsonobj.get("user").toString()).get("url").toString();

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

    // 根据url 查询推荐的issue  获得ArrayList<UrlsPages>  页面链接 和 页数
    public ArrayList<UrlsPages> getUrlListDetail(String curl, String url, int page, int totalPage,String lables,String q) {
        ArrayList<UrlsPages> urlPages = new ArrayList<>();
        if (totalPage > 10) {  // 如果大于10页的时候 做复杂的分页处理
            if(page<5){
                for(int i =1;i<11;i++){
//                   System.out.println(i);
                    UrlsPages u = new UrlsPages("/"+curl+"?url="+url+"&page="+i+"&labels="+lables+"&q="+q,""+i);
                    urlPages.add(u);
                }
            }
            else if(totalPage-page<=6){
                for(int i=totalPage-10;i<=totalPage;i++){
                    UrlsPages u = new UrlsPages("/"+curl+"?url="+url+"&page="+i+"&labels="+lables+"&q="+q,""+i);
                    urlPages.add(u);
                }
            }
            else{
                for(int i=page-4;i<=page+6;i++){
                    UrlsPages u = new UrlsPages("/"+curl+"?url="+url+"&page="+i+"&labels="+lables+"&q="+q,""+i);
                    urlPages.add(u);
                }
            }
        }
        else{
            for(int i = 1;i<totalPage+1;i++){
                UrlsPages u = new UrlsPages("/"+curl+"?url="+url+"&page="+i+"&labels="+lables+"&q="+q,""+i);
                urlPages.add(u);
            }
        }
//        System.out.println("size:"+urlPages.size());
        return urlPages;
    }


    // 根据url 查询推荐的StackOverFlow return ArrayList<GithubIssueDto>
    public ArrayList<StackOverFlowIssueDto> findStackOverflowIssueDtos(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = githubCommonProvider.getRequest(url,null);
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
//            System.out.println(string);
            if(string == null){
                return null;
            }
            JSONObject jsonobj = JSON.parseObject(string);
            JSONArray jsonArray = jsonobj.getJSONArray("items");
            return getStackOverFlowDtos(jsonArray);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<StackOverFlowIssueDto> getStackOverFlowDtos( JSONArray jsonArray){
        ArrayList<StackOverFlowIssueDto> stackOverFlowIssues = new ArrayList<>();
        for (int i=0; i<jsonArray.size(); i++) {
            StackOverFlowIssueDto stackOverFlowIssueDto = JSON.parseObject(jsonArray.get(i).toString(), StackOverFlowIssueDto.class);
            stackOverFlowIssues.add(stackOverFlowIssueDto);
        }
        return stackOverFlowIssues;
    }
}
