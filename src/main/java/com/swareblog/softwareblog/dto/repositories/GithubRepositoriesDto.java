package com.swareblog.softwareblog.dto.repositories;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class GithubRepositoriesDto {
    private String id;
    private String name;
    private String html_url;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+0")
    private Date created_at;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+0")
    private Date updated_at;
    private String watchers;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getWatchers() {
        return watchers;
    }

    public void setWatchers(String watchers) {
        this.watchers = watchers;
    }

    @Override
    public String toString() {
        return "GithubRepositoriesDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", html_url='" + html_url + '\'' +
                ", description='" + description + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", watchers='" + watchers + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
