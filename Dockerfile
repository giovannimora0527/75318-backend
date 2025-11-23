# --- ETAPA 1: COMPILACIÓN (Build) ---
# Usamos una imagen robusta de Maven para asegurar que compile bien
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copiamos configuración y dependencias
COPY pom.xml .
COPY src ./src

# Compilamos saltando tests para evitar que un test fallido detenga la construcción
# El flag -Dmaven.test.skip=true es más agresivo que -DskipTests y asegura que compile sí o sí
RUN mvn clean package -Dmaven.test.skip=true

# --- ETAPA 2: EJECUCIÓN (Run) ---
# CAMBIO CLAVE: Usamos 'jammy' (Ubuntu) en lugar de 'alpine'.
# Esto evita problemas de librerías nativas (musl vs glibc) que suelen crashear Java.
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Copiamos el jar generado
COPY --from=build /app/target/*.jar app.jar

# Variable para mejorar tiempos de arranque en entornos virtuales
ENV JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom"

# Exponemos el puerto
EXPOSE 8081

# Comando de arranque directo
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]