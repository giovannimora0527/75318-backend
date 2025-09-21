package com.uniminuto.clinica.model;

import com.uniminuto.clinica.entity.Cita;
import lombok.Data;

/**
 *
 * @author JAVIER-CUERVO
 * @param <T>
 */
@Data
public class CitaRs<T> {
    private int status;
    private String mensaje;
    private T data;

    public void setStatus(int i) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setMensaje(String cita_guardada_exitosamente) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setData(Cita citaGuardada) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}