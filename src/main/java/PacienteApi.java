import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.service.PacienteService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pacientes")
public class PacienteApi {
    private final PacienteService pacienteService;

    public PacienteApi(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    // Servicio 1: listar todos
    @GetMapping
    public List<Paciente> listarPacientes() {
        return pacienteService.obtenerTodos();
    }

    // Servicio 2: buscar por documento
    @GetMapping("/{documento}")
    public ResponseEntity<Paciente> buscarPorDocumento(@PathVariable String documento) {
        return pacienteService.buscarPorDocumento(documento)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
