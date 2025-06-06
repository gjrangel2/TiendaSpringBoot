package com.example.tienda.config; // O el paquete que hayas elegido

    
    import org.springframework.context.annotation.Configuration;
    import org.springframework.web.servlet.config.annotation.CorsRegistry;
    import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

    @Configuration
    public class CorsConfig implements WebMvcConfigurer {

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**") // Aplica a todos los endpoints de tu API
                    // **MUY IMPORTANTE**: Reemplaza esto con la URL REAL de tu frontend Angular en Render.
                    // Si tienes múltiples dominios de frontend (ej. staging y producción), puedes listarlos aquí.
                    .allowedOrigins("https://tiendaspringbootfrontend.onrender.com", "http://localhost:4200")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos permitidos
                    .allowedHeaders("*") // Permite todos los encabezados
                    .allowCredentials(true) // Si usas credenciales (cookies, tokens de auth), habilita esto
                    .maxAge(3600); // Tiempo de cacheo de la respuesta preflight
        }
    }