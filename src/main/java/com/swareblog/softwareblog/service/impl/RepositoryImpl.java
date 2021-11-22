package com.swareblog.softwareblog.service.impl;

import com.swareblog.softwareblog.dao.RepositoryDao;
import com.swareblog.softwareblog.service.RepositoryService;
import com.swareblog.softwareblog.vo.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RepositoryImpl implements RepositoryService {
    @Resource
    private RepositoryDao repositoryDao;
    public void insertRepository(Repository repository){
        repositoryDao.insertRepository(repository);
    }

    public List<Repository> findRepositoryList(String username){
        return repositoryDao.findRepositoryList(username);
    }
}
