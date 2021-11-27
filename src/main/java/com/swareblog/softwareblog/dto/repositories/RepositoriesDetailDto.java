package com.swareblog.softwareblog.dto.repositories;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.rjeschke.txtmark.Processor;

import java.util.Date;

public class RepositoriesDetailDto {
    private String name;
    private String full_name;
    private String html_url;
    private String description;
    private String tags_url;  // 可以下载这个压缩包
    private String contributors_url;  // 项目组里面的人
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+0")
    private Date created_at;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+0")
    private Date updated_at;
    private String git_url;
    private String ssh_url;
    private String clone_url;
    private String size;
    private String watchers_count;
    private String language;
    private String subscribers_count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
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
        this.description = Processor.process(description);
    }

    public String getTags_url() {
        return tags_url;
    }

    public void setTags_url(String tags_url) {
        this.tags_url = tags_url;
    }

    public String getContributors_url() {
        return contributors_url;
    }

    public void setContributors_url(String contributors_url) {
        this.contributors_url = contributors_url;
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

    public String getGit_url() {
        return git_url;
    }

    public void setGit_url(String git_url) {
        this.git_url = git_url;
    }

    public String getSsh_url() {
        return ssh_url;
    }

    public void setSsh_url(String ssh_url) {
        this.ssh_url = ssh_url;
    }

    public String getClone_url() {
        return clone_url;
    }

    public void setClone_url(String clone_url) {
        this.clone_url = clone_url;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getWatchers_count() {
        return watchers_count;
    }

    public void setWatchers_count(String watchers_count) {
        this.watchers_count = watchers_count;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSubscribers_count() {
        return subscribers_count;
    }

    public void setSubscribers_count(String subscribers_count) {
        this.subscribers_count = subscribers_count;
    }

    @Override
    public String toString() {
        return "RepositoriesDetailDto{" +
                "name='" + name + '\'' +
                ", full_name='" + full_name + '\'' +
                ", html_url='" + html_url + '\'' +
                ", description='" + description + '\'' +
                ", tags_url='" + tags_url + '\'' +
                ", contributors_url='" + contributors_url + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", git_url='" + git_url + '\'' +
                ", ssh_url='" + ssh_url + '\'' +
                ", clone_url='" + clone_url + '\'' +
                ", size='" + size + '\'' +
                ", watchers_count='" + watchers_count + '\'' +
                ", language='" + language + '\'' +
                ", subscribers_count='" + subscribers_count + '\'' +
                '}';
    }
}

