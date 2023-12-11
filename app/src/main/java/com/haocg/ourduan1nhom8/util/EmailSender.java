package com.haocg.ourduan1nhom8.util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
    static Context context;

    public EmailSender(Context context) {
        this.context = context;
    }

    public static void sendEmail(String recipientEmail, String subject, String messageContent){ // static --
        final String stringSenderEmail = "haocgph46686@fpt.edu.vn";
        final String stringPasswordSenderEmail = "vwlivbqwjzbylolc";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(stringSenderEmail, stringPasswordSenderEmail);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(stringSenderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(messageContent);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(message);
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "Email sent successfully!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        // :))
//        Session session = Session.getInstance(properties, new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(stringSenderEmail,stringPasswordSenderEmail);
//            }
//        });
//
//        try {
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(stringSenderEmail));
//            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(recipientEmail));
//            message.setSubject(subject);
//            message.setText(messageContent);
//
//            Thread thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        Transport.send(message);
//                    } catch (MessagingException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            thread.start();
//
//            System.out.println("Email sent successfully!");
//            Toast.makeText(context, "Email sent successfully!", Toast.LENGTH_SHORT).show();
//        }catch (MessagingException e) {
//            e.printStackTrace();
//        }
    }
}
