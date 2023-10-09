package com.ledger.api_user.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@RequestMapping("/user")
@Slf4j
public class CaptchaController {

    @Resource
    private DefaultKaptcha defaultKaptcha;

    //produces配合swagger在图形化界面中看到图片而不是乱码
    @GetMapping(value = "/captcha",produces = "image/jpeg")
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("image/jpeg");
        //获取文本内容
        String text = defaultKaptcha.createText();
        log.warn("验证码文本内容{}", text);
        //保存文本
        request.getSession().setAttribute("code", text);
        //使用文本创建图像
        BufferedImage image = defaultKaptcha.createImage(text);
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            //输出流输出图片
            ImageIO.write(image,"jpg",outputStream);
            //刷新缓存
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                //关闭流
                assert outputStream != null;
                outputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}