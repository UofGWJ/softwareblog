package com.swareblog.softwareblog.service;


import com.swareblog.softwareblog.vo.ContributeDetail;

import java.util.List;


public interface ContributeDetailService {
    void insertContribute(ContributeDetail contributeDetail);
    List<ContributeDetail> findContributeDetailList(String username);
    List<ContributeDetail> findContributeDetailListAll();
    int getContribute(String username,String issuetitle,String type);
}