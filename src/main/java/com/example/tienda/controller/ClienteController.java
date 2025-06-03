// src/main/java/com/example/tienda/controller/ClienteController.java
package com.example.tienda.controller; // Paquete de esta clase

import com.example.tienda.model.Cliente; // Importa la entidad Cliente
import com.example.tienda.service.ClienteService; // Importa el servicio de Cliente
import org.springframework.beans.factory.annotation.Autowired; // Para inyección de dependencias
import org.springframework.http.HttpStatus; // Para manejar códigos de estado HTTP
import org.springframework.http.ResponseEntity; // Para encapsular la respuesta HTTP
import org.springframework.web.bind.annotation.*; // Importa anotaciones de Spring Web (RestController, RequestMapping, GetMapping, etc.)
import java.util.List; // Para manejar listas

@RestController // Combina @Controller y @ResponseBody, indica que esta clase maneja requests y retorna datos directamente en el cuerpo de la respuesta (JSON/XML)
@RequestMapping("/api/clientes") // Mapea todas las solicitudes HTTP que comiencen con "/api/clientes" a este controlador
public class ClienteController {

    @Autowired // Inyección de dependencias del ClienteService
    private ClienteService clienteService;

    // Endpoint para obtener todos los clientes
    @GetMapping // Mapea solicitudes GET a "/api/clientes"
    public List<Cliente> obtenerTodos() {
        return clienteService.obtenerTodosLosClientes(); // Llama al servicio para obtener y retornar la lista de clientes
    }

    // Endpoint para obtener un cliente por su ID
    @GetMapping("/{id}") // Mapea solicitudes GET a "/api/clientes/{id}" donde {id} es una variable de ruta
    public ResponseEntity<Cliente> obtenerPorId(@PathVariable Long id) { // @PathVariable extrae el valor de 'id' de la URL
        return clienteService.obtenerClientePorId(id) // Busca el cliente por ID usando el servicio
                .map(ResponseEntity::ok) // Si se encuentra, envuelve el cliente en una respuesta HTTP 200 OK
                .orElse(ResponseEntity.notFound().build()); // Si no se encuentra, retorna una respuesta HTTP 404 Not Found
    }

    // Endpoint para crear un nuevo cliente
    @PostMapping // Mapea solicitudes POST a "/api/clientes"
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente) { // @RequestBody convierte el JSON del cuerpo de la solicitud en un objeto Cliente
        Cliente nuevoCliente = clienteService.guardarCliente(cliente); // Guarda el nuevo cliente usando el servicio
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente); // Retorna el cliente creado con un estado HTTP 201 Created
    }

    // Endpoint para actualizar un cliente existente
    @PutMapping("/{id}") // Mapea solicitudes PUT a "/api/clientes/{id}"
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody Cliente clienteDetails) {
        return clienteService.obtenerClientePorId(id) // Intenta obtener el cliente existente
                .map(clienteExistente -> { // Si el cliente existe...
                    clienteExistente.setNombre(clienteDetails.getNombre()); // Actualiza el nombre
                    clienteExistente.setApellido(clienteDetails.getApellido()); // Actualiza el apellido
                    clienteExistente.setEmail(clienteDetails.getEmail()); // Actualiza el email
                    Cliente clienteActualizado = clienteService.guardarCliente(clienteExistente); // Guarda los cambios
                    return ResponseEntity.ok(clienteActualizado); // Retorna el cliente actualizado con HTTP 200 OK
                })
                .orElse(ResponseEntity.notFound().build()); // Si no se encuentra, retorna HTTP 404 Not Found
    }

    // Endpoint para eliminar un cliente
    @DeleteMapping("/{id}") // Mapea solicitudes DELETE a "/api/clientes/{id}"
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        if (clienteService.obtenerClientePorId(id).isPresent()) { // Verifica si el cliente existe
            clienteService.eliminarCliente(id); // Elimina el cliente
            return ResponseEntity.noContent().build(); // Retorna una respuesta HTTP 204 No Content (éxito sin cuerpo)
        } else {
            return ResponseEntity.notFound().build(); // Si no se encuentra, retorna HTTP 404 Not Found
        }
    }
}