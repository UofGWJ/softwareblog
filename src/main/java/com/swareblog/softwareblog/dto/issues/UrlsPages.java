package com.swareblog.softwareblog.dto.issues;

public class UrlsPages {
    private String urls;
    private String pages;

    public UrlsPages(String urls, String pages) {
        this.urls = urls;
        this.pages = pages;
    }

    @Override
    public String toString() {
        return "UrlsPages{" +
                "urls='" + urls + '\'' +
                ", pages='" + pages + '\'' +
                '}';
    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }
}
