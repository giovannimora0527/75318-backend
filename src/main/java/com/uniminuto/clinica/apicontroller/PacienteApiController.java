package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.PacienteApi;
import com.uniminuto.clinica.model.PacienteRq;
import com.uniminuto.clinica.model.PacienteRs;
import com.uniminuto.clinica.service.PacienteService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException; 

@RestController
public class PacienteApiController implements PacienteApi {

    private final PacienteService pacienteService;

    // Inyección de dependencias a través del constructor (buena práctica)
    public PacienteApiController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @Override
    public List<PacienteRs> obtenerTodos() {
        return pacienteService.obtenerTodos();
    }

    @Override
    public Optional<PacienteRs> buscarPorDocumento(String documento) {
        return pacienteService.buscarPorDocumento(documento);
    }

   @Override
    public PacienteRs guardar(@RequestBody PacienteRq pacienteRq) throws BadRequestException {
    return pacienteService.guardar(pacienteRq);
    }
    
    @Override
    public void eliminar(Long id) {
        pacienteService.eliminar(id);
    }

    @Override
    public List<PacienteRs> obtenerPacientesOrdenadosPorFechaNacimiento() {
        return pacienteService.obtenerPacientesOrdenadosPorFechaNacimiento();
    }
}