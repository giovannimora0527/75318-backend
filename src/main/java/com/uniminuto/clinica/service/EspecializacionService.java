package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Especializacion;
import com.uniminuto.clinica.repository.EspecializacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class EspecializacionService {

    @Autowired
    private EspecializacionRepository especializacionRepository;

 
    public List<Especializacion> listarTodo() {
        return especializacionRepository.findAll();
    }

 
    public Especializacion buscarEspecializacionPorCodigo(String codigo) {
        return especializacionRepository.findByCodigoEspecializacion(codigo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se encontró la especialización con código: " + codigo));
    }

    public Especializacion guardarEspecializacion(Especializacion especializacion) {
        if (especializacionRepository.existsByCodigoEspecializacion(especializacion.getCodigoEspecializacion())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya existe una especialización con el código: " + especializacion.getCodigoEspecializacion());
        }
        return especializacionRepository.save(especializacion);
    }

 
    public Especializacion actualizarEspecializacion(Long id, Especializacion especializacion) {
        Optional<Especializacion> existente = especializacionRepository.findById(id);
        if (existente.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No existe una especialización con ID: " + id);
        }

        Especializacion actual = existente.get();
        actual.setNombre(especializacion.getNombre());
        actual.setDescripcion(especializacion.getDescripcion());
        actual.setCodigoEspecializacion(especializacion.getCodigoEspecializacion());

        return especializacionRepository.save(actual);
    }
}
