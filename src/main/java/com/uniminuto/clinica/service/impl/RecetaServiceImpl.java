package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.repository.RecetaRepository;
import com.uniminuto.clinica.service.RecetaService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RecetaServiceImpl implements RecetaService {

    private final RecetaRepository recetaRepository;

    public RecetaServiceImpl(RecetaRepository recetaRepository) {
        this.recetaRepository = recetaRepository;
    }

    @Override
    public Receta guardarReceta(Receta receta) {
        return recetaRepository.save(receta);
    }

    @Override
    public List<Receta> obtenerTodas() {
        return recetaRepository.findAll();
    }

    @Override
    public Receta obtenerPorId(Long id) {
        return recetaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Receta> listarRecetaPorFechaCreacionDesc() {
        return recetaRepository.findAllByOrderByFechaCreacionDesc();
    }
}
