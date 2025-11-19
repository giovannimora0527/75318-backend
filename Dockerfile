# Dockerfile para Backend Spring Boot
# Multi-stage build para optimizar el tamaño de la imagen

# Etapa 1: Construcción con Maven
FROM maven:3.9-eclipse-temurin-21 AS build

# Establecer directorio de trabajo
WORKDIR /app

# Copiar archivos de configuración de Maven
COPY pom.xml .

# Descargar dependencias (se cachean si no cambia el pom.xml)
RUN mvn dependency:go-offline -B

# Copiar el código fuente
COPY src ./src

# Construir la aplicación
RUN mvn clean package -DskipTests

# Etapa 2: Imagen de ejecución
FROM eclipse-temurin:21-jre-alpine

# Instalar wget para health check
RUN apk add --no-cache wget

# Establecer directorio de trabajo
WORKDIR /app

# Copiar el JAR desde la etapa de construcción
COPY --from=build /app/target/*.jar app.jar

# Copiar la carpeta de diagramas para servir las imágenes
COPY diagramas /app/diagramas

# Crear usuario no-root para seguridad y dar permisos (como root antes de cambiar de usuario)
RUN addgroup -S spring && adduser -S spring -G spring && \
    chown -R spring:spring /app && \
    chmod -R 755 /app/diagramas

# Cambiar al usuario no-root
USER spring:spring

# Exponer el puerto de la aplicación
EXPOSE 8000

# Variables de entorno por defecto (pueden ser sobrescritas)
ENV SPRING_PROFILES_ACTIVE=docker
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Health check - usar endpoint de documentación que soporta GET
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD wget --spider --quiet http://localhost:8000/clinica/v1/documentacion/sistema || exit 1

# Comando para ejecutar la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

