// src/main/java/com/example/tienda/model/Pedido.java
package com.example.tienda.model; // Define el paquete donde se encuentra esta clase

import jakarta.persistence.Entity; // Anotación para marcar como entidad JPA
import jakarta.persistence.GeneratedValue; // Para generación automática de IDs
import jakarta.persistence.GenerationType; // Tipo de estrategia de generación de IDs
import jakarta.persistence.Id; // Para marcar la clave primaria
import jakarta.persistence.JoinColumn; // Para especificar la columna de unión en relaciones
import jakarta.persistence.JoinTable; // Para especificar la tabla de unión en relaciones ManyToMany
import jakarta.persistence.ManyToMany; // Para definir una relación muchos a muchos
import jakarta.persistence.ManyToOne; // Para definir una relación muchos a uno
import jakarta.persistence.Table; // Para especificar el nombre de la tabla
import lombok.Data; // Genera getters, setters, etc.
import lombok.NoArgsConstructor; // Genera constructor sin argumentos
import lombok.AllArgsConstructor; // Genera constructor con todos los argumentos
import java.time.LocalDateTime; // Para manejar fechas y horas
import java.util.List; // Para manejar colecciones de objetos (productos)

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity // Indica que esta clase es una entidad JPA
@Table(name = "pedidos") // Nombre de la tabla en la BD
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id // Clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID autogenerado
    private Long id; // Identificador único del pedido

    private LocalDateTime fechaPedido; // Fecha y hora en que se realizó el pedido

    private String estado; // Estado del pedido (ej: "PENDIENTE", "ENVIADO", "ENTREGADO")

    @ManyToOne // Define una relación muchos-a-uno: muchos pedidos pueden pertenecer a un cliente
    @JoinColumn(name = "cliente_id", nullable = false) // Especifica la columna de clave foránea 'cliente_id' en la tabla 'pedidos'
                                                      // nullable = false significa que un pedido siempre debe tener un cliente asociado
    
     @JsonIgnore
     private Cliente cliente; // El cliente que realizó el pedido

    @ManyToMany // Define una relación muchos-a-muchos: un pedido puede tener muchos productos, y un producto puede estar en muchos pedidos
    @JoinTable(
        name = "pedido_productos", // Nombre de la tabla intermedia que manejará la relación
        joinColumns = @JoinColumn(name = "pedido_id"), // Columna en la tabla intermedia que referencia al ID de Pedido
        inverseJoinColumns = @JoinColumn(name = "producto_id") // Columna en la tabla intermedia que referencia al ID de Producto
    )
     @JsonIgnore
    private List<Producto> productos; // Lista de productos incluidos en este pedido

    private Double total; // El monto total del pedido (se podría calcular o establecer directamente)
}
