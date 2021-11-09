package com.swareblog.softwareblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LayoutController {
    @GetMapping("layout")
    public String index(HttpServletRequest request) {
        request.getSession().invalidate();
        return "index";
    }
}
