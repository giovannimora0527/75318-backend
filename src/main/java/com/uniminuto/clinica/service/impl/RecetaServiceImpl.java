package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.*;
import com.uniminuto.clinica.model.RecetaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.repository.*;
import com.uniminuto.clinica.service.RecetaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


    @Service
    @RequiredArgsConstructor
    public class RecetaServiceImpl implements RecetaService {

        @Autowired
        private final RecetaRepository recetaRepository;

        @Autowired
        private CitaRepository citaRepository;

        @Autowired
        private MedicamentoRepository medicamentoRepository;

        @Override
        public RespuestaRs<Receta> guardarReceta(RecetaRq recetaRq) {
            // 1. Validar si existe la cita
            Cita cita = citaRepository.findById(recetaRq.getCitaId().longValue())
                    .orElseThrow(() -> new RuntimeException("La cita no existe"));

            // 2. Verificar si existe el medicamento

            Medicamento medicamento = medicamentoRepository.findById(recetaRq.getMedicamentoId().longValue())
                    .orElseThrow(() -> new RuntimeException("Medicamento no encontrado"));

            // 3. Verficar si ya existe la receta
            Optional<Receta> recetaExistente = recetaRepository.findRecetaByCitaAndMedicamento(cita, medicamento);
            if (recetaExistente.isPresent()) {
                return new RespuestaRs<>("Ya existe una receta", 400, null);
            }
            // 2. Crar ka cita
            Receta receta = new Receta();
            receta.setCita(cita);
            receta.setMedicamento(medicamento);
            receta.setDosis(recetaRq.getDosis());
            receta.setIndicaciones(recetaRq.getIndicaciones());

            // 3. Guardar la cita
            Receta guardada = recetaRepository.save(receta);

            // 4. Respuesta exitosa
            return new RespuestaRs<>("Receta guardada correctamente", 201, guardada);
        }

        @Override
        public List<Receta> listarRecetas() {
            return recetaRepository.findAll();
        }
        @Override
        public List<Receta> listarRecetasOrdenadosPorFecha() {
            return this.recetaRepository.findAllByOrderByFechaCreacionRegistroDesc();
        }
    }
