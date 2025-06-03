// src/main/java/com/example/tienda/service/ClienteService.java
package com.example.tienda.service; // Paquete de esta clase

import com.example.tienda.model.Cliente; // Importa la entidad Cliente
import com.example.tienda.repository.ClienteRepository; // Importa el repositorio de Cliente
import org.springframework.beans.factory.annotation.Autowired; // Para la inyección de dependencias
import org.springframework.stereotype.Service; // Marca esta clase como un Servicio de Spring
import java.util.List; // Para manejar listas de clientes
import java.util.Optional; // Para manejar valores que pueden ser nulos (como el resultado de findById)

@Service // Indica que esta clase es un componente de Servicio en Spring (contiene lógica de negocio)
public class ClienteService {

    @Autowired // Inyección de dependencias: Spring crea e inyecta una instancia de ClienteRepository aquí
    private ClienteRepository clienteRepository;

    // Método para obtener todos los clientes
    public List<Cliente> obtenerTodosLosClientes() {
        return clienteRepository.findAll(); // Llama al método findAll() del repositorio
    }

    // Método para obtener un cliente por su ID
    public Optional<Cliente> obtenerClientePorId(Long id) {
        return clienteRepository.findById(id); // Llama al método findById() del repositorio
    }

    // Método para crear o actualizar un cliente
    public Cliente guardarCliente(Cliente cliente) {
        return clienteRepository.save(cliente); // Llama al método save() del repositorio (crea si el ID es nulo, actualiza si existe)
    }

    // Método para eliminar un cliente por su ID
    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id); // Llama al método deleteById() del repositorio
    }
}