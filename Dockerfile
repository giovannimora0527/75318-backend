# Usar imagen base de Java 21
FROM eclipse-temurin:21-jdk

# Directorio de trabajo
WORKDIR /app

# Copiar el JAR
COPY target/*.jar app.jar

# Exponer el puerto correcto
EXPOSE 8000

# Ejecutar Spring Boot indicando explícitamente el puerto
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=8000"]
