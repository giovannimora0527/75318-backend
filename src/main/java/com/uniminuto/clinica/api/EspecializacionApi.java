package com.uniminuto.clinica.api;

import com.uniminuto.clinica.model.EspecializacionRq;
import com.uniminuto.clinica.model.EspecializacionRs;
import com.uniminuto.clinica.model.RespuestaRs;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RequestMapping("/especializacion")
public interface EspecializacionApi {

    @RequestMapping(value = "/listar", produces = {"application/json"}, method = RequestMethod.GET)
    List<EspecializacionRs> listarEspecializaciones();

    @RequestMapping(value = "/buscar", produces = {"application/json"}, method = RequestMethod.GET)
    EspecializacionRs buscarPorCodigo(@RequestParam String codigo);

    @RequestMapping(value = "/guardar", consumes = {"application/json"}, produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<RespuestaRs> guardarEspecializacion(@RequestBody EspecializacionRq request);

    @RequestMapping(value = "/actualizar", consumes = {"application/json"}, produces = {"application/json"}, method = RequestMethod.POST)
    ResponseEntity<RespuestaRs> actualizarEspecializacion(@RequestBody EspecializacionRq request);
}
