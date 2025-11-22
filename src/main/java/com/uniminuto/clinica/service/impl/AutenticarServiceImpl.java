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

import com.uniminuto.clinica.model.RecuperarPasswordRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.service.AuditoriaService;
import com.uniminuto.clinica.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import javax.mail.MessagingException;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import com.uniminuto.clinica.entity.Session;
import com.uniminuto.clinica.repository.SessionRepository;
import com.uniminuto.clinica.security.JwtUtil;

import javax.transaction.Transactional;

@Slf4j
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

        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(request.getUsername());
        if (usuarioOpt.isEmpty()) {
            throw new BadRequestException("Usuario o contraseña incorrectos");
        }
        Usuario usuario = usuarioOpt.get();
        //borrar a aqui
        System.out.println("=== DEBUG AUTENTICACIÓN ===");
        System.out.println("Usuario recibido: [" + request.getUsername() + "]");
        System.out.println("Contraseña recibida: [" + request.getPassword() + "]");
        System.out.println("Longitud de la contraseña: " + (request.getPassword() != null ? request.getPassword().length() : "null"));
        if (request.getPassword() != null) {
            for (int i = 0; i < request.getPassword().length(); i++) {
                System.out.println("  Carácter " + i + ": '" + request.getPassword().charAt(i) + "' (ASCII: " + (int)request.getPassword().charAt(i) + ")");
            }
        }
        System.out.println("Hash en BD: " + usuario.getPassword());
        System.out.println("Hash de la entrada: " + this.cifrarService.encriptarPassword(request.getPassword()));
        System.out.println("============================");
        //Borrar todo antes de aqui
        boolean passwordOk;
        if (passwordEncoder != null) {
            passwordOk = passwordEncoder.matches(request.getPassword(), usuario.getPassword());
        } else {
            passwordOk = usuario.getPassword().equals(this.cifrarService.encriptarPassword(request.getPassword()));
        }
        if (!passwordOk) {
            throw new BadRequestException("Usuario o contraseña incorrectos");
        }
        // Generar y devolver un JWT
        AutenticatorRs rta = new AutenticatorRs();
        String token = jwtUtil.generateToken(usuario);
        rta.setToken(token);

        // Creamos la sesión del usuario autenticado
        crearSesionUsuario(usuario, token);
        return rta;
    }

    @Override
    @Transactional
    public RespuestaRs recuperarPassword(
            RecuperarPasswordRq request,
            String ipAddress,
            String userAgent) throws MessagingException {

        String username = request.getUsername().trim();

        log.info("Iniciando proceso de recuperación de contraseña para username: {}", username);

        // Buscar usuario en BD
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);

        if (usuarioOpt.isEmpty()) {
            // USUARIO NO EXISTE - Registrar en auditoría como FALLIDO
            log.warn("Intento de recuperación con usuario inexistente: {}", username);

            auditoriaService.registrarEvento(
                    username,
                    AuditoriaServiceImpl.MOTIVO_RECUPERAR_CONTRASENA,
                    false, // NO exitoso
                    "Usuario no encontrado en el sistema",
                    ipAddress,
                    userAgent
            );

            // ⚠️ IMPORTANTE: Retornar el MISMO mensaje de éxito por seguridad
            return construirRespuestaExito();
        }

        // Usuario EXISTE
        Usuario usuario = usuarioOpt.get();

        // Verificar si el usuario está activo
        if (!usuario.isActivo()) {
            log.warn("Intento de recuperación con usuario inactivo: {}", username);

            auditoriaService.registrarEvento(
                    username,
                    AuditoriaServiceImpl.MOTIVO_RECUPERAR_CONTRASENA,
                    false,
                    "Usuario existe pero está inactivo",
                    ipAddress,
                    userAgent
            );

            return construirRespuestaExito();
        }

        // Verificar intentos sospechosos
        if (auditoriaService.tieneIntentosSospechosos(username)) {
            log.error("⚠️ ALERTA: Usuario '{}' tiene intentos sospechosos de recuperación. IP: {}",
                    username, ipAddress);
            // Aún así enviamos el correo, pero dejamos el log de alerta
        }

        try {
            // Generar nueva contraseña temporal
            String passwordTemporal = generarPasswordTemporal();
            String hashPasswordTemporal = cifrarService.encriptarPassword(passwordTemporal);

            // Actualizar contraseña en BD
            usuario.setPassword(hashPasswordTemporal);
            usuarioRepository.save(usuario);

            // Enviar correo con contraseña temporal
            enviarCorreoRecuperacion(usuario, passwordTemporal);

            // Registrar en auditoría como EXITOSO
            auditoriaService.registrarEvento(
                    username,
                    AuditoriaServiceImpl.MOTIVO_RECUPERAR_CONTRASENA,
                    true, // Exitoso
                    null, // Sin error
                    ipAddress,
                    userAgent
            );

            log.info("✅ Recuperación exitosa para usuario: {}. Email enviado a: {}",
                    username, usuario.getEmail());

        } catch (Exception e) {
            log.error("Error al procesar recuperación para usuario: {}", username, e);

            // Registrar error en auditoría
            auditoriaService.registrarEvento(
                    username,
                    AuditoriaServiceImpl.MOTIVO_RECUPERAR_CONTRASENA,
                    false,
                    "Error técnico: " + e.getMessage(),
                    ipAddress,
                    userAgent
            );

            // Aún así retornamos mensaje de éxito por seguridad
        }

        return construirRespuestaExito();
    }

    /**
     * Genera una contraseña temporal aleatoria de 10 caracteres.
     */
    private String generarPasswordTemporal() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%";
        StringBuilder password = new StringBuilder();
        int longitudDeseada = 10;

        for (int i = 0; i < longitudDeseada; i++) {
            int indiceAleatorio = (int) (Math.random() * caracteres.length());
            password.append(caracteres.charAt(indiceAleatorio));
        }

        return password.toString();
    }

    /**
     * Envía correo electrónico con la contraseña temporal.
     */
    private void enviarCorreoRecuperacion(Usuario usuario, String passwordTemporal)
            throws MessagingException {

        String asunto = "Recuperación de Contraseña - Clínica Uniminuto";

        String htmlBody = String.format("""
                <html>
                <head>
                    <style>
                        body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                        .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                        .header { background: linear-gradient(135deg, #667eea 0%%, #764ba2 100%%); 
                                  color: white; padding: 30px; text-align: center; border-radius: 10px 10px 0 0; }
                        .content { background: #f9f9f9; padding: 30px; border-radius: 0 0 10px 10px; }
                        .password-box { background: white; border-left: 4px solid #667eea; 
                                       padding: 15px; margin: 20px 0; font-size: 18px; font-weight: bold; }
                        .warning { background: #fff3cd; border-left: 4px solid #ffc107; 
                                  padding: 15px; margin: 20px 0; }
                        .footer { text-align: center; margin-top: 30px; font-size: 12px; color: #666; }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <div class="header">
                            <h2>🔐 Recuperación de Contraseña</h2>
                        </div>
                        <div class="content">
                            <p>Hola <strong>%s</strong>,</p>
                            
                            <p>Hemos recibido una solicitud de recuperación de contraseña para tu cuenta.</p>
                            
                            <div class="password-box">
                                🔑 Tu contraseña temporal es: <span style="color: #667eea;">%s</span>
                            </div>
                            
                            <div class="warning">
                                <strong>⚠️ Importante:</strong>
                                <ul>
                                    <li>Esta contraseña es temporal y debe ser cambiada después de iniciar sesión.</li>
                                    <li>Por tu seguridad, te recomendamos cambiarla inmediatamente.</li>
                                    <li>Si no solicitaste este cambio, contacta al administrador de inmediato.</li>
                                </ul>
                            </div>
                            
                            <p><strong>Datos de tu cuenta:</strong></p>
                            <ul>
                                <li><strong>Usuario:</strong> %s</li>
                                <li><strong>Correo:</strong> %s</li>
                            </ul>
                            
                            <p>Una vez inicies sesión, dirígete a la sección de configuración para establecer una nueva contraseña.</p>
                        </div>
                        <div class="footer">
                            <p>Este es un mensaje automático, por favor no responder a este correo.</p>
                            <p>© 2024 Clínica Uniminuto - Sistema de Gestión Médica</p>
                        </div>
                    </div>
                </body>
                </html>
                """,
                usuario.getUsername(),
                passwordTemporal,
                usuario.getUsername(),
                usuario.getEmail()
        );

        emailService.sendHtmlEmail(
                usuario.getEmail(),
                asunto,
                htmlBody,
                emailService.getTo()
        );
    }

    /**
     * Construye la respuesta de éxito estándar.
     * Este mensaje SIEMPRE se retorna, exista o no el usuario.
     */
    private RespuestaRs construirRespuestaExito() {
        RespuestaRs respuesta = new RespuestaRs();
        respuesta.setStatus(200);
        respuesta.setMensaje("Si el usuario existe, se ha enviado un correo electrónico " +
                "con instrucciones para recuperar la contraseña.");
        return respuesta;
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


}
