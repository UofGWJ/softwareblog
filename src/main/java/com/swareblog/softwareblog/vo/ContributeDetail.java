package com.swareblog.softwareblog.vo;

import java.util.Date;

public class ContributeDetail {
    private int id;
    private String user;
    private String issuetitle;
    private String html_url;
    private String keyword;
    private String programming;
    private String p_sort;
    private String p_order;
    private String github;
    private String stackoverflow;
    private String commits;
    private String type;
    private Date time;

    public ContributeDetail() {
    }

    public ContributeDetail(String user, String issuetitle, String html_url, String keyword, String programming, String p_sort, String p_order, String github, String stackoverflow, String commits, String type, Date time) {
        this.user = user;
        this.issuetitle = issuetitle;
        this.html_url = html_url;
        this.keyword = keyword;
        this.programming = programming;
        this.p_sort = p_sort;
        this.p_order = p_order;
        this.github = github;
        this.stackoverflow = stackoverflow;
        this.commits = commits;
        this.type = type;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getIssuetitle() {
        return issuetitle;
    }

    public void setIssuetitle(String issuetitle) {
        this.issuetitle = issuetitle;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getProgramming() {
        return programming;
    }

    public void setProgramming(String programming) {
        this.programming = programming;
    }


    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getStackoverflow() {
        return stackoverflow;
    }

    public void setStackoverflow(String stackoverflow) {
        this.stackoverflow = stackoverflow;
    }

    public String getCommits() {
        return commits;
    }

    public void setCommits(String commits) {
        this.commits = commits;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getP_sort() {
        return p_sort;
    }

    public void setP_sort(String p_sort) {
        this.p_sort = p_sort;
    }

    public String getP_order() {
        return p_order;
    }

    public void setP_order(String p_order) {
        this.p_order = p_order;
    }

    @Override
    public String toString() {
        return "ContributeDetail{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", issuetitle='" + issuetitle + '\'' +
                ", html_url='" + html_url + '\'' +
                ", keyword='" + keyword + '\'' +
                ", programming='" + programming + '\'' +
                ", p_sort='" + p_sort + '\'' +
                ", p_order='" + p_order + '\'' +
                ", github='" + github + '\'' +
                ", stackoverflow='" + stackoverflow + '\'' +
                ", commits='" + commits + '\'' +
                ", type='" + type + '\'' +
                ", time=" + time +
                '}';
    }
}
