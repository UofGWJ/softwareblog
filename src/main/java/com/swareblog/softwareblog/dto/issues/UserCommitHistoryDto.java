package com.swareblog.softwareblog.dto.issues;

import java.util.Date;

public class UserCommitHistoryDto {
    private String html_url;
    private String url;
    private String title;
    private String body;
    private String labels;

    private Date created_at;

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        if ("[]".equals(labels)) {
            this.labels = "";
        } else {
            this.labels = labels;
        }
    }

    @Override
    public String toString() {
        return "UserCommitHistoryDto{" +
                "html_url='" + html_url + '\'' +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", labels='" + labels + '\'' +
                ", created_at=" + created_at +
                '}';
    }
}
