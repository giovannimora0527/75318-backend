package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.model.LoginRequest;
import com.uniminuto.clinica.model.LoginResponse;
import com.uniminuto.clinica.entity.ControlAcceso;
import com.uniminuto.clinica.entity.ActualizaciondeUsuario; 
import com.uniminuto.clinica.repository.ActualizacionUsuarioRepository;
import com.uniminuto.clinica.repository.ControlAccesoRepository;
import com.uniminuto.clinica.service.AuditoriaService;
import com.uniminuto.clinica.service.AuthService;
import com.uniminuto.clinica.service.ConfiguracionSistemaService;
import java.time.LocalDateTime;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {
    
    @Autowired
    private ActualizacionUsuarioRepository usuarioRepo;
    
    @Autowired
    private ControlAccesoRepository controlAccesoRepo;
    
    @Autowired
    private AuditoriaService auditoriaService;
    
    @Autowired
    private ConfiguracionSistemaService configService;
    
    @Override
    public LoginResponse iniciarSesion(LoginRequest request) throws BadRequestException {
        
        String username = request.getUsername();
        String password = request.getPassword();
        String ip = obtenerIpCliente();
        
        Optional<ControlAcceso> controlOpt = controlAccesoRepo.findByUsername(username);
        
        if (controlOpt.isPresent()) {
            ControlAcceso control = controlOpt.get();
            
            if (control.getBloqueadoHasta() != null && 
                control.getBloqueadoHasta().isAfter(LocalDateTime.now())) {
                
                long minutosRestantes = java.time.Duration.between(
                    LocalDateTime.now(), 
                    control.getBloqueadoHasta()
                ).toMinutes();
                
                registrarIntentoLogin(username, false, ip, "Usuario bloqueado temporalmente");
                
                return LoginResponse.builder()
                    .exitoso(false)
                    .mensaje("Usuario temporalmente bloqueado. Intente nuevamente en " + minutosRestantes + " minutos.")
                    .token(null)
                    .username(null)
                    .rol(null)
                    .bloqueado(true)
                    .intentosRestantes(0)
                    .build();
            }
        }
        
        Optional<ActualizaciondeUsuario> optUsuario = usuarioRepo.findByUsername(username);
        
        if (!optUsuario.isPresent()) {
            registrarIntentoLogin(username, false, ip, "Usuario no existe");
            auditoriaService.registrarLoginFallido(username, ip, "Usuario no existe");
            Integer intentosRestantes = verificarYBloquearUsuario(username, ip);
            
            return LoginResponse.builder()
                .exitoso(false)
                .mensaje("Credenciales inválidas")
                .bloqueado(false)
                .intentosRestantes(intentosRestantes)
                .build();
        }
        
        ActualizaciondeUsuario usuario = optUsuario.get();
        
        if (!usuario.getActivo()) {
            registrarIntentoLogin(username, false, ip, "Usuario inactivo");
            auditoriaService.registrarLoginFallido(username, ip, "Usuario inactivo");
            
            return LoginResponse.builder()
                .exitoso(false)
                .mensaje("Credenciales inválidas")
                .bloqueado(false)
                .intentosRestantes(0)
                .build();
        }
        
        String passwordHash = generarHash(password);
        
        if (!passwordHash.equals(usuario.getPasswordHash())) {
            registrarIntentoLogin(username, false, ip, "Contraseña incorrecta");
            auditoriaService.registrarLoginFallido(username, ip, "Contraseña incorrecta");
            Integer intentosRestantes = verificarYBloquearUsuario(username, ip);
            
            return LoginResponse.builder()
                .exitoso(false)
                .mensaje("Credenciales inválidas")
                .bloqueado(false)
                .intentosRestantes(intentosRestantes)
                .build();
        }
        
        if (controlOpt.isPresent()) {
            ControlAcceso control = controlOpt.get();
            control.setIntentosFallidos(0);
            control.setFechaBloqueo(null);
            control.setBloqueadoHasta(null);
            controlAccesoRepo.save(control);
        }
        
        registrarIntentoLogin(username, true, ip, "Login exitoso");
        auditoriaService.registrarLoginExitoso(username, ip);
        
        String token = generarToken(usuario);
        
        return LoginResponse.builder()
            .exitoso(true)
            .mensaje("Autenticación exitosa")
            .token(token)
            .username(usuario.getUsername())
            .rol(usuario.getRol())
            .bloqueado(false)
            .intentosRestantes(null)
            .build();
    }

    private String obtenerIpCliente() {
        try {
            ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attrs != null) {
                HttpServletRequest request = attrs.getRequest();
                String ip = request.getHeader("X-Forwarded-For");
                if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getRemoteAddr();
                }
                return ip;
            }
        } catch (Exception e) {
        }
        return "0.0.0.0";
    }
    
    @Override
    public Boolean usuarioBloqueado(String username) {
        Optional<ControlAcceso> controlOpt = controlAccesoRepo.findByUsername(username);
        
        if (controlOpt.isPresent()) {
            ControlAcceso control = controlOpt.get();
            return control.getBloqueadoHasta() != null && 
                   control.getBloqueadoHasta().isAfter(LocalDateTime.now());
        }
        
        return false;
    }
    
    @Override
    public void registrarIntentoLogin(String username, Boolean exitoso, String ip, String mensaje) {
        if (exitoso) {
            auditoriaService.registrarLoginExitoso(username, ip);
        } else {
            auditoriaService.registrarLoginFallido(username, ip, mensaje);
        }
        
        Optional<ControlAcceso> controlOpt = controlAccesoRepo.findByUsername(username);
        ControlAcceso control;
        
        if (controlOpt.isPresent()) {
            control = controlOpt.get();
        } else {
            control = new ControlAcceso();
            control.setUsername(username);
            control.setIntentosFallidos(0);
        }
        
        control.setFechaUltimoIntento(LocalDateTime.now());
        control.setIpAddress(ip);
        
        if (!exitoso) {
            control.setIntentosFallidos(control.getIntentosFallidos() + 1);
        } else {
            control.setIntentosFallidos(0);
            control.setFechaBloqueo(null);
            control.setBloqueadoHasta(null);
        }
        
        controlAccesoRepo.save(control);
    }
    
    @Override
    public Integer verificarYBloquearUsuario(String username, String ip) {
        Integer intentosMax = configService.getIntentosLoginMax();
        
        Optional<ControlAcceso> controlOpt = controlAccesoRepo.findByUsername(username);
        
        if (!controlOpt.isPresent()) {
            return intentosMax - 1;
        }
        
        ControlAcceso control = controlOpt.get();
        
        if (control.getFechaUltimoIntento() != null) {
            long minutosDesdeUltimoIntento = java.time.Duration.between(
                control.getFechaUltimoIntento(), 
                LocalDateTime.now()
            ).toMinutes();
            
            if (minutosDesdeUltimoIntento > 30) {
                control.setIntentosFallidos(1);
                control.setFechaUltimoIntento(LocalDateTime.now());
                controlAccesoRepo.save(control);
                return intentosMax - 1;
            }
        }
        
        Integer intentosRestantes = intentosMax - control.getIntentosFallidos();
        
        if (control.getIntentosFallidos() >= intentosMax) {
            Integer minutosBloqueo = configService.getTiempoBloqueoMinutos();
            
            control.setFechaBloqueo(LocalDateTime.now());
            control.setBloqueadoHasta(LocalDateTime.now().plusMinutes(minutosBloqueo));
            controlAccesoRepo.save(control);
            
            auditoriaService.registrarBloqueoUsuario(username, ip, minutosBloqueo);
            
            return 0;
        }
        
        return Math.max(0, intentosRestantes);
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
            return password;
        }
    }
    
    private String generarToken(ActualizaciondeUsuario usuario) {
        return "TOKEN_SIMULADO_" + usuario.getUsername() + "_" + System.currentTimeMillis();
    }
}