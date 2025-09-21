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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.coyote.BadRequestException;
import java.time.LocalDateTime;

/**
 * Implementación del servicio de citas que gestiona la lógica de negocio para la gestión de citas médicas.
 */
@Service
public class CitaServiceImpl implements CitaService {

    /** Repositorio para la entidad Cita, permitiendo la interacción con la base de datos. */
    @Autowired
    private CitaRepository citaRepository;

    /** Repositorio para la entidad Paciente, permitiendo la interacción con la base de datos. */
    @Autowired
    private PacienteRepository pacienteRepository;

    /** Repositorio para la entidad Medico, permitiendo la interacción con la base de datos. */
    @Autowired
    private MedicoRepository medicoRepository;

    /**
     * Almacena una nueva cita en el sistema.
     *
     * Este método realiza las siguientes validaciones:
     * - Verifica que los campos obligatorios del DTO de solicitud no sean nulos.
     * - Confirma que el paciente y el médico asociados a la cita existan en la base de datos.
     * - Valida que no exista una cita previa para el mismo médico en la misma fecha y hora.
     *
     * @param citaNueva El DTO de solicitud (CitaRq) que contiene los datos de la nueva cita.
     * @return Un objeto RespuestaRs que indica el éxito de la operación.
     * @throws BadRequestException Si alguna de las validaciones falla (campos nulos, IDs inexistentes, cita duplicada).
     */
    @Override
    public RespuestaRs guardarCita(CitaRq citaNueva) throws BadRequestException {
        // Validación de campos obligatorios en el DTO de solicitud.
        this.validarCampos(citaNueva);

        // Validación de la existencia del paciente y el médico en la base de datos.
        Optional<Paciente> paciente = this.pacienteRepository.findById(citaNueva.getPacienteId());
        if (!paciente.isPresent()) {
            throw new BadRequestException("No existe el paciente con el ID proporcionado.");
        }

        Optional<Medico> medico = this.medicoRepository.findById(citaNueva.getMedicoId());
        if (!medico.isPresent()) {
            throw new BadRequestException("No existe el medico con el ID proporcionado.");
        }

        // Validación de duplicidad de cita para el mismo médico y la misma fecha/hora.
        List<Cita> citasExistentes = this.citaRepository.findByMedicoIdAndFechaHora(
            citaNueva.getMedicoId(),
            citaNueva.getFechaHora()
        );

        if (!citasExistentes.isEmpty()) {
            throw new BadRequestException("Ya existe una cita agendada para este médico en la fecha y hora seleccionada.");
        }

        // Creación y asignación de datos a la nueva entidad Cita.
        Cita nuevaCita = new Cita();
        nuevaCita.setPaciente(paciente.get());
        nuevaCita.setMedico(medico.get());
        nuevaCita.setFechaHora(citaNueva.getFechaHora());
        nuevaCita.setEstado(citaNueva.getEstado().toUpperCase());
        nuevaCita.setMotivo(citaNueva.getMotivo());

        // Almacenamiento de la entidad en la base de datos.
        this.citaRepository.save(nuevaCita);

        // Creación y retorno del objeto de respuesta con el mensaje de éxito.
        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("La cita se ha guardado correctamente.");
        rta.setStatus(200);
        return rta;
    }

    /**
     * Valida que los campos obligatorios del DTO de solicitud no sean nulos o vacíos.
     *
     * @param citaNueva El DTO de solicitud (CitaRq) a validar.
     * @throws BadRequestException Si alguno de los campos obligatorios es nulo o vacío.
     */
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

    /**
     * Obtiene una lista de las citas más recientes, ordenadas de forma descendente por fecha y hora.
     *
     * @return Una lista de DTOs (CitaRs) que representan las citas recientes.
     */
    @Override
    public List<CitaRs> listarCitasRecientes() {
        // Recuperación de las entidades Cita de la base de datos, ordenadas por fecha y hora descendente.
        List<Cita> citas = citaRepository.findAllByOrderByFechaHoraDesc();
        
        // Mapeo de la lista de entidades Cita a una lista de DTOs CitaRs.
        return citas.stream()
            .map(this::mapToCitaRs)
            .collect(Collectors.toList());
    }
    
    /**
     * Convierte una entidad Cita a un DTO CitaRs.
     *
     * Este método se encarga de extraer los datos de la entidad de la base de datos
     * y mapearlos al objeto DTO para ser devuelto a la capa de API.
     *
     * @param cita La entidad Cita de la que se extraerán los datos.
     * @return Un DTO CitaRs que contiene los datos de la cita.
     */
    private CitaRs mapToCitaRs(Cita cita) {
        CitaRs dto = new CitaRs();
        dto.setId(cita.getId());
        dto.setFechaHora(cita.getFechaHora());
        dto.setEstado(cita.getEstado());
        dto.setMotivo(cita.getMotivo());
        
        // Extracción y mapeo de la información de las relaciones (Paciente y Medico).
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