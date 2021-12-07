package com.swareblog.softwareblog.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddContributeDetailTest {

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
     * MockMvc
     */
    private MockHttpSession session;

    protected MockHttpSession getSession()
    {
        return session;
    }

    /**
     * 所有测试方法执行之前执行该方法
     */
    @Before
    public void before() {
        //获取mockmvc对象实例
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//        this.session =  (MockHttpSession)getLoginSession();
//        System.out.println(this.session.getAttribute("user"));
    }

    /**
     * 完成登录功能，返回当前登录会话
     *
     * @return HttpSession
     * @see
     */
//    private HttpSession getLoginSession()
//    {
//        String url = "https://github.com/login/oauth/authorize?client_id=d32754e05783c716a028&redirect_url=http://localhost:8080/callback&scope=user&state=1";
//        MvcResult result = null;
//        try
//        {
//            result = mockMvc
//                    .perform(// 1
//                            MockMvcRequestBuilders.get(url) // 2
//                    )
//                    .andReturn();// 4
//            System.out.println(result.getResponse().getContentAsString());
//            System.out.println(result.getResponse().getContentLength());
//            System.out.println(result.getResponse().getStatus());
////            MockHttpServletResponse response=mockMvc.perform(MockMvcRequestBuilders.get(url)
////                    .contentType(MediaType.APPLICATION_JSON_VALUE)
////            )
////                    .andDo(MockMvcResultHandlers.print())
////                    .andReturn()
////                    .getResponse();
////            System.out.println(response.getContentAsString());
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        return result.getRequest().getSession();
//    }

    @Test
    public void addContributeDetail() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(// 1
                        MockMvcRequestBuilders.get("/addcontributedetail") // 2
                        .param("issuetitle","Report files should include connection information")        // 3
                        .param("html_url","https://github.com/nipy/nipype/issues/380")        // 3
                        .param("keyword","")        // 3
                        .param("programming","1")        // 3
                        .param("sort","1")        // 3
                        .param("order","1")        // 3
                        .param("github","1")        // 3
                        .param("stackoverflow","1")        // 3
                        .param("commits","getList")        // 3
                        .param("type","issue")        // 3
                                .sessionAttr("user","UofGWJ")
                                .sessionAttr("user_url","https://api.github.com/users/UofGWJ")
                                .sessionAttr("accessToken","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")
                )
                .andReturn();// 4

        int status = mvcResult.getResponse().getStatus(); // 5
        Assert.assertEquals("False", 200, status); // 7
    }

    @Test
    public void userIssueHistory() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(// 1
                        MockMvcRequestBuilders.get("/mycontribute") // 2
                                .sessionAttr("user","UofGWJ")
                                .sessionAttr("user_url","https://api.github.com/users/UofGWJ")
                                .sessionAttr("accessToken","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")
                        //.param("name","getList")        // 3
                )
                .andReturn();// 4

        int status = mvcResult.getResponse().getStatus(); // 5
        String responseString = mvcResult.getResponse().getContentAsString(); // 6

        Assert.assertEquals("False", 200, status); // 7
//        Assert.assertEquals("返回结果不一致", "/index", responseString); // 8

    }
}