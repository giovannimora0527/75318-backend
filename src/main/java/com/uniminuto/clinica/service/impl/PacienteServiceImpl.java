package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.repository.PacienteRepository;
import com.uniminuto.clinica.service.PacienteService;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author JAVIER-CUERVO
 */
@Service
public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;
    /*  *
    * @return
    */
    @Override
    public List<Paciente> listarTodosLosPacientes() {
        return this.pacienteRepository.findAll();
    }

    /**
     *
     * @param numero_documento
     * @return
     * @throws BadRequestException
     */

    @Override
    public Paciente encontrarPorFechaNacimiento(String fecha_nacimiento) throws BadRequestException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Paciente> listarPorFechaNacimiento() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Paciente encontrarPorNumeroDocumento(String numero_documento)
            throws BadRequestException {
        Optional<Paciente> optDocument;
        optDocument = this.pacienteRepository
                .findByNumeroDocumento(numero_documento);
        if (!optDocument.isPresent()) {
            throw new BadRequestException("No existe el paciente");
        }
        return optDocument.get();
    }

    public List<Paciente> listarPorEdadDescendente() {
        return this.pacienteRepository.findAllByFechaNacimientoDesc();
    }

    private static class PacienteRepository {

        public PacienteRepository() {
        }

        private List<Paciente> findAll() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        private Optional<Paciente> findByNumeroDocumento(String numero_documento) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        private List<Paciente> findAllByFechaNacimientoDesc() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }
}
