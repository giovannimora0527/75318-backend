package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.model.RecuperacionPasswordResponse;
import com.uniminuto.clinica.entity.TokenRecuperacion;
import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.repository.TokenRecuperacionRepository;
import com.uniminuto.clinica.repository.UsuarioRepository;
import com.uniminuto.clinica.service.AuditoriaService;
import com.uniminuto.clinica.service.ConfiguracionSistemaService;
import com.uniminuto.clinica.service.RecuperacionPasswordService;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class RecuperacionPasswordServiceImpl implements RecuperacionPasswordService {
    
    @Autowired
    private UsuarioRepository usuarioRepo;
    
    @Autowired
    private TokenRecuperacionRepository tokenRepo;
    
    @Autowired
    private AuditoriaService auditoriaService;
    
    @Autowired
    private ConfiguracionSistemaService configService;
    
    @Autowired
    private JavaMailSender javaMailSender;
    
  
    private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%";
    
   
    private static final int LONGITUD_PASSWORD = 12;
    
   
    private static final SecureRandom RANDOM = new SecureRandom(); 
    
   
    @Override
    public RecuperacionPasswordResponse solicitarRecuperacion(String username, String ip) 
            throws BadRequestException {
        
        Optional<Usuario> optUsuario = usuarioRepo.findByUsername(username);
        
       
        if (!optUsuario.isPresent()) {
            auditoriaService.registrarRecuperacionPasswordInvalida(username, ip);
            return construirRespuestaGenerica();
        }
        
        Usuario usuario = optUsuario.get();
        
 
        if (!usuario.isActivo()) {
            auditoriaService.registrarRecuperacionPasswordInvalida(username, ip);
            return construirRespuestaGenerica();
        }
        
       
        if (!tieneEmailValido(usuario)) {
            auditoriaService.registrarEvento(
                "RECUPERACION_PASSWORD_SIN_EMAIL",
                "Usuario sin email configurado intentó recuperar contraseña",
                username,
                ip,
                "Usuario ID: " + usuario.getId()
            );
            return construirRespuestaGenerica();
        }
        
        
        String passwordTemporal = generarPasswordTemporal();
        
       
        TokenRecuperacion token = crearTokenRecuperacion(usuario.getId());
        tokenRepo.save(token);
        
        
        usuario.setPassword(generarHash(passwordTemporal));
        usuarioRepo.save(usuario);
        
       
        if (configService.isEmailRecuperacionHabilitado()) {
            enviarEmailRecuperacion(usuario.getEmail(), passwordTemporal, username);
        }
        
      
        auditoriaService.registrarRecuperacionPasswordExitosa(username, ip);
        
        return construirRespuestaGenerica();
    }
    

    @Override
    public String generarPasswordTemporal() {
        StringBuilder password = new StringBuilder(LONGITUD_PASSWORD);
        for (int i = 0; i < LONGITUD_PASSWORD; i++) {
            int index = RANDOM.nextInt(CARACTERES.length());
            password.append(CARACTERES.charAt(index));
        }
        return password.toString();
    }
    
   
    @Override
    public void enviarEmailRecuperacion(String email, String passwordTemporal, String username) {
        try {
     
            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setTo(email);
            mensaje.setSubject("Recuperación de contraseña - Sistema Clínica");
            
         
            StringBuilder cuerpoMensaje = new StringBuilder();
            cuerpoMensaje.append("Querido Usuario: ").append(username).append("\n\n");
            cuerpoMensaje.append("Has solicitado recuperar tu contraseña.\n");
            cuerpoMensaje.append("Tu contraseña temporal es: ").append(passwordTemporal).append("\n\n");
            cuerpoMensaje.append("Por favor, inicia sesión con esta contraseña temporal ");
            cuerpoMensaje.append("y cámbiala inmediatamente por una nueva.\n\n");
            cuerpoMensaje.append("Esta contraseña expirará en ");
            cuerpoMensaje.append(configService.getTokenExpiracionHoras()).append(" horas.\n\n");
            cuerpoMensaje.append("Si no solicitaste este cambio, ignora este mensaje.\n\n");
            cuerpoMensaje.append("Saludos,\n");
            cuerpoMensaje.append("Sistema de Clínica Uniminuto");
            
            mensaje.setText(cuerpoMensaje.toString());
            
     
            javaMailSender.send(mensaje);
            
            // Log de éxito
       
            System.out.println("EMAIL ENVIADO EXITOSAMENTE");
            System.out.println("Para: " + email);
            System.out.println("Usuario: " + username);
         
            
        } catch (Exception e) {
            // Log de error
            System.err.println("========================================");
            System.err.println("ERROR CRÍTICO AL ENVIAR EMAIL");
            System.err.println("Para: " + email);
            System.err.println("Usuario: " + username);
            System.err.println("Error: " + e.getMessage());
            System.err.println("Se realizará ROLLBACK de la transacción");
  
            e.printStackTrace();
            
        
            throw new RuntimeException("Error al enviar email de recuperación a " + email + ": " + e.getMessage(), e);
        }
    }
    
   
    private String generarHash(String password) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            
            for (byte b : array) {
                sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));
            }
            
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            
            throw new RuntimeException("Error fatal en algoritmo de hash MD5", e);
        }
    }
    
   
    private boolean tieneEmailValido(Usuario usuario) {
        return usuario.getEmail() != null && !usuario.getEmail().trim().isEmpty();
    }
    
   
    private TokenRecuperacion crearTokenRecuperacion(Long usuarioId) {
        TokenRecuperacion token = new TokenRecuperacion();
        token.setUsuarioId(usuarioId);
        token.setToken(UUID.randomUUID().toString());
        token.setFechaCreacion(LocalDateTime.now());
        token.setFechaExpiracion(
            LocalDateTime.now().plusHours(configService.getTokenExpiracionHoras())
        );
        token.setUsado(false);
        return token;
    }
    
    
    private RecuperacionPasswordResponse construirRespuestaGenerica() {
        return RecuperacionPasswordResponse.builder()
            .exitoso(true)
            .mensaje("Si el usuario existe, se ha enviado un correo con instrucciones para recuperar la contraseña.")
            .build();
    }
}