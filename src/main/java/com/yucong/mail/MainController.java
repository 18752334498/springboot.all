package com.yucong.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("send")
public class MainController {

    @Autowired
    private JavaMailSender javaMailSender;

    @RequestMapping("mail")
    public void sendSimpleMail1() {
        SimpleMailMessage message = new SimpleMailMessage();
        // 设定邮件参数
        message.setFrom("2424693824@qq.com"); // 发送者
        message.setTo("18752334498@139.com"); // 接受者
        message.setSubject("主题:邮件"); // 主题
        message.setText("what the fuck"); // 邮件内容

        // 发送邮件
        javaMailSender.send(message);
    }


}
