package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.PacienteApi;
import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.service.PacienteService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class PacienteController implements PacienteApi {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @Override
    public List<Paciente> obtenerTodos() {
        return pacienteService.obtenerTodos();
    }

    @Override
    public Optional<Paciente> buscarPorDocumento(String documento) {
        return pacienteService.buscarPorDocumento(documento);
    }

    @Override
    public Paciente guardar(Paciente paciente) {
        return pacienteService.guardar(paciente);
    }

    @Override
    public void eliminar(Long id) {
        pacienteService.eliminar(id);
    }
}
