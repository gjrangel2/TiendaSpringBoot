
// src/main/java/com/example/tienda/repository/PedidoRepository.java
package com.example.tienda.repository; // Paquete de esta interfaz

import com.example.tienda.model.Pedido; // Importa la entidad Pedido
import org.springframework.data.jpa.repository.JpaRepository; // Importa la interfaz base JpaRepository
import org.springframework.stereotype.Repository; // Anotación para marcar como un componente de repositorio

@Repository // Marca esta interfaz como un Repositorio de Spring
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    // Manejará la entidad Pedido, cuya clave primaria es de tipo Long.
    // Proporciona métodos CRUD listos para usar.
    // Podrías añadir métodos para buscar pedidos por cliente, por fecha, etc.
    // List<Pedido> findByClienteId(Long clienteId); // Ejemplo de método de consulta personalizado
}