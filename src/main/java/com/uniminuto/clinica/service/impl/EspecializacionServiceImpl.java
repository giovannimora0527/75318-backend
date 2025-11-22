package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.*;
import com.uniminuto.clinica.model.CitaRq;
import com.uniminuto.clinica.model.EspecializacionRq;
import com.uniminuto.clinica.model.RespuestaRs;
import com.uniminuto.clinica.model.UsuarioRq;
import com.uniminuto.clinica.repository.EspecializacionRepository;
import com.uniminuto.clinica.repository.MedicoRepository;
import com.uniminuto.clinica.service.EspecializacionService;

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
public class EspecializacionServiceImpl implements EspecializacionService {

    @Autowired
    private EspecializacionRepository especializacionRepository;

    @Override
    public List<Especializacion> listarTodo() {
        return this.especializacionRepository.findAll()
                .stream()
                .sorted((e1, e2) -> e1.getNombre().compareToIgnoreCase(e2.getNombre()))
                .toList();
    }

    @Override
    public Especializacion buscarEspecializacionPorCod(String codigo)
            throws BadRequestException {
        Optional<Especializacion> optEspc = this.especializacionRepository
                .findByCodigoEspecializacion(codigo);
        if (!optEspc.isPresent()) {
            throw new BadRequestException("No se encuentra la especializacion");
        }

        return optEspc.get();
    }

    @Override
    public RespuestaRs guardarEspecializacion(EspecializacionRq especializacionRq)
            throws BadRequestException {

        validarCampos(especializacionRq);

        Optional<Especializacion> optEspecializacion =
                especializacionRepository.findByCodigoEspecializacion(especializacionRq.getCodigoEspecializacion());

        if (optEspecializacion.isPresent()) {
            throw new BadRequestException("La especializacion ya existe.");
        }

        Especializacion nuevo = new Especializacion();
        nuevo.setNombre(especializacionRq.getNombre());
        nuevo.setDescripcion(especializacionRq.getDescripcion());
        nuevo.setCodigoEspecializacion(especializacionRq.getCodigoEspecializacion());

        // GUARDAR Y GENERAR ID
        nuevo = especializacionRepository.save(nuevo);

        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("Especializacion guardada correctamente.");
        rta.setStatus(200);
        return rta;
    }

    public RespuestaRs actualizarEspecializacion(EspecializacionRq especializacionRq)
            throws BadRequestException {
        // Paso 1. Consultar si el campo id existe y viene en el request
        if (especializacionRq.getId() == null) {
            throw new BadRequestException("El id de la cita es obligatoria");
        }
        // Paso 2. Consultar si el medicamento existe por id
        Optional<Especializacion> optEspecializacion = especializacionRepository
                .findById(especializacionRq.getId());
        // Paso 3. Si no existe lanzo error
        if (!optEspecializacion.isPresent()) {
            throw new BadRequestException("La especializacion no existe y no se puede actualizar");
        }
        // Paso 4. Si existe voy y valido que el atributo nombre cambie y si cambia lo consulto por nombre
        Especializacion especializacionActual = optEspecializacion.get();
        if (!especializacionActual.getId().equals(especializacionRq.getId())) {}
        Optional<Especializacion> optEspecializacionPorId = especializacionRepository
                .findById(especializacionRq.getId());
        // Paso 5. Si existe por nombre lanzo error
        if (optEspecializacionPorId.isPresent()) {
            throw new BadRequestException("El id de la especializacion ya existe");
        }


        // Paso 6. Si no existe por nombre, actualizo los datos del medicamento
        especializacionActual.setNombre(especializacionRq.getNombre() == null ? especializacionActual.getNombre() : especializacionRq.getNombre());
        especializacionActual.setDescripcion(especializacionRq.getDescripcion() == null ? especializacionActual.getDescripcion() : especializacionRq.getDescripcion());
        especializacionActual.setCodigoEspecializacion(especializacionRq.getCodigoEspecializacion() == null ? especializacionActual.getCodigoEspecializacion() : especializacionRq.getCodigoEspecializacion());
        this.especializacionRepository.save(especializacionActual);
        // Paso 7. Retorno la respuesta
        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("Especializacion actualizado exitosamente");
        rta.setStatus(200);

        return rta;
    }

    @Override
    public RespuestaRs eliminarEspecializacion(Integer idEspecializacion) throws BadRequestException {

        Optional<Especializacion> optEspecializacion = especializacionRepository.findById(idEspecializacion);

        if (optEspecializacion.isEmpty()) {
            throw new BadRequestException("La especializacion no existe, no se puede eliminar");
        }

        Especializacion especializacion = optEspecializacion.get();

        // Eliminamos el paciente
        especializacionRepository.delete(especializacion);

        // Respuesta
        RespuestaRs rta = new RespuestaRs();
        rta.setMensaje("Especializacion eliminado correctamente");
        rta.setStatus(200);

        return rta;
    }

    private void validarCampos(EspecializacionRq especializacionRq)
            throws BadRequestException {

        if (especializacionRq.getNombre() == null || especializacionRq.getNombre().isBlank()) {
            throw new BadRequestException("El campo nombre es obligatorio.");
        }
        if (especializacionRq.getDescripcion() == null || especializacionRq.getDescripcion().isBlank()) {
            throw new BadRequestException("El campo descripcion es obligatorio.");
        }
        if (especializacionRq.getCodigoEspecializacion() == null || especializacionRq.getCodigoEspecializacion().isBlank()) {
            throw new BadRequestException("El campo codigo de especializacion es obligatorio.");
        }
    }
}
