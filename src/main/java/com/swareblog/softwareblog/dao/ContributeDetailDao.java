package com.swareblog.softwareblog.dao;

import com.swareblog.softwareblog.vo.ContributeDetail;
import com.swareblog.softwareblog.vo.Issue;

import java.util.List;

public interface ContributeDetailDao {
    void insertContribute(ContributeDetail contributeDetail);

    List<ContributeDetail> findContributeDetailList(String username);

    List<ContributeDetail> findContributeDetailListAll();

    int getContribute(String username,String issuetitle,String type);
}
