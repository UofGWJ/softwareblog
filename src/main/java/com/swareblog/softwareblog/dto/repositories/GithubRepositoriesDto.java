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

}
