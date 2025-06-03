// src/main/java/com/example/tienda/TiendaApplication.java
package com.example.tienda; // Paquete raíz de tu aplicación

import org.springframework.boot.SpringApplication; // Clase para iniciar una aplicación Spring Boot
import org.springframework.boot.autoconfigure.SpringBootApplication; // Anotación principal de Spring Boot

@SpringBootApplication // Esta anotación combina @Configuration, @EnableAutoConfiguration y @ComponentScan
                       // @Configuration: Marca la clase como una fuente de definiciones de beans.
                       // @EnableAutoConfiguration: Habilita la configuración automática de Spring Boot.
                       // @ComponentScan: Escanea componentes (como @Controller, @Service, @Repository) en el paquete actual y subpaquetes.
public class TiendaApplication {

    public static void main(String[] args) {
        SpringApplication.run(TiendaApplication.class, args); // Inicia la aplicación Spring Boot
        // Esto levanta un servidor web embebido (Tomcat por defecto) y despliega tu aplicación.
    }
}