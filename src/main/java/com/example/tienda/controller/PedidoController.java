// src/main/java/com/example/tienda/controller/PedidoController.java
package com.example.tienda.controller; // Paquete de esta clase
import com.example.tienda.dto.EmailRequestDTO; // Importa el nuevo DTO

import com.example.tienda.dto.PedidoRequestDTO; // Importa el DTO para la solicitud de creación de pedido
import com.example.tienda.model.Pedido; // Importa la entidad Pedido
import com.example.tienda.service.ClienteProductoService;
import com.example.tienda.service.EmailService;
import com.example.tienda.service.PedidoService; // Importa el servicio de Pedido
import org.springframework.beans.factory.annotation.Autowired; // Para inyección de dependencias
import org.springframework.http.HttpStatus; // Para códigos de estado HTTP
import org.springframework.http.ResponseEntity; // Para la respuesta HTTP
import org.springframework.web.bind.annotation.*; // Anotaciones de Spring Web
import org.slf4j.Logger; // Importa el logger
import org.slf4j.LoggerFactory; // Importa el LoggerFactory

import java.util.List; // Para manejar listas

@RestController // Indica que esta clase es un controlador REST
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/pedidos") // Mapea las solicitudes que comiencen con "/api/pedidos"
public class PedidoController {

    @Autowired // Inyección del PedidoService
    private PedidoService pedidoService;
    @Autowired
    private EmailService emailService;
    private static final Logger logger = LoggerFactory.getLogger(PedidoController.class);

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
                    pedidoRequest.getProductosIds(),
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

    @PostMapping("/clientes-productos-pdf") // Mapea POST a "/api/pedidos/report/send"
    public ResponseEntity<String> generateAndSendReport(@RequestBody EmailRequestDTO emailRequestDTO) { // Recibe el email como String JSON
        try {
           String toEmail = emailRequestDTO.getEmail(); // <--- MODIFICACIÓN AQUÍ: Obtiene el email del DTO
            String salesEmail = "fotosrangel22@gmail.com";

            logger.info("Solicitud para generar y enviar reporte PDF a: {}", toEmail);
            // También se enviará al correo de ventas fijo
            pedidoService.generateAndSendPedidosReport(salesEmail);
            // Puedes enviar una copia también al 'toEmail' si es diferente
            if (!toEmail.equalsIgnoreCase(salesEmail)) {
                 pedidoService.generateAndSendPedidosReport(toEmail); // Enviar una copia al email proporcionado
            }

            return ResponseEntity.ok("Reporte PDF generado y enviado exitosamente a " + salesEmail + " (y a " + toEmail + " si es diferente).");
        } catch (Exception e) {
            logger.error("Error al generar y enviar el reporte PDF: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al generar y enviar el reporte PDF: " + e.getMessage());
        }
    }

    @PostMapping("/report/send")
    public ResponseEntity<?> generateAndSendClientesProductosPdf(@RequestBody EmailRequestDTO emailRequestDTO) {
        try {
            String toEmail = emailRequestDTO.getEmail();
            String subject = "Listado de Clientes y Productos";
            String body = "Estimado/a,<br><br>Adjunto encontrará el listado de clientes y productos.<br><br>Saludos cordiales,<br>El equipo de la Tienda";
            
            

            byte[] pdfBytes = ClienteProductoService.generateClientesProductosPdf();
            String filename = "clientes_productos.pdf";
            String contentType = "application/pdf";
    
            emailService.sendEmailWithAttachment(toEmail, subject, body, pdfBytes, filename, contentType);
            return ResponseEntity.ok("PDF generado y enviado exitosamente a " + toEmail);
        } catch (Exception e) {
            logger.error("Error al generar y enviar el PDF: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al generar y enviar el PDF: " + e.getMessage());
        }
    }
}