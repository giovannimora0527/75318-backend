package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.model.CitaRq;
import com.uniminuto.clinica.model.CitaRs; 
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.repository.CitaRepository;
import com.uniminuto.clinica.repository.MedicoRepository;
import com.uniminuto.clinica.repository.PacienteRepository;
import com.uniminuto.clinica.service.CitaService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors; 
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CitaServiceImpl implements CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Override
    public RespuestaRs guardarCita(CitaRq citaNueva) throws BadRequestException {
        // Paso 1. Validar que los campos no estén vacíos
        this.validarCampos(citaNueva);

        // Paso 2. Consultar si existen el paciente y el medico
        Optional<Paciente> paciente = this.pacienteRepository.findById(citaNueva.getPacienteId());
        if (!paciente.isPresent()) {
            throw new BadRequestException("No existe el paciente con el ID proporcionado.");
        }

        Optional<Medico> medico = this.medicoRepository.findById(citaNueva.getMedicoId());
        if (!medico.isPresent()) {
            throw new BadRequestException("No existe el medico con el ID proporcionado.");
        }

        // Paso 3. Crear la nueva cita y setear los campos
        Cita nuevaCita = new Cita();
        nuevaCita.setPaciente(paciente.get());
        nuevaCita.setMedico(medico.get());
        nuevaCita.setFechaHora(citaNueva.getFechaHora());
        nuevaCita.setEstado(citaNueva.getEstado().toUpperCase());
        nuevaCita.setMotivo(citaNueva.getMotivo());

        // Paso 4. Guardar la cita
        this.citaRepository.save(nuevaCita);

        // Paso 5. Devolver respuesta OK
        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("La cita se ha guardado correctamente.");
        rta.setStatus(200);
        return rta;
    }

    private void validarCampos(CitaRq citaNueva) throws BadRequestException {
        if (citaNueva.getPacienteId() == null) {
            throw new BadRequestException("El campo 'pacienteId' es obligatorio.");
        }
        if (citaNueva.getMedicoId() == null) {
            throw new BadRequestException("El campo 'medicoId' es obligatorio.");
        }
        if (citaNueva.getFechaHora() == null) {
            throw new BadRequestException("El campo 'fechaHora' es obligatorio.");
        }
        if (citaNueva.getEstado() == null || citaNueva.getEstado().isBlank()) {
            throw new BadRequestException("El campo 'estado' es obligatorio.");
        }
    }

    @Override
    public List<CitaRs> listarCitasRecientes() {
        // 1. Obtiene las entidades de la base de datos
        List<Cita> citas = citaRepository.findAllByOrderByFechaHoraDesc();
        
        // 2. Mapea (transforma) las entidades a DTOs
        return citas.stream()
                    .map(this::mapToCitaRs)
                    .collect(Collectors.toList());
    }
    
    /**
     * Método auxiliar para transformar una entidad Cita a un DTO CitaRs.
     * @param cita La entidad Cita a mapear.
     * @return El DTO CitaRs resultante.
     */
    private CitaRs mapToCitaRs(Cita cita) {
        CitaRs dto = new CitaRs();
        dto.setId(cita.getId());
        dto.setFechaHora(cita.getFechaHora());
        dto.setEstado(cita.getEstado());
        dto.setMotivo(cita.getMotivo());
        
        // Aquí extraes solo la información que necesitas de las relaciones
        if (cita.getPaciente() != null) {
            dto.setPacienteId(cita.getPaciente().getId());
            dto.setNombreCompletoPaciente(cita.getPaciente().getNombres() + " " + cita.getPaciente().getApellidos());
        }
        if (cita.getMedico() != null) {
            dto.setMedicoId(cita.getMedico().getId());
            dto.setNombreCompletoMedico(cita.getMedico().getNombres() + " " + cita.getMedico().getApellidos());
        }
        return dto;
    }
}