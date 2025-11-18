package com.uniminuto.clinica.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuración de Swagger/OpenAPI para documentación de la API REST.
 * 
 * @author Sistema Clínica Uniminuto
 * @version 1.0
 */
@Configuration
public class SwaggerConfig {

    /**
     * Configuración principal de OpenAPI/Swagger.
     * 
     * @return Configuración de OpenAPI con información de la API
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API REST - Sistema de Gestión Clínica")
                        .version("1.0.0")
                        .description("""
                                API REST para el sistema de gestión de clínica desarrollado para Uniminuto.
                                
                                ## Características principales:
                                - Gestión de usuarios y autenticación con JWT
                                - Gestión de médicos, pacientes y citas
                                - Gestión de recetas médicas y medicamentos
                                - Sistema de auditoría completo
                                - Control de intentos de inicio de sesión
                                - Recuperación de contraseñas
                                
                                ## Endpoints Públicos (sin autenticación):
                                - POST /auth/login - Iniciar sesión
                                - POST /auth/recuperar-contrasena - Recuperar contraseña
                                - GET /documentacion/sistema - Documentación del sistema
                                - GET /diagramas/** - Imágenes de diagramas
                                
                                ## Endpoints Protegidos (requieren autenticación):
                                La mayoría de endpoints requieren un token JWT. Para probarlos:
                                1. Primero, autentícate usando POST /auth/login
                                2. Copia el token recibido en la respuesta
                                3. Haz clic en el botón "Authorize" (arriba a la derecha)
                                4. Ingresa: Bearer {tu_token} (sin llaves, solo el token)
                                5. Ahora podrás probar los endpoints protegidos
                                
                                **Nota:** Puedes probar los endpoints públicos sin autenticación.
                                """)
                        .contact(new Contact()
                                .name("Equipo de Desarrollo")
                                .email("programacionwebuniminuto1@gmail.com"))
                        .license(new License()
                                .name("Proyecto Académico")
                                .url("https://www.uniminuto.edu")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8000/clinica/v1")
                                .description("Servidor de desarrollo local")
                ))
                .components(new Components()
                        .addSecuritySchemes("bearer-jwt", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")));
    }
}

