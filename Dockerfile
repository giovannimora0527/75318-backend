# Stage 1: build con Maven y JDK 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY pom.xml .
COPY src ./src
RUN mvn -B -DskipTests package

# Stage 2: runtime con JDK 21
FROM eclipse-temurin:21-jre
WORKDIR /app
ARG JAR_FILE=target/*.jar
COPY --from=build /workspace/${JAR_FILE} /app/app.jar
ENV JAVA_OPTS="--enable-preview"
EXPOSE 8000
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
