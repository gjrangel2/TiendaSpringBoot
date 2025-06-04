// src/main/java/com/example/tienda/controller/ProductoController.java
package com.example.tienda.controller; // Paquete de esta clase

import com.example.tienda.model.Producto; // Importa la entidad Producto
import com.example.tienda.service.ProductoService; // Importa el servicio de Producto
import org.springframework.beans.factory.annotation.Autowired; // Para inyección de dependencias
import org.springframework.http.HttpStatus; // Para códigos de estado HTTP
import org.springframework.http.ResponseEntity; // Para la respuesta HTTP
import org.springframework.web.bind.annotation.*; // Anotaciones de Spring Web
import java.util.List; // Para manejar listas

@RestController // Indica que esta clase es un controlador REST
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/productos") // Mapea las solicitudes que comiencen con "/api/productos"
public class ProductoController {

    @Autowired // Inyección del ProductoService
    private ProductoService productoService;

    // Endpoint para obtener todos los productos
    @GetMapping // Mapea GET a "/api/productos"
    public List<Producto> obtenerTodos() {
        return productoService.obtenerTodosLosProductos(); // Retorna la lista de productos
    }

    // Endpoint para obtener un producto por ID
    @GetMapping("/{id}") // Mapea GET a "/api/productos/{id}"
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        return productoService.obtenerProductoPorId(id) // Busca el producto
                .map(ResponseEntity::ok) // Si existe, HTTP 200 OK
                .orElse(ResponseEntity.notFound().build()); // Si no, HTTP 404 Not Found
    }

    // Endpoint para crear un nuevo producto
    @PostMapping // Mapea POST a "/api/productos"
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) { // @RequestBody para obtener el producto del cuerpo de la solicitud
        Producto nuevoProducto = productoService.guardarProducto(producto); // Guarda el producto
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto); // Retorna HTTP 201 Created con el producto
    }

    // Endpoint para actualizar un producto
    @PutMapping("/{id}") // Mapea PUT a "/api/productos/{id}"
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto productoDetails) {
        return productoService.obtenerProductoPorId(id) // Busca el producto existente
                .map(productoExistente -> { // Si existe...
                    productoExistente.setNombre(productoDetails.getNombre()); // Actualiza nombre
                    productoExistente.setDescripcion(productoDetails.getDescripcion()); // Actualiza descripción
                    productoExistente.setPrecio(productoDetails.getPrecio()); // Actualiza precio
                    productoExistente.setStock(productoDetails.getStock()); // Actualiza stock
                    Producto productoActualizado = productoService.guardarProducto(productoExistente); // Guarda los cambios
                    return ResponseEntity.ok(productoActualizado); // Retorna HTTP 200 OK
                })
                .orElse(ResponseEntity.notFound().build()); // Si no, HTTP 404 Not Found
    }

    // Endpoint para eliminar un producto
    @DeleteMapping("/{id}") // Mapea DELETE a "/api/productos/{id}"
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        if (productoService.obtenerProductoPorId(id).isPresent()) { // Verifica si existe
            productoService.eliminarProducto(id); // Elimina el producto
            return ResponseEntity.noContent().build(); // Retorna HTTP 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // Si no, HTTP 404 Not Found
        }
    }
}