package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Auditoria;
import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.repository.AuditoriaRepository;
import com.uniminuto.clinica.service.AuditoriaService;
import com.uniminuto.clinica.service.UsuarioService;
import com.uniminuto.clinica.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import org.springframework.data.domain.Pageable;


@Service
public class AuditoriaServiceImpl implements AuditoriaService {

    @Autowired
    private AuditoriaRepository auditoriaRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private HttpServletRequest request;


    @Override
    public void registrarAuditoria(
            String tabla,
            Integer idRegistro,
            String tipoEvento,
            Object valoresAntes,
            Object valoresDespues,
            String descripcion)
    {
        Auditoria a = new Auditoria();

        a.setFechaHora(LocalDateTime.now());


        // Usuario logeado
        Usuario usuario = usuarioService.getUsuarioLogeado();
        a.setNombreUsuario(usuario != null ? usuario.getUsername() : "Sistema");

        a.setTablaAfectada(tabla);
        a.setIdRegistroAfectado(idRegistro);
        a.setTipoEvento(tipoEvento);

        // Convertir objetos a JSON
        a.setValoresAntes(JsonUtil.toJson(valoresAntes));
        a.setValoresDespues(JsonUtil.toJson(valoresDespues));

        a.setDescripcion(descripcion);

        // Obtener IP real
        a.setIpOrigen(getClientIp());

        auditoriaRepository.save(a);
    }

    @Override
    public void registrarAuditoriaExterna(
            String nombreUsuario,
            String tabla,
            Integer idRegistro,
            String tipoEvento,
            Object valoresAntes,
            Object valoresDespues,
            String descripcion)
    {
        Auditoria auditoria = new Auditoria();

        auditoria.setFechaHora(LocalDateTime.now());

        // Nombre del usuario proporcionado externamente
        auditoria.setNombreUsuario(nombreUsuario != null ? nombreUsuario : "Desconocido");

        auditoria.setTablaAfectada(tabla);
        auditoria.setIdRegistroAfectado(idRegistro);
        auditoria.setTipoEvento(tipoEvento);
        auditoria.setValoresAntes(JsonUtil.toJson(valoresAntes));
        auditoria.setValoresDespues(JsonUtil.toJson(valoresDespues));
        auditoria.setDescripcion(descripcion);
        auditoria.setIpOrigen(getClientIp());

        auditoriaRepository.save(auditoria);
    }

    @Override
    public Page<Auditoria> listarAuditoria(String usuarioFiltro,
                                           String tipoEventoFiltro,
                                           String fechaFiltro,
                                           Pageable pageable) {
        // Aquí puedes personalizar tu método en el repository para filtros combinados
        return auditoriaRepository.buscarConFiltros(usuarioFiltro, tipoEventoFiltro, fechaFiltro, pageable);
    }


    private String getClientIp() {
        String ip = request.getHeader("X-Forwarded-For");
        return (ip == null) ? request.getRemoteAddr() : ip;
    }

}



