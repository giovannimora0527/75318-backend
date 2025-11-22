package com.uniminuto.clinica.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String remitente; // Remitente configurado en application.properties


    @Override
    public void enviarCorreoSimple(String to, String subject, String body)
            throws BadRequestException {

        try {
            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setTo(to);
            mensaje.setSubject(subject);
            mensaje.setText(body);
            mensaje.setFrom(remitente);

            mailSender.send(mensaje);

        } catch (Exception e) {
            log.error("Error enviando correo simple", e);
            throw new BadRequestException("No se pudo enviar el correo simple.");
        }
    }


    @Override
    public void enviarCorreo(String to, String subject, String body)
            throws BadRequestException, MessagingException {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, false);
            helper.setFrom(remitente);

            mailSender.send(message);

        } catch (Exception e) {
            log.error("Error enviando correo", e);
            throw new BadRequestException("Error al enviar el correo.");
        }
    }


    @Override
    public String getTo() {
        return this.remitente;
    }


    @Override
    public void sendHtmlEmail(String to,
                              String subject,
                              String htmlBody,
                              String from)
            throws BadRequestException, MessagingException {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
            helper.setFrom(from != null ? from : remitente);

            mailSender.send(message);

        } catch (Exception e) {
            log.error("Error enviando correo HTML", e);
            throw new BadRequestException("Error al enviar correo HTML.");
        }
    }


    @Override
    public void enviarCorreoConAdjuntos(
            String to,
            String subject,
            String body,
            String from,
            MultipartFile adjunto,
            String filename) throws BadRequestException {

        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, false);
            helper.setFrom(from != null ? from : remitente);

            helper.addAttachment(filename, adjunto);

            mailSender.send(message);

        } catch (Exception e) {
            log.error("Error enviando correo con adjunto", e);
            throw new BadRequestException("No se pudo enviar correo con adjunto.");
        }
    }


    @Override
    public RespuestaRs testEmail(String correoDestinatario)
            throws BadRequestException, MessagingException {

        enviarCorreoSimple(
                correoDestinatario,
                "Correo de prueba",
                "Este es un correo de prueba enviado desde el sistema."
        );

        return new RespuestaRs(
                "Correo enviado correctamente a " + correoDestinatario,
                200
        );
    }
}
