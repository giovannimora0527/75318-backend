package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.repository.CitaRepository;
import com.uniminuto.clinica.repository.MedicoRepository;
import com.uniminuto.clinica.repository.PacienteRepository;
import com.uniminuto.clinica.service.CitaService;
import com.uniminuto.clinica.service.MedicoService;
import com.uniminuto.clinica.service.PacienteService;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.CitaRq;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitaServiceImpl implements CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private PacienteService pacienteService;


    @Override
    public List<Cita> buscarPorPaciente(Long id)
            throws BadRequestException {
        try {
            Paciente e = this.pacienteService
                    .buscarPacienteId(id);
            return this.citaRepository.findByPacienteId(e);
        } catch (BadRequestException e) {
            throw e;
        }
    }
    @Override
    public List<Cita> buscarPorMedico(Long id)
            throws BadRequestException {
        try {
            Medico e = this.medicoService
                    .buscarMedicoId(id);
            return this.citaRepository.findByMedicoId(e);
        } catch (BadRequestException e) {
            throw e;
        }
    }

    @Override
    public RespuestaRs guardarCita(CitaRq citaRq)
            throws BadRequestException {
        // Paso 1. Validar que los campos llegue bien
        this.validarCampos(citaRq);
        // 2. Buscar al Paciente por ID
        Paciente paciente = pacienteService.buscarPacienteId(citaRq.getIdPaciente()); // Lanza error si no existe
        Medico medico = medicoService.buscarMedicoId(citaRq.getIdMedico()); // ...
    // Paso 4. Creo la cita y seteo los campos que lleguen del post
    Cita nuevo = new Cita();

        nuevo.setPaciente(paciente);  // ✅ Asignar objeto Paciente
        nuevo.setMedico(medico);    // ✅ Asignar objeto Medico
        nuevo.setFechaHora(citaRq.getFechaHora());
        nuevo.setEstado(citaRq.getEstado().toLowerCase());
        nuevo.setMotivo(citaRq.getMotivo().toLowerCase());


        this.citaRepository.save(nuevo);

    // Paso 5. Devuelve respuesta ok
    RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("La cita se ha guardado correctamente.");
        rta.setStatus(200);
        return rta;
}

private void validarCampos(CitaRq citaRq)
        throws BadRequestException {
    if (citaRq.getIdPaciente() == null) {
        throw new BadRequestException("El campo IdPaciente es obligatorio.");
    }
    if (citaRq.getIdMedico() == null) {
        throw new BadRequestException("El campo IdMedico es obligatorio.");
    }
    if (citaRq.getFechaHora() == null) {
        throw new BadRequestException("El campo FechaHora es obligatorio.");
    }
    if (citaRq.getEstado() == null
            || citaRq.getEstado().isBlank()
            || citaRq.getEstado().isEmpty()) {
        throw new BadRequestException("El campo Estado es obligatorio.");
    }
    if (citaRq.getMotivo() == null
            || citaRq.getMotivo().isBlank()
            || citaRq.getMotivo().isEmpty()) {
        throw new BadRequestException("El campo Motivo es obligatorio.");
    }
}
}
