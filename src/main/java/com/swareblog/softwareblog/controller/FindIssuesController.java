package com.swareblog.softwareblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FindIssuesController {
    @GetMapping("/myissues")
    public String index() {
        return "myissues";
    }
}
