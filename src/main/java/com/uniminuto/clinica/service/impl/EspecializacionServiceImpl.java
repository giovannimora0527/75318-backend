package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Especializacion;
import com.uniminuto.clinica.model.EspecializacionRq;
import com.uniminuto.clinica.model.EspecializacionRs;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.repository.EspecializacionRepository;
import com.uniminuto.clinica.service.EspecializacionService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EspecializacionServiceImpl implements EspecializacionService {

    private final EspecializacionRepository repository;

    @Override
    public List<EspecializacionRs> listarEspecializaciones() {
        return repository.findAll().stream().map(this::mapToRs).collect(Collectors.toList());
    }

    @Override
    public EspecializacionRs buscarPorCodigo(String codigo) {
        Optional<Especializacion> opt = repository.findByCodigoEspecializacion(codigo);
        return opt.map(this::mapToRs).orElse(null);
    }

    @Override
    @Transactional
    public RespuestaRs guardarEspecializacion(EspecializacionRq request) throws BadRequestException {
        validarRq(request);

        if (repository.findByCodigoEspecializacion(request.getCodigoEspecializacion()).isPresent()) {
            throw new BadRequestException("Ya existe una especialización con el código: " + request.getCodigoEspecializacion());
        }

        Especializacion e = new Especializacion();
        e.setCodigoEspecializacion(request.getCodigoEspecializacion());
        e.setNombre(request.getNombre());
        e.setDescripcion(request.getDescripcion());

        repository.save(e);

        RespuestaRs r = new RespuestaRs();
        // Ajusta al formato de RespuestaRs que usa tu proyecto:
        // algunos módulos usan setStatus/setMensaje, otros constructores.
        r.setStatus(200);
        r.setMensaje("Especialización guardada con éxito");
        return r;
    }

    @Override
    @Transactional
    public RespuestaRs actualizarEspecializacion(EspecializacionRq request) throws BadRequestException {
        if (request.getId() == null) {
            throw new BadRequestException("El ID es obligatorio para actualizar la especialización");
        }

        validarRq(request);

        Especializacion e = repository.findById(request.getId())
                .orElseThrow(() -> new BadRequestException("No existe una especialización con el ID: " + request.getId()));

        // Si cambias el código, asegúrate que no exista otro con ese código
        Optional<Especializacion> existente = repository.findByCodigoEspecializacion(request.getCodigoEspecializacion());
        if (existente.isPresent() && !existente.get().getId().equals(request.getId())) {
            throw new BadRequestException("Ya existe otra especialización con el código: " + request.getCodigoEspecializacion());
        }

        e.setCodigoEspecializacion(request.getCodigoEspecializacion());
        e.setNombre(request.getNombre());
        e.setDescripcion(request.getDescripcion());

        repository.save(e);

        RespuestaRs r = new RespuestaRs();
        r.setStatus(200);
        r.setMensaje("Especialización actualizada con éxito");
        return r;
    }

    private void validarRq(EspecializacionRq rq) throws BadRequestException {
        if (rq == null) {
            throw new BadRequestException("El cuerpo de la petición no puede ser vacío");
        }
        if (rq.getCodigoEspecializacion() == null || rq.getCodigoEspecializacion().trim().isEmpty()) {
            throw new BadRequestException("El campo codigoEspecializacion es obligatorio");
        }
        if (rq.getNombre() == null || rq.getNombre().trim().isEmpty()) {
            throw new BadRequestException("El campo nombre es obligatorio");
        }
    }

    private EspecializacionRs mapToRs(Especializacion e) {
        EspecializacionRs rs = new EspecializacionRs();
        rs.setId(e.getId());
        rs.setCodigoEspecializacion(e.getCodigoEspecializacion());
        rs.setNombre(e.getNombre());
        rs.setDescripcion(e.getDescripcion());
        return rs;
    }

    @Override
    public Especializacion buscarEspecializacionPorCod(String codigo) throws BadRequestException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
