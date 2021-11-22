package com.swareblog.softwareblog.service.impl;

import com.swareblog.softwareblog.dao.ContributeDetailDao;
import com.swareblog.softwareblog.dao.IssueDao;
import com.swareblog.softwareblog.service.ContributeDetailService;
import com.swareblog.softwareblog.vo.ContributeDetail;
import com.swareblog.softwareblog.vo.Issue;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ContributeDetailImpl implements ContributeDetailService {
    @Resource
    private ContributeDetailDao contributeDetailDao;

    public void insertContribute(ContributeDetail contributeDetail) {
        contributeDetailDao.insertContribute(contributeDetail);
    }

    public List<ContributeDetail> findContributeDetailList(String username){
        List<ContributeDetail> contributeDetails = contributeDetailDao.findContributeDetailList(username);
        return contributeDetails;
    }
    public List<ContributeDetail> findContributeDetailListAll(){
        List<ContributeDetail> contributeDetails = contributeDetailDao.findContributeDetailListAll();
        return contributeDetails;
    }

    public  int getContribute(String username,String issuetitle,String type){
        return contributeDetailDao.getContribute(username,issuetitle,type);
    }
}

