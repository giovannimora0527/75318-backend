[33mcommit 4076c78fa59ded75f7ea8644d6b085971bbbc3cf[m
Author: Colombia21 <amecale05@gmail.com>
Date:   Sun Oct 19 22:50:08 2025 -0500

    Guardando cambios antes de cambiar de rama

[1mdiff --git a/pom.xml b/pom.xml[m
[1mindex 1448f80..6596522 100644[m
[1m--- a/pom.xml[m
[1m+++ b/pom.xml[m
[36m@@ -121,4 +121,4 @@[m
         </plugins>[m
     </build>[m
 [m
[31m-</project>[m
[32m+[m[32m</project>[m
\ No newline at end of file[m
[1mdiff --git a/src/main/java/com/uniminuto/clinica/api/PacienteApi.java b/src/main/java/com/uniminuto/clinica/api/PacienteApi.java[m
[1mindex df9ca2d..9cf4371 100644[m
[1m--- a/src/main/java/com/uniminuto/clinica/api/PacienteApi.java[m
[1m+++ b/src/main/java/com/uniminuto/clinica/api/PacienteApi.java[m
[36m@@ -5,6 +5,7 @@[m [mimport java.util.List;[m
 import org.apache.coyote.BadRequestException;[m
 import org.springframework.http.ResponseEntity;[m
 import org.springframework.web.bind.annotation.CrossOrigin;[m
[32m+[m[32mimport org.springframework.web.bind.annotation.RequestBody;[m
 import org.springframework.web.bind.annotation.RequestMapping;[m
 import org.springframework.web.bind.annotation.RequestMethod;[m
 import org.springframework.web.bind.annotation.RequestParam;[m
[36m@@ -13,11 +14,7 @@[m [mimport org.springframework.web.bind.annotation.RequestParam;[m
 @RequestMapping("/paciente")[m
 public interface PacienteApi {[m
 [m
[31m-    /**[m
[31m-     * Lista los usuarios de la bd.[m
[31m-     *[m
[31m-     * @return[m
[31m-     */[m
[32m+[m[41m    [m
     @RequestMapping(value = "/listar",[m
             produces = {"application/json"},[m
             consumes = {"application/json"},[m
[36m@@ -33,12 +30,7 @@[m [mpublic interface PacienteApi {[m
             @RequestParam String numeroDocumento)[m
             throws BadRequestException;[m
 [m
[31m-[m
[31m-    /**[m
[31m-     * Lista los usuarios de la bd.[m
[31m-     *[m
[31m-     * @return[m
[31m-     */[m
[32m+[m[41m   [m
     @RequestMapping(value = "/listar-orden-fecha-nacimiento",[m
             produces = {"application/json"},[m
             consumes = {"application/json"},[m
[36m@@ -46,4 +38,13 @@[m [mpublic interface PacienteApi {[m
     ResponseEntity<List<Paciente>> listarPacientesXOrden([m
             @RequestParam String orden[m
     );[m
[31m-}[m
[32m+[m
[32m+[m[41m    [m
[32m+[m[32m    @RequestMapping(value = "/guardar-o-actualizar",[m
[32m+[m[32m            produces = {"application/json"},[m
[32m+[m[32m            consumes = {"application/json"},[m
[32m+[m[32m            method = RequestMethod.POST)[m
[32m+[m[32m    ResponseEntity<Paciente> guardarOActualizarPaciente([m
[32m+[m[32m            @RequestBody Paciente paciente)[m
[32m+[m[32m            throws BadRequestException;[m
[32m+[m[32m}[m
\ No newline at end of file[m
[1mdiff --git a/src/main/java/com/uniminuto/clinica/apicontroller/PacienteApiController.java b/src/main/java/com/uniminuto/clinica/apicontroller/PacienteApiController.java[m
[1mindex 81ea574..9671819 100644[m
[1m--- a/src/main/java/com/uniminuto/clinica/apicontroller/PacienteApiController.java[m
[1m+++ b/src/main/java/com/uniminuto/clinica/apicontroller/PacienteApiController.java[m
[36m@@ -9,10 +9,7 @@[m [mimport org.springframework.beans.factory.annotation.Autowired;[m
 import org.springframework.http.ResponseEntity;[m
 import org.springframework.web.bind.annotation.RestController;[m
 [m
[31m-/**[m
[31m- *[m
[31m- * @author lmora[m
[31m- */[m
[32m+[m
 @RestController[m
 public class PacienteApiController implements PacienteApi {[m
 [m
[36m@@ -34,4 +31,9 @@[m [mpublic class PacienteApiController implements PacienteApi {[m
     public ResponseEntity<List<Paciente>> listarPacientesXOrden(String orden) {[m
         return ResponseEntity.ok(pacienteService.listarOrdenadoPorFechaNacimiento(orden.equals("asc")));[m
     }[m
[31m-}[m
[32m+[m
[32m+[m[32m    @Override[m
[32m+[m[32m    public ResponseEntity<Paciente> guardarOActualizarPaciente(Paciente paciente) throws BadRequestException {[m
[32m+[m[32m        return ResponseEntity.ok(pacienteService.guardarOActualizarPaciente(paciente));[m
[32m+[m[32m    }[m
[32m+[m[32m}[m
\ No newline at end of file[m
[1mdiff --git a/src/main/java/com/uniminuto/clinica/entity/Paciente.java b/src/main/java/com/uniminuto/clinica/entity/Paciente.java[m
[1mindex 49b7874..089c9d8 100644[m
[1m--- a/src/main/java/com/uniminuto/clinica/entity/Paciente.java[m
[1m+++ b/src/main/java/com/uniminuto/clinica/entity/Paciente.java[m
[36m@@ -53,4 +53,4 @@[m [mpublic class Paciente implements Serializable {[m
 [m
     @Column(name = "direccion")[m
     private String direccion;[m
[31m-}[m
[32m+[m[32m}[m
\ No newline at end of file[m
[1mdiff --git a/src/main/java/com/uniminuto/clinica/repository/PacienteRepository.java b/src/main/java/com/uniminuto/clinica/repository/PacienteRepository.java[m
[1mindex 302fcf7..e8932e1 100644[m
[1m--- a/src/main/java/com/uniminuto/clinica/repository/PacienteRepository.java[m
[1m+++ b/src/main/java/com/uniminuto/clinica/repository/PacienteRepository.java[m
[36m@@ -11,8 +11,11 @@[m [mimport java.util.Optional;[m
 public interface PacienteRepository extends JpaRepository<Paciente, Integer> {[m
 [m
     Optional<Paciente> findByNumeroDocumento(String documento);[m
[32m+[m[41m    [m
[32m+[m[41m  [m
[32m+[m[32m    Optional<Paciente> findByNumeroDocumentoAndIdNot(String documento, Integer id);[m
 [m
     List<Paciente> findAllByOrderByFechaNacimientoAsc();[m
 [m
     List<Paciente> findAllByOrderByFechaNacimientoDesc();[m
[31m-}[m
[32m+[m[32m}[m
\ No newline at end of file[m
[1mdiff --git a/src/main/java/com/uniminuto/clinica/service/PacienteService.java b/src/main/java/com/uniminuto/clinica/service/PacienteService.java[m
[1mindex b4a5b8e..3e233c4 100644[m
[1m--- a/src/main/java/com/uniminuto/clinica/service/PacienteService.java[m
[1m+++ b/src/main/java/com/uniminuto/clinica/service/PacienteService.java[m
[36m@@ -4,25 +4,17 @@[m [mimport com.uniminuto.clinica.entity.Paciente;[m
 import java.util.List;[m
 import org.apache.coyote.BadRequestException;[m
 [m
[31m-/**[m
[31m- *[m
[31m- * @author lmora[m
[31m- */[m
[32m+[m
 public interface PacienteService {[m
[31m-    /**[m
[31m-     * Lista todos los pacientes de la bd.[m
[31m-     * @return Lista de pacientes.[m
[31m-     */[m
[32m+[m[41m   [m
     List<Paciente> encontrarTodosLosPacientes();[m
 [m
[31m-    /**[m
[31m-     * Busca un paciente dado un documento de identidad.[m
[31m-     * @param documento documento a buscar.[m
[31m-     * @return Paciente encontrado.[m
[31m-     * @throws BadRequestException excepcion.[m
[31m-     */[m
[32m+[m[41m   [m
     Paciente buscarPacientePorDocumento(String documento) throws BadRequestException;[m
 [m
 [m
     List<Paciente> listarOrdenadoPorFechaNacimiento(boolean ascendente);[m
[32m+[m
[32m+[m[41m   [m
[32m+[m[32m    Paciente guardarOActualizarPaciente(Paciente paciente) throws BadRequestException;[m
 }[m
\ No newline at end of file[m
[1mdiff --git a/src/main/java/com/uniminuto/clinica/service/impl/PacienteServiceImpl.java b/src/main/java/com/uniminuto/clinica/service/impl/PacienteServiceImpl.java[m
[1mindex 371abb9..1fbaad5 100644[m
[1m--- a/src/main/java/com/uniminuto/clinica/service/impl/PacienteServiceImpl.java[m
[1m+++ b/src/main/java/com/uniminuto/clinica/service/impl/PacienteServiceImpl.java[m
[36m@@ -9,10 +9,7 @@[m [mimport org.apache.coyote.BadRequestException;[m
 import org.springframework.beans.factory.annotation.Autowired;[m
 import org.springframework.stereotype.Service;[m
 [m
[31m-/**[m
[31m- *[m
[31m- * @author lmora[m
[31m- */[m
[32m+[m
 @Service[m
 public class PacienteServiceImpl implements PacienteService {[m
 [m
[36m@@ -28,7 +25,7 @@[m [mpublic class PacienteServiceImpl implements PacienteService {[m
     public Paciente buscarPacientePorDocumento(String documento) throws BadRequestException {[m
         Optional<Paciente> optPaciente = this.pacienteRepository.findByNumeroDocumento(documento);[m
         if (!optPaciente.isPresent()) {[m
[31m-            throw new BadRequestException("No se encuentra el paciente");[m
[32m+[m[32m            throw new BadRequestException("No se encuentra el paciente con documento: " + documento);[m
         }[m
         return optPaciente.get();[m
     }[m
[36m@@ -42,4 +39,31 @@[m [mpublic class PacienteServiceImpl implements PacienteService {[m
         }[m
     }[m
 [m
[31m-}[m
[32m+[m[41m   [m
[32m+[m[32m    @Override[m
[32m+[m[32m    public Paciente guardarOActualizarPaciente(Paciente paciente) throws BadRequestException {[m
[32m+[m[32m        if (paciente.getId() == null) {[m
[32m+[m[41m            [m
[32m+[m[32m            Optional<Paciente> existente = pacienteRepository.findByNumeroDocumento(paciente.getNumeroDocumento());[m
[32m+[m[32m            if (existente.isPresent()) {[m
[32m+[m[32m                throw new BadRequestException("Ya existe un paciente con el número de documento: " + paciente.getNumeroDocumento());[m
[32m+[m[32m            }[m
[32m+[m[32m        } else {[m
[32m+[m[41m           [m
[32m+[m[32m            Optional<Paciente> pacienteBD = pacienteRepository.findById(paciente.getId());[m
[32m+[m[32m            if (!pacienteBD.isPresent()) {[m
[32m+[m[32m                 throw new BadRequestException("No se encuentra el paciente con ID: " + paciente.getId() + " para actualizar.");[m
[32m+[m[32m            }[m
[32m+[m
[32m+[m[41m        [m
[32m+[m[32m            Optional<Paciente> duplicado = pacienteRepository.findByNumeroDocumentoAndIdNot(paciente.getNumeroDocumento(), paciente.getId());[m
[32m+[m[32m            if (duplicado.isPresent()) {[m
[32m+[m[32m                throw new BadRequestException("El número de documento: " + paciente.getNumeroDocumento() + " ya está asignado a otro paciente.");[m
[32m+[m[32m            }[m
[32m+[m[32m        }[m
[32m+[m
[32m+[m[41m       [m
[32m+[m[32m        return pacienteRepository.save(paciente);[m
[32m+[m[32m    }[m
[32m+[m
[32m+[m[32m}[m
\ No newline at end of file[m
[1mdiff --git a/src/main/resources/application.properties b/src/main/resources/application.properties[m
[1mindex d4f2a8a..56d94e3 100644[m
[1m--- a/src/main/resources/application.properties[m
[1m+++ b/src/main/resources/application.properties[m
[36m@@ -1,6 +1,6 @@[m
 spring.application.name=clinica[m
 [m
[31m-server.port=8000[m
[32m+[m[32mserver.port=8081[m
 server.tomcat.max-threads=32[m
 server.servlet.contextPath=/clinica/v1[m
 [m
[36m@@ -8,7 +8,7 @@[m [mserver.servlet.contextPath=/clinica/v1[m
 # Configuraci\u00f3n de conexi\u00f3n a MySQL[m
 spring.datasource.url=jdbc:mysql://localhost:3306/clinica[m
 spring.datasource.username=root[m
[31m-spring.datasource.password=1234[m
[32m+[m[32mspring.datasource.password=[m
 spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver[m
 [m
 # Configuraci\u00f3n de Hibernate (ORM)[m
