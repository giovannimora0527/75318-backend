package com.uniminuto.clinica.service.impl;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Receta;
import com.uniminuto.clinica.repository.CitaRepository;
import com.uniminuto.clinica.repository.RecetaRepository;
import com.uniminuto.clinica.service.CitaService;
import com.uniminuto.clinica.service.RecetaService;
import com.uniminuto.clinica.model.RecetaRq;
import com.uniminuto.clinica.model.RespuestaRs;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio de recetas que contiene la lógica de negocio
 * para la gestión de prescripciones médicas en el sistema clínico.
 *
 * @author crash
 * @version 1.0
 * @since 2025-09-20
 */

@Service
public class RecetaServiceImpl implements RecetaService {

    /**
     * Repositorio JPA para operaciones de persistencia de la entidad Receta.
     * Maneja las consultas y transacciones con la base de datos.
     */
    @Autowired
    private RecetaRepository recetaRepository;

    /**
     * Servicio de citas utilizado para validar la existencia de citas
     * antes de crear recetas asociadas.
     */
    @Autowired
    private CitaService citaService;

    /**
     * Repositorio JPA para consultas directas a la entidad Cita.
     * Utilizado para validaciones y búsquedas específicas.
     */
    @Autowired
    private CitaRepository citaRepository;

    //Obtiene todas las recetas utilizando el método findAll() del repositorio JPA.
    @Override
    public List<Receta> listarTodasRecetas() {
        return recetaRepository.findAll();
    }
/* Implementa el ordenamiento descendente utilizando Java 8 Streams
 * y la comparación de fechas de creación.
 *
 * @return List<Receta> donde el primer elemento es la receta más reciente
 */
    @Override
    public List<Receta> listarRecetasDesc() {
        return recetaRepository.findAll()
                /**
                 * Conversión a Stream para operaciones funcionales sobre la colección
                 */
                .stream()
                /**
                 * Ordenamiento descendente por fecha de creación:
                 * r2.compareTo(r1) invierte el orden natural para obtener descendente
                 * - r1: primera receta en la comparación
                 * - r2: segunda receta en la comparación
                 * - getFechaCreacionRegistro(): obtiene LocalDateTime de creación
                 * - compareTo(): método de LocalDateTime para comparación cronológica
                 */
                .sorted((r1, r2) -> r2.getFechaCreacionRegistro().compareTo(r1.getFechaCreacionRegistro()))
                //* Materialización del Stream procesado en una List inmutable
                .toList();
    }
/*Busca recetas por ID de cita sin validar la existencia previa de la cita.
* param citaId Long que debe corresponder a una cita existente
* @return List<Receta> puede ser vacía si la cita no tiene recetas asociadas
* */
    @Override
    public List<Receta> buscarPorCita(Long citaId) throws BadRequestException {

        return recetaRepository.findByCitaId(citaId);
    }
//Busca recetas que contengan un medicamento específico identificado por su ID.
    @Override
    public List<Receta> buscarPorMedicamento(Integer medicamentoId) {
        return recetaRepository.findByMedicamentoId(medicamentoId);
    }

/* Utiliza Optional para manejar de forma segura la posible ausencia de la receta.
 * Proporciona mensaje de error descriptivo en caso de no encontrar el registro.
 *
 * @param id Long clave primaria que debe existir en la tabla receta
 * @throws BadRequestException con mensaje específico si la receta no existe
 */

    @Override
    public Receta buscarRecetaPorId(Long id) throws BadRequestException {
        /**
         * Optional<Receta> contenedor que evita el manejo directo de valores null
         */
        Optional<Receta> optReceta = recetaRepository.findById(id);
        /**
         * Validación de existencia antes de extraer el objeto
         */

        if (!optReceta.isPresent()) {
            throw new BadRequestException("No existe la receta con el id: " + id);
        }
        /**
         * Extracción segura del objeto Receta del contenedor Optional
         */
        return optReceta.get();
    }

    /**
     * {@inheritDoc}
     *
     * Proceso completo de creación de receta médica:
     * 1. Validación de campos obligatorios del request
     * 2. Verificación de existencia de la cita asociada
     * 3. Creación de nueva instancia con fecha automática
     * 4. Persistencia en base de datos
     * 5. Respuesta con confirmación de éxito
     *
     * @param recetaRq RecetaRq modelo que encapsula los datos de entrada
     * @return RespuestaRs objeto estandarizado con resultado de la operación
     * @throws BadRequestException si alguna validación falla durante el proceso
     */
    @Override
    public RespuestaRs guardarReceta(RecetaRq recetaRq) throws BadRequestException {
        // Paso 1: Validar campos obligatorios
        validarCampos(recetaRq);

        // Paso 2: Buscar y validar que la Cita existe
        // Reutilizamos el método de CitaService para obtener la cita completa
        Cita cita = buscarCitaPorId(recetaRq.getIdCita());

        // Paso 3: Crear nueva receta y asignar valores
        Receta nuevaReceta = new Receta();
        nuevaReceta.setCita(cita); //Asignación de relación JPA con Cita (Many-to-One) - Establece la referencia completa al objeto Cita
        nuevaReceta.setMedicamentoId(recetaRq.getMedicamentoId()); //Asignación directa del ID del medicamento
        nuevaReceta.setDosis(recetaRq.getDosis().toLowerCase()); //Normalización de dosis a minúsculas para consistencia en almacenamiento
        nuevaReceta.setIndicaciones(recetaRq.getIndicaciones() != null ?
                recetaRq.getIndicaciones().toLowerCase() : null); //Normalización condicional de indicaciones
        nuevaReceta.setFechaCreacionRegistro(LocalDateTime.now()); // Fecha actual mediante .now() para timestamp automático

        // Paso 4: Guardar en base de datos
        recetaRepository.save(nuevaReceta);

        // Paso 5: Retornar respuesta exitosa
        RespuestaRs respuesta = new RespuestaRs();  //Objeto de respuesta estandarizado RespuestaRs
        respuesta.setMensaje("La receta se ha guardado correctamente."); //Mensaje claro y positivo
        respuesta.setStatus(200);
        return respuesta;
    }

    /**
     * Método privado de validación que verifica la integridad y completitud
     * de los datos requeridos para crear una nueva receta.
     *
     * @param recetaRq RecetaRq objeto request a validar
     * @throws BadRequestException si algún campo obligatorio es inválido
     */
    private void validarCampos(RecetaRq recetaRq) throws BadRequestException {
        if (recetaRq.getIdCita() == null) { //Validación de ID de cita asociada
            throw new BadRequestException("El campo IdCita es obligatorio.");
        }
        if (recetaRq.getMedicamentoId() == null) { //Validación de ID de medicamento
            throw new BadRequestException("El campo MedicamentoId es obligatorio.");
        }
        if (recetaRq.getDosis() == null || recetaRq.getDosis().isBlank()) {//Validación de dosis prescrita
            throw new BadRequestException("El campo Dosis es obligatorio.");
        }
    }

    /*/**
     * Método auxiliar para buscar y validar la existencia de una cita
     * antes de crear una receta asociada.
     *
     * @param citaId Long identificador de la cita a buscar
     * @return Cita objeto completo de la cita encontrada
     * @throws BadRequestException si hay error, lo capta y devuelve el mensaje*/
    private Cita buscarCitaPorId(Long citaId) throws BadRequestException {
        return citaRepository.findById(citaId)
                .orElseThrow(() -> new BadRequestException("No existe la cita con id: " + citaId));
    }
}
