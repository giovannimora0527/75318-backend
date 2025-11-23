package com.uniminuto.clinica.api;

import com.uniminuto.clinica.model.AuditoriaLogDTO;
import com.uniminuto.clinica.model.AuditoriaLogFiltroDTO;
import com.uniminuto.clinica.model.PageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/auditoria")
public interface AuditoriaApi {
    
    @PostMapping("/buscar")
    ResponseEntity<PageResponse<AuditoriaLogDTO>> buscarConFiltros(
            @RequestBody AuditoriaLogFiltroDTO filtro);
    
    @GetMapping("/listar")
    ResponseEntity<PageResponse<AuditoriaLogDTO>> listar(
            @RequestParam(required = false) Integer pagina,
            @RequestParam(required = false) Integer tamanoPagina);
}