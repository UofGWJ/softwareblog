package com.swareblog.softwareblog.dto.issues;

public class StackOverFlowIssueDto {
    private String link;
    private String title;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "StackOverflowIssueDto{" +
                "link='" + link + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
