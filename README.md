# 🛍️ Tienda Spring Boot – Backend

Este es el repositorio del **backend** para la aplicación web de una tienda, desarrollada con **Spring Boot** y expuesta mediante una API REST. El frontend se encuentra en un repositorio separado.

> 🔗 [Repositorio del Frontend](https://github.com/gjrangel2/TiendaSpringBootFrontend)

---

## 🚀 Despliegue

El proyecto está desplegado en **Render.com** utilizando un archivo `Dockerfile`, ya que el entorno no detectaba automáticamente el tipo de aplicación. Gracias a Docker, se logró un despliegue exitoso del backend.

---

## 🔧 Tecnologías utilizadas

- Java 17
- Spring Boot 3+
- Spring Web
- Spring Data JPA
- Maven
- MySQL o H2
- Docker
- Render.com
- Springdoc OpenAPI (Swagger)
---

## 📄 Documentación de la API

Una vez ejecutado localmente o en producción, accede a la documentación interactiva de la API:
http://localhost:8080/swagger-ui/index.html


---

## ⚙️ Cómo ejecutar localmente

### Requisitos

- Java 17
- Maven

### Pasos

```bash
git clone https://github.com/gjrangel2/TiendaSpringBoot.git
cd TiendaSpringBoot
mvn clean install
mvn spring-boot:run

Abre en navegador:
http://localhost:8080

# Construir imagen
docker build -t tienda-backend .

# Ejecutar contenedor
docker run -p 8080:8080 tienda-backend

📬3104441243
Desarrollado por Geyson Rangel
📧 [ingerangel22@gmail.com]
