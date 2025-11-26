package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.service.EmailService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private com.uniminuto.clinica.model.EmailConfig emailConfig;

    @Override
    public void enviarCorreoSimple(final String to, final String subject,
                                   final String body)
            throws BadRequestException {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            message.setFrom(emailConfig.getTo());
            mailSender.send(message);
            System.out.println("Correo enviado exitosamente a: " + to);
        } catch (MailException e) {
            System.err.println("Error al enviar el correo: " + e.getMessage());
            throw new BadRequestException("Error. " + e.getMessage());
        }
    }

    @Override
    public String getTo() {
        return emailConfig.getTo();
    }

    @Override
    public void enviarCorreo(final String to,
                             final String subject,
                             final String body,
                             final String from)
            throws BadRequestException, MessagingException {
        try {
            this.sendHtmlEmail(to, subject, body, from);
        } catch (MailException e) {
            System.err.println("Error al enviar el correo: " + e.getMessage());
            throw new BadRequestException("Error. " + e.getMessage());
        } catch (MessagingException ex) {
            Logger.getLogger(EmailServiceImpl.class.getName())
                    .log(Level.SEVERE, null, ex);
            throw new MessagingException("Error => " + ex.getMessage());
        }
    }

    @Override
    public void sendHtmlEmail(final String to,
                              final String subject,
                              final String htmlBody,
                              final String from)
            throws MessagingException {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true); // true = HTML
            helper.setFrom(from);

            mailSender.send(message);
            System.out.println("Correo enviado con éxito a " + to);
        } catch (MailException e) {
            System.err.println("Error al enviar correo HTML: " + e.getMessage());
            throw new MessagingException("Error al enviar correo HTML: " + e.getMessage());
        }
    }

    @Override
    public RespuestaRs testEmail(String correoDestinatario) throws BadRequestException, MessagingException {
        this.sendHtmlEmail(correoDestinatario, "Prueba de correo",
                "<h1>Este es un correo de prueba</h1><p>Enviado desde el servicio de email.</p>",
                this.getTo());

        RespuestaRs rta = new RespuestaRs();
        rta.setStatus(200);
        rta.setMensaje("Correo enviado exitosamente a " + correoDestinatario);
        return rta;
    }

    @Override
    public void enviarCorreoConAdjuntos(String to, String subject, String body, String from,
                                        MultipartFile adjunto, String filename) throws BadRequestException {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true); // HTML
            helper.setFrom(from);

            if (adjunto != null && !adjunto.isEmpty()) {
                helper.addAttachment(filename, new ByteArrayResource(adjunto.getBytes()));
            }

            mailSender.send(message);
            System.out.println("Correo con adjunto enviado exitosamente a: " + to);
        } catch (Exception e) {
            Logger.getLogger(EmailServiceImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new BadRequestException("Error al enviar correo con adjunto. " + e.getMessage());
        }
    }
}
