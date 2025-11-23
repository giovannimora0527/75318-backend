package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.UsuarioRq;
import com.uniminuto.clinica.repository.UsuarioRepository;
import com.uniminuto.clinica.service.UsuarioService;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servicio corregido para permitir actualización parcial (sin obligar password)
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> listarTodosLosUsuarios() {
        return this.usuarioRepository.findAll();
    }

    @Override
    public List<Usuario> encontrarPorRol(String rol) {
        return this.usuarioRepository.findByRol(rol);
    }

    @Override
    public Usuario encontrarPorNombre(String nombreUsuario) throws BadRequestException {
        Optional<Usuario> optUser = this.usuarioRepository.findByUsername(nombreUsuario);
        if (!optUser.isPresent()) {
            throw new BadRequestException("No existe el usuario");
        }
        return optUser.get();
    }

    @Override
    public List<Usuario> buscarPorEstado(Integer estado) {
        boolean activo = estado == 1;
        return this.usuarioRepository.findByActivo(activo);
    }

    @Override
    public RespuestaRs guardarUsuario(UsuarioRq usuarioNuevo) throws BadRequestException {
        
        // 1. Validamos solo username y rol (Quitamos la validación de pass de aquí)
        this.validarCamposBasicos(usuarioNuevo);
        
        Optional<Usuario> optUser = this.usuarioRepository
                .findByUsername(usuarioNuevo.getUsername().toLowerCase());
        
        Usuario usuarioAGuardar;
        String mensaje;

        if (optUser.isPresent()) {
            // ===================== MODO EDICIÓN =====================
            usuarioAGuardar = optUser.get();
            mensaje = "Usuario actualizado correctamente.";

            // LÓGICA INTELIGENTE: Solo cambiamos la contraseña si el usuario escribió algo nuevo.
            // Si viene null o vacía, NO la tocamos (mantenemos la vieja).
            if (usuarioNuevo.getPass() != null && !usuarioNuevo.getPass().trim().isEmpty()) {
                usuarioAGuardar.setPassword(this.encriptarPassword(usuarioNuevo.getPass()));
            }
            
        } else {
            // ===================== MODO CREACIÓN =====================
            // Aquí SÍ es obligatoria la contraseña
            if (usuarioNuevo.getPass() == null || usuarioNuevo.getPass().trim().isEmpty()) {
                throw new BadRequestException("El campo pass es obligatorio para usuarios nuevos.");
            }

            usuarioAGuardar = new Usuario();
            usuarioAGuardar.setActivo(true);
            usuarioAGuardar.setFechaCreacion(LocalDateTime.now());
            usuarioAGuardar.setPassword(this.encriptarPassword(usuarioNuevo.getPass()));
            mensaje = "Usuario creado correctamente.";
        }

        // Campos comunes
        usuarioAGuardar.setUsername(usuarioNuevo.getUsername().toLowerCase());
        usuarioAGuardar.setRol(usuarioNuevo.getRol().toUpperCase());

        this.usuarioRepository.save(usuarioAGuardar);

        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje(mensaje);
        rta.setStatus(200);
        return rta;
    }

    // Renombrado a "Basicos" porque ya no valida password
    private void validarCamposBasicos(UsuarioRq usuarioNuevo) throws BadRequestException {
        if (usuarioNuevo.getUsername() == null || usuarioNuevo.getUsername().isBlank()) {
            throw new BadRequestException("El campo username es obligatorio.");
        }
        if (usuarioNuevo.getRol() == null || usuarioNuevo.getRol().isBlank()) {
            throw new BadRequestException("El campo rol es obligatorio.");
        }
    }

    private String encriptarPassword(String passAEncriptar) {
        String algoritmo = "MD5";
        try {
            MessageDigest md = MessageDigest.getInstance(algoritmo);
            byte[] hashBytes = md.digest(passAEncriptar.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Algoritmo no soportado: " + algoritmo, e);
        }
    }
}