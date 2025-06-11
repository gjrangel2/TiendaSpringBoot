// src/main/java/com/example/tienda/service/PedidoService.java
package com.example.tienda.service; // Paquete de esta clase

import com.example.tienda.model.Cliente; // Importa entidad Cliente
import com.example.tienda.model.Pedido; // Importa entidad Pedido
import com.example.tienda.model.Producto; // Importa entidad Producto
import com.example.tienda.repository.ClienteRepository; // Importa repositorio Cliente
import com.example.tienda.repository.PedidoRepository; // Importa repositorio Pedido
import com.example.tienda.repository.ProductoRepository; // Importa repositorio Producto
import org.springframework.beans.factory.annotation.Autowired; // Para inyección de dependencias
import org.springframework.stereotype.Service; // Marca como servicio de Spring
import org.springframework.transaction.annotation.Transactional; // Para manejar transacciones
import org.slf4j.Logger; // Importa el logger
import org.slf4j.LoggerFactory; // Importa el LoggerFactory


import java.time.LocalDateTime; // Para la fecha del pedido
import java.util.List; // Para la lista de productos
import java.util.Optional; // Para valores opcionales
import java.util.ArrayList; // Para crear listas

@Service // Indica que esta clase es un Servicio de Spring
public class PedidoService {

    private static final Logger logger = LoggerFactory.getLogger(PedidoService.class);

    @Autowired // Inyección de PedidoRepository
    private PedidoRepository pedidoRepository;

    @Autowired // Inyección de ClienteRepository
    private ClienteRepository clienteRepository;

    @Autowired // Inyección de ProductoRepository
    private ProductoRepository productoRepository;

    @Autowired // Inyecta el nuevo servicio de PDF
    private PdfGeneratorService pdfGeneratorService;
    
    @Autowired // Inyecta el nuevo servicio de Email
    private EmailService emailService;

    // Método para obtener todos los pedidos
    public List<Pedido> obtenerTodosLosPedidos() {
        return pedidoRepository.findAll(); // Retorna todos los pedidos
    }

    // Método para obtener un pedido por su ID
    public Optional<Pedido> obtenerPedidoPorId(Long id) {
        return pedidoRepository.findById(id); // Busca un pedido por ID
    }

    // Método para crear un nuevo pedido
    // Usamos @Transactional para asegurar que todas las operaciones de BD se completen o ninguna (atomicidad)
    @Transactional
    public Pedido crearPedido(Long clienteId, List<Long> productoIds, String estado) {
        // 1. Buscar el cliente
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + clienteId)); // Lanza excepción si el cliente no existe

        // 2. Buscar los productos y verificar stock (simplificado: solo los busca)
        List<Producto> productosDelPedido = new ArrayList<>(); // Crea una lista para almacenar los productos del pedido
        double totalCalculado = 0.0; // Inicializa el total del pedido

        for (Long productoId : productoIds) { // Itera sobre los IDs de los productos recibidos
            Producto producto = productoRepository.findById(productoId)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + productoId)); // Lanza excepción si un producto no existe
            productosDelPedido.add(producto); // Añade el producto encontrado a la lista de productos del pedido
            totalCalculado += producto.getPrecio(); // Suma el precio del producto al total
            // Aquí podrías añadir lógica para verificar y decrementar el stock del producto
        }

        // 3. Crear el objeto Pedido
        Pedido nuevoPedido = new Pedido(); // Crea una nueva instancia de Pedido
        nuevoPedido.setCliente(cliente); // Asigna el cliente al pedido
        nuevoPedido.setProductos(productosDelPedido); // Asigna la lista de productos al pedido
        nuevoPedido.setFechaPedido(LocalDateTime.now()); // Establece la fecha actual del pedido
        nuevoPedido.setEstado(estado != null ? estado : "PENDIENTE"); // Establece el estado del pedido, por defecto "PENDIENTE"
        nuevoPedido.setTotal(totalCalculado); // Asigna el total calculado al pedido

        return pedidoRepository.save(nuevoPedido); // Guarda el nuevo pedido en la base de datos
    }

    // Método para actualizar el estado de un pedido
    @Transactional
    public Optional<Pedido> actualizarEstadoPedido(Long id, String nuevoEstado) {
        Optional<Pedido> pedidoExistente = pedidoRepository.findById(id); // Busca el pedido por ID
        if (pedidoExistente.isPresent()) { // Si el pedido existe
            Pedido pedido = pedidoExistente.get(); // Obtiene el objeto Pedido
            pedido.setEstado(nuevoEstado); // Actualiza su estado
            return Optional.of(pedidoRepository.save(pedido)); // Guarda los cambios y retorna el pedido actualizado
        }
        return Optional.empty(); // Retorna vacío si el pedido no se encontró
    }


    // Método para eliminar un pedido por su ID
    public void eliminarPedido(Long id) {
        // Antes de eliminar un pedido, podrías querer realizar validaciones o liberar recursos asociados.
        // Por ejemplo, si eliminar un pedido implica reponer stock de productos, esa lógica iría aquí o en un método @PreRemove en la entidad Pedido.
        // La relación ManyToMany con productos se maneja a través de la tabla de unión (pedido_productos).
        // Al eliminar un Pedido, Hibernate/JPA se encargará de eliminar las entradas correspondientes en la tabla de unión
        // si la relación está configurada correctamente (cascade types, orphanRemoval, etc., aunque aquí no es estrictamente necesario para delete).
        if (!pedidoRepository.existsById(id)) { // Verifica si el pedido existe antes de intentar eliminarlo
             throw new RuntimeException("Pedido no encontrado con ID: " + id); // Lanza una excepción si no existe
        }
        pedidoRepository.deleteById(id); // Elimina el pedido
    }

    @Transactional // Es importante para que Hibernate pueda cargar las relaciones
    public void generateAndSendPedidosReport(String toEmail) {
        logger.info("Generando y enviando reporte de pedidos a: {}", toEmail);

        List<Pedido> pedidos = pedidoRepository.findAll(); // Obtener todos los pedidos
        // Asegurarse de que las colecciones 'productos' y 'cliente' estén inicializadas para el PDF
        pedidos.forEach(pedido -> {
            if (pedido.getCliente() != null) {
                pedido.getCliente().getId(); // Forzar carga del proxy del cliente
            }
            if (pedido.getProductos() != null) {
                pedido.getProductos().size(); // Forzar carga de la colección de productos
            }
        });

        byte[] pdfBytes = pdfGeneratorService.generatePedidosPdf(pedidos); // Generar el PDF

        String subject = "Reporte de Pedidos de Tienda";
        String body = "Estimado/a,<br><br>Adjunto encontrará el reporte de pedidos de la tienda.<br><br>Saludos cordiales,<br>El equipo de la Tienda";
        String filename = "reporte_pedidos.pdf";
        String contentType = "application/pdf";

        emailService.sendEmailWithAttachment(toEmail, subject, body, pdfBytes, filename, contentType);
        logger.info("Reporte de pedidos enviado a: {}", toEmail);
    }
}