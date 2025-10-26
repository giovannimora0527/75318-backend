package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.entity.FormulaMedica;
import com.uniminuto.clinica.service.FormulaMedicaService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clinica/v1/formula-medica")
@CrossOrigin(origins = "http://localhost:4200") 
public class FormulaMedicaApiController {

    @Autowired
    private FormulaMedicaService formulaMedicaService;

    @GetMapping("/listar")
    public ResponseEntity<List<FormulaMedica>> listarFormulas() {
        return ResponseEntity.ok(this.formulaMedicaService.listarFormulas());
    }

    @PostMapping("/guardar")
    public ResponseEntity<FormulaMedica> guardarFormula(@RequestBody FormulaMedica formula)
            throws BadRequestException {
        return ResponseEntity.ok(this.formulaMedicaService.guardarFormula(formula));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<FormulaMedica> actualizarFormula(
            @PathVariable Long id,
            @RequestBody FormulaMedica formula
    ) throws BadRequestException {
        return ResponseEntity.ok(this.formulaMedicaService.actualizarFormula(id, formula));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarFormula(@PathVariable Long id)
            throws BadRequestException {
        this.formulaMedicaService.eliminarFormula(id);
        return ResponseEntity.ok().build();
    }
}
