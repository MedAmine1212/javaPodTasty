/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Douiri Amine
 */
public class JavaMailUtils {
    public static void sendMail(String recepient,Integer code) throws MessagingException{
        System.out.print("Prapring send email");
        Properties properties = new Properties();   
        properties.put("mail.smtp.auth",true);
        properties.put("mail.smtp.starttls.enable",true);
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");
        String myAccountEmail="podtastyy@gmail.com";
        String password="podtastyesprit2020";
        Session session = Session.getInstance(properties, new Authenticator(){          
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail,password);
            }
        });
        Message message = PrepareMessage(session,myAccountEmail,recepient,code);
        Transport.send(message);
        System.out.print("Email sent");
    }
        public static void forgetPassword(String recepient,Integer code) throws MessagingException{
        System.out.print("Praprin send email");
        Properties properties = new Properties();   
        properties.put("mail.smtp.auth",true);
        properties.put("mail.smtp.starttls.enable",true);
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");
        String myAccountEmail="podtastyy@gmail.com";
        String password="podtastyesprit2020";
        Session session = Session.getInstance(properties, new Authenticator(){          
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail,password);
            }
        });
        Message message = PrepareMessageForgetPassword(session,myAccountEmail,recepient,code);
        Transport.send(message);
        System.out.print("Email sent");



        }
        private static Message PrepareMessageForgetPassword(Session session, String myAccountEmail, String recepient, Integer code) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("PODTASTY : reset your password");
            message.setText("Hey this is your reset your password code : "+" "+code);
            return message;
        } catch (Exception ex) {
            Logger.getLogger(JavaMailUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        }
    private static Message PrepareMessage(Session session, String myAccountEmail, String recepient, Integer code) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("Welcome to PODTASTY ,please activate your account ");
            message.setText("Hey this is your activation code : "+" "+code);
            return message;
        } catch (Exception ex) {
            Logger.getLogger(JavaMailUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
