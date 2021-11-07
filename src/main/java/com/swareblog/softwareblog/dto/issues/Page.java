package com.swareblog.softwareblog.dto.issues;

public class Page{
    private static int page;

    public static int getPage() {
        return page;
    }

    public  void setPage(int page) {
        this.page = page;
    }
}
