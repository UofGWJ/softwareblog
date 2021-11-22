package com.swareblog.softwareblog.service;

import com.swareblog.softwareblog.vo.Repository;

import java.util.List;

public interface RepositoryService {
    void insertRepository(Repository repository);

    List<Repository> findRepositoryList(String username);
}
