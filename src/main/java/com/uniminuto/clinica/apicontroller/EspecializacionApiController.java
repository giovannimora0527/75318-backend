package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.EspecializacionApi;
import com.uniminuto.clinica.entity.Especializacion;
import com.uniminuto.clinica.service.EspecializacionService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/especializacion")
public class EspecializacionApiController implements EspecializacionApi {

    @Autowired
    private EspecializacionService servicio;

    @Override
    @GetMapping("/listar")
    public ResponseEntity<List<Especializacion>> listarEspecializaciones() {
        List<Especializacion> lista = servicio.listarTodo();
        return ResponseEntity.ok(lista);
    }

    @Override
    @GetMapping("/buscar-por-codigo")
    public ResponseEntity<Especializacion> buscarPorCodigo(@RequestParam String codigo)
            throws BadRequestException {
        Especializacion esp = servicio.buscarEspecializacionPorCod(codigo);
        return ResponseEntity.ok(esp);
    }

    @Override
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Especializacion> actualizar(@PathVariable("id") Long id,
                                                      @RequestBody Especializacion especializacion) {
        Especializacion actualizado = servicio.actualizar(id, especializacion);
        return ResponseEntity.ok(actualizado);
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearEspecializacion(@RequestBody Especializacion especializacion) {
        try {
            if (especializacion.getCodigoEspecializacion() == null || especializacion.getCodigoEspecializacion().isEmpty()) {
                return ResponseEntity.badRequest().body("El código de la especialización es obligatorio");
            }

            Especializacion nueva = servicio.crear(especializacion);
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al crear la especialización: " + e.getMessage());
        }
    }
}
