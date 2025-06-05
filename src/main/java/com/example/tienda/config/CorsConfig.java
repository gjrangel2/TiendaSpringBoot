package com.example.tienda.config; // O el paquete que hayas elegido

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // Indica que esta clase es una fuente de definiciones de beans de Spring
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Aplica la configuración CORS a todos los endpoints bajo /api/
                .allowedOrigins("http://localhost:4200") // PERMITE solicitudes desde el origen de tu Angular
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Permite estos métodos HTTP
                .allowedHeaders("*") // Permite todos los encabezados
                .allowCredentials(true); // Permite el envío de cookies o encabezados de autorización
    }
}