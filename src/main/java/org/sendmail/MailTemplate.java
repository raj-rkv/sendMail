package org.sendmail;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public class MailTemplate {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        String from=sc.nextLine();
        String toAdd=sc.nextLine();
        String ccAdd=sc.nextLine();
        String body=sc.nextLine();
        try {
            //sendMailWithoutAttachment(from,toAdd,ccAdd,body);
            sendMailWithAttachment(from,toAdd,ccAdd,body);
                    }
        catch (MessagingException |IOException e)
        {
            System.out.println(e.getLocalizedMessage());
        }

    }

    private static Session getSession(){
        Properties properties=System.getProperties();
        //Need following properties for establishing connection with email service provider--Gmail
        //Host name
        //Port no
        //ssl level
        //authentication param

        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","465");
        properties.put("mail.smtp.ssl.enable","true");
        properties.put("mail.smtp.auth","true");

        //Steps for Sending Mail

        //1. Creating session for establishing connection with Gmail server

        Session session=Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("eng.rkvofficial@gmail.com",ConstantPassword.PASSWORD);
            }
        } );

        return session;
    }

    public static void sendMailWithoutAttachment(String fromAddress,String toAddress,String ccAddress,String messageBody) throws MessagingException {


        //Compose the mail
        //? addFrom & setFrom
        Session session=getSession();
        MimeMessage message=new MimeMessage(session);
        message.setFrom(fromAddress);
        message.addRecipients(Message.RecipientType.TO,toAddress);
        message.addRecipients(Message.RecipientType.CC,ccAddress);
        message.setSubject("Demo Mail");
        message.setText(messageBody);

        //3.Send the mail
        Transport.send(message);
        System.out.println("Email send Successfully");

    }

    public static void sendMailWithAttachment(String fromAddress,String toAddress,String ccAddress,String messageBody) throws MessagingException, IOException {
        //Compose the mail
        //? addFrom & setFrom
        Session session=getSession();
        MimeMessage message=new MimeMessage(session);
        message.setFrom(fromAddress);
        message.addRecipients(Message.RecipientType.TO,toAddress);
        message.addRecipients(Message.RecipientType.CC,ccAddress);
        message.setSubject("Demo Mail with attachment ");

        //Setting body with Attachment
        //Set path of the file
        String path="C://Users//ASUS//Desktop//Artboard_1_copy4x-a.jpg"; //path of the file
        MimeMultipart mimeMultipart=new MimeMultipart();
        //for body test
        MimeBodyPart bodyText=new MimeBodyPart();
        //for file
        MimeBodyPart bodyAttachment=new MimeBodyPart();

        bodyText.setText(messageBody);

        File file=new File(path);
        bodyAttachment.attachFile(file);
        mimeMultipart.addBodyPart(bodyText);
        mimeMultipart.addBodyPart(bodyAttachment);

        message.setContent(mimeMultipart);

        //Send the mail

        Transport.send(message);
        }
}
