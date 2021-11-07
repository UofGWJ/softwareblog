package com.swareblog.softwareblog.dto.Issues;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.ArrayList;
import java.util.Date;

public class GithubIssueDto {

    private String url;
    private String repository_url;
    private String comments_url;
    private String html_url;
    private String id;
    private String title;
    private ArrayList<String> labels;
    private String state;
    private String locked;
    private String comments;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+0")
    private Date created_at;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+0")
    private Date updated_at;
    private String body;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRepository_url() {
        return repository_url;
    }

    public void setRepository_url(String repository_url) {
        this.repository_url = repository_url;
    }

    public String getComments_url() {
        return comments_url;
    }

    public void setComments_url(String comments_url) {
        this.comments_url = comments_url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getLabels() {
        return labels;
    }

    public void setLabels(ArrayList<String> labels) {
        ArrayList<String> lables_temp = new ArrayList<>();
        for (int i = 0; i < labels.size(); i++) {
            Labels labels1 = JSON.parseObject(labels.get(i), Labels.class);
            if (!"good first issue".equals(labels1.getName()))
            {
                lables_temp.add(labels1.getName());
            }
        }
        this.labels = lables_temp;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "GithubIssueDto{" +
                "url='" + url + '\'' +
                ", repository_url='" + repository_url + '\'' +
                ", comments_url='" + comments_url + '\'' +
                ", html_url='" + html_url + '\'' +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", labels=" + labels +
                ", state='" + state + '\'' +
                ", locked='" + locked + '\'' +
                ", comments='" + comments + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", body='" + body + '\'' +
                '}';
    }
}

class Labels {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Labels{" +
                "name='" + name + '\'' +
                '}';
    }
}
