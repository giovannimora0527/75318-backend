package com.uniminuto.clinica.model;
import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Medicamento;
import lombok.Data;

import javax.persistence.*;

@Data
public class RecetaRq {

    private Long id;
    private String dosis;
    private String indicaciones;
    private Cita cita;
    private Medicamento medicamento;
}
