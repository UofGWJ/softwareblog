package com.swareblog.softwareblog.dto.repositories;

public class PageRepositories {
    private static int page;

    public static int getPage() {
        return page;
    }

    public  void setPage(int page) {
        this.page = page;
    }
}
