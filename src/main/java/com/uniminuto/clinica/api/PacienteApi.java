<<<<<<< HEAD
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.uniminuto.clinica.api;


=======
package com.uniminuto.clinica.api;

>>>>>>> 602c738be275f7f1826ccf9ef7bcb734aeea96d1
import com.uniminuto.clinica.entity.Paciente;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
<<<<<<< HEAD
 * @author crash
=======
 * @author Darleys
>>>>>>> 602c738be275f7f1826ccf9ef7bcb734aeea96d1
 */
@CrossOrigin(origins = "*")
@RequestMapping("/paciente")
public interface PacienteApi {
    
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Paciente>> listarPacientes();
<<<<<<< HEAD
    //Funcion buscar por documento identidad
    @RequestMapping(value = "/buscar-NumDoc",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Paciente> encontrarPorDocumentoIdentidad(
       @RequestParam String numeroDocumento
    ) throws BadRequestException;

    @RequestMapping(value = "/listarEdadDes",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Paciente>> listarPacientesDes();

    @RequestMapping(value = "buscar-por-id",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Paciente> buscarPacienteId(
       @RequestParam Long id
=======
    
    
    @RequestMapping(value = "/buscar-x-documento",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Paciente> buscarPorDocumento(
      @RequestParam String documento
>>>>>>> 602c738be275f7f1826ccf9ef7bcb734aeea96d1
    ) throws BadRequestException;
}
