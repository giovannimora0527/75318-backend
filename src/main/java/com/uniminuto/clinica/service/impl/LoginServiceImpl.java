package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Auditoria;
import com.uniminuto.clinica.entity.Session;
import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.LoginRequest;
import com.uniminuto.clinica.model.LoginResponse;
import com.uniminuto.clinica.repository.AuditoriaRepository;
import com.uniminuto.clinica.repository.SessionRepository;
import com.uniminuto.clinica.repository.UsuarioRepository;
import com.uniminuto.clinica.service.LoginService;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Optional;
import java.time.LocalDateTime;


@Service
public class LoginServiceImpl implements LoginService {

    private final UsuarioRepository usuarioRepository;
    private final SessionRepository sessionRepository;
    private final AuditoriaRepository auditoriaRepository;

    private final String SECRET_KEY = "MI_SECRET_KEY";

    public LoginServiceImpl(UsuarioRepository usuarioRepository,
                            SessionRepository sessionRepository,
                            AuditoriaRepository auditoriaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.sessionRepository = sessionRepository;
        this.auditoriaRepository = auditoriaRepository;
    }

    @Override
    public LoginResponse login(LoginRequest request, String ipOrigen) {
        Optional<Usuario> userOpt = usuarioRepository.findByUsername(request.getUsername());
        if(userOpt.isEmpty()) throw new RuntimeException("Usuario no encontrado");

        Usuario user = userOpt.get();

        // Validación de contraseña
        if(!user.getPassword().equals(request.getPassword())) {
            // Incrementar intentos fallidos
            user.setIntentosFallidos(user.getIntentosFallidos() + 1);
            usuarioRepository.save(user);
            throw new RuntimeException("Contraseña incorrecta");
        }

        // Resetear intentos fallidos
        user.setIntentosFallidos(0);
        usuarioRepository.save(user);

        // Generar token JWT
        String token = Jwts.builder()
                .setSubject(user.getUsername())
                .claim("rol", user.getRol())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 60*60*1000))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        LocalDateTime ahora = LocalDateTime.now();

        // Guardar sesión
        Session session = new Session();
        session.setUserId(user.getId());
        session.setToken(token);
        session.setFechaIniSesion(ahora);
        session.setFechaExpiracion(ahora.plusHours(1));
        sessionRepository.save(session);

        // Guardar auditoría
        Auditoria auditoria = new Auditoria();
        auditoria.setFechaHora(ahora);
        auditoria.setNombreUsuario(user.getUsername());
        auditoria.setTablaAfectada("USUARIO");
        auditoria.setIdRegistroAfectado(user.getId());
        auditoria.setTipoEvento("LOGIN");
        auditoria.setValoresAntes(""); // opcional
        auditoria.setValoresDespues(""); // opcional
        auditoria.setDescripcion("Inicio de sesión exitoso");
        auditoria.setIpOrigen(ipOrigen);
        auditoriaRepository.save(auditoria);

        return new LoginResponse(token, user.getRol());
    }
}


