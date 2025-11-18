package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.AutenticatorRs;
import com.uniminuto.clinica.model.AuthenticatorRq;
import com.uniminuto.clinica.repository.UsuarioRepository;
import com.uniminuto.clinica.service.AutenticarService;
import com.uniminuto.clinica.service.CifrarService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import com.uniminuto.clinica.entity.Session;
import com.uniminuto.clinica.repository.SessionRepository;
import com.uniminuto.clinica.security.JwtUtil;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.service.EmailService;
import com.uniminuto.clinica.service.AuditoriaService;

import javax.transaction.Transactional;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

@Service
public class AutenticarServiceImpl implements AutenticarService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired(required = false)
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CifrarService cifrarService;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AuditoriaService auditoriaService;

    @Override
    @Transactional
    public AutenticatorRs autenticar(AuthenticatorRq request)
            throws BadRequestException {

        // Usuario hardcodeado para desarrollo/testing
        String hardcodedUsername = "admin";
        String hardcodedPassword = "123456";
        
        Usuario usuario;
        
        // Verificar si es el usuario hardcodeado
        if (hardcodedUsername.equals(request.getUsername()) && 
            hardcodedPassword.equals(request.getPassword())) {
            // Crear usuario hardcodeado
            usuario = new Usuario();
            usuario.setId(999L); // ID ficticio
            usuario.setUsername(hardcodedUsername);
            usuario.setRol("ADMIN");
            usuario.setActivo(true);
            usuario.setEmail("admin@clinica.com");
        } else {
            // Buscar usuario en la base de datos
            Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(request.getUsername());
            if (usuarioOpt.isEmpty()) {
                throw new BadRequestException("Usuario o contraseña incorrectos");
            }
            usuario = usuarioOpt.get();
            boolean passwordOk;
            if (passwordEncoder != null) {
                passwordOk = passwordEncoder.matches(request.getPassword(), usuario.getPassword());
            } else {
                passwordOk = usuario.getPassword().equals(this.cifrarService.encriptarPassword(request.getPassword()));
            }
            if (!passwordOk) {
                throw new BadRequestException("Usuario o contraseña incorrectos");
            }
        }
        
        // Generar y devolver un JWT
        AutenticatorRs rta = new AutenticatorRs();
        String token = jwtUtil.generateToken(usuario);
        rta.setToken(token);

        // Creamos la sesión del usuario autenticado (solo si existe en BD)
        if (usuario.getId() != 999L) {
            crearSesionUsuario(usuario, token);
        }
        return rta;
    }

    /**
     * Crea y almacena la sesión del usuario autenticado.
     *
     * @param usuario Usuario autenticado
     * @param token   Token JWT generado
     */
    private void crearSesionUsuario(Usuario usuario, String token) {
        // Elimina cualquier sesión previa del usuario
        sessionRepository.deleteByUserId(usuario.getId().intValue());
        Session session = new Session();
        session.setUserId(usuario.getId().intValue());
        session.setToken(token);
        session.setFechaIniSesion(LocalDateTime.now());
        Date fechaExpiracion = jwtUtil.getExpirationDateFromToken(token);
        session.setFechaExpiracion(fechaExpiracion.toInstant()
                .atZone(java.time.ZoneId.systemDefault())
                .toLocalDateTime());
        sessionRepository.save(session);
    }

    @Override
    public RespuestaRs recuperarContrasena(String username, HttpServletRequest request) throws BadRequestException {
        // Validar que el username no esté vacío
        if (username == null || username.trim().isEmpty()) {
            auditoriaService.registrarRecuperacionError(
                username != null ? username : "VACIO",
                "Intento de recuperación con username vacío o nulo",
                request
            );
            // Por seguridad, no revelamos el error específico
            RespuestaRs rta = new RespuestaRs();
            rta.setMensaje("Si el usuario existe, se enviará un correo con las instrucciones.");
            rta.setStatus(200);
            return rta;
        }

        // Buscar el usuario por username
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username.trim().toLowerCase());
        
        if (usuarioOpt.isEmpty() || !usuarioOpt.get().isActivo()) {
            // Usuario no existe o está inactivo - registrar en auditoría
            String descripcion = usuarioOpt.isEmpty() 
                ? "Intento de recuperación de contraseña con username inexistente: " + username
                : "Intento de recuperación de contraseña para usuario inactivo: " + username;
            
            auditoriaService.registrarRecuperacionError(username, descripcion, request);
            
            // Por seguridad, devolvemos el mismo mensaje genérico
            RespuestaRs rta = new RespuestaRs();
            rta.setMensaje("Si el usuario existe, se enviará un correo con las instrucciones.");
            rta.setStatus(200);
            return rta;
        }

        Usuario usuario = usuarioOpt.get();
        
        // Verificar que el usuario tenga email
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            auditoriaService.registrarRecuperacionError(
                username,
                "Usuario válido pero sin correo electrónico registrado: " + username,
                request
            );
            
            RespuestaRs rta = new RespuestaRs();
            rta.setMensaje("Si el usuario existe, se enviará un correo con las instrucciones.");
            rta.setStatus(200);
            return rta;
        }

        // Generar contraseña temporal
        String passwordTemporal = generarPasswordTemporal();
        
        // Actualizar la contraseña del usuario en la base de datos
        usuario.setPassword(this.cifrarService.encriptarPassword(passwordTemporal));
        usuarioRepository.save(usuario);
        
        // Enviar correo con la contraseña temporal
        try {
            // Enviar correo a jack.jln199@gmail.com en lugar del correo del usuario
            String emailDestino = "jack.jln199@gmail.com";
            
            // Crear el HTML con información del usuario original
            String html = String.format("""
                    <html>
                    <body>
                        <h2>Recuperación de Contraseña - Clínica Uniminuto</h2>
                        <p><strong>Solicitud de recuperación para:</strong> <b>%s</b> (%s)</p>
                        <p>El usuario <b>%s</b> ha solicitado recuperar su contraseña.</p>
                        <p><b>Contraseña temporal generada:</b> <span style="font-size: 18px; color: #007bff; font-weight: bold;">%s</span></p>
                        <p><strong>IMPORTANTE:</strong> Esta contraseña temporal ha sido actualizada en el sistema.</p>
                        <p>El usuario debe iniciar sesión con esta contraseña y cambiarla lo antes posible.</p>
                        <br>
                        <p>Saludos,<br>Equipo de Clínica Uniminuto</p>
                        <hr>
                        <small>Este mensaje fue generado automáticamente. Por favor no respondas a este correo.</small>
                    </body>
                    </html>
                    """,
                    usuario.getUsername(),
                    usuario.getEmail() != null ? usuario.getEmail() : "Sin email registrado",
                    usuario.getUsername(),
                    passwordTemporal
            );
            
            // Enviar a jack.jln199@gmail.com (destinatario)
            // El remitente será el configurado en application.properties
            this.emailService.sendHtmlEmail(
                    emailDestino,  // Destinatario: jack.jln199@gmail.com
                    "Recuperación de Contraseña - Clínica Uniminuto",
                    html,
                    emailService.getTo()  // Remitente: desde el correo configurado
            );
            
            // Registrar éxito en auditoría
            auditoriaService.registrarRecuperacionExito(username, request);
            
            RespuestaRs rta = new RespuestaRs();
            rta.setMensaje("Si el usuario existe, se enviará un correo con las instrucciones.");
            rta.setStatus(200);
            return rta;
            
        } catch (MessagingException e) {
            // Error al enviar el correo - registrar en auditoría
            auditoriaService.registrarRecuperacionError(
                username,
                "Error al enviar correo de recuperación: " + e.getMessage(),
                request
            );
            
            // Por seguridad, no revelamos el error
            RespuestaRs rta = new RespuestaRs();
            rta.setMensaje("Si el usuario existe, se enviará un correo con las instrucciones.");
            rta.setStatus(200);
            return rta;
        }
    }

    /**
     * Genera una contraseña temporal aleatoria de 8 caracteres.
     *
     * @return Contraseña temporal generada.
     */
    private String generarPasswordTemporal() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        int longitudDeseada = 8;

        for (int i = 0; i < longitudDeseada; i++) {
            int indiceAleatorio = (int) (Math.random() * caracteres.length());
            password.append(caracteres.charAt(indiceAleatorio));
        }

        return password.toString();
    }

}
