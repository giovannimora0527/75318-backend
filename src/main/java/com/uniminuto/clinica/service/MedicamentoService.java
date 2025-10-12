package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Medicamento;

import java.util.List;
import java.util.Optional;

public interface MedicamentoService {

    List<Medicamento> listarTodos();

    Optional<Medicamento> buscarPorId(Long id);

    Medicamento guardar(Medicamento medicamento);

    Medicamento actualizar(Long id, Medicamento medicamento);

    void eliminar(Long id);
}
