package com.swareblog.softwareblog.dao;

import com.swareblog.softwareblog.vo.Issue;

import java.util.List;


public interface IssueDao{

    void insertIssue(Issue issue);

    List<Issue> findIssueList(String username);
}
