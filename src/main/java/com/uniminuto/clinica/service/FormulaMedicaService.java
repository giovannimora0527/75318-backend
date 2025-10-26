package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.FormulaMedica;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 *
 * @author sebas
 */
public interface FormulaMedicaService {

    List<FormulaMedica> listarFormulas();

    FormulaMedica guardarFormula(FormulaMedica formula) throws BadRequestException;

    FormulaMedica actualizarFormula(Long id, FormulaMedica formula) throws BadRequestException;

    void eliminarFormula(Long id) throws BadRequestException;
}
