package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Especializacion;
import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.repository.MedicoRepository;
import com.uniminuto.clinica.service.EspecializacionService;
import com.uniminuto.clinica.service.MedicoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class MedicoServiceImpl implements MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private EspecializacionService especializacionService;

    @Override
    public List<Medico> listarMedicos() {
        return medicoRepository.findAll();
    }

    @Override
    public List<Medico> buscarPorEspecialidad(String codigo) {
        Especializacion esp = especializacionService.buscarEspecializacionPorCod(codigo);
        return medicoRepository.findByEspecializacion(esp);
    }

    @Override
    public RespuestaRs guardarMedico(Medico medico) {
        medicoRepository.save(medico);
        return new RespuestaRs(200, "Médico guardado correctamente");
    }

    @Override
    public RespuestaRs actualizarMedico(Long id, Medico medico) {
        Medico existente = medicoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico no encontrado"));

        // ACTUALIZAR SOLO CAMPOS QUE EXISTEN EN TU ENTIDAD
        existente.setTipoDocumento(medico.getTipoDocumento());
        existente.setDocumento(medico.getDocumento());
        existente.setNombres(medico.getNombres());
        existente.setApellidos(medico.getApellidos());
        existente.setTelefono(medico.getTelefono());
        existente.setRegistroProfesional(medico.getRegistroProfesional());
        existente.setEspecializacion(medico.getEspecializacion());

        medicoRepository.save(existente);

        return new RespuestaRs(200, "Médico actualizado correctamente");
    }
}
