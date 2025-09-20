package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.repository.CitaRepository;
import com.uniminuto.clinica.repository.RecetaRepository;
import com.uniminuto.clinica.service.RecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author jartunduaga
 */
@Service
public class RecetaServiceImpl implements RecetaService {
    @Autowired
    private RecetaRepository recetaRepository;

    @Autowired
    private CitaRepository citaRepository;

    @Override
    public Receta guardarReceta(Long citaId, Receta receta) {
        Optional<Cita> citaOpcional = citaRepository.findById(citaId);
        if (!citaOpcional.isPresent()){
            throw new RuntimeException("La cita con ID " + citaId + " no existe");
        }
        receta.setCita(citaOpcional.get());
        return recetaRepository.save(receta);
    }

    @Override
    public List<Receta> listarPorCita(Long citaId) {
        return recetaRepository.findByCitaId(citaId);
    }

    @Override
    public List<Receta> listaRecetas() {
        return recetaRepository.findAll(Sort.by(Sort.Direction.DESC, "fechaCreacionRegistro"));
    }
}
