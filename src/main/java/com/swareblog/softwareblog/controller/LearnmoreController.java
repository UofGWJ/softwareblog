package com.swareblog.softwareblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

@Controller
public class LearnmoreController {
    @GetMapping("learnmore")
    public String index(HttpServletRequest request) {
//        request.getSession().invalidate();
        return "learnmore";
    }
    @GetMapping("false")
    public String false_page(HttpServletRequest request) {
//        request.getSession().invalidate();
        return "404";
    }

}
