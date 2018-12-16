package com.newcoder.question.util;

import javafx.beans.property.Property;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.jws.WebParam;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.Map;
import java.util.Properties;

@Service
public class MailSender implements InitializingBean {
    public static final Logger logger  = LoggerFactory.getLogger(MailSender.class);
    private JavaMailSenderImpl mailSender;

    @Autowired
    VelocityEngine velocityEngine;

    public boolean sendWithHTMLTemplate(String to, String subject,
                                        String template, Map<String,Object> model) {
        try{
            MimeMessage mimeMessage=  mailSender.createMimeMessage();
            String nick = MimeUtility.encodeText("MaYuWan");
            InternetAddress from = new InternetAddress(nick+"<18229047585@163.com>");
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, template, "UTF-8", model);
            mimeMessageHelper.setText(text,true);
            mailSender.send(mimeMessage);
            return true;
        }catch (Exception e){
            logger.error("发送模板失败: "+e.getMessage());
            return false;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //对JavaMailSenderImpl的配置
        mailSender = new JavaMailSenderImpl();
        mailSender.setPassword("myw1994518");
        mailSender.setUsername("18229047585@163.com");//应该是系统用户名
        mailSender.setHost("smtp.163.com");
        //mailSender.setPort(553);
        mailSender.setProtocol("smtps");
        mailSender.setDefaultEncoding("utf8");
        Properties properties = new Properties();
        properties.put("mail.smtp.ssl.enable",true);
        mailSender.setJavaMailProperties(properties);

    }
}
