package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.model.CitaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.repository.CitaRepository;
import com.uniminuto.clinica.repository.MedicoRepository;
import com.uniminuto.clinica.repository.PacienteRepository;
import com.uniminuto.clinica.service.CitaService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CitaServiceImpl implements CitaService {

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

   @Override
   public RespuestaRs guardarCita(CitaRq citarq) throws BadRequestException {
       this.validarFormulario(citarq);
       Optional<Cita> optCita = citaRepository
               .findByMotivo(citarq.getMotivo());
       if (optCita.isPresent()){
           throw new BadRequestException("La cita ya existe");
       }
       Cita nuevo = this.mapearACita(citarq);
       citaRepository.save(nuevo);
       RespuestaRs rta = new RespuestaRs();
       rta.setMensaje("Cita creada exitosamente");
       rta.setStatus(200);

       return rta;
   }

    @Override
    public List<Cita> listarCitasRecientes() {
        return citaRepository.findAllByOrderByFechaHoraDesc();
    }

    private void validarFormulario(CitaRq citaRq) throws BadRequestException {
       if (citaRq.getMotivo() == null || citaRq.getMotivo().isBlank()){
           throw new BadRequestException("El motivo debe ser obligatorio");
       }
       if (citaRq.getEstado() == null || citaRq.getEstado().isBlank()){
           throw new BadRequestException("El estado debe ser obligatorio");
       }
       if (citaRq.getFechaHora() == null){
           throw new BadRequestException("La fecha es obligatoria");
       }
   }
   private Cita mapearACita (CitaRq citaRq){
       Cita nuevo = new Cita();
       nuevo.setFechaHora(citaRq.getFechaHora());
       nuevo.setMotivo(citaRq.getMotivo());
       nuevo.setEstado(citaRq.getEstado());
       nuevo.setPaciente(citaRq.getPaciente());
       nuevo.setMedico(citaRq.getMedico());
       return nuevo;
   }
}
