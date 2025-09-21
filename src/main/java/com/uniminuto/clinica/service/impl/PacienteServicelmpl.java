package com.uniminuto.clinica.service.impl;


import com.uniminuto.clinica.entity.Paciente;
import com.uniminuto.clinica.repository.PacienteRepository;
import com.uniminuto.clinica.service.PacienteService;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 *
 *
 * @author crash
 */
@Service
public class PacienteServiceImpl implements PacienteService {
    /**
     * Repositorio JPA para operaciones de persistencia de la entidad Paciente.
     */
    @Autowired
    private PacienteRepository PacienteRepository;
    /**
     * Implementa el ordenamiento utilizando Java 8 Streams y el método compareTo
     * de LocalDate para garantizar un orden cronológico preciso.
     * @return List<Paciente> Lista ordenada donde el primer elemento es el paciente
     * con la fecha de nacimiento más antigua
     */
    @Override
    public List<Paciente> listarPacientesDes() {
        return PacienteRepository.findAll()
                /**
                 * Stream que permite operaciones funcionales sobre la colección de pacientes
                 */
                .stream()
                /**
                 * Operación de ordenamiento que compara fechas de nacimiento.
                 * Utiliza expresión lambda (m1, m2) donde:
                 * - m1: primer paciente en la comparación
                 * - m2: segundo paciente en la comparación
                 * - compareTo: devuelve -1, 0, o 1 según la fecha sea anterior, igual o posterior
                 */
                .sorted((m1, m2) -> m1.getFechaNacimiento().compareTo(m2.getFechaNacimiento()))
                /**
                 * Convierte el Stream ordenado de nuevo a una lista
                 */
                .toList();
    }
    //* Utiliza el método findAll() del repositorio JPA para obtener todos los registros
    @Override
    public List<Paciente> listarTodo() {
        return this.PacienteRepository.findAll();
    }
    //Funcion buscar por documento identidad
    /*
     * Utiliza Optional para manejar de forma segura la posible ausencia del paciente.
     * Sigue el patrón de programación defensiva para evitar NullPointerException.
    * */
    @Override
    public Paciente encontrarPorDocumentoIdentidad(String numeroDocumento)
            throws BadRequestException {
        /**
         * Optional<Paciente> contenedor que puede o no contener un paciente
         * Evita el riesgo de retornar null directamente
         */
        Optional<Paciente> optUser = this.PacienteRepository
                .findByNumeroDocumento(numeroDocumento);
        //Validación de existencia del paciente antes de retornarlo
        if (!optUser.isPresent()) {
            throw new BadRequestException("No existe el usuario con el documento de identidad: " + numeroDocumento);
        }
        //Extracción segura del objeto Paciente del Optional
        return optUser.get();
    }
/*
 * Implementación similar al método de búsqueda por documento pero utilizando
 * el identificador primario de la base de datos.
*/
    @Override
    //Búsqueda por clave primaria utilizando el método heredado de JpaRepository
    public Paciente buscarPacienteId(Long id) throws BadRequestException {
        /**
         * Optional<Paciente> contenedor que puede o no contener un paciente
         * Evita el riesgo de retornar null directamente
         */
        Optional<Paciente> optUser = this.PacienteRepository.findById(id);
        if (!optUser.isPresent()) {
            throw new BadRequestException("No existe el usuario con el id: " + id);
        }

        return optUser.get();
    }
}
