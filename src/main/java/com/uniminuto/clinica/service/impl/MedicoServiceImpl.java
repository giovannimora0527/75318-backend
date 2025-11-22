package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.*;
import com.uniminuto.clinica.model.*;
import com.uniminuto.clinica.repository.EspecializacionRepository;
import com.uniminuto.clinica.repository.MedicoRepository;
import com.uniminuto.clinica.service.AuditoriaService;
import com.uniminuto.clinica.service.EspecializacionService;
import com.uniminuto.clinica.service.MedicoService;

import java.util.List;
import java.util.Optional;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lmora
 */
@Service
public class MedicoServiceImpl implements MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private EspecializacionService especializacionService;


    @Autowired
    private EspecializacionRepository especializacionRepository;

    @Autowired
    private AuditoriaService auditoriaService;

    @Override
    public List<Medico> listarMedicos() {
        return this.medicoRepository.findAll();
    }

    @Override
    public List<Medico> buscarPorEspecialidad(String codigo)
            throws BadRequestException {
        Especializacion e = this.especializacionService
                .buscarEspecializacionPorCod(codigo);
        return this.medicoRepository.findByEspecializacion(e);
    }

    @Override
    public RespuestaRs guardarMedico(MedicoRq medicoRq) throws BadRequestException {
        Optional<Medico> optmedico = this.medicoRepository.findByDocumento(
                medicoRq.getDocumento()
        );
        if (optmedico.isPresent()) {
            throw new BadRequestException("El número de documento ya está registrado");
        }

        optmedico = this.medicoRepository.findByRegistroProfesional(medicoRq.getRegistroProfesional());
        if (optmedico.isPresent()) {
            throw new BadRequestException("Existe otro medico con el registro profesional ingresado");
        }

        Optional<Especializacion> optEsp = this.especializacionRepository
                .findById(medicoRq.getEspecializacion());
        if (optEsp.isEmpty()) {
            throw new BadRequestException("La especialización no existe");
        }

        Medico medicoGuardar = this.convertToRqToEntidad(medicoRq, optEsp.get());
        medicoGuardar = this.medicoRepository.save(medicoGuardar);
        // Registrar auditoria
        auditoriaService.registrarAuditoria(
                "medico",
                medicoGuardar.getId(),
                "INSERT",
                null,
                medicoGuardar,
                "Registro de medico creado"
        );        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("Médico guardado exitosamente");
        rta.setStatus(200);
        return rta;
    }

    private Medico convertToRqToEntidad(MedicoRq medicoRq, Especializacion especialidad) {
        Medico medico = new Medico();
        medico.setNombres(medicoRq.getNombres());
        medico.setApellidos(medicoRq.getApellidos());
        medico.setDocumento(medicoRq.getDocumento());
        medico.setTipoDocumento(medicoRq.getTipoDocumento());
        medico.setRegistroProfesional(medicoRq.getRegistroProfesional());
        medico.setEspecializacion(especialidad);
        medico.setTelefono(medicoRq.getTelefono());
        return medico;
    }


    @Override
    public RespuestaRs actualizarMedico(MedicoRq medicoRq)
            throws BadRequestException {

        if (medicoRq.getId() == null) {
            throw new BadRequestException("El id del medico es obligatorio");
        }

        // 1. Buscar el paciente actual
        Optional<Medico> optMedico = medicoRepository.findById(medicoRq.getId());
        if (optMedico.isEmpty()) {
            throw new BadRequestException("El medico no existe para actualizar");
        }

        Medico medicoActual = optMedico.get();

        // Guardamos valores ANTES para auditoría
        String valoresAntes = medicoActual.toString();

        Optional<Especializacion> optEsp = especializacionRepository.findById(medicoRq.getEspecializacion());
        if (optEsp.isEmpty()) {
            throw new BadRequestException("La especializacion no existe");
        }
        Especializacion especializacion = optEsp.get();

        // 4. Actualizar datos
        medicoActual.setNombres(medicoRq.getNombres());
        medicoActual.setApellidos(medicoRq.getApellidos());
        medicoActual.setTipoDocumento(medicoRq.getTipoDocumento());
        medicoActual.setTipoDocumento(medicoRq.getTipoDocumento());
        medicoActual.setDocumento(medicoRq.getDocumento());
        medicoActual.setEspecializacion(especializacion);
        medicoActual.setTelefono(medicoRq.getTelefono());
        medicoActual.setRegistroProfesional(medicoRq.getRegistroProfesional());


        // 5. Guardar
        Medico actualizado = medicoRepository.save(medicoActual);

        // Valores después de actualización
        String valoresDespues = actualizado.toString();

        // 6. Registrar Auditoría
        auditoriaService.registrarAuditoria(
                "medico",
                actualizado.getId(),
                "UPDATE",
                valoresAntes,
                valoresDespues,
                "Medico actualizado"
        );

        // Respuesta
        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("Medico actualizado exitosamente");
        rta.setStatus(200);

        return rta;
    }

    @Override
    public RespuestaRs eliminarMedico(Integer idMedico) throws BadRequestException {

        Optional<Medico> optMedico = medicoRepository.findById(idMedico);

        if (optMedico.isEmpty()) {
            throw new BadRequestException("El medico no existe, no se puede eliminar");
        }

        Medico medico = optMedico.get();

        // Guardamos valores ANTES para auditoría
        String valoresAntes = medico.toString();

        // Eliminamos el paciente
        medicoRepository.delete(medico);

        // Auditoría
        auditoriaService.registrarAuditoria(
                "medico",
                medico.getId(),
                "DELETE",
                valoresAntes,
                null,
                "Medico eliminado"
        );

        // Respuesta
        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("Medico eliminado correctamente");
        rta.setStatus(200);

        return rta;
    }

}