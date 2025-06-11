// src/main/java/com/example/tienda/service/EmailService.java
package com.example.tienda.service;

import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender; // Inyecta el JavaMailSender configurado en application.properties

    public void sendEmailWithAttachment(String to, String subject, String body, byte[] attachment, String attachmentName, String attachmentContentType) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true); // true para multipart/mixed (con archivos adjuntos)

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true); // true para HTML, false para texto plano

            // Añadir el archivo adjunto
            helper.addAttachment(attachmentName, new ByteArrayResource(attachment), attachmentContentType);

            mailSender.send(message);
            logger.info("Correo enviado exitosamente a {} con el archivo adjunto: {}", to, attachmentName);
        } catch (Exception e) {
            logger.error("Error al enviar el correo a {}: {}", to, e.getMessage(), e);
            throw new RuntimeException("Error al enviar el correo", e);
        }
    }
}