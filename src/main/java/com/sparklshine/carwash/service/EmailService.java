package com.sparklshine.carwash.service;

import com.sparklshine.carwash.entity.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendBookingConfirmation(Booking booking) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(booking.getCustomerEmail());
            helper.setSubject("✨ Booking Confirmed - Sparkle & Shine Car Wash");

            String htmlContent = getBookingConfirmationHtml(booking);
            helper.setText(htmlContent, true);

            mailSender.send(message);
            System.out.println("Booking confirmation email sent to: " + booking.getCustomerEmail());
        } catch (MessagingException e) {
            System.out.println("Failed to send email: " + e.getMessage());
        }
    }

    public void sendCompletionEmail(Booking booking) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(booking.getCustomerEmail());
            helper.setSubject("✅ Car Wash Completed - Sparkle & Shine");

            String htmlContent = getCompletionEmailHtml(booking);
            helper.setText(htmlContent, true);

            mailSender.send(message);
            System.out.println("Completion email sent to: " + booking.getCustomerEmail());
        } catch (MessagingException e) {
            System.out.println("Failed to send completion email: " + e.getMessage());
        }
    }

    private String getBookingConfirmationHtml(Booking booking) {
        return "<!DOCTYPE html>" +
            "<html>" +
            "<head><style>" +
            "body { font-family: Arial, sans-serif; margin: 0; padding: 0; background: #f4f4f4; }" +
            ".container { max-width: 600px; margin: 20px auto; background: white; border-radius: 15px; overflow: hidden; box-shadow: 0 5px 20px rgba(0,0,0,0.1); }" +
            ".header { background: linear-gradient(135deg, #667eea, #764ba2); color: white; padding: 30px; text-align: center; }" +
            ".header h1 { margin: 0; font-size: 28px; }" +
            ".content { padding: 30px; }" +
            ".booking-details { background: #f8f9fa; padding: 20px; border-radius: 10px; margin: 20px 0; }" +
            ".detail-row { display: flex; justify-content: space-between; padding: 10px 0; border-bottom: 1px solid #e0e0e0; }" +
            ".detail-label { font-weight: bold; color: #555; }" +
            ".detail-value { color: #333; }" +
            ".status { background: #28a745; color: white; padding: 8px 16px; border-radius: 20px; display: inline-block; font-size: 14px; }" +
            ".footer { background: #1a1a2e; color: white; padding: 20px; text-align: center; font-size: 12px; }" +
            "</style></head>" +
            "<body>" +
            "<div class='container'>" +
            "<div class='header'>" +
            "<h1>✨ Sparkle & Shine</h1>" +
            "<p>Your booking is confirmed!</p>" +
            "</div>" +
            "<div class='content'>" +
            "<h2>Hello " + booking.getCustomerName() + "!</h2>" +
            "<p>Thank you for choosing Sparkle & Shine Car Wash. Your booking has been confirmed.</p>" +
            "<div class='booking-details'>" +
            "<h3>📋 Booking Details</h3>" +
            "<div class='detail-row'><span class='detail-label'>Booking ID:</span><span class='detail-value'>#" + booking.getBookingId() + "</span></div>" +
            "<div class='detail-row'><span class='detail-label'>Service:</span><span class='detail-value'>" + booking.getServiceType() + "</span></div>" +
            "<div class='detail-row'><span class='detail-label'>Date:</span><span class='detail-value'>" + booking.getBookingDate() + "</span></div>" +
            "<div class='detail-row'><span class='detail-label'>Time:</span><span class='detail-value'>" + booking.getBookingTime() + "</span></div>" +
            "<div class='detail-row'><span class='detail-label'>Vehicle:</span><span class='detail-value'>" + (booking.getVehicleRegistration() != null ? booking.getVehicleRegistration() : "Not provided") + "</span></div>" +
            "<div class='detail-row'><span class='detail-label'>Status:</span><span class='detail-value'><span class='status'>" + booking.getStatus() + "</span></span></div>" +
            "</div>" +
            "<p>📍 <strong>Location:</strong> 123 Main Street, Witbank, Mpumalanga</p>" +
            "<p>📞 <strong>Contact:</strong> +27 13 000 0000</p>" +
            "<p>We look forward to serving you!</p>" +
            "</div>" +
            "<div class='footer'>" +
            "<p>&copy; 2026 Sparkle & Shine Car Wash | Quality Service You Can Trust</p>" +
            "</div>" +
            "</div>" +
            "</body>" +
            "</html>";
    }

    private String getCompletionEmailHtml(Booking booking) {
        return "<!DOCTYPE html>" +
            "<html>" +
            "<head><style>" +
            "body { font-family: Arial, sans-serif; margin: 0; padding: 0; background: #f4f4f4; }" +
            ".container { max-width: 600px; margin: 20px auto; background: white; border-radius: 15px; overflow: hidden; box-shadow: 0 5px 20px rgba(0,0,0,0.1); }" +
            ".header { background: linear-gradient(135deg, #28a745, #20c997); color: white; padding: 30px; text-align: center; }" +
            ".header h1 { margin: 0; font-size: 28px; }" +
            ".content { padding: 30px; }" +
            ".review-box { background: #f8f9fa; padding: 20px; border-radius: 10px; text-align: center; margin: 20px 0; }" +
            ".stars { color: #ffc107; font-size: 24px; }" +
            ".footer { background: #1a1a2e; color: white; padding: 20px; text-align: center; font-size: 12px; }" +
            ".btn { display: inline-block; background: linear-gradient(135deg, #667eea, #764ba2); color: white; padding: 10px 20px; text-decoration: none; border-radius: 25px; margin-top: 10px; }" +
            "</style></head>" +
            "<body>" +
            "<div class='container'>" +
            "<div class='header'>" +
            "<h1>🎉 Car Wash Completed!</h1>" +
            "<p>Your vehicle is sparkling clean</p>" +
            "</div>" +
            "<div class='content'>" +
            "<h2>Hello " + booking.getCustomerName() + "!</h2>" +
            "<p>Great news! Your vehicle has been successfully washed and is ready for pickup.</p>" +
            "<div class='booking-details'>" +
            "<h3>📋 Service Summary</h3>" +
            "<div class='detail-row'><span class='detail-label'>Booking ID:</span><span class='detail-value'>#" + booking.getBookingId() + "</span></div>" +
            "<div class='detail-row'><span class='detail-label'>Service:</span><span class='detail-value'>" + booking.getServiceType() + "</span></div>" +
            "<div class='detail-row'><span class='detail-label'>Completed on:</span><span class='detail-value'>" + java.time.LocalDateTime.now() + "</span></div>" +
            "</div>" +
            "<div class='review-box'>" +
            "<p><strong>Loved our service?</strong></p>" +
            "<div class='stars'>★★★★★</div>" +
            "<p>Leave us a review on Google or Facebook!</p>" +
            "</div>" +
            "<p>🚗 Come pick up your sparkling clean car at:</p>" +
            "<p>📍 123 Main Street, Witbank, Mpumalanga</p>" +
            "<p>📞 Need more info? Call us at +27 13 000 0000</p>" +
            "<p>Thank you for choosing Sparkle & Shine! 🌟</p>" +
            "</div>" +
            "<div class='footer'>" +
            "<p>&copy; 2026 Sparkle & Shine Car Wash | Drive Safe, Drive Clean</p>" +
            "</div>" +
            "</div>" +
            "</body>" +
            "</html>";
    }
}