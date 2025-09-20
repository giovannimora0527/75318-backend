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

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementación del servicio de citas que contiene la lógica de negocio
 * para la gestión de citas médicas en el sistema clínico.
 *
 * @author crash
 * @version 1.0
 * @since 2025-09-20
 */

@Service
public class CitaServiceImpl implements CitaService {

    /**
     * Repositorio JPA para operaciones CRUD sobre la entidad Cita.
     * Maneja la persistencia y consultas a la base de datos.
     */
    @Autowired
    private CitaRepository citaRepository;

    /**
     * Servicio de médicos utilizado para validar la existencia de médicos
     * antes de crear una cita.
     */
    @Autowired
    private MedicoService medicoService;

    /**
     * Servicio de pacientes utilizado para validar la existencia de pacientes
     * antes de crear una cita.
     */
    @Autowired
    private PacienteService pacienteService;

    /**
     * {@inheritDoc}
     *
     * Valida la existencia del paciente antes de buscar sus citas para
     * proporcionar un mensaje de error más claro en caso de ID inválido.
     *
     * @param id Long identificador que debe existir en la tabla paciente
     * @return List<Cita> citas asociadas al paciente, puede ser lista vacía
     * @throws BadRequestException si el paciente no existe
     */

    @Override
    public List<Cita> buscarPorPaciente(Long id) throws BadRequestException {
        // ✅ CORREGIDO: Validar que el paciente existe y luego buscar por ID
        pacienteService.buscarPacienteId(id); // Lanza excepción si no existe
        return citaRepository.findByPacienteId(id); // ✅ Ahora pasa el ID directamente
    }
    /**
     * {@inheritDoc}
     *
     * Implementación similar a buscarPorPaciente pero para médicos.
     * Garantiza que solo se busquen citas de médicos existentes.
     */
    @Override
    public List<Cita> buscarPorMedico(Long id) throws BadRequestException {
        // ✅ CORREGIDO: Validar que el médico existe y luego buscar por ID
        medicoService.buscarMedicoId(id); // Lanza excepción si no existe
        return citaRepository.findByMedicoId(id); // ✅ Ahora pasa el ID directamente
    }

    /**
     * {@inheritDoc}
     *
     * Valida los campos del request, verifica la existencia
     * de paciente y médico, crea y guarda la nueva cita.
     *
     * @param citaRq CitaRq objeto que contiene los datos de la nueva cita
     * @return RespuestaRs mensaje de éxito o error
     * @throws BadRequestException si faltan campos o no existen paciente/médico
     */
    @Override
    public RespuestaRs guardarCita(CitaRq citaRq)
            throws BadRequestException {
        // Paso 1. Validar que los campos llegue bien
        this.validarCampos(citaRq);
        // 2. Buscar al Paciente por ID
        Paciente paciente = pacienteService.buscarPacienteId(citaRq.getIdPaciente()); // Lanza error si no existe
        Medico medico = medicoService.buscarMedicoId(citaRq.getIdMedico()); // ...
    // Paso 4. Creo la cita y seteo los campos que lleguen del post
        validarCitaDuplicada(citaRq);


        Cita nuevo = new Cita();

        nuevo.setPaciente(paciente);  // ✅ Asignar objeto Paciente
        nuevo.setMedico(medico);    // ✅ Asignar objeto Medico
        nuevo.setFechaHora(citaRq.getFechaHora());
        /**
         * Normalización del motivo a minúsculas para consistencia
         */
        nuevo.setEstado(citaRq.getEstado().toLowerCase());
        nuevo.setMotivo(citaRq.getMotivo().toLowerCase());

//El método save() de JPA maneja la inserción y generación de ID automático
        this.citaRepository.save(nuevo);

    // Paso 5. Devuelve respuesta ok
    RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("La cita se ha guardado correctamente.");
        rta.setStatus(200);
        return rta;
}

    /**
     * Valida que no exista una cita similar (mismo paciente + médico + motivo)
     * en una ventana de tiempo de 20 minutos hacia atrás desde ahora.
     *
     /* @param citaRq CitaRq datos de la nueva cita a validar
     /* @throws BadRequestException si existe una cita similar reciente
     */
    private void validarCitaDuplicada(CitaRq citaRq) throws BadRequestException {
        // Buscar citas idénticas (mismo paciente, médico, motivo y fecha/hora)
        List<Cita> citasIdenticas = citaRepository.findCitasIdenticas(
                citaRq.getIdPaciente(),
                citaRq.getIdMedico(),
                citaRq.getMotivo(),
                citaRq.getFechaHora()
        );

        if (!citasIdenticas.isEmpty()) {
            throw new BadRequestException(
                    "Ya existe una cita idéntica (mismo paciente, médico, motivo y fecha/hora). " +
                            "No se permite duplicar citas exactas."
            );
        }
    }

    /**
     * Método privado que valida que todos los campos obligatorios del request
     * estén presentes y contengan valores válidos.
     *
     /* @param citaRq CitaRq objeto a validar
     /* @throws BadRequestException si algún campo obligatorio es nulo, vacío o inválido
     */

private void validarCampos(CitaRq citaRq)
        throws BadRequestException {
    /*
     * Validación de ID de paciente
     * Debe ser un Long no nulo
     */
    if (citaRq.getIdPaciente() == null) {
        throw new BadRequestException("El campo IdPaciente es obligatorio.");
    }
    /**
     * Validación de ID de médico
     * Debe ser un Long no nulo
     */
    if (citaRq.getIdMedico() == null) {
        throw new BadRequestException("El campo IdMedico es obligatorio.");
    }
    /**
     * Validación de fecha y hora de la cita
     * Debe ser un LocalDateTime no nulo
     */
    if (citaRq.getFechaHora() == null) {
        throw new BadRequestException("El campo FechaHora es obligatorio.");
    }
    /**
     * Validación de estado de la cita
     * No debe ser nulo ni contener solo espacios en blanco
     */
    if (citaRq.getEstado() == null
            || citaRq.getEstado().isBlank()
            || citaRq.getEstado().isEmpty()) {
        throw new BadRequestException("El campo Estado es obligatorio.");
    }
    /**
     * Validación de motivo de la cita
     * No debe ser nulo ni contener solo espacios en blanco
     */
    if (citaRq.getMotivo() == null
            || citaRq.getMotivo().isBlank()
            || citaRq.getMotivo().isEmpty()) {
        throw new BadRequestException("El campo Motivo es obligatorio.");
    }
}

    /**
     * {@inheritDoc}
     *
     * Lista todas las citas ordenadas por fecha y hora en orden descendente.
     * Utiliza Java 8 Streams para el procesamiento y ordenamiento de datos.
     *
     * @return List<Cita> lista ordenada donde las citas más recientes aparecen primero
     */
    @Override
    public List<Cita> listarCitasDesc() {
        return citaRepository.findAll()
                /**
                 * Conversión a Stream para operaciones funcionales
                 */
                .stream()
                /**
                 * Ordenamiento descendente por fecha y hora de la cita.
                 * c2.compareTo(c1) invierte el orden natural para obtener orden descendente:
                 * - c1: primera cita en la comparación
                 * - c2: segunda cita en la comparación
                 * - getFechaHora(): obtiene LocalDateTime de la cita
                 * - compareTo(): compara fechas/horas cronológicamente
                 */
                .sorted((c1, c2) -> c2.getFechaHora().compareTo(c1.getFechaHora())) // ✅ Orden descendente (más recientes primero)
                /**
                 * Materialización del Stream en una List inmutable
                 */
                .toList();
    }

    /**
     * {@inheritDoc}
     *
     * Valida la existencia del paciente antes de buscar sus citas para
     * proporcionar un mensaje de error más claro en caso de ID inválido.
     *
     * @param id Long identificador que debe existir en la tabla paciente
     * @return List<Cita> citas asociadas al paciente, puede ser lista vacía
     * @throws BadRequestException si el paciente no existe
     */
    @Override
    public Cita buscarCitaPorId(Long id) throws BadRequestException {
        return citaRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("No existe la cita con id: " + id));
    }
}
