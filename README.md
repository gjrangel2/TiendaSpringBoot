# 🛍️ Tienda Spring Boot – Backend

Este es el repositorio del **backend** para la aplicación web de una tienda tipo CRUD, desarrollada con **Spring Boot** y expuesta mediante una API REST. El frontend se encuentra en un repositorio separado.

> 🔗 [Repositorio del Frontend](https://github.com/gjrangel2/TiendaSpringBootFrontend)
---
## Estructura del proyecto
![estructura](https://github.com/user-attachments/assets/65045c83-b872-4c8e-9987-ac5d8ca4a0ae)

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
- PostgreSQL
- Docker
- Render.com
- Springdoc OpenAPI (Swagger)
- GitHub
---

## 📄 Documentación de la API

Una vez ejecutado localmente o en producción, accede a la documentación interactiva de la API:
http://localhost:8080/swagger-ui/index.html
![docs1](https://github.com/user-attachments/assets/dd7e3719-2e2c-45af-a564-ed289b9f4004)
![docs2](https://github.com/user-attachments/assets/f7922658-8936-47a3-a459-682664009c5e)
![docs3](https://github.com/user-attachments/assets/9ae5b7ed-9dcc-4974-9ec8-b7af69087657)


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
```


----------------------------------------------------------------------------------------------

**NOTA: en caso de que el link de despligue no funcione contactar a 3104441243, ya que al ser una capa gratuita de RENDER, en ocasiones se detiene y hay que reiniciar el deploy**


----------------------------------------------------------------------------------------------
Desarrollado por **Geyson Rangel**

[LinkedIn](https://www.linkedin.com/in/geyson-jair-rangel-ortega-79a022233/) 
