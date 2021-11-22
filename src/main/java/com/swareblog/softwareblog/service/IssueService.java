package com.swareblog.softwareblog.service;

import com.swareblog.softwareblog.vo.Issue;

import java.util.List;

public interface IssueService {
    void insertIssue(Issue issue);
    List<Issue> findIssueList(String username);
}
