package com.narendra.bss.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{

	@Autowired
    private JavaMailSender mailSender;
      
	@Override
	public void sendEmail(String to, String subject, String body) throws MessagingException {

		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
		/*
		 * String htmlMsg = "<h3>Hello World!</h3>"; mimeMessage.setContent(htmlMsg,
		 * "text/html");
		 */
		helper.setText(body, true);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setFrom("BSS-Admin@bss.com");
		mailSender.send(mimeMessage);
	}
    
}
