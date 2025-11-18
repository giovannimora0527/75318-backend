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
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AutenticarServiceImpl implements AutenticarService {

    private static final Logger logger = LoggerFactory.getLogger(AutenticarServiceImpl.class);

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

    @Value("${security.login.max-intentos-fallidos:3}")
    private Integer maxIntentosFallidos;

    @Value("${security.login.tiempo-bloqueo-minutos:5}")
    private Integer tiempoBloqueoMinutos;

    @Override
    @Transactional
    public AutenticatorRs autenticar(AuthenticatorRq request, HttpServletRequest httpRequest)
            throws BadRequestException {

        String username = request.getUsername();
        
        // Buscar usuario en la base de datos
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);
        
        if (usuarioOpt.isEmpty()) {
            // Usuario no existe - registrar intento fallido
            logger.warn("Intento de login fallido: Usuario '{}' no existe. IP: {}", 
                username, obtenerIpOrigen(httpRequest));
            auditoriaService.registrarIntentoFallidoLogin(
                username, 
                "Usuario no existe", 
                0, 
                httpRequest
            );
            throw new BadRequestException("Usuario o contraseña incorrectos");
        }
        
        Usuario usuario = usuarioOpt.get();
        
        // Verificar si el usuario está bloqueado (ANTES de verificar contraseña)
        if (estaUsuarioBloqueado(usuario)) {
            long minutosRestantes = calcularMinutosRestantesBloqueo(usuario);
            logger.warn("Intento de login bloqueado: Usuario '{}' está bloqueado. Minutos restantes: {}. IP: {}", 
                username, minutosRestantes, obtenerIpOrigen(httpRequest));
            auditoriaService.registrarIntentoFallidoLogin(
                username, 
                String.format("Usuario bloqueado. Minutos restantes: %d", minutosRestantes), 
                usuario.getIntentosFallidos() != null ? usuario.getIntentosFallidos() : 0, 
                httpRequest
            );
            throw new BadRequestException(String.format(
                "Usuario bloqueado temporalmente. Intente nuevamente en %d minuto(s).", 
                minutosRestantes
            ));
        }
        
        // Verificar contraseña
        boolean passwordOk;
        if (passwordEncoder != null) {
            passwordOk = passwordEncoder.matches(request.getPassword(), usuario.getPassword());
        } else {
            passwordOk = usuario.getPassword().equals(this.cifrarService.encriptarPassword(request.getPassword()));
        }
        
        if (!passwordOk) {
            // Contraseña incorrecta - incrementar intentos fallidos
            incrementarIntentosFallidos(usuario, httpRequest);
            throw new BadRequestException("Usuario o contraseña incorrectos");
        }
        
        // Login exitoso - resetear intentos fallidos
        resetearIntentosFallidos(usuario);
        logger.info("Login exitoso para usuario '{}'. IP: {}", username, obtenerIpOrigen(httpRequest));
        
        // Generar y devolver un JWT
        AutenticatorRs rta = new AutenticatorRs();
        String token = jwtUtil.generateToken(usuario);
        rta.setToken(token);

        // Crear la sesión del usuario autenticado
        crearSesionUsuario(usuario, token);
        return rta;
    }
    
    /**
     * Verifica si un usuario está bloqueado temporalmente.
     * 
     * @param usuario Usuario a verificar
     * @return true si el usuario está bloqueado, false en caso contrario
     */
    private boolean estaUsuarioBloqueado(Usuario usuario) {
        if (usuario.getFechaBloqueo() == null) {
            return false;
        }
        
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime fechaDesbloqueo = usuario.getFechaBloqueo().plusMinutes(tiempoBloqueoMinutos);
        
        if (ahora.isBefore(fechaDesbloqueo)) {
            return true;
        } else {
            // El bloqueo expiró - desbloquear automáticamente
            usuario.setFechaBloqueo(null);
            usuario.setIntentosFallidos(0);
            usuarioRepository.save(usuario);
            logger.info("Bloqueo automático expirado para usuario '{}'. Usuario desbloqueado.", usuario.getUsername());
            return false;
        }
    }
    
    /**
     * Calcula los minutos restantes de bloqueo para un usuario.
     * 
     * @param usuario Usuario bloqueado
     * @return Minutos restantes de bloqueo
     */
    private long calcularMinutosRestantesBloqueo(Usuario usuario) {
        if (usuario.getFechaBloqueo() == null) {
            return 0;
        }
        
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime fechaDesbloqueo = usuario.getFechaBloqueo().plusMinutes(tiempoBloqueoMinutos);
        
        if (ahora.isBefore(fechaDesbloqueo)) {
            return java.time.Duration.between(ahora, fechaDesbloqueo).toMinutes() + 1;
        }
        
        return 0;
    }
    
    /**
     * Incrementa el contador de intentos fallidos y bloquea al usuario si excede el límite.
     * 
     * @param usuario Usuario que falló el login
     * @param request Request HTTP para obtener la IP
     */
    private void incrementarIntentosFallidos(Usuario usuario, HttpServletRequest request) {
        int intentosActuales = usuario.getIntentosFallidos() != null ? usuario.getIntentosFallidos() : 0;
        intentosActuales++;
        usuario.setIntentosFallidos(intentosActuales);
        
        logger.warn("Intento fallido de login para usuario '{}'. Intentos fallidos: {}/{}. IP: {}", 
            usuario.getUsername(), intentosActuales, maxIntentosFallidos, obtenerIpOrigen(request));
        
        auditoriaService.registrarIntentoFallidoLogin(
            usuario.getUsername(), 
            "Contraseña incorrecta", 
            intentosActuales, 
            request
        );
        
        // Si alcanza el máximo de intentos, bloquear usuario
        if (intentosActuales >= maxIntentosFallidos) {
            bloquearUsuario(usuario, request);
        } else {
            usuarioRepository.save(usuario);
        }
    }
    
    /**
     * Bloquea temporalmente a un usuario por exceder el límite de intentos fallidos.
     * 
     * @param usuario Usuario a bloquear
     * @param request Request HTTP para obtener la IP
     */
    private void bloquearUsuario(Usuario usuario, HttpServletRequest request) {
        usuario.setFechaBloqueo(LocalDateTime.now());
        usuarioRepository.save(usuario);
        
        logger.error("Usuario '{}' bloqueado automáticamente por {} intentos fallidos consecutivos. Bloqueo de {} minutos. IP: {}", 
            usuario.getUsername(), maxIntentosFallidos, tiempoBloqueoMinutos, obtenerIpOrigen(request));
        
        auditoriaService.registrarBloqueoUsuario(usuario.getUsername(), tiempoBloqueoMinutos, request);
    }
    
    /**
     * Resetea el contador de intentos fallidos cuando el login es exitoso.
     * 
     * @param usuario Usuario que inició sesión exitosamente
     */
    private void resetearIntentosFallidos(Usuario usuario) {
        if (usuario.getIntentosFallidos() != null && usuario.getIntentosFallidos() > 0) {
            usuario.setIntentosFallidos(0);
            usuario.setFechaBloqueo(null);
            usuarioRepository.save(usuario);
            logger.info("Intentos fallidos reseteados para usuario '{}' después de login exitoso.", usuario.getUsername());
        }
    }
    
    /**
     * Obtiene la IP de origen del request.
     * 
     * @param request Request HTTP
     * @return IP de origen
     */
    private String obtenerIpOrigen(HttpServletRequest request) {
        if (request == null) {
            return "DESCONOCIDA";
        }
        
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        
        return ip != null ? ip : "DESCONOCIDA";
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
