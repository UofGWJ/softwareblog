package com.swareblog.softwareblog.dto.issues;

public class PageIssues {
    private static int page;

    public static int getPage() {
        return page;
    }

    public  void setPage(int page) {
        this.page = page;
    }
}
