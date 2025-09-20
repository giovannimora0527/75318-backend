package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.repository.CitaRepository;
import com.uniminuto.clinica.repository.RecetaRepository;
import com.uniminuto.clinica.service.RecetaService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
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
            throw new RuntimeException("No existe el paciente");
        }
        receta.setCita(citaOpcional.get());
        return recetaRepository.save(receta);
    }

    @Override
    public List<Receta> listarPorCita(Long citaId) {
        return recetaRepository.findByCitaId(citaId);
    }
}
