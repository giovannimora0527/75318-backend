package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.AuditoriaApi;
import com.uniminuto.clinica.model.AuditoriaLogDTO;
import com.uniminuto.clinica.model.AuditoriaLogFiltroDTO;
import com.uniminuto.clinica.model.PageResponse;
import com.uniminuto.clinica.service.AuditoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuditoriaApiController implements AuditoriaApi {
    
    @Autowired
    private AuditoriaService servicio;
    
    @Override
    public ResponseEntity<PageResponse<AuditoriaLogDTO>> buscarConFiltros(
            AuditoriaLogFiltroDTO filtro) {
        
        PageResponse<AuditoriaLogDTO> response = servicio.buscarConFiltros(filtro);
        return ResponseEntity.ok(response);
    }
    
    @Override
    public ResponseEntity<PageResponse<AuditoriaLogDTO>> listar(
            Integer pagina, 
            Integer tamanoPagina) {
        
        AuditoriaLogFiltroDTO filtro = new AuditoriaLogFiltroDTO();
        filtro.setPagina(pagina != null ? pagina : 0);
        filtro.setTamanoPagina(tamanoPagina != null ? tamanoPagina : 10);
        
        PageResponse<AuditoriaLogDTO> response = servicio.buscarConFiltros(filtro);
        return ResponseEntity.ok(response);
    }
}
