package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.FormulaMedica;
import com.uniminuto.clinica.repository.FormulaMedicaRepository;
import com.uniminuto.clinica.service.FormulaMedicaService;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**<<<
 *
 * @author sebas
 */
@Service
public class FormulaMedicaServiceImpl implements FormulaMedicaService {

    @Autowired
    private FormulaMedicaRepository formulaMedicaRepository;



    @Override
    public List<FormulaMedica> listarFormulas() {
        return this.formulaMedicaRepository.findAll();
    }

    @Override
    public FormulaMedica guardarFormula(FormulaMedica formula)
            throws BadRequestException {
        try {
            if (formula.getPaciente() == null || formula.getMedico() == null) {
                throw new BadRequestException("El paciente y el médico son obligatorios");
            }
            return this.formulaMedicaRepository.save(formula);
        } catch (Exception e) {
            throw new BadRequestException("Error al guardar la fórmula médica: " + e.getMessage());
        }
    }

    @Override
    public FormulaMedica actualizarFormula(Long id, FormulaMedica formula)
            throws BadRequestException {
        Optional<FormulaMedica> formulaExistente = this.formulaMedicaRepository.findById(id);
        if (!formulaExistente.isPresent()) {
            throw new BadRequestException("No se encontró la fórmula médica con ID: " + id);
        }

        FormulaMedica f = formulaExistente.get();
        f.setDescripcion(formula.getDescripcion());
        f.setFechaEmision(formula.getFechaEmision());
        f.setMedico(formula.getMedico());
        f.setPaciente(formula.getPaciente());

        return this.formulaMedicaRepository.save(f);
    }

    @Override
    public void eliminarFormula(Long id) throws BadRequestException {
        Optional<FormulaMedica> formula = this.formulaMedicaRepository.findById(id);
        if (!formula.isPresent()) {
            throw new BadRequestException("No existe fórmula médica con ID: " + id);
        }
        this.formulaMedicaRepository.delete(formula.get());
    }
}
