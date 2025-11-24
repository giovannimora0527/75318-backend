# 75318-backend/Dockerfile
FROM eclipse-temurin:21-jdk-alpine

# Instalar curl para healthcheck
RUN apk add --no-cache curl

WORKDIR /app

# Copiar el JAR (asegúrate de que el nombre coincida con tu build)
COPY target/clinica-*.jar app.jar

EXPOSE 8000

ENTRYPOINT ["java", "-jar", "app.jar"]
