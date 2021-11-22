package com.swareblog.softwareblog.dao;


import com.swareblog.softwareblog.vo.Repository;

import java.util.List;

public interface RepositoryDao {
    void insertRepository(Repository repository);

    List<Repository> findRepositoryList(String username);
}
