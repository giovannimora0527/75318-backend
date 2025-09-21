package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.RecetaApi;
import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.model.RecetaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.service.RecetaService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * @author JAVIER-CUERVO
 */
@RestController
public class RecetaApiController implements RecetaApi {

    @Autowired
    private RecetaService recetaService;

    @Override
    public ResponseEntity<RespuestaRs> guardarReceta(RecetaRq recetaNueva) throws BadRequestException {
        if (recetaNueva.getCitaId() == null || recetaNueva.getMedicamentoId() == null) {
            throw new BadRequestException("El ID de la cita y medicamento son obligatorios");
        }

        recetaService.crearReceta(
                recetaNueva.getCitaId(),
                recetaNueva.getMedicamentoId(),
                recetaNueva.getDosis(),
                recetaNueva.getIndicaciones()
        );

        RespuestaRs respuesta = new RespuestaRs();
        respuesta.setMensaje("Receta creada exitosamente");
        respuesta.setStatus(200);

        return ResponseEntity.ok(respuesta);
    }

    @Override
    public ResponseEntity<List<Receta>> listarRecetasPorCita(String citaId) throws BadRequestException {
        if (citaId == null || citaId.trim().isEmpty()) {
            throw new BadRequestException("El ID de la cita es obligatorio");
        }

        List<Receta> recetas = recetaService.obtenerRecetasPorCita(citaId);

        if (recetas.isEmpty()) {
            throw new BadRequestException("No hay recetas guardadas para este ID");
        }

        return ResponseEntity.ok(recetas);
    }

    @Override
    public ResponseEntity<List<Receta>> listarTodas() {
        return ResponseEntity.ok(recetaService.obtenerTodas());
    }
}