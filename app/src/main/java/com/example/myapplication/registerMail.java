package com.example.myapplication;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class registerMail extends AsyncTask{
    private Context context;
    private Session session;
    private String email;
    private String subject;
    private String message;
    public registerMail(Context context,String email, String subject, String message){
        this.context = context;
        this.email = email;
        this.subject = subject;
        this.message = message;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        Properties properties = new Properties();
          properties.put("mail.smtp.auth","true");
          properties.put("mail.smtp.starttls.enable","true");
          properties.put("mail.smtp.host","smtp.gmail.com");
          properties.put("mail.smtp.port","587");
        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(gmail.email, gmail.password);
            }
        });
        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(gmail.email));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(gmail.email));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);
            Transport.send(mimeMessage);
        }
        catch (MessagingException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }
}
