package com.swareblog.softwareblog.dto.Issues;

public class Page{
    private static int page;

    public static int getPage() {
        return page;
    }

    public  void setPage(int page) {
        this.page = page;
    }
}
