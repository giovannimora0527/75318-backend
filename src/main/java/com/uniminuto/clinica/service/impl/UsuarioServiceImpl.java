package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.UsuarioRq;
import com.uniminuto.clinica.repository.UsuarioRepository;
import com.uniminuto.clinica.service.AuditoriaService;
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


@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private CifrarService cifrarService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AuditoriaService auditoriaService;

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

        // 1. Validar campos obligatorios
        this.validarCampos(usuarioNuevo);

        // 2. Verificar que no exista el usuario
        Optional<Usuario> optUser = this.usuarioRepository
                .findByUsername(usuarioNuevo.getUsername().toLowerCase());
        if (optUser.isPresent()) {
            throw new BadRequestException("El usuario ya existe.");
        }

        // 3. Crear el usuario
        Usuario nuevo = new Usuario();
        nuevo.setActivo(true);
        nuevo.setFechaCreacion(LocalDateTime.now());
        nuevo.setRol(usuarioNuevo.getRol().toUpperCase());
        nuevo.setUsername(usuarioNuevo.getUsername().toLowerCase());
        nuevo.setEmail(usuarioNuevo.getEmail());

        nuevo = this.usuarioRepository.save(nuevo);

        // 4. Generar contraseña
        String password = generarPass();
        nuevo.setPassword(this.cifrarService.encriptarPassword(password));
        this.usuarioRepository.save(nuevo);

        // 5. Enviar email
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
                password
        );

        this.emailService.sendHtmlEmail(
                nuevo.getEmail(),
                "Envio de contraseña",
                html,
                emailService.getTo()
        );

        // ⭐ 6. Registrar en Auditoría
        auditoriaService.registrar(
                nuevo.getUsername(),
                "CREAR_USUARIO",
                "Se creó el usuario con username: " + nuevo.getUsername(),
                "127.0.0.1"  // aquí puedes poner la IP real del request
        );

        // 7. Respuesta
        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("El usuario se ha guardado correctamente.");
        rta.setStatus(200);
        return rta;
    }

    /**
     * Genera una contraseña aleatoria de 8 caracteres.
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
    public RespuestaRs actualizarUsuario(UsuarioRq usuarioNuevo)
            throws BadRequestException {

        // 1. Verificar ID
        Optional<Usuario> optUser = this.usuarioRepository.findById(usuarioNuevo.getId());
        if (optUser.isEmpty()) {
            throw new BadRequestException("El ID del usuario no existe.");
        }

        Usuario userActualizar = optUser.get();

        // 2. Validar si está cambiando username
        if (!userActualizar.getUsername().toLowerCase()
                .equals(usuarioNuevo.getUsername().toLowerCase())) {

            Optional<Usuario> optUserName = this.usuarioRepository
                    .findByUsername(usuarioNuevo.getUsername().toLowerCase());
            if (optUserName.isPresent()) {
                throw new BadRequestException("El nombre de usuario ya existe.");
            }
        }

        // 3. Validar campos obligatorios
        this.validarCampos(usuarioNuevo);

        // 4. Actualizar valores
        userActualizar.setUsername(usuarioNuevo.getUsername().toLowerCase());
        userActualizar.setPassword(this.cifrarService.encriptarPassword(usuarioNuevo.getPassword()));
        userActualizar.setActivo(usuarioNuevo.isActivo());
        userActualizar.setRol(usuarioNuevo.getRol());
        userActualizar.setEmail(usuarioNuevo.getEmail());
        this.usuarioRepository.save(userActualizar);

        // ⭐ 5. Registrar Auditoría
        auditoriaService.registrar(
                userActualizar.getUsername(),
                "ACTUALIZAR_USUARIO",
                "Se actualizó el usuario con ID: " + userActualizar.getId(),
                "127.0.0.1"
        );

        // 6. Respuesta
        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("El usuario se ha actualizado correctamente.");
        rta.setStatus(200);
        return rta;
    }

    @Override
    public RespuestaRs eliminarUsuario(Long idUsuario) throws BadRequestException {
        // 1. Buscar usuario por ID
        Optional<Usuario> optUser = usuarioRepository.findById(idUsuario);
        if (optUser.isEmpty()) {
            throw new BadRequestException("El ID del usuario no existe.");
        }

        Usuario usuarioEliminar = optUser.get();

        // 2. Eliminar usuario
        usuarioRepository.delete(usuarioEliminar);

        // 3. Registrar auditoría
        auditoriaService.registrar(
                usuarioEliminar.getUsername(),
                "ELIMINAR_USUARIO",
                "Se eliminó el usuario con ID: " + idUsuario,
                "127.0.0.1"  // puedes reemplazarlo con la IP real si la tienes
        );

        // 4. Retornar respuesta
        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("El usuario se ha eliminado correctamente.");
        rta.setStatus(200);
        return rta;
    }

    @Override
    public Usuario getUsuarioLogeado() {
        return null;
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

