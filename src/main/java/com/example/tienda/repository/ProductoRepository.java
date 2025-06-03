// src/main/java/com/example/tienda/repository/ProductoRepository.java
package com.example.tienda.repository; // Paquete de esta interfaz

import com.example.tienda.model.Producto; // Importa la entidad Producto
import org.springframework.data.jpa.repository.JpaRepository; // Importa la interfaz base JpaRepository
import org.springframework.stereotype.Repository; // Anotación para marcar como un componente de repositorio

@Repository // Marca esta interfaz como un Repositorio de Spring
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Manejará la entidad Producto, cuya clave primaria es de tipo Long.
    // Proporciona métodos CRUD listos para usar.
}
