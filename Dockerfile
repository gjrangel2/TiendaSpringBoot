# --- Primera etapa: Construcción con Maven y JDK de Alpine ---
FROM maven:3.9-eclipse-temurin-17-alpine AS build

# Establecemos el directorio de trabajo
WORKDIR /app

# Copiamos los archivos del proyecto
COPY . .

# Limpiamos, empaquetamos y construimos el proyecto sin ejecutar tests
RUN mvn -Dmaven.test.skip=true clean package

# --- Segunda etapa: Imagen final para la aplicación en ejecución (con JRE de Alpine) ---
FROM eclipse-temurin:17-jre-alpine

# Establecemos el directorio de trabajo
WORKDIR /app

# Copiamos el JAR construido desde la etapa anterior
COPY --from=build /app/target/tienda-0.0.1-SNAPSHOT.jar ./app.jar

# Exponemos el puerto en el que corre la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]