package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Paciente;
import java.util.List;
import java.util.Optional;

public interface PacienteService {
    List<Paciente> obtenerTodos();
    Optional<Paciente> buscarPorDocumento(String documento);
    Paciente guardar(Paciente paciente);
    void eliminar(Long id);
}
