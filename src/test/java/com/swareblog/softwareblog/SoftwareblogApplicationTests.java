package com.swareblog.softwareblog;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

// 这里是简单的进行测试 比较两个int 是否相等，如果不相等就不会
@SpringBootTest
class SoftwareblogApplicationTests {

    @Test
    void contextLoads() {
        int num = new Integer(2);
        Assert.assertEquals(num,1);
    }

}
