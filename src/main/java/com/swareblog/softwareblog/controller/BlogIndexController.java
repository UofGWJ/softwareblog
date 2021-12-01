package com.swareblog.softwareblog.controller;

import com.swareblog.softwareblog.service.ContributeDetailService;
import com.swareblog.softwareblog.vo.ContributeDetail;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class BlogIndexController {

    @Resource
    private ContributeDetailService contributeDetailService;

    @GetMapping("/")
    public String index(Model model) {
        try{
        List<ContributeDetail> contributeDetails = contributeDetailService.findContributeDetailListAll();
        int keyword_sum = 0;
        int programming_sum = 0;
        int p_sort_sum = 0;
        int p_order_sum = 0;
        int github_sum = 0;
        int stackoverflow_sum = 0;
        int count = contributeDetails.size();
        for(ContributeDetail c : contributeDetails){
            keyword_sum += Integer.parseInt(c.getKeyword());
            programming_sum += Integer.parseInt(c.getProgramming());
            p_sort_sum += Integer.parseInt(c.getP_sort());
            p_order_sum += Integer.parseInt(c.getP_order());
            github_sum += Integer.parseInt(c.getGithub());
            stackoverflow_sum += Integer.parseInt(c.getStackoverflow());
        }
        double keyword_mean = new BigDecimal((float)keyword_sum/count).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        double programming_mean = new BigDecimal((float)programming_sum/count).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        double p_sort_mean =  new BigDecimal((float)p_sort_sum/count).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        double p_order_mean =  new BigDecimal((float)p_order_sum/count).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        double github_mean =  new BigDecimal((float)github_sum/count).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        double stackoverflow_mean =  new BigDecimal((float)stackoverflow_sum/count).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        model.addAttribute("keyword",keyword_mean);
        model.addAttribute("programming",programming_mean);
        model.addAttribute("p_sort",p_sort_mean);
        model.addAttribute("p_order",p_order_mean);
        model.addAttribute("github",github_mean);
        model.addAttribute("stackoverflow",stackoverflow_mean);
            return "index";
        }
        catch(Exception ex){
            model.addAttribute("keyword",0);
            model.addAttribute("programming",0);
            model.addAttribute("p_sort",0);
            model.addAttribute("p_order",0);
            model.addAttribute("github",0);
            model.addAttribute("stackoverflow",0);
            return "index";
        }

    }
}
