package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.DocumentacionApi;
import com.uniminuto.clinica.model.RespuestaRs;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Controlador para la documentación del sistema.
 * 
 * @author Sistema Clínica Uniminuto
 * @version 1.0
 */
@RestController
public class DocumentacionApiController implements DocumentacionApi {

    @Override
    public ResponseEntity<RespuestaRs> obtenerDocumentacionSistema(HttpServletRequest request) {
        Map<String, Object> documentacion = new HashMap<>();
        
        // Información general del sistema
        Map<String, Object> infoSistema = new HashMap<>();
        infoSistema.put("nombre", "Sistema de Gestión Clínica");
        infoSistema.put("version", "1.0.0");
        infoSistema.put("descripcion", "Sistema integral para la gestión de una clínica médica");
        infoSistema.put("desarrolladoPor", "Equipo de Desarrollo - Uniminuto");
        infoSistema.put("tecnologias", new String[]{
            "Spring Boot 2.7.18",
            "Angular 19",
            "MySQL 8.0",
            "JWT Authentication",
            "Swagger/OpenAPI 3.0"
        });
        documentacion.put("informacionSistema", infoSistema);
        
        // Arquitectura
        Map<String, Object> arquitectura = new HashMap<>();
        arquitectura.put("tipo", "Arquitectura en Capas (Layered Architecture)");
        arquitectura.put("descripcion", """
            El sistema implementa una arquitectura en capas que separa las responsabilidades 
            en diferentes niveles:
            
            - **Capa de Presentación (Frontend)**: Angular 19 con componentes modulares
            - **Capa de API (Backend)**: Spring Boot REST API con controladores
            - **Capa de Servicios**: Lógica de negocio y reglas de negocio
            - **Capa de Persistencia**: JPA/Hibernate con MySQL
            - **Capa de Seguridad**: Spring Security con JWT
            
            Esta arquitectura facilita el mantenimiento, escalabilidad y separación de responsabilidades.
            """);
        arquitectura.put("ventajas", new String[]{
            "Separación clara de responsabilidades",
            "Facilita el mantenimiento y testing",
            "Escalabilidad horizontal",
            "Reutilización de código",
            "Fácil integración con servicios externos"
        });
        documentacion.put("arquitectura", arquitectura);
        
        // Endpoints principales
        Map<String, Object> endpoints = new HashMap<>();
        endpoints.put("autenticacion", new String[]{
            "POST /auth/login - Inicio de sesión",
            "POST /auth/recuperar-contrasena - Recuperación de contraseña"
        });
        endpoints.put("usuarios", new String[]{
            "GET /usuario/listar - Listar usuarios",
            "POST /usuario/guardar - Crear usuario",
            "POST /usuario/actualizar - Actualizar usuario"
        });
        endpoints.put("medicos", new String[]{
            "GET /medico/listar - Listar médicos",
            "POST /medico/guardar - Crear médico",
            "POST /medico/actualizar - Actualizar médico"
        });
        endpoints.put("pacientes", new String[]{
            "GET /paciente/listar - Listar pacientes",
            "POST /paciente/guardar - Crear paciente",
            "POST /paciente/actualizar/{id} - Actualizar paciente"
        });
        endpoints.put("citas", new String[]{
            "GET /cita/listar - Listar citas",
            "POST /cita/guardar - Crear cita",
            "POST /cita/actualizar/{id} - Actualizar cita"
        });
        endpoints.put("recetas", new String[]{
            "GET /receta/recetas - Listar recetas",
            "POST /receta/guardar - Crear receta",
            "POST /receta/actualizar-receta/{id} - Actualizar receta"
        });
        endpoints.put("medicamentos", new String[]{
            "GET /medicamento/listar - Listar medicamentos",
            "POST /medicamento/guardar - Crear medicamento",
            "POST /medicamento/actualizar/{id} - Actualizar medicamento"
        });
        endpoints.put("auditoria", new String[]{
            "GET /auditoria/listar - Listar registros de auditoría",
            "GET /auditoria/listar?tipoEvento={tipo} - Filtrar por tipo de evento"
        });
        documentacion.put("endpoints", endpoints);
        
        // Diagramas UML (URLs de imágenes)
        Map<String, String> diagramas = new HashMap<>();
        
        // Construir la URL base desde la petición HTTP (funciona en desarrollo y Docker)
        String scheme = request.getScheme(); // http o https
        String serverName = request.getServerName(); // localhost o dominio
        int serverPort = request.getServerPort(); // 8000
        String contextPath = request.getContextPath(); // /clinica/v1
        
        // Construir URL base
        String baseUrl = scheme + "://" + serverName;
        if ((scheme.equals("http") && serverPort != 80) || 
            (scheme.equals("https") && serverPort != 443)) {
            baseUrl += ":" + serverPort;
        }
        baseUrl += contextPath;
        
        // URLs de las imágenes de diagramas
        diagramas.put("diagramaClases", baseUrl + "/diagramas/DiagramaClase.png");
        diagramas.put("diagramaDespliegue", baseUrl + "/diagramas/DiagramaDespliegue.png");
        diagramas.put("diagramaArquitectura", baseUrl + "/diagramas/DiagramaArquitectura.png");
        
        documentacion.put("diagramasUML", diagramas);
        
        // Análisis de Arquitectura
        Map<String, Object> analisisArquitectura = new HashMap<>();
        analisisArquitectura.put("tipoArquitectura", "Arquitectura en Capas (Layered Architecture)");
        analisisArquitectura.put("justificacion", """
            Se eligió una arquitectura en capas por las siguientes razones:
            
            1. **Separación de Responsabilidades**: Cada capa tiene una responsabilidad específica y bien definida,
               lo que facilita el mantenimiento y la comprensión del código.
            
            2. **Facilidad de Desarrollo**: Permite que diferentes desarrolladores trabajen en diferentes capas
               sin interferir entre sí, mejorando la productividad del equipo.
            
            3. **Testabilidad**: Cada capa puede ser probada de forma independiente, facilitando la creación
               de pruebas unitarias y de integración.
            
            4. **Escalabilidad**: La arquitectura en capas permite escalar cada componente de forma independiente
               según las necesidades del sistema.
            
            5. **Reutilización**: Los servicios de negocio pueden ser reutilizados por diferentes controladores
               o incluso por otros sistemas mediante la exposición de APIs.
            
            6. **Seguridad**: La capa de seguridad está claramente definida y puede ser aplicada de forma
               consistente en toda la aplicación.
            
            7. **Mantenibilidad**: Los cambios en una capa no afectan directamente a las otras, facilitando
               el mantenimiento y la evolución del sistema.
            """);
        analisisArquitectura.put("capas", new String[]{
            "Capa de Presentación (Angular)",
            "Capa de API REST (Spring Boot Controllers)",
            "Capa de Servicios (Business Logic)",
            "Capa de Persistencia (JPA/Hibernate)",
            "Capa de Seguridad (Spring Security + JWT)"
        });
        analisisArquitectura.put("patronesUtilizados", new String[]{
            "Repository Pattern",
            "Service Layer Pattern",
            "DTO Pattern",
            "Singleton Pattern (Spring Beans)",
            "Dependency Injection"
        });
        documentacion.put("analisisArquitectura", analisisArquitectura);
        
        // Documentación de código
        Map<String, Object> documentacionCodigo = new HashMap<>();
        documentacionCodigo.put("backend", """
            El código backend está documentado utilizando JavaDoc estándar. Todas las clases principales,
            interfaces, métodos públicos y campos importantes incluyen comentarios JavaDoc que describen:
            - Propósito de la clase/método
            - Parámetros de entrada
            - Valores de retorno
            - Excepciones que pueden lanzarse
            - Ejemplos de uso cuando es relevante
            """);
        documentacionCodigo.put("frontend", """
            El código frontend está documentado utilizando TypeScript Doc (JSDoc). Los servicios, componentes
            y modelos incluyen comentarios que describen:
            - Propósito del componente/servicio
            - Parámetros de métodos
            - Tipos de retorno
            - Ejemplos de uso
            """);
        documentacionCodigo.put("api", """
            La documentación de la API REST está disponible mediante Swagger/OpenAPI 3.0.
            Accede a la documentación interactiva en: http://localhost:8000/clinica/v1/swagger-ui.html
            """);
        documentacion.put("documentacionCodigo", documentacionCodigo);
        
        // Información de seguridad
        Map<String, Object> seguridad = new HashMap<>();
        seguridad.put("autenticacion", "JWT (JSON Web Tokens)");
        seguridad.put("controlAcceso", "Spring Security con filtros personalizados");
        seguridad.put("controlIntentos", """
            - Máximo de intentos fallidos: 3 (configurable)
            - Tiempo de bloqueo: 5 minutos (configurable)
            - Registro en auditoría de todos los intentos
            - Bloqueo automático después del límite
            """);
        seguridad.put("auditoria", """
            - Registro de todos los eventos CRUD
            - Registro de intentos de login fallidos
            - Registro de bloqueos de usuarios
            - Registro de recuperaciones de contraseña
            - Captura de IP de origen
            """);
        documentacion.put("seguridad", seguridad);
        
        RespuestaRs respuesta = new RespuestaRs();
        respuesta.setStatus(200);
        respuesta.setMensaje("Documentación del sistema");
        respuesta.setData(documentacion);
        
        return ResponseEntity.ok(respuesta);
    }
}

