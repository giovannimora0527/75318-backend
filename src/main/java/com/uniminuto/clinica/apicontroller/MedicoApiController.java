package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.MedicoApi;
import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.service.MedicoService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MedicoApiController implements MedicoApi {

    @Autowired
    private MedicoService medicoService;

    @Override
    public ResponseEntity<List<Medico>> listarMedicos() {
        return ResponseEntity.ok(medicoService.listarMedicos());
    }

    @Override
    public ResponseEntity<RespuestaRs> guardarMedico(Medico medico) {
        return ResponseEntity.ok(medicoService.guardarMedico(medico));
    }

    @Override
    public ResponseEntity<RespuestaRs> actualizarMedico(Medico medico) {
        try {
            medicoService.actualizarMedico(medico.getId(), medico);
            return ResponseEntity.ok(new RespuestaRs(200, "Médico actualizado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                new RespuestaRs(400, e.getMessage())
            );
        }
    }

    @Override
    public ResponseEntity<List<Medico>> listarMedicosporEspecialidad(String codigo) {
        return ResponseEntity.ok(medicoService.buscarPorEspecialidad(codigo));
    }
}
