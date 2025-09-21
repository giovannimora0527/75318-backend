package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.PacienteApi;
import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.service.PacienteService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author JAVIER-CUERVO
 */
@RestController
public class PacienteApiController implements PacienteApi {

    @Autowired
    private PacienteService pacienteService;

    @Override
    public ResponseEntity<List<Paciente>> listarPaciente() {
        return ResponseEntity.ok(pacienteService.listarTodosLosPacientes());
    }
    public ResponseEntity<PacienteService> buscarPorFechaNacimiento(String fecha_nacimiento)
            throws BadRequestException {   
        return null;
    }

    @Override
    public ResponseEntity<Paciente> buscarPacientePorNumeroDocumento(String numero_documento) throws BadRequestException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ResponseEntity<Paciente> buscarPacientePorFechaNacimiento(String fecha_nacimiento) throws BadRequestException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Paciente> obtenerPacientesOrdenados() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}

