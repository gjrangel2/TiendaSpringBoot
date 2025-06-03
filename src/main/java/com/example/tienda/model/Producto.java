// src/main/java/com/example/tienda/model/Producto.java
package com.example.tienda.model; // Define el paquete donde se encuentra esta clase

import jakarta.persistence.Entity; // Importa la anotación Entity para marcar esta clase como una entidad JPA
import jakarta.persistence.GeneratedValue; // Importa la anotación GeneratedValue para la generación automática de IDs
import jakarta.persistence.GenerationType; // Importa el tipo de estrategia de generación de IDs
import jakarta.persistence.Id; // Importa la anotación Id para marcar el campo como clave primaria
import jakarta.persistence.Table; // Importa la anotación Table para especificar el nombre de la tabla en la BD
import lombok.Data; // Anotación de Lombok que genera getters, setters, toString, equals, hashCode
import lombok.NoArgsConstructor; // Anotación de Lombok que genera un constructor sin argumentos
import lombok.AllArgsConstructor; // Anotación de Lombok que genera un constructor con todos los argumentos

@Entity // Indica que esta clase es una entidad JPA
@Table(name = "productos") // Especifica el nombre de la tabla en la base de datos como "productos"
@Data // Genera getters, setters, etc.
@NoArgsConstructor // Genera constructor sin argumentos
@AllArgsConstructor // Genera constructor con todos los argumentos
public class Producto {

    @Id // Marca este campo como la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura la generación automática del ID
    private Long id; // Identificador único para el producto

    private String nombre; // Nombre del producto
    private String descripcion; // Descripción detallada del producto
    private Double precio; // Precio del producto
    private Integer stock; // Cantidad disponible en inventario
}