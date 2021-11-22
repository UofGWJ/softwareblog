package com.swareblog.softwareblog.service.impl;

import com.swareblog.softwareblog.dao.IssueDao;
import com.swareblog.softwareblog.service.IssueService;
import com.swareblog.softwareblog.vo.Issue;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IssueImpl implements IssueService {
    @Resource
    private IssueDao issueDao;
    public void insertIssue(Issue issue){
        issueDao.insertIssue(issue);
    }


    public List<Issue> findIssueList(String username){
        List<Issue> issues = issueDao.findIssueList(username);
        return issues;
    }
}
