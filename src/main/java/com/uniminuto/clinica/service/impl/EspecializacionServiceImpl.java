package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Especializacion;
import com.uniminuto.clinica.repository.EspecializacionRepository;
import com.uniminuto.clinica.service.EspecializacionService;
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
    private EspecializacionRepository repo;

    @Override
    public List<Especializacion> listarTodo() {
        return this.repo.findAll();
    }

    @Override
    public Especializacion buscarEspecializacionPorCod(String codigo)
            throws BadRequestException {
        Optional<Especializacion> optEspc = this.repo
                .findByCodigoEspecializacion(codigo);
        if (!optEspc.isPresent()) {         
            throw new BadRequestException("No se encuentra la especializacion");
        }

        return optEspc.get();
    }

    @Override
    public Especializacion crearEspecializacion(Especializacion especializacion)
            throws BadRequestException {

        // Validar que el nombre no esté vacío
        if (especializacion.getNombre() == null || especializacion.getNombre().trim().isEmpty()) {
            throw new BadRequestException("El nombre es requerido");
        }

        // Validar que el código no esté vacío
        if (especializacion.getCodigoEspecializacion() == null ||
                especializacion.getCodigoEspecializacion().trim().isEmpty()) {
            throw new BadRequestException("El código de especialización es requerido");
        }

        // Verificar que no exista una especialización con el mismo nombre
        Optional<Especializacion> especializacionConMismoNombre = this.repo
                .findByNombre(especializacion.getNombre().trim());

        if (especializacionConMismoNombre.isPresent()) {
            throw new BadRequestException("Ya existe una especialización con el nombre: " + especializacion.getNombre());
        }

        // Verificar que no exista una especialización con el mismo código
        Optional<Especializacion> especializacionConMismoCodigo = this.repo
                .findByCodigoEspecializacion(especializacion.getCodigoEspecializacion().trim());

        if (especializacionConMismoCodigo.isPresent()) {
            throw new BadRequestException("Ya existe una especialización con el código: " + especializacion.getCodigoEspecializacion());
        }

        // Limpiar espacios en blanco
        especializacion.setNombre(especializacion.getNombre().trim());
        especializacion.setCodigoEspecializacion(especializacion.getCodigoEspecializacion().trim());

        return this.repo.save(especializacion);
    }

    @Override
    public Especializacion actualizarEspecializacion(Long id, Especializacion especializacion)
            throws BadRequestException {

        // Verificar que la especialización existe
        Optional<Especializacion> especializacionExistente = this.repo.findById(id);

        if (!especializacionExistente.isPresent()) {
            throw new BadRequestException("No se encuentra la especialización con ID: " + id);
        }

        // Validar que el nombre no esté vacío
        if (especializacion.getNombre() == null || especializacion.getNombre().trim().isEmpty()) {
            throw new BadRequestException("El nombre es requerido");
        }

        // Validar que el código no esté vacío
        if (especializacion.getCodigoEspecializacion() == null ||
                especializacion.getCodigoEspecializacion().trim().isEmpty()) {
            throw new BadRequestException("El código de especialización es requerido");
        }

        // Verificar que no exista otra especialización con el mismo nombre
        Optional<Especializacion> especializacionConMismoNombre = this.repo
                .findByNombre(especializacion.getNombre().trim());

        if (especializacionConMismoNombre.isPresent() &&
                !especializacionConMismoNombre.get().getId().equals(id)) {
            throw new BadRequestException("Ya existe otra especialización con el nombre: " + especializacion.getNombre());
        }

        // Verificar que no exista otra especialización con el mismo código
        Optional<Especializacion> especializacionConMismoCodigo = this.repo
                .findByCodigoEspecializacion(especializacion.getCodigoEspecializacion().trim());

        if (especializacionConMismoCodigo.isPresent() &&
                !especializacionConMismoCodigo.get().getId().equals(id)) {
            throw new BadRequestException("Ya existe otra especialización con el código: " + especializacion.getCodigoEspecializacion());
        }

        // Actualizar los datos
        Especializacion especializacionActual = especializacionExistente.get();
        especializacionActual.setNombre(especializacion.getNombre().trim());
        especializacionActual.setDescripcion(especializacion.getDescripcion());
        especializacionActual.setCodigoEspecializacion(especializacion.getCodigoEspecializacion().trim());

        return this.repo.save(especializacionActual);
    }
}
