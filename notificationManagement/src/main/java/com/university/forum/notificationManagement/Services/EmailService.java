package com.university.forum.notificationManagement.Services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    private final String fromEmail="synclick.team@gmail.com";

    private final JavaMailSender mailSender;
    private final String appName = "ISSATSo++";

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String toEmail, String subject, String content) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("synclick.team@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);

        System.out.println("Email sent successfully!");

    }

    public void sendEmailWithHtml(String toEmail, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject(subject);

            String emailContent = "<!DOCTYPE html>" +
                    "<html lang='en'>" +
                    "<head>" +
                    "    <style>" +
                    "        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f6f9fc; padding: 0; margin: 0; }" +
                    "        .email-wrapper { width: 100%; background-color: #f6f9fc; padding: 20px; }" +
                    "        .email-content { max-width: 600px; background-color: #ffffff; margin: 0 auto; padding: 30px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }" +
                    "        .header { text-align: center; padding-bottom: 20px; border-bottom: 1px solid #ddd; }" +
                    "        .header img { width: 150px; border-radius: 8px; }" +
                    "        .header h1 { font-size: 24px; color: #2c3e50; margin-top: 10px; }" +
                    "        .body-content { padding: 20px 0; font-size: 16px; color: #333; line-height: 1.6; }" +
                    "        .body-content h2 { color: #3498db; font-size: 20px; margin-bottom: 10px; }" +
                    "        .cta-button { display: inline-block; padding: 12px 25px; margin-top: 20px; background-color: #2c3e50; color: #ffffff; text-decoration: none; border-radius: 5px; font-weight: bold; font-size: 14px; }" +
                    "        .cta-button:hover { background-color: #3498db; }" +
                    "        .footer { text-align: center; font-size: 12px; color: #999; padding-top: 20px; border-top: 1px solid #ddd; margin-top: 20px; }" +
                    "        .footer p { margin: 5px 0; }" +
                    "    </style>" +
                    "</head>" +
                    "<body>" +
                    "    <div class='email-wrapper'>" +
                    "        <div class='email-content'>" +
                    "            <div class='header'>" +
                    "                <img src='https://res.cloudinary.com/dfvbhzskm/image/upload/v1743344256/ISSATSo/settingsImages/beerfvhlu2czeyw6p8yr.jpg' alt='ISSATSo Logo'>" +
                    "                <h1>Institut Supérieur des Sciences Appliquées et de Technologie de Sousse</h1>" +
                    "            </div>" +
                    "            <div class='body-content'>" +
                    "                <h2>" + subject + "</h2>" +
                    "                <p>Dear Student,</p>" +
                    "                <p>" + content + "</p>" +
                    "                <p>As a valued member of ISSATSo++, you will have access to exclusive features and services:</p>" +
                    "                <ul>" +
                    "                    <li>Personalized notifications and updates</li>" +
                    "                    <li>Academic resources and support</li>" +
                    "                    <li>Seamless communication with your peers and faculty</li>" +
                    "                </ul>" +
                    "                  <div>"+
                    "                <a href='https://issatso.rnu.tn/' class='cta-button'>Explore ISSATSo++</a>" +
                    "            </div>" +
                    "            </div>" +
                    "            <div class='footer'>" +
                    "                <p>© 2025 ISSATSo++ | All rights reserved.</p>" +
                    "                <p>If you did not request this email, please ignore it.</p>" +
                    "            </div>" +
                    "        </div>" +
                    "    </div>" +
                    "</body>" +
                    "</html>";

            helper.setText(emailContent, true);
            mailSender.send(message);
            System.out.println("HTML Email sent successfully!");

        } catch (MessagingException e) {
            System.out.println("Error sending HTML email: " + e.getMessage());
        }
    }



    public void sendPasswordEmailWithHtml(String toEmail, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            String emailContent = "<!DOCTYPE html>" +
                    "<html lang='en'>" +
                    "<head>" +
                    "  <meta charset='UTF-8'>" +
                    "  <meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                    "  <style>" +
                    "    body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f4f4f4; padding: 0; margin: 0; }" +
                    "    .email-wrapper { padding: 30px; background-color: #f4f4f4; }" +
                    "    .email-content { max-width: 600px; margin: auto; background-color: #ffffff; padding: 30px; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); }" +
                    "    .header { text-align: center; border-bottom: 1px solid #ddd; padding-bottom: 20px; }" +
                    "    .header img { width: 120px; border-radius: 6px; }" +
                    "    .header h1 { font-size: 20px; color: #2c3e50; margin-top: 10px; }" +
                    "    .body-content { margin-top: 20px; font-size: 16px; color: #333; line-height: 1.6; }" +
                    "    .password-box { background-color: #f1f5f9; border: 1px solid #ccc; padding: 15px; margin: 20px 0; font-family: monospace; font-size: 18px; text-align: center; border-radius: 5px; }" +
                    "    .copy-button { display: block; width: fit-content; margin: auto; background-color: #1d4ed8; color: white; padding: 12px 20px; border-radius: 5px; font-size: 16px; font-weight: bold; text-align: center; text-decoration: none; border: none; cursor: pointer; }" +
                    "    .copy-button:hover { background-color: #2563eb; }" +
                    "    .footer { margin-top: 30px; text-align: center; font-size: 12px; color: #888; border-top: 1px solid #ddd; padding-top: 15px; }" +
                    "  </style>" +
                    "</head>" +
                    "<body>" +
                    "  <div class='email-wrapper'>" +
                    "    <div class='email-content'>" +
                    "      <div class='header'>" +
                    "        <img src='https://res.cloudinary.com/dfvbhzskm/image/upload/v1743344256/ISSATSo/settingsImages/beerfvhlu2czeyw6p8yr.jpg' alt='ISSATSo Logo'>" +
                    "        <h1>Institut Supérieur des Sciences Appliquées et de Technologie de Sousse</h1>" +
                    "      </div>" +
                    "      <div class='body-content'>" +
                    "        <p>Dear Student, "+toEmail+"</p>" +
                    "        <p>Your new password has been securely generated. Please find it below:</p>" +
                    "        <div class='password-box'>" + content + "</div>" +
                    "        <p>For your security, we recommend that you change this password after logging into the application.</p>" +
                    "        <p>If you have any questions or issues, please contact our support team.</p>" +
                    "      </div>" +
                    "      <div class='footer'>" +
                    "        <p>© 2025 ISSATSo++ | All rights reserved.</p>" +
                    "        <p>If you did not request this email, please ignore it.</p>" +
                    "      </div>" +
                    "    </div>" +
                    "  </div>" +
                    "</body>" +
                    "</html>";



            helper.setText(emailContent, true);
            mailSender.send(message);
            System.out.println("HTML Email sent successfully!");

        } catch (MessagingException e) {
            System.out.println("Error sending HTML email: " + e.getMessage());
        }
    }


}
