package com.mospan.railway.util;

import com.mospan.railway.model.Ticket;
import com.mospan.railway.service.UserService;
import org.apache.log4j.Logger;


import java.util.Properties;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

    private static final Logger logger = Logger.getLogger(EmailSender.class);

    public static void sendTripCancelNotification(Ticket ticket) {
        logger.info("Sending an email");
        String recipient = ticket.getUser().getDetails().getEmail();

        String sender = "railway.service@outlook.com";

        String host = "smtp.outlook.com";

        Properties properties = System.getProperties();

        properties.setProperty("mail.smtp.host", host);

        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("railway.service@outlook.com", new UserService().getEmailSenderData());
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(sender));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

            message.setSubject("Your trip has been cancelled");

            message.setText("Dear " + ticket.getUser().getDetails().getFirstName() + ",\n\nYour trip on " + ticket.getTrip().getDepartDate() + " " +
                    "from " + ticket.getTrip().getRoute().getStartStation().getName() + " to " + ticket.getTrip().getRoute().getEndStation().getName() +
                    " was cancelled. Sorry for inconveniences and thank you for understanding." +
                    "\n\nBest regards, RailwayService");


            Transport.send(message);
            logger.info("Email with trip cancel notification successfully sent to user " + ticket.getUser().getLogin());
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
