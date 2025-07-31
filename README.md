# 🛍️ Tienda Spring Boot – Backend

Este es el repositorio del **backend** para la aplicación web de una tienda tipo CRUD, desarrollada con **Spring Boot** y expuesta mediante una API REST. El frontend se encuentra en un repositorio separado.

> 🔗 [Repositorio del Frontend](https://github.com/gjrangel2/TiendaSpringBootFrontend)
---
## Estructura del proyecto
src
└── main
    ├── java
    │   └── com
    │       └── example
    │           └── tienda
    │               ├── config
    │               │   └── CorsConfig.java
    │               ├── controller
    │               │   ├── ClienteController.java
    │               │   ├── PedidoController.java
    │               │   └── ProductoController.java
    │               ├── dto
    │               │   ├── EmailRequestDTO.java
    │               │   ├── PedidoRequestDTO.java
    │               │   └── ReporteRequestDTO.java
    │               ├── model
    │               │   ├── Cliente.java
    │               │   ├── Pedido.java
    │               │   └── Producto.java
    │               ├── repository
    │               │   ├── ClienteRepository.java
    │               │   ├── PedidoRepository.java
    │               │   └── ProductoRepository.java
    │               ├── service
    │               │   ├── ClienteService.java
    │               │   ├── EmailService.java
    │               │   ├── PdfGeneratorService.java
    │               │   ├── PedidoService.java
    │               │   └── ProductoService.java
    │               └── TiendaApplication.java
    ├── resources
    │   ├── static
    │   ├── templates
    │   └── application.properties
└── test

---
## 🚀 Despliegue

El proyecto está desplegado en la capa gratuita de **Render.com** utilizando un archivo `Dockerfile`, ya que el entorno no detectaba automáticamente el tipo de aplicación. Gracias a Docker, se logró un despliegue exitoso del backend.
![render2](https://github.com/user-attachments/assets/4920fe06-7c1a-44a7-8092-3c47413bd6a3)

---

## 🔧 Tecnologías utilizadas

- Java 17
- Spring Boot 3+
- Spring Web
- Spring Data JPA
- Spring Boot Mail
- Maven
- MySQL o H2
- Docker
- Render.com
- Springdoc OpenAPI (Swagger)
---

## 📄 Documentación de la API

Una vez ejecutado localmente o en producción, accede a la documentación interactiva de la API:
http://localhost:8080/swagger-ui/index.html
![docs1](https://github.com/user-attachments/assets/dd7e3719-2e2c-45af-a564-ed289b9f4004)


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
