// src/main/java/com/example/tienda/controller/PedidoController.java
package com.example.tienda.controller; // Paquete de esta clase

import com.example.tienda.dto.PedidoRequestDTO; // Importa el DTO para la solicitud de creación de pedido
import com.example.tienda.model.Pedido; // Importa la entidad Pedido
import com.example.tienda.service.PedidoService; // Importa el servicio de Pedido
import org.springframework.beans.factory.annotation.Autowired; // Para inyección de dependencias
import org.springframework.http.HttpStatus; // Para códigos de estado HTTP
import org.springframework.http.ResponseEntity; // Para la respuesta HTTP
import org.springframework.web.bind.annotation.*; // Anotaciones de Spring Web

import java.util.List; // Para manejar listas

@RestController // Indica que esta clase es un controlador REST
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/pedidos") // Mapea las solicitudes que comiencen con "/api/pedidos"
public class PedidoController {

    @Autowired // Inyección del PedidoService
    private PedidoService pedidoService;

    // Endpoint para obtener todos los pedidos
    @GetMapping // Mapea GET a "/api/pedidos"
    public List<Pedido> obtenerTodos() {
        return pedidoService.obtenerTodosLosPedidos(); // Retorna la lista de todos los pedidos
    }

    // Endpoint para obtener un pedido por ID
    @GetMapping("/{id}") // Mapea GET a "/api/pedidos/{id}"
    public ResponseEntity<Pedido> obtenerPorId(@PathVariable Long id) {
        return pedidoService.obtenerPedidoPorId(id) // Busca el pedido
                .map(ResponseEntity::ok) // Si existe, HTTP 200 OK
                .orElse(ResponseEntity.notFound().build()); // Si no, HTTP 404 Not Found
    }

    // Endpoint para crear un nuevo pedido
    @PostMapping // Mapea POST a "/api/pedidos"
    public ResponseEntity<?> crearPedido(@RequestBody PedidoRequestDTO pedidoRequest) { // @RequestBody para obtener el DTO del cuerpo de la solicitud
        try {
            // Llama al servicio para crear el pedido, pasando los IDs y el estado desde el DTO
            Pedido nuevoPedido = pedidoService.crearPedido(
                    pedidoRequest.getClienteId(),
                    pedidoRequest.getProductoIds(),
                    pedidoRequest.getEstado()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido); // Retorna HTTP 201 Created con el pedido creado
        } catch (RuntimeException e) { // Captura excepciones si, por ejemplo, un cliente o producto no se encuentra
            return ResponseEntity.badRequest().body(e.getMessage()); // Retorna HTTP 400 Bad Request con el mensaje de error
        }
    }

    // Endpoint para actualizar el estado de un pedido
    @PutMapping("/{id}/estado") // Mapea PUT a "/api/pedidos/{id}/estado"
    public ResponseEntity<Pedido> actualizarEstadoPedido(@PathVariable Long id, @RequestBody String nuevoEstado) {
        // El cuerpo de la solicitud solo contendrá el nuevo estado como un String JSON, ej: "ENVIADO"
        // Spring lo deserializará a un String.
        // Si necesitas un JSON más complejo, crea un DTO para el estado.
        return pedidoService.actualizarEstadoPedido(id, nuevoEstado)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    // Endpoint para eliminar un pedido
    @DeleteMapping("/{id}") // Mapea DELETE a "/api/pedidos/{id}"
    public ResponseEntity<Void> eliminarPedido(@PathVariable Long id) {
        try {
            pedidoService.eliminarPedido(id); // Intenta eliminar el pedido
            return ResponseEntity.noContent().build(); // Retorna HTTP 204 No Content si tiene éxito
        } catch (RuntimeException e) { // Captura si el pedido no se encuentra para eliminar
            return ResponseEntity.notFound().build(); // Retorna HTTP 404 Not Found
        }
    }
}