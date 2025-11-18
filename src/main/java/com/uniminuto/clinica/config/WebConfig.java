package com.uniminuto.clinica.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * Configuración para servir recursos estáticos (imágenes de diagramas).
 * 
 * @author Sistema Clínica Uniminuto
 * @version 1.0
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Obtener la ruta absoluta de la carpeta diagramas (relativa al directorio del proyecto)
        String projectRoot = System.getProperty("user.dir");
        String diagramasPath = projectRoot + File.separator + "diagramas" + File.separator;
        
        // Normalizar la ruta para Windows/Linux
        diagramasPath = diagramasPath.replace("\\", "/");
        if (!diagramasPath.endsWith("/")) {
            diagramasPath += "/";
        }
        
        // Registrar el handler para servir las imágenes de diagramas
        registry.addResourceHandler("/diagramas/**")
                .addResourceLocations("file:" + diagramasPath)
                .setCachePeriod(3600); // Cache por 1 hora
    }
}

