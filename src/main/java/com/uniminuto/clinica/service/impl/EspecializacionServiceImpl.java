package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Especializacion;
import com.uniminuto.clinica.repository.EspecializacionRepository;
import com.uniminuto.clinica.service.EspecializacionService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EspecializacionServiceImpl implements EspecializacionService {

    @Autowired
    private EspecializacionRepository repo;

    // Listar todas las especializaciones
    @Override
    public List<Especializacion> listarTodo() {
        return this.repo.findAll();
    }

    // Buscar una especialización por código
    @Override
    public Especializacion buscarEspecializacionPorCod(String codigo) throws BadRequestException {
        Optional<Especializacion> optEspc = this.repo.findByCodigoEspecializacion(codigo);
        if (!optEspc.isPresent()) {
            throw new BadRequestException("No se encuentra la especialización con código: " + codigo);
        }
        return optEspc.get();
    }

    // Crear una nueva especialización
    @Override
    public Especializacion guardarEspecializacion(Especializacion especializacion) throws BadRequestException {
        // Verificar si la especialización ya existe por su código
        if (repo.existsByCodigoEspecializacion(especializacion.getCodigoEspecializacion())) {
            throw new BadRequestException("Ya existe una especialización con el código: " + especializacion.getCodigoEspecializacion());
        }
        return repo.save(especializacion);
    }

    // Actualizar una especialización existente
    @Override
    public Especializacion actualizarEspecializacion(Long id, Especializacion especializacion) throws BadRequestException {
        Optional<Especializacion> optEspc = this.repo.findById(id);
        if (!optEspc.isPresent()) {
            throw new BadRequestException("No existe una especialización con el ID: " + id);
        }

        Especializacion existingEspecializacion = optEspc.get();
        existingEspecializacion.setNombre(especializacion.getNombre());
        existingEspecializacion.setDescripcion(especializacion.getDescripcion());
        existingEspecializacion.setCodigoEspecializacion(especializacion.getCodigoEspecializacion());

        return repo.save(existingEspecializacion);
    }
}
