# ======== Compilacion ============
FROM maven:3.9.12-eclipse-temurin-21 AS build
# El usuario no debe ser root
USER nobody
# La direccion por default es /app (si no existe Docker la crea)
WORKDIR /app
# Crear carpeta para el repo de Maven
RUN mkdir -p /app/.m2
# Decirle a Maven que use esa carpeta
ENV MAVEN_OPTS="-Dmaven.repo.local=/app/.m2"
# Copiamos el pom al /app
COPY pom.xml .
# Ejecutamos esto
RUN mvn dependency:go-offline
# Copiamos el codigo a la carpeta src 
COPY src ./src
# Compilamos en un .jar
RUN mvn package -DskipTests

# =========== Ejecucion ===========

# Descargamos solo el jre
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 80

USER nobody

CMD ["java", "-jar", "app.jar"]