package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Especializacion;
import com.uniminuto.clinica.repository.EspecializacionRepository;
import com.uniminuto.clinica.service.EspecializacionService;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EspecializacionServiceImpl implements EspecializacionService {

    @Autowired
    private EspecializacionRepository repo; 

    @Override
    public List<Especializacion> listarTodo() {
        return this.repo.findAll();
    }

    @Override
    public Especializacion buscarEspecializacionPorCod(String codigo)
            throws BadRequestException {
        Optional<Especializacion> optEspc = this.repo
                .findByCodigoEspecializacion(codigo);
        if (!optEspc.isPresent()) {         
            throw new BadRequestException("No se encuentra la especializacion");
        }

        return optEspc.get();
    }
    
    @Override
    public List<Especializacion> listarOrdenadoPorCodigoEspecializacion(boolean ascendente) {
        if (ascendente) {
            return this.repo.findAllByOrderByCodigoEspecializacionAsc();
        }
        return this.repo.findAllByOrderByCodigoEspecializacionDesc();
    }
    
    @Override
    public Especializacion GuardarEspecializacion(Especializacion especializacion) throws BadRequestException {
        
        if (especializacion.getId() == null) {
  
            Optional<Especializacion> existente = this.repo.findByCodigoEspecializacion(especializacion.getCodigoEspecializacion());
            if (existente.isPresent()) {
                throw new BadRequestException("Ya existe una especialización con este código: " + especializacion.getCodigoEspecializacion());
            }
        } else {

            Optional<Especializacion> especializacionBD = this.repo.findById(especializacion.getId());
            if (!especializacionBD.isPresent()) {
                throw new BadRequestException("No se encuentra la especialización con ID: " + especializacion.getId() + " para actualizar.");
            }

            Optional<Especializacion> duplicado = this.repo.findByCodigoEspecializacionAndIdNot(
                    especializacion.getCodigoEspecializacion(),
                    especializacion.getId()
            );
            if (duplicado.isPresent()) {
                throw new BadRequestException("El código de especialización: " + especializacion.getCodigoEspecializacion() + " ya está asignado a otra especialización.");
            }
        }

        return this.repo.save(especializacion);
    }


    @Override
    public void eliminarEspecializacion(Long id) {

        if (!this.repo.existsById(id)) {
             return; 
        }
        
      
        this.repo.deleteById(id);
    }
}