# --- Primera etapa: Construcción de la aplicación (con JDK de Alpine) ---
# Usamos una imagen de OpenJDK 21 LTS para asegurar la compatibilidad y estabilidad
FROM eclipse-temurin:21-jdk-jammy as build

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia los archivos de configuración de Maven (pom.xml) y descarga las dependencias
# Esto ayuda a que el proceso de compilación sea más rápido en futuras builds (caching de capas de Docker)
COPY pom.xml .
RUN mvn dependency:resolve

# Copia todo el código fuente de la aplicación al directorio de trabajo
COPY src ./src

# Limpiamos, empaquetamos y construimos el proyecto sin ejecutar tests
RUN mvn -Dmaven.test.skip=true clean package

# --- Segunda etapa: Imagen final para la aplicación en ejecución (con JRE de Alpine) ---
# Usamos una imagen de OpenJDK 21 JRE para el entorno de ejecución ligero
FROM eclipse-temurin:21-jre-jammy

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo JAR compilado desde la etapa de 'build' a la imagen final
COPY --from=build /app/target/*.jar app.jar

# Expone el puerto en el que la aplicación escuchará (ajusta según tu aplicación)
EXPOSE 8080

# Define el comando para ejecutar la aplicación cuando el contenedor se inicie
ENTRYPOINT ["java", "-jar", "app.jar"]