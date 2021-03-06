package com.swareblog.softwareblog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@MapperScan("com.swareblog.softwareblog.dao")
@SpringBootApplication
public class SoftwareblogApplication  {

    public static void main(String[] args) {
        SpringApplication.run(SoftwareblogApplication.class, args);
    }

}
