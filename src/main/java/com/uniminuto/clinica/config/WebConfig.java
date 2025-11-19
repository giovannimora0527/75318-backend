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
        // Obtener la ruta absoluta de la carpeta diagramas
        // En desarrollo: relativa al directorio del proyecto
        // En Docker: dentro de /app/diagramas
        String projectRoot = System.getProperty("user.dir");
        String diagramasPath;
        
        // Verificar si estamos en Docker (el directorio de trabajo es /app)
        if ("/app".equals(projectRoot) || projectRoot.endsWith("/app")) {
            // En Docker, la carpeta diagramas está en /app/diagramas
            diagramasPath = "/app/diagramas/";
        } else {
            // En desarrollo, buscar en el directorio del proyecto
            diagramasPath = projectRoot + File.separator + "diagramas" + File.separator;
        }
        
        // Normalizar la ruta para Windows/Linux
        diagramasPath = diagramasPath.replace("\\", "/");
        if (!diagramasPath.endsWith("/")) {
            diagramasPath += "/";
        }
        
        // Verificar que el directorio existe
        File diagramasDir = new File(diagramasPath);
        if (!diagramasDir.exists()) {
            System.err.println("ADVERTENCIA: El directorio de diagramas no existe: " + diagramasPath);
        } else {
            System.out.println("Directorio de diagramas encontrado: " + diagramasPath);
            // Listar archivos para debugging
            File[] files = diagramasDir.listFiles();
            if (files != null) {
                System.out.println("Archivos en el directorio:");
                for (File file : files) {
                    System.out.println("  - " + file.getName());
                }
            }
        }
        
        // Registrar el handler para servir las imágenes de diagramas
        registry.addResourceHandler("/diagramas/**")
                .addResourceLocations("file:" + diagramasPath)
                .setCachePeriod(3600); // Cache por 1 hora
    }
}

