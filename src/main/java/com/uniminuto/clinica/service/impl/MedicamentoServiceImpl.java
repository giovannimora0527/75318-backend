package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Medicamento;
import com.uniminuto.clinica.repository.MedicamentoRepository;
import com.uniminuto.clinica.service.MedicamentoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MedicamentoServiceImpl implements MedicamentoService {

    private final MedicamentoRepository medicamentoRepository;

    public MedicamentoServiceImpl(MedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medicamento> listarTodos() {
        return medicamentoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Medicamento> buscarPorId(Long id) {
        return medicamentoRepository.findById(id);
    }

    @Override
    public Medicamento guardar(Medicamento medicamento) {
        medicamento.setFechaCreacionRegistro(LocalDateTime.now());
        // fechaModificacionRegistro se deja nula al crear
        return medicamentoRepository.save(medicamento);
    }

    @Override
    public Medicamento actualizar(Long id, Medicamento medicamento) {
        return medicamentoRepository.findById(id).map(existente -> {
            existente.setNombre(medicamento.getNombre());
            existente.setPresentacion(medicamento.getPresentacion());
            existente.setDescripcion(medicamento.getDescripcion());
            existente.setFechaCompra(medicamento.getFechaCompra());
            existente.setFechaVence(medicamento.getFechaVence());
            return medicamentoRepository.save(existente);
        }).orElseThrow(() -> new RuntimeException("Medicamento no encontrado con id: " + id));
    }

    @Override
    public void eliminar(Long id) {
        medicamentoRepository.deleteById(id);
    }
}
