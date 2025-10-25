<<<<<<< HEAD
package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.PacienteRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.repository.PacienteRepository;
import com.uniminuto.clinica.service.PacienteService;

=======
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.repository.PacienteRepository;
import com.uniminuto.clinica.service.PacienteService;
>>>>>>> origin/916724_BrayanEscorcha
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
<<<<<<< HEAD
 * @author lmora
 */
@Service
public class PacienteServiceImpl implements PacienteService {

=======
 * @author Brayan Escorcha
 */

@Service
public class PacienteServiceImpl implements PacienteService{
     
>>>>>>> origin/916724_BrayanEscorcha
    @Autowired
    private PacienteRepository pacienteRepository;


<<<<<<< HEAD

    @Override
    public Paciente buscarPacientePorDocumento(String documento) throws BadRequestException {
        return null;
    }

    @Override
    public List<Paciente> encomntrarPorNombre(String nombresPaciente) throws BadRequestException {
        List<Paciente> pacientes = this.pacienteRepository.findByNombres(nombresPaciente);
        if (pacientes.isEmpty()) {
            throw new BadRequestException("No existe ningún paciente con ese nombre.");
        }
        return pacientes;
    }


    @Override
    public List<Paciente> listaTodosLosPacientes() {
        return null;
    }

    @Override
    public List<Paciente> listarOrdenadoPorFechaNacimiento(boolean ascendente) {
        if (ascendente) {
            return this.pacienteRepository.findAllByOrderByFechaNacimientoAsc();
        } else {
            return this.pacienteRepository.findAllByOrderByFechaNacimientoDesc();
        }
    }

    @Override
    public RespuestaRs guardarPaciente(PacienteRq pacienteNuevo)

        throws BadRequestException {

            // Paso 1. Validar que los campos llegue bien
            this.validarCampos(pacienteNuevo);
            // Paso 2. Consulto si existe el paiente por username
            Optional<Paciente> optUser = this.pacienteRepository
                    .findBynombres(pacienteNuevo.getNombres().toLowerCase());
            if (optUser.isPresent()) {
                // Paso 3. Si existe lanzo error que ya existe el paciente
                throw new BadRequestException("El paciente ya existe.");
            }
            // Paso 4. Creo el paciente y seteo los campos que lleguen del post
            Paciente nuevo = new Paciente();
            nuevo.setNombres(pacienteNuevo.getNombres());
            nuevo.setApellidos(pacienteNuevo.getApellidos());
            nuevo.setNumeroDocumento(pacienteNuevo.getNumeroDocumento());
            nuevo.setFechaNacimiento(pacienteNuevo.getFechaNacimiento());
            nuevo.setGenero(pacienteNuevo.getGenero());
            nuevo.setTelefono(pacienteNuevo.getTelefono());
            nuevo.setDireccion(pacienteNuevo.getDireccion());
            this.pacienteRepository.save(nuevo);

            // Paso 5. Devuelve respuesta ok
            RespuestaRs rta = new RespuestaRs();
            rta.setMensaje("El paciente se ha guardado correctamente.");
            rta.setStatus(200);
            return rta;

        }

        private void validarCampos(PacienteRq pacienteNuevo)
            throws BadRequestException {
        if (pacienteNuevo.getNombres() == null
                || pacienteNuevo.getNombres ().isBlank()
                || pacienteNuevo.getNombres().isEmpty()) {
            throw new BadRequestException("El campo nombre es obligatorio.");
        }
        if (pacienteNuevo.getApellidos() == null
                || pacienteNuevo.getApellidos().isBlank()
                || pacienteNuevo.getApellidos().isEmpty()) {
            throw new BadRequestException("El campo apellido es obligatorio.");
        }
        if (pacienteNuevo.getNumeroDocumento() == null
                || pacienteNuevo.getNumeroDocumento().isBlank()
                || pacienteNuevo.getNumeroDocumento().isEmpty()) {
            throw new BadRequestException("El campo número de documento es obligatorio.");
        }
        if (pacienteNuevo.getFechaNacimiento() == null
                || pacienteNuevo.getFechaNacimiento().isBlank()
                || pacienteNuevo.getFechaNacimiento().isEmpty()) {
            throw new BadRequestException("El campo fecha de nacimiento es obligatorio.");
        }
        if (pacienteNuevo.getGenero() == null
                || pacienteNuevo.getGenero().isBlank()
                || pacienteNuevo.getGenero().isEmpty()) {
            throw new BadRequestException("El campo género es obligatorio.");
        }
        if (pacienteNuevo.getTelefono()    == null
                || pacienteNuevo.getTelefono().isBlank()
                || pacienteNuevo.getTelefono().isEmpty()) {
            throw new BadRequestException("El campo teléfono es obligatorio.");
        }
        if (pacienteNuevo.getDireccion() == null
                || pacienteNuevo.getDireccion().isBlank()
                || pacienteNuevo.getDireccion().isEmpty()) {
            throw new BadRequestException("El campo dirección es obligatorio.");
        }
    }

    @Override
    public RespuestaRs actualizarPaciente(PacienteRq pacienteNuevo) throws BadRequestException {
        return null;
    }
}

=======
    @Override
    public List<Paciente> ListarTodosLosPacientes() {
        return this.pacienteRepository.findAll();
    }

    @Override
    public Paciente EncontrarPorNombre(String nombrePaciente) 
            throws BadRequestException {
        Optional<Paciente> optUser = this.pacienteRepository
                .findByUsername(nombrePaciente);
        if (!optUser.isPresent()) {
            throw new BadRequestException("No existe el usuario");
        }
        
        return optUser.get();
    }

    
    }
>>>>>>> origin/916724_BrayanEscorcha


