package com.fruitfresh.mail;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class ConfigMail {

    @Bean
    public JavaMailSender getJavaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("ntcmia@gmail.com");
        mailSender.setPassword("Thanhcong1");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;


//        @Bean
//        public JavaMailSender getJavaMailSender(){
//            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//            mailSender.setHost("smtp.gmail.com");
//            mailSender.setPort(587);
//
//            mailSender.setUsername("ntcmia@gmail.com");
//            mailSender.setPassword("0972260479");
//
//            Properties props = mailSender.getJavaMailProperties();
//            props.put("mail.transport.protocol", "smtp");
//            props.put("mail.smtp.auth", "true");
//            props.put("mail.smtp.starttls.enable", "true");
//            props.put("mail.debug", "true");
//
//            return mailSender;
//        }
    }
}
