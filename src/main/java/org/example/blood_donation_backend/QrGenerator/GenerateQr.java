



package org.example.blood_donation_backend.QrGenerator;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GenerateQr {
    static String namee;

    /*public static String setData(String email,String) {


        System.out.println("Gmail:"+gmail );
        try {
            String data = "Sample Data for QR Code";
            String filePath = "qrcode.png";
            generateQRCodeImage(data, 350, 350, filePath);

            sendEmail(gmail, filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return gmail;
    }*/

    private static void generateQRCodeImage(String data, int width, int height, String filePath)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height);

        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        File outputFile = new File(filePath);
        ImageIO.write(bufferedImage, "png", outputFile);
    }

    private static void sendEmail(String recipientEmail, String attachmentPath) {
        String senderEmail = "apsaralihini11@gmail.com";
        String senderPassword = "nlthclganmfhevbv";
        String subject = "Your QR Code";
        String body = "Congratulations "+"\""+namee+"\""+".. \"Thank you for registering as a donor on our Blood Donation Website. \"\n" +
                "+ \"Here is your QR code for future reference";


        System.out.println(body);

        java.util.Properties properties = new java.util.Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        javax.mail.Session session = javax.mail.Session.getInstance(properties, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            javax.mail.Message message = new javax.mail.internet.MimeMessage(session);
            message.setFrom(new javax.mail.internet.InternetAddress(senderEmail));
            message.setRecipients(javax.mail.Message.RecipientType.TO, javax.mail.internet.InternetAddress.parse(recipientEmail));
            message.setSubject(subject);

            javax.mail.Multipart multipart = new javax.mail.internet.MimeMultipart();

            javax.mail.internet.MimeBodyPart messageBodyPart = new javax.mail.internet.MimeBodyPart();
            messageBodyPart.setText(body);
            multipart.addBodyPart(messageBodyPart);

            javax.mail.internet.MimeBodyPart attachmentPart = new javax.mail.internet.MimeBodyPart();
            attachmentPart.attachFile(new File(attachmentPath));
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            javax.mail.Transport.send(message);

            System.out.println("Email sent successfully with the QR code attachment.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String setData(String email, String nicOrPassport) {

        System.out.println("Gmail:"+email+" "+nicOrPassport );
        String email1=email;
        try {
            String data = "Sample Data for QR Code";
            String filePath = "qrcode.png";
            generateQRCodeImage(data, 350, 350, filePath);

            sendEmail(email1, filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return
                email1;
    }
}

