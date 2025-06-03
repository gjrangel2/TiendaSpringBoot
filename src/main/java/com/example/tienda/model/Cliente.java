// src/main/java/com/example/tienda/model/Cliente.java
package com.example.tienda.model; // Define el paquete donde se encuentra esta clase

import jakarta.persistence.Entity; // Importa la anotación Entity para marcar esta clase como una entidad JPA
import jakarta.persistence.GeneratedValue; // Importa la anotación GeneratedValue para la generación automática de IDs
import jakarta.persistence.GenerationType; // Importa el tipo de estrategia de generación de IDs
import jakarta.persistence.Id; // Importa la anotación Id para marcar el campo como clave primaria
import jakarta.persistence.Table; // Importa la anotación Table para especificar el nombre de la tabla en la BD
import lombok.Data; // Anotación de Lombok que genera getters, setters, toString, equals, hashCode
import lombok.NoArgsConstructor; // Anotación de Lombok que genera un constructor sin argumentos
import lombok.AllArgsConstructor; // Anotación de Lombok que genera un constructor con todos los argumentos

@Entity // Indica que esta clase es una entidad JPA y se mapeará a una tabla en la base de datos
@Table(name = "clientes") // Especifica el nombre de la tabla en la base de datos como "clientes"
@Data // Genera automáticamente getters, setters, toString(), equals() y hashCode()
@NoArgsConstructor // Genera un constructor sin argumentos (requerido por JPA)
@AllArgsConstructor // Genera un constructor con todos los campos como argumentos
public class Cliente {

    @Id // Marca este campo como la clave primaria de la tabla
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura la generación automática del ID (autoincremental por la BD)
    private Long id; // Identificador único para el cliente

    private String nombre; // Nombre del cliente
    private String apellido; // Apellido del cliente
    private String email; // Email del cliente (podría tener validaciones adicionales o ser único)
}