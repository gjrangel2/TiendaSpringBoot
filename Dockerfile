# Usamos una imagen base de Java que incluye Maven
FROM maven:3.9-eclipse-temurin-17 AS build

# Establecemos el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos el archivo pom.xml y lo descargamos primero para aprovechar el cache de Docker
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiamos todo el código fuente del proyecto
COPY src ./src

# Construimos el proyecto para generar el JAR ejecutable
RUN mvn clean package -DskipTests

# --- Segunda etapa: Imagen final para la aplicación en ejecución ---
# Usamos una imagen base más ligera solo con el JRE (Java Runtime Environment)
FROM openjdk:17.0-jre-slim

# Establecemos el directorio de trabajo
WORKDIR /app

# Copiamos el JAR generado de la etapa de construcción a la imagen final
COPY --from=build /app/target/*.jar app.jar

# Exponemos el puerto en el que la aplicación Spring Boot escuchará
EXPOSE 8080

# Comando para ejecutar la aplicación cuando el contenedor se inicie
ENTRYPOINT ["java", "-jar", "app.jar"]