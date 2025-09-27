<<<<<<< HEAD
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uniminuto.clinica.api;
=======
package com.uniminuto.clinica.api;

>>>>>>> 602c738be275f7f1826ccf9ef7bcb734aeea96d1
import com.uniminuto.clinica.entity.Especializacion;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
<<<<<<< HEAD
=======

>>>>>>> 602c738be275f7f1826ccf9ef7bcb734aeea96d1
/**
 *
 * @author lmora
 */
@CrossOrigin(origins = "*")
@RequestMapping("/especializacion")
public interface EspecializacionApi {
    
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Especializacion>> listarEspecializaciones();
    
    
    @RequestMapping(value = "/buscar-por-codigo",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Especializacion> buscarPorCodigo(
      @RequestParam String codigo
    ) throws BadRequestException;
}
