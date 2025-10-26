package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Especializacion;
import com.uniminuto.clinica.entity.Medicamento;
import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.entity.Usuario;
import com.uniminuto.clinica.model.MedicamentoRq;
import com.uniminuto.clinica.model.MedicoRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.UsuarioRq;
import com.uniminuto.clinica.repository.EspecializacionRepository;
import com.uniminuto.clinica.repository.MedicoRepository;
import com.uniminuto.clinica.service.EspecializacionService;
import com.uniminuto.clinica.service.MedicoService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lmora
 */
@Service
public class MedicoServiceImpl implements MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private EspecializacionService especializacionService;


    @Autowired
    private EspecializacionRepository especializacionRepository;

    @Override
    public List<Medico> listarMedicos() {
        return this.medicoRepository.findAll();
    }

    @Override
    public List<Medico> buscarPorEspecialidad(String codigo)
            throws BadRequestException {
        Especializacion e = this.especializacionService
                .buscarEspecializacionPorCod(codigo);
        return this.medicoRepository.findByEspecializacion(e);
    }

    @Override
    public RespuestaRs guardarMedico(MedicoRq medicoRq) throws BadRequestException {
        Optional<Medico> optmedico = this.medicoRepository.findByDocumento(
                medicoRq.getDocumento()
        );
        if (optmedico.isPresent()) {
            throw new BadRequestException("El número de documento ya está registrado");
        }

        optmedico = this.medicoRepository.findByRegistroProfesional(medicoRq.getRegistroProfesional());
        if (optmedico.isPresent()) {
            throw new BadRequestException("Existe otro medico con el registro profesional ingresado");
        }

        Optional<Especializacion> optEsp = this.especializacionRepository
                .findById(medicoRq.getEspecializacion());
        if (optEsp.isEmpty()) {
            throw new BadRequestException("La especialización no existe");
        }

        Medico medicoGuardar = this.convertToRqToEntidad(medicoRq, optEsp.get());
        this.medicoRepository.save(medicoGuardar);
        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("Médico guardado exitosamente");
        rta.setStatus(200);
        return rta;
    }

    private Medico convertToRqToEntidad(MedicoRq medicoRq, Especializacion especialidad) {
        Medico medico = new Medico();
        medico.setNombres(medicoRq.getNombres());
        medico.setApellidos(medicoRq.getApellidos());
        medico.setDocumento(medicoRq.getDocumento());
        medico.setTipoDocumento(medicoRq.getTipoDocumento());
        medico.setRegistroProfesional(medicoRq.getRegistroProfesional());
        medico.setEspecializacion(especialidad);
        medico.setTelefono(medicoRq.getTelefono());
        return medico;
    }

    public RespuestaRs actualizarMedico(MedicoRq medicoRq)
            throws BadRequestException {
        // Paso 1. Consultar si el campo id existe y viene en el request
        if (medicoRq.getId() == null) {
            throw new BadRequestException("El id del medico es obligatorio");
        }
        // Paso 2. Consultar si el medicamento existe por id
        Optional<Medico> optMedico = medicoRepository
                .findById(medicoRq.getId());
        // Paso 3. Si no existe lanzo error
        if (!optMedico.isPresent()) {
            throw new BadRequestException("El medico no existe y no se puede actualizar");
        }
        // Paso 4. Si existe voy y valido que el atributo nombre cambie y si cambia lo consulto por nombre
        Medico medicoActual = optMedico.get();
        if (!medicoActual.getNombres()
                .toLowerCase().equals(medicoRq.getNombres().toLowerCase())) {
            Optional<Medico> optMedicoPorNombre = medicoRepository
                    .findByNombres(medicoRq.getNombres());
            // Paso 5. Si existe por nombre lanzo error
            if (optMedicoPorNombre.isPresent()) {
                throw new BadRequestException("El nombre del medico ya existe");
            }
        }

        // Paso 6. Si no existe por nombre, actualizo los datos del medicamento
        medicoActual.setNombres(medicoRq.getNombres() == null ? medicoActual.getNombres() : medicoRq.getNombres());
        medicoActual.setApellidos(medicoRq.getApellidos() == null ? medicoRq.getApellidos() : medicoRq.getApellidos());
        medicoActual.setTipoDocumento(medicoRq.getTipoDocumento() == null ? medicoRq.getTipoDocumento() : medicoRq.getTipoDocumento());
        medicoActual.setTelefono(medicoRq.getTelefono() == null ? medicoRq.getTelefono() : medicoRq.getTelefono());
        medicoActual.setRegistroProfesional(medicoRq.getRegistroProfesional() == null ? medicoRq.getRegistroProfesional() : medicoRq.getTipoDocumento());
        this.medicoRepository.save(medicoActual);
        // Paso 7. Retorno la respuesta
        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("Medico actualizado exitosamente");
        rta.setStatus(200);

        return rta;
    }
}