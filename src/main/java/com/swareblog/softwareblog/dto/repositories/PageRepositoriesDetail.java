package com.swareblog.softwareblog.dto.repositories;

public class PageRepositoriesDetail {
    private static int page;

    public static int getPage() {
        return page;
    }

    public  void setPage(int page) {
        this.page = page;
    }
}
