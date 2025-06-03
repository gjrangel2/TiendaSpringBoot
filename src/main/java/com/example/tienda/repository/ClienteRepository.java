// src/main/java/com/example/tienda/repository/ClienteRepository.java
package com.example.tienda.repository; // Paquete de esta interfaz

import com.example.tienda.model.Cliente; // Importa la entidad Cliente
import org.springframework.data.jpa.repository.JpaRepository; // Importa la interfaz base JpaRepository
import org.springframework.stereotype.Repository; // Anotación para marcar como un componente de repositorio de Spring

@Repository // Indica que esta interfaz es un Repositorio de Spring (un bean gestionado por Spring)
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // JpaRepository<Cliente, Long> significa que este repositorio manejará la entidad Cliente,
    // y el tipo de la clave primaria de Cliente es Long.
    // Spring Data JPA proporcionará automáticamente implementaciones para métodos CRUD básicos
    // como save(), findById(), findAll(), deleteById(), etc.
    // Puedes añadir métodos de consulta personalizados aquí si los necesitas.
}