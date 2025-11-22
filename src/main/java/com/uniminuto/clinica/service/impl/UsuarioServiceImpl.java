package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.UsuarioRq;
import com.uniminuto.clinica.repository.UsuarioRepository;
import com.uniminuto.clinica.service.CifrarService;
import com.uniminuto.clinica.service.EmailService;
import com.uniminuto.clinica.service.UsuarioService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

/**
 *
 * @author lmora
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private CifrarService cifrarService;

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
    public Usuario encontrarPorNombre(String nombreUsuario)
            throws BadRequestException {
        Optional<Usuario> optUser = this.usuarioRepository
                .findByUsername(nombreUsuario);
        if (!optUser.isPresent()) {
            throw new BadRequestException("No existe el usuario");
        }

        return optUser.get();
    }

    @Override
    public List<Usuario> buscarPorEstado(Integer estado) {
        boolean activo = estado == 1 ? true : false;
        return this.usuarioRepository.findByActivo(activo);
    }

    @Override
    public RespuestaRs guardarUsuario(UsuarioRq usuarioNuevo)
            throws BadRequestException, MessagingException {
        // Paso 1. Validar que los campos lleguen bien
        this.validarCampos(usuarioNuevo);

        // Paso 2. Consulto si existe el usuario por username
        Optional<Usuario> optUser = this.usuarioRepository
                .findByUsername(usuarioNuevo.getUsername().toLowerCase());
        if (optUser.isPresent()) {
            // Paso 3. Si existe, lanzo error que ya existe el usuario
            throw new BadRequestException("El usuario ya existe.");
        }

        // Paso 4. Creo el usuario y seteo los campos que llegan del POST
        Usuario nuevo = new Usuario();
        nuevo.setActivo(true);
        nuevo.setFechaCreacion(LocalDateTime.now());
        nuevo.setRol(usuarioNuevo.getRol().toUpperCase());
        nuevo.setUsername(usuarioNuevo.getUsername().toLowerCase());
        nuevo.setEmail(usuarioNuevo.getEmail());

        // Paso 5. Genero la contraseña temporal y la asigno a tempPassword
        String tempPassword = generarPass();
        nuevo.setTempPasswordHash(this.cifrarService.encriptarPassword(tempPassword));
        nuevo.setTempPasswordExpiration(LocalDateTime.now().plusDays(1)); // Expira en 1 día // 🔥🔥🔥🔥Tiempo de expiracion para la contraseña temporal🔥🔥🔥🔥
        nuevo.setPassword(""); // Mantener vacío para forzar cambio de contraseña

        // Paso 6. Guardo el usuario en la base de datos
        nuevo = this.usuarioRepository.save(nuevo);

        // Paso 7. Preparo el correo de bienvenida
        String html = String.format("""
                    <html>
                    <body>
                        <h2>¡Bienvenido a la Clínica Uniminuto!</h2>
                        <p>Hola <b>%s</b>,</p>
                        <p>Tu cuenta ha sido creada exitosamente.</p>
                        <p><b>Usuario:</b> %s</p>
                        <p><b>Correo:</b> %s</p>
                        <p><b>Contraseña temporal:</b> %s</p>
                        <p>Por favor, inicia sesión y cambia tu contraseña lo antes posible.</p>
                        <br>
                        <p>Si tienes alguna duda, responde a este correo.</p>
                        <hr>
                        <small>Este mensaje fue generado automáticamente, por favor no respondas a este correo.</small>
                    </body>
                    </html>
                    """,
                nuevo.getUsername(),
                nuevo.getUsername(),
                nuevo.getEmail(),
                tempPassword
        );

        // Paso 8. Envío el correo
        this.emailService.sendHtmlEmail(
                nuevo.getEmail(),
                "Envio de contraseña temporal",
                html,
                emailService.getTo()
        );

        // Paso 9. Devuelve respuesta ok
        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("El usuario se ha guardado correctamente. Debe cambiar su contraseña temporal al iniciar sesión.");
        rta.setStatus(200);
        return rta;
    }

    /**
     * Genera una contraseña aleatoria de 8 caracteres.
     *
     * @return Contraseña generada.
     */
    private String generarPass() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        int longitudDeseada = 8;

        for (int i = 0; i < longitudDeseada; i++) {
            int indiceAleatorio = (int) (Math.random() * caracteres.length());
            password.append(caracteres.charAt(indiceAleatorio));
        }

        return password.toString();
    }

    @Override
    public RespuestaRs actualizarUsuario(UsuarioRq usuarioNuevo) throws BadRequestException {

        Optional<Usuario> optUser = usuarioRepository.findById(usuarioNuevo.getId());
        if (optUser.isEmpty()) {
            throw new BadRequestException("El ID del usuario no existe.");
        }

        Usuario userActualizar = optUser.get();

        // Validación de username
        if (!userActualizar.getUsername().equalsIgnoreCase(usuarioNuevo.getUsername())) {
            Optional<Usuario> optUserName = usuarioRepository.findByUsername(usuarioNuevo.getUsername().toLowerCase());
            if (optUserName.isPresent()) {
                throw new BadRequestException("El nombre de usuario ya existe.");
            }
        }

        this.validarCampos(usuarioNuevo);

        userActualizar.setUsername(usuarioNuevo.getUsername().toLowerCase());
        userActualizar.setActivo(usuarioNuevo.isActivo());
        userActualizar.setRol(usuarioNuevo.getRol());
        userActualizar.setEmail(usuarioNuevo.getEmail());

        // *** SOLO ACTUALIZAR PASSWORD SI LO ENVIAN ***
        if (usuarioNuevo.getPassword() != null && !usuarioNuevo.getPassword().isBlank()) {
            userActualizar.setPassword(
                    cifrarService.encriptarPassword(usuarioNuevo.getPassword())
            );
        }

        usuarioRepository.save(userActualizar);

        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("El usuario se ha actualizado correctamente.");
        rta.setStatus(200);
        return rta;
    }

    private void validarCampos(UsuarioRq usuarioNuevo)
            throws BadRequestException {
        if (usuarioNuevo.getUsername() == null
                || usuarioNuevo.getUsername().isBlank()
                || usuarioNuevo.getUsername().isEmpty()) {
            throw new BadRequestException("El campo username es obligatorio.");
        }
        if (usuarioNuevo.getEmail() == null
                || usuarioNuevo.getEmail().isBlank()
                || usuarioNuevo.getEmail().isEmpty()) {
            throw new BadRequestException("El campo email es obligatorio.");
        }
        if (usuarioNuevo.getRol() == null
                || usuarioNuevo.getRol().isBlank()
                || usuarioNuevo.getRol().isEmpty()) {
            throw new BadRequestException("El campo rol es obligatorio.");
        }
    }
}
