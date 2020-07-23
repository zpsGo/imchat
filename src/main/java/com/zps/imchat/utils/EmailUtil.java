package com.zps.imchat.utils;


import com.zps.imchat.config.EmailAccountConfig;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName EmailUtil
 * @Author DiangD
 * @Date 2020/7/22
 * @Version 1.0
 * @Description 邮件服务工具类(只限于SMTP协议)
 **/
@Component
public class EmailUtil {
    private static final String Sender = EmailAccountConfig.sender;
    private static final String SendPwd = EmailAccountConfig.password;
    private static final String MAIL_SMTP_HOST =EmailAccountConfig.host ;
    private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

    private final Lock lock = new ReentrantLock(false);


    /**
     * @param receiver 接收方
     * @param content 邮件内容
     * @param title 邮件标题
     * @Description: 发送邮件
     */
    public int sendEmail(String receiver, String content, String title) {
        if (receiver != null) {
            //设置配置
            Properties props = new Properties();
            props.setProperty("mail.transport.protocol", "smtp");
            props.setProperty("mail.smtp.host", MAIL_SMTP_HOST);//设置邮件服务器主机名
            props.setProperty("mail.smtp.port", "465");
            props.setProperty("mail.smtp.socketFactory.port", "465");
            props.setProperty("mail.smtp.ssl.enable", "true");
            props.setProperty("mail.smtp.auth", "true");//发送服务器需要身份验证
            props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            Session session = Session.getDefaultInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(Sender, SendPwd);
                }
            });//设置环境信息
            session.setDebug(true);
            //可重入锁保证线程安全
            lock.lock();
            try {
                MimeMessage message = createMimeMessage(session, receiver, content, title);
                Transport.send(message);
            } catch (UnsupportedEncodingException | MessagingException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
            return 1;
        }
        return 0;
    }

    /**
     * @param session session
     * @param receiveMail 接收方
     * @param content 内容
     * @param title 标题
     * @Description: 创建邮件
     */
    private  MimeMessage createMimeMessage(Session session, String receiveMail, String content, String title) throws UnsupportedEncodingException, MessagingException {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);

        // 2. From: 发件人
        message.setFrom(new InternetAddress(EmailUtil.Sender, "ImChat工作组", "UTF-8"));

        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "亲爱的"+receiveMail, "UTF-8"));

        // 4. Subject: 邮件主题
        message.setSubject(title, "UTF-8");

        // 5. Content: 邮件正文（可以使用html标签）
        message.setContent(content, "text/html;charset=UTF-8");

        // 6. 设置发件时间
        message.setSentDate(new Date());

        // 7. 保存设置
        message.saveChanges();

        return message;
    }
}
