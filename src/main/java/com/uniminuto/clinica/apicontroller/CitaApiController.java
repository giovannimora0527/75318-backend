package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.CitaRq;
import com.uniminuto.clinica.api.CitaApi;
import java.util.List;
import com.uniminuto.clinica.entity.Cita;
import org.apache.coyote.BadRequestException;
import com.uniminuto.clinica.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CitaApiController implements CitaApi {

    @Autowired
    private CitaService citaService;

    @Override
    public ResponseEntity<List<Cita>>
    listarCitasporPaciente(Long id)
            throws BadRequestException {
        return ResponseEntity.ok(this.citaService
                .buscarPorPaciente(id));
    }

    @Override
    public ResponseEntity<List<Cita>>
    listarCitasporMedico(Long id)
            throws BadRequestException {
        return ResponseEntity.ok(this.citaService
                .buscarPorMedico(id));
    }


    @Override
    public ResponseEntity<RespuestaRs> guardarCita(CitaRq citaNuevo)
            throws BadRequestException {
        return ResponseEntity.ok(this.citaService.guardarCita(citaNuevo));
    }

}
