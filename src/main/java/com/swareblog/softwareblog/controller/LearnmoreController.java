package com.swareblog.softwareblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

@Controller
public class LearnmoreController {
    @GetMapping("learnmore")
    public String index(HttpServletRequest request) {
        File file = new File("d:/abc.txt");
        InputStream in = null;
        byte[] tempByte = new byte[1024];
        int byteread = 0;

        try {
            System.out.println("以字节为单位读取文件内容，一次读多个字节：");
            in = new FileInputStream(file);
            while ((byteread = in.read(tempByte)) != -1 ) {
                System.out.write(tempByte, 0, byteread);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
//        request.getSession().invalidate();
        return "learnmore";
    }
}
