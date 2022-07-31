package amin.code.orders.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;

@Component
public class EmailServiceImpl {

    @Autowired
    private JavaMailSender emailSender;
    private MimeMessage message;
    private MimeMessageHelper messageHelper;
    public void sendSimpleMessage(String to, String subject, String text) throws MessagingException {
        message = emailSender.createMimeMessage();
        messageHelper = new MimeMessageHelper(message, true, "UTF-8");
       // SimpleMailMessage message = new SimpleMailMessage();
        messageHelper.setFrom("rnlwhrznzl66@daum.net");
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setText(text,true);
        //File file = new ClassPathResource("static/btn_floating_kayak.png").getFile();
        // FileSystemResource fsr = new FileSystemResource(file);

        // messageHelper.addInline("sample-img", fsr);
        emailSender.send(message);
    }
}