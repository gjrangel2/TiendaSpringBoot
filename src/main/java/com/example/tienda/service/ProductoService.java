// src/main/java/com/example/tienda/service/ProductoService.java
package com.example.tienda.service; // Paquete de esta clase

import com.example.tienda.model.Producto; // Importa la entidad Producto
import com.example.tienda.repository.ProductoRepository; // Importa el repositorio de Producto
import org.springframework.beans.factory.annotation.Autowired; // Para la inyección de dependencias
import org.springframework.stereotype.Service; // Marca esta clase como un Servicio de Spring
import java.util.List; // Para manejar listas de productos
import java.util.Optional; // Para manejar valores opcionales

@Service // Indica que esta clase es un Servicio de Spring
public class ProductoService {

    @Autowired // Inyección de dependencias del ProductoRepository
    private ProductoRepository productoRepository;

    // Método para obtener todos los productos
    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll(); // Retorna la lista de todos los productos desde la BD
    }

    // Método para obtener un producto por su ID
    public Optional<Producto> obtenerProductoPorId(Long id) {
        return productoRepository.findById(id); // Busca un producto por su ID
    }

    // Método para guardar (crear o actualizar) un producto
    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto); // Guarda el producto en la BD
    }

    // Método para eliminar un producto por su ID
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id); // Elimina el producto de la BD
    }
}