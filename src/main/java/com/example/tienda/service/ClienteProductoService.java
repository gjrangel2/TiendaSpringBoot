package com.example.tienda.service;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tienda.model.Cliente;
import com.example.tienda.model.Producto;
import com.example.tienda.repository.ClienteRepository;
import com.example.tienda.repository.ProductoRepository;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ClienteProductoService {

    @Autowired
    private static ClienteRepository clienteRepository;

    @Autowired
    private static ProductoRepository productoRepository;

    private static final Logger logger = LoggerFactory.getLogger(ClienteProductoService.class);


    public static byte[] generateClientesProductosPdf() {
        List<Cliente> clientes = clienteRepository.findAll();
        List<Producto> productos = productoRepository.findAll();

        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, baos);
            document.open();

            // Agregar título y encabezado
            Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, new java.awt.Color(41, 128, 185));
            Paragraph title = new Paragraph("Listado de Clientes y Productos", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // Agregar listado de clientes
            Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, new java.awt.Color(52, 73, 94));
            Font fontBody = FontFactory.getFont(FontFactory.HELVETICA, 10, new java.awt.Color(44, 62, 80));

            for (Cliente cliente : clientes) {
                document.add(new Paragraph("Cliente ID: " + cliente.getId(), fontHeader));
                document.add(new Paragraph("Nombre: " + cliente.getNombre(), fontBody));
                document.add(new Paragraph("Apellido: " + cliente.getApellido(), fontBody));
                document.add(new Paragraph("Correo electrónico: " + cliente.getEmail(), fontBody));
                document.add(new Paragraph());
            }

            // Agregar listado de productos
            for (Producto producto : productos) {
                document.add(new Paragraph("Producto ID: " + producto.getId(), fontHeader));
                document.add(new Paragraph("Nombre: " + producto.getNombre(), fontBody));
                document.add(new Paragraph("Descripción: " + producto.getDescripcion(), fontBody));
                document.add(new Paragraph("Precio: " + producto.getPrecio(), fontBody));
                document.add(new Paragraph());
            }

            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            logger.error("Error al generar el PDF: {}", e.getMessage(), e);
            return null;
        }
    }
}