package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Especializacion;
import com.uniminuto.clinica.repository.EspecializacionRepository;
import com.uniminuto.clinica.service.EspecializacionService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EspecializacionServiceImpl implements EspecializacionService {

    @Autowired
    private EspecializacionRepository repo;

    @Override
    public List<Especializacion> listarTodo() {
        return this.repo.findAll();
    }

    @Override
    public Especializacion buscarEspecializacionPorCod(String codigo) {
        return repo.findByCodigoEspecializacion(codigo)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.BAD_REQUEST,
                                "No se encuentra la especialización"
                        )
                );
    }

    @Override
    public Especializacion actualizar(Long id, Especializacion especializacion) {
        return repo.findById(id)
            .map(existente -> {
                existente.setNombre(especializacion.getNombre());
                existente.setDescripcion(especializacion.getDescripcion());
                existente.setCodigoEspecializacion(especializacion.getCodigoEspecializacion());
                return repo.save(existente);
            })
            .orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Especialización no encontrada con id: " + id
                )
            );
    }

    @Override
    public Especializacion crear(Especializacion especializacion) {
        return repo.save(especializacion);
    }
}
