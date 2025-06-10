// src/main/java/com/example/tienda/dto/PedidoRequestDTO.java
package com.example.tienda.dto; // Paquete para DTOs

import lombok.Data; // Lombok para getters, setters, etc.
import java.util.List; // Para la lista de IDs de productos

@Data // Genera getters, setters, toString, equals, hashCode
public class PedidoRequestDTO {
    private Long clienteId; // ID del cliente que realiza el pedido
    private List<Long> productosIds; // Lista de IDs de los productos en el pedido
    private String estado; // Estado inicial del pedido (opcional, puede tener un valor por defecto en el servicio)
}