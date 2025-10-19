package com.uniminuto.clinica.service.impl;


import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.model.PacienteRq;
import com.uniminuto.clinica.repository.PacienteRepository;
import com.uniminuto.clinica.service.PacienteService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


/**
 *
 * @author jartunduaga
 */
@Service
public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;


    @Override
    public List<Paciente> listarLosPacientes() {
        return this.pacienteRepository.findAll();
    }


    @Override
    public Paciente encontrarPorDocumento(String numeroDocumento)
            throws BadRequestException {
        Optional<Paciente> optUser = this.pacienteRepository
                .findByNumeroDocumento(numeroDocumento);
        if (!optUser.isPresent()) {
            throw new BadRequestException("No existe el paciente");
        }

        return optUser.get();
    }

    @Override
    public List<Paciente> listarPacientePorFechaNacimiento(String orden) {
        Sort.Direction direccion = "asc".equalsIgnoreCase(orden)
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;
        return pacienteRepository.findAll(Sort.by(direccion, "fechaNacimiento"));
    }

    @Override
    public Paciente guardarPaciente(PacienteRq pacienteRq) throws BadRequestException {
        // Validar que no exista un paciente con el mismo número de documento
        if (pacienteRepository.existsByNumeroDocumento(pacienteRq.getNumeroDocumento())) {
            throw new BadRequestException("Ya existe un paciente con este número de documento");
        }

        // Validar que no exista un paciente con el mismo email
        if (pacienteRq.getEmail() != null && !pacienteRq.getEmail().isEmpty()
                && pacienteRepository.existsByEmail(pacienteRq.getEmail())) {
            throw new BadRequestException("Ya existe un paciente con este email");
        }

        // Mapear PacienteRq a Paciente
        Paciente paciente = new Paciente();
        paciente.setUsuarioId(pacienteRq.getUsuarioId());
        paciente.setTipoDocumento(pacienteRq.getTipoDocumento());
        paciente.setNumeroDocumento(pacienteRq.getNumeroDocumento());
        paciente.setNombres(pacienteRq.getNombres());
        paciente.setApellidos(pacienteRq.getApellidos());
        paciente.setFechaNacimiento(pacienteRq.getFechaNacimiento());
        paciente.setGenero(pacienteRq.getGenero());
        paciente.setTelefono(pacienteRq.getTelefono());
        paciente.setDireccion(pacienteRq.getDireccion());
        paciente.setEmail(pacienteRq.getEmail());
        paciente.setActivo(true);
        paciente.setFechaRegistro(LocalDateTime.now());

        return pacienteRepository.save(paciente);
    }

    @Override
    public Paciente actualizarPaciente(Long id, PacienteRq pacienteRq) throws BadRequestException {
        // Buscar el paciente por ID
        Optional<Paciente> optPaciente = pacienteRepository.findById(id);
        if (!optPaciente.isPresent()) {
            throw new BadRequestException("No existe un paciente con el ID: " + id);
        }

        Paciente paciente = optPaciente.get();

        // Validar que el nuevo número de documento no esté en uso por otro paciente
        if (!paciente.getNumeroDocumento().equals(pacienteRq.getNumeroDocumento())
                && pacienteRepository.existsByNumeroDocumentoAndIdNot(pacienteRq.getNumeroDocumento(), id)) {
            throw new BadRequestException("Ya existe otro paciente con este número de documento");
        }

        // Validar que el nuevo email no esté en uso por otro paciente
        if (pacienteRq.getEmail() != null && !pacienteRq.getEmail().isEmpty()
                && !pacienteRq.getEmail().equals(paciente.getEmail())
                && pacienteRepository.existsByEmailAndIdNot(pacienteRq.getEmail(), id)) {
            throw new BadRequestException("Ya existe otro paciente con este email");
        }

        // Actualizar los campos
        paciente.setUsuarioId(pacienteRq.getUsuarioId());
        paciente.setTipoDocumento(pacienteRq.getTipoDocumento());
        paciente.setNumeroDocumento(pacienteRq.getNumeroDocumento());
        paciente.setNombres(pacienteRq.getNombres());
        paciente.setApellidos(pacienteRq.getApellidos());
        paciente.setFechaNacimiento(pacienteRq.getFechaNacimiento());
        paciente.setGenero(pacienteRq.getGenero());
        paciente.setTelefono(pacienteRq.getTelefono());
        paciente.setDireccion(pacienteRq.getDireccion());
        paciente.setEmail(pacienteRq.getEmail());

        return pacienteRepository.save(paciente);
    }

}
