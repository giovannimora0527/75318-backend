package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Especializacion;
import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.repository.MedicoRepository;
import com.uniminuto.clinica.service.EspecializacionService;
import com.uniminuto.clinica.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MedicoServiceImpl implements MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private EspecializacionService especializacionService;

    // Listar todos los médicos
    @Override
    public List<Medico> listarMedicos() {
        return this.medicoRepository.findAll();
    }

    // Buscar médicos por especialidad usando el código
    @Override
    public List<Medico> buscarPorEspecialidad(String codigo) {
        try {
            // Obtener la especialización por su código
            Especializacion especializacion = this.especializacionService.buscarEspecializacionPorCod(codigo);
            // Buscar los médicos que corresponden a la especialización
            return this.medicoRepository.findByEspecializacion(especializacion);
        } catch (ResponseStatusException e) {
            // En caso de que no se encuentre la especialización, lanzar un error con un código adecuado
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se encuentra la especialización con el código: " + codigo);
        }
    }

}
