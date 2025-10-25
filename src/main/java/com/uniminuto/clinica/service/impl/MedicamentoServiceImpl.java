package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Medicamento;
import com.uniminuto.clinica.model.MedicamentoRq;
import com.uniminuto.clinica.model.MedicamentoRs;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.repository.MedicamentoRepository;
import com.uniminuto.clinica.service.MedicamentoService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicamentoServiceImpl implements MedicamentoService {

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @Override
    public RespuestaRs guardarMedicamento(MedicamentoRq medicamentoRq) throws BadRequestException {
        validarMedicamentoRq(medicamentoRq);

        boolean esActualizacion = medicamentoRq.getId() != null;

        Medicamento medicamento = esActualizacion
            ? medicamentoRepository.findById(medicamentoRq.getId()).orElse(new Medicamento())
            : new Medicamento();

        // Mapear campos desde el RQ a la entidad
        medicamento.setNombre(medicamentoRq.getNombre());
        medicamento.setDescripcion(medicamentoRq.getDescripcion());
        medicamento.setPresentacion(medicamentoRq.getPresentacion());

        // Validar duplicados solo si es creación o cambio de nombre
        Optional<Medicamento> existente = medicamentoRepository.findByNombre(medicamentoRq.getNombre());
        if (existente.isPresent() && (!esActualizacion || !existente.get().getId().equals(medicamentoRq.getId()))) {
            throw new BadRequestException("Ya existe un medicamento con el nombre: " + medicamentoRq.getNombre());
        }

        medicamentoRepository.save(medicamento);

        RespuestaRs respuesta = new RespuestaRs();
        respuesta.setStatus(200);
        respuesta.setMensaje(esActualizacion ? "Medicamento actualizado con éxito" : "Medicamento guardado con éxito");
        return respuesta;
    }

    private void validarMedicamentoRq(MedicamentoRq medicamentoRq) throws BadRequestException {
        if (medicamentoRq == null) {
            throw new BadRequestException("El cuerpo de la petición no puede ser vacío");
        }
        if (medicamentoRq.getNombre() == null || medicamentoRq.getNombre().trim().isEmpty()) {
            throw new BadRequestException("El campo nombre es obligatorio");
        }
    }

    @Override
    public List<MedicamentoRs> listarMedicamentos() {
        return medicamentoRepository.findAllByOrderByNombreAsc().stream().map(m -> {
            MedicamentoRs dto = new MedicamentoRs();
            dto.setId(m.getId());
            dto.setNombre(m.getNombre());
            dto.setDescripcion(m.getDescripcion());
            dto.setPresentacion(m.getPresentacion());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public List<MedicamentoRs> listarMedicamentosRecientes() {
        return medicamentoRepository.findAllByOrderByIdDesc().stream().map(m -> {
            MedicamentoRs dto = new MedicamentoRs();
            dto.setId(m.getId());
            dto.setNombre(m.getNombre());
            dto.setDescripcion(m.getDescripcion());
            dto.setPresentacion(m.getPresentacion());
            return dto;
        }).collect(Collectors.toList());
    }
}