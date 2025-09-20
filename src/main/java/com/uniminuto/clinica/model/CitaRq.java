package com.uniminuto.clinica.model;
import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.entity.Paciente;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
public class CitaRq {

    private Long id;
    private LocalDate fechaHora;
    private String motivo;
    private String estado;
    private Paciente paciente;
    private Medico medico;
}
