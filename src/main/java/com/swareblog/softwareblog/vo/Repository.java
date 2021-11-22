package com.swareblog.softwareblog.vo;

import java.util.Date;

public class Repository {
    private int id;
    private String userid;
    private String html_url;
    private String title;
    private String language;
    private String q;
    private Date time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Repository{" +
                "id=" + id +
                ", userid='" + userid + '\'' +
                ", html_url='" + html_url + '\'' +
                ", title='" + title + '\'' +
                ", language='" + language + '\'' +
                ", q='" + q + '\'' +
                ", time=" + time +
                '}';
    }
}
