package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.EspecializacionApi;
import com.uniminuto.clinica.model.EspecializacionRq;
import com.uniminuto.clinica.model.EspecializacionRs;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.service.EspecializacionService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class EspecializacionApiController implements EspecializacionApi {

    private final EspecializacionService service;

    @Override
    public List<EspecializacionRs> listarEspecializaciones() {
        return service.listarEspecializaciones();
    }

    @Override
    public EspecializacionRs buscarPorCodigo(String codigo) {
        return service.buscarPorCodigo(codigo);
    }

    @Override
    public ResponseEntity<RespuestaRs> guardarEspecializacion(EspecializacionRq request) {
        try {
            RespuestaRs r = service.guardarEspecializacion(request);
            return ResponseEntity.ok(r);
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(new RespuestaRs(400, e.getMessage()));
        }
    }

    @Override
    public ResponseEntity<RespuestaRs> actualizarEspecializacion(EspecializacionRq request) {
        try {
            RespuestaRs r = service.actualizarEspecializacion(request);
            return ResponseEntity.ok(r);
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(new RespuestaRs(400, e.getMessage()));
        }
    }
}
