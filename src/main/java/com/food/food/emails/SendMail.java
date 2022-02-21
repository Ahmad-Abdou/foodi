package com.food.food.emails;

import com.food.food.controller.OrderController;
import org.springframework.mail.javamail.JavaMailSender;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;


public class SendMail {

    private JavaMailSender mailSender;


    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    public void send() {

        final String username = "kyoub1996@gmail.com";
        final String password = "2714444j";

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress("Food"));


            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(OrderController.email));

            // Set Subject: header field
            message.setSubject("Receipts for purchase");

            // Send the actual HTML message, as big as you like
            message.setContent(
                    "<h1>Thanks for your purchase </h1>"+
                            "<span style=\"font-size:25px;font-weight:800;\">"+OrderController.fullName.toUpperCase() +"</span>"+"</br></br>"+
                            "Your order Number :                "+" <span style=\"font-size:16px;font-weight:600;\">"+OrderController.orderNumber +"</span>"+"</br></br>"+
                            "Product :                          "+" <span style=\"font-size:16px;font-weight:600;\">"+OrderController.itemList+"</span>"+"</br></br>"+
                            "Date :                             "+" <span style=\"font-size:16px;font-weight:600;\">" + OrderController.date +"</span>"+"</br></br>",
                    "text/html");

            // Send message
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}