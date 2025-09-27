package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Especializacion;
import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.repository.MedicoRepository;
import com.uniminuto.clinica.service.EspecializacionService;
import com.uniminuto.clinica.service.MedicoService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lmora
 */
@Service
public class MedicoServiceImpl implements MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private EspecializacionService especializacionService;

    @Override
    public List<Medico> listarMedicos() {
        return this.medicoRepository.findAll();
    }

    @Override
    public List<Medico> buscarPorEspecialidad(String codigo)
            throws BadRequestException {
        try {
            Especializacion e = this.especializacionService
                    .buscarEspecializacionPorCod(codigo);
            return this.medicoRepository.findByEspecializacion(e);
        } catch (BadRequestException e) {
            throw e;
        }
    }

<<<<<<< HEAD
    @Override
    public Medico buscarMedicoId(Long id)
            throws BadRequestException {
        return this.medicoRepository.findById(id)
                .orElseThrow(() -> new BadRequestException(
                        "No se encontró el médico con id: " + id));
    }

=======
>>>>>>> 602c738be275f7f1826ccf9ef7bcb734aeea96d1
}
