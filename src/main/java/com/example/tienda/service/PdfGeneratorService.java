// src/main/java/com/example/tienda/service/PdfGeneratorService.java
package com.example.tienda.service;

import com.example.tienda.model.Pedido;
import com.lowagie.text.*; // Importaciones de OpenPDF
import com.lowagie.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List; // OJO: Este es java.util.List, NO el de OpenPDF

@Service
public class PdfGeneratorService {

    private static final Logger logger = LoggerFactory.getLogger(PdfGeneratorService.class);

    public byte[] generatePedidosPdf(List<Pedido> pedidos) {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, baos);
            document.open();

            // Título
            Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, new java.awt.Color(41, 128, 185));
            Paragraph title = new Paragraph("Reporte de Pedidos", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            if (pedidos == null || pedidos.isEmpty()) {
                Paragraph noPedidos = new Paragraph("No hay pedidos registrados para el reporte.", FontFactory.getFont(FontFactory.HELVETICA, 12, Font.ITALIC));
                noPedidos.setAlignment(Element.ALIGN_CENTER);
                document.add(noPedidos);
            } else {
                // Iterar sobre cada pedido para añadirlo al PDF
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, new java.awt.Color(52, 73, 94));
                Font fontBody = FontFactory.getFont(FontFactory.HELVETICA, 10, new java.awt.Color(44, 62, 80));

                for (Pedido pedido : pedidos) {
                    document.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));

                    Paragraph pedidoInfo = new Paragraph();
                    pedidoInfo.add(new Chunk("Pedido ID: ", fontHeader));
                    pedidoInfo.add(new Chunk(pedido.getId().toString(), fontBody));
                    pedidoInfo.add(new Chunk("  Fecha: ", fontHeader));
                    pedidoInfo.add(new Chunk(pedido.getFechaPedido().format(formatter), fontBody));
                    pedidoInfo.add(new Chunk("  Estado: ", fontHeader));
                    pedidoInfo.add(new Chunk(pedido.getEstado(), fontBody));
                    pedidoInfo.add(new Chunk("  Total: ", fontHeader));
                    pedidoInfo.add(new Chunk(String.format("$%.2f", pedido.getTotal()), fontBody));
                    document.add(pedidoInfo);

                    if (pedido.getCliente() != null) {
                        Paragraph clienteInfo = new Paragraph();
                        clienteInfo.add(new Chunk("Cliente: ", fontHeader));
                        clienteInfo.add(new Chunk(pedido.getCliente().getNombre() + " " + pedido.getCliente().getApellido() + " (ID: " + pedido.getCliente().getId() + ")", fontBody));
                        document.add(clienteInfo);
                    }

                    if (pedido.getProductos() != null && !pedido.getProductos().isEmpty()) {
                        Paragraph productosHeader = new Paragraph("Productos del Pedido:", fontHeader);
                        productosHeader.setSpacingBefore(5);
                        document.add(productosHeader);

                        // Crear una lista de productos para el PDF (AHORA USANDO EL NOMBRE COMPLETO DE LA CLASE)
                        com.lowagie.text.List productList = new com.lowagie.text.List(false, 10); // <-- CORRECCIÓN AQUÍ
                        for (com.example.tienda.model.Producto producto : pedido.getProductos()) { // Asegúrate de que el tipo Producto sea correcto
                            productList.add(new ListItem(
                                " - " + producto.getNombre() + " (ID: " + producto.getId() + ", Precio: $" + String.format("%.2f", producto.getPrecio()) + ")",
                                fontBody
                            ));
                        }
                        document.add(productList);
                    } else {
                        document.add(new Paragraph("Productos: Ninguno", fontBody));
                    }
                    document.add(new Paragraph(" ")); // Espacio entre pedidos
                }
            }

            document.close();
            logger.info("PDF de pedidos generado exitosamente.");
            return baos.toByteArray();

        } catch (DocumentException e) {
            logger.error("Error al generar el PDF: {}", e.getMessage(), e);
            throw new RuntimeException("Error al generar el PDF", e);
        } finally {
            if (document.isOpen()) {
                document.close();
            }
        }
    }
}
