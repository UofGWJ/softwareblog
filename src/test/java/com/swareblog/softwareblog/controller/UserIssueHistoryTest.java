package com.swareblog.softwareblog.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserIssueHistoryTest {
    /**
     * 模拟mvc测试对象
     */
    private MockMvc mockMvc;

    /**
     * web项目上下文
     */
    @Autowired
    private WebApplicationContext webApplicationContext;

    /**
     * 所有测试方法执行之前执行该方法
     */
    @Before
    public void before() {
        //获取mockmvc对象实例
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    @Test
    public void userIssueHistory() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(// 1
                        MockMvcRequestBuilders.get("/issueshistory") // 2
                                .sessionAttr("user","UofGWJ")
                                .sessionAttr("user_url","https://api.github.com/users/UofGWJ")
                                .sessionAttr("accessToken","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")
                )
                .andReturn();// 4

        int status = mvcResult.getResponse().getStatus(); // 5
        Assert.assertEquals("False", 200, status); // 7
    }

    @Test
    public void userIssueHistory1() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(// 1
                        MockMvcRequestBuilders.get("/issueshistory") // 2
                )
                .andReturn();// 4

        int status = mvcResult.getResponse().getStatus(); // 5
        Assert.assertEquals("False", 302, status); // 7
    }

    @Test
    public void userIssueHistory2() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(// 1
                        MockMvcRequestBuilders.get("/issueshistory") // 2
                                .sessionAttr("user","UofGWJ1")
                                .sessionAttr("user_url","https://api.github.com/users/UofGWJ1")
                                .sessionAttr("accessToken","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1")
                )
                .andReturn();// 4

        int status = mvcResult.getResponse().getStatus(); // 5
        Assert.assertEquals("False", 200, status); // 7
    }

}