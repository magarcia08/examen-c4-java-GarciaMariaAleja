/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tecnostore.test;

/**
 *
 * @author camper
 */

import tecnostore.dao.CelularDAO;
import tecnostore.dao.VentaDAO;
import tecnostore.model.Celular;
import tecnostore.model.DetalleVenta;
import tecnostore.model.Venta;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReporteService {

    private static ReporteService instancia;

    private final CreditoDAO creditoDAO;
    private final VentaDAO   ventaDAO;
    private final CelularDAO celularDAO;

    private static final String RUTA = "src/reportes/reporte_global.txt";

    private ReporteService() {
        this.creditoDAO = new CreditoDAO();
        this.ventaDAO   = new VentaDAO();
        this.celularDAO = new CelularDAO();
    }

    public static ReporteService getInstancia() {
        if (instancia == null) {
            instancia = new ReporteService();
        }
        return instancia;
    }

    public void generarReporteGlobal() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA, true))) {

            bw.write("========================================");
            bw.newLine();
            bw.write("  REPORTE GLOBAL DE GESTION TECNOSTORE  ");
            bw.newLine();
            bw.write("  Generado: " + LocalDateTime.now());
            bw.newLine();
            bw.write("========================================");
            bw.newLine();

            reporteTotalVentas(bw);
            reporteCelularesVendidosPorModelo(bw);
            reporteClientesConCreditosPendientes(bw);
            reporteStockActual(bw);

            bw.write("========================================");
            bw.newLine();
            bw.write("Reporte guardado en: " + RUTA);
            bw.newLine();
            bw.write("========================================");
            bw.newLine();

            System.out.println("\nReporte guardado en: " + RUTA);

        } catch (IOException e) {
            System.out.println("Error al generar reporte global: " + e.getMessage());
        }
    }

    private void reporteTotalVentas(BufferedWriter bw) throws IOException {
        try {
            List<Venta> ventas = ventaDAO.listarTodos();
            bw.write("\nTotal de ventas: $");
            if (ventas.isEmpty()) {
                bw.write("0");
                bw.newLine();
                return;
            }
            double totalVentas = ventas.stream()
                .collect(Collectors.summingDouble(Venta::getTotal));
            bw.write(String.format("%,.0f", totalVentas));
            bw.newLine();
            System.out.println("\nTotal de ventas: $" + String.format("%,.0f", totalVentas));
        } catch (Exception e) {
            System.out.println("Error en reporte 1: " + e.getMessage());
        }
    }

    private void reporteCelularesVendidosPorModelo(BufferedWriter bw) throws IOException {
        try {
            List<Venta> ventas = ventaDAO.listarTodos();
            bw.write("\nCelulares vendidos por modelo:");
            bw.newLine();
            System.out.println("\nCelulares vendidos por modelo:");
            if (ventas.isEmpty()) {
                bw.write("No hay ventas registradas.");
                bw.newLine();
                return;
            }
            Map<String, Integer> vendidosPorModelo = ventas.stream()
                .flatMap(v -> v.getDetalles().stream())
                .filter(d -> d.getCelular() != null &&
                             d.getCelular().getModelo() != null &&
                            !d.getCelular().getModelo().trim().isEmpty())
                .collect(Collectors.groupingBy(
                    d -> d.getCelular().getMarca() + " " + d.getCelular().getModelo(),
                    Collectors.summingInt(DetalleVenta::getCantidad)
                ));
            if (vendidosPorModelo.isEmpty()) {
                bw.write("No hay detalles de venta registrados.");
                bw.newLine();
                return;
            }
            vendidosPorModelo.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(e -> {
                    try {
                        String linea = "- " + e.getKey() + ": " + e.getValue() + " unidades";
                        bw.write(linea);
                        bw.newLine();
                        System.out.println(linea);
                    } catch (IOException ex) {
                        System.out.println("Error escribiendo reporte 2: " + ex.getMessage());
                    }
                });
        } catch (Exception e) {
            System.out.println("Error en reporte 2: " + e.getMessage());
        }
    }

    private void reporteClientesConCreditosPendientes(BufferedWriter bw) throws IOException {
        try {
            List<Credito> pendientes = creditoDAO.listarPendientes();
            bw.write("\nClientes con créditos pendientes:");
            bw.newLine();
            System.out.println("\nClientes con créditos pendientes:");
            if (pendientes.isEmpty()) {
                bw.write("No hay creditos pendientes.");
                bw.newLine();
                return;
            }
            Map<String, Double> saldoPorCliente = pendientes.stream()
                .filter(c -> c.getEstado().equals("PENDIENTE"))
                .collect(Collectors.groupingBy(
                    c -> c.getCliente().getNombre(),
                    Collectors.summingDouble(Credito::getSaldoPendiente)
                ));
            saldoPorCliente.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .forEach(e -> {
                    try {
                        String linea = "- " + e.getKey() + " → $" +
                                       String.format("%,.0f", e.getValue());
                        bw.write(linea);
                        bw.newLine();
                        System.out.println(linea);
                    } catch (IOException ex) {
                        System.out.println("Error escribiendo reporte 3: " + ex.getMessage());
                    }
                });
        } catch (Exception e) {
            System.out.println("Error en reporte 3: " + e.getMessage());
        }
    }

    private void reporteStockActual(BufferedWriter bw) throws IOException {
        try {
            List<Celular> celulares = celularDAO.listarTodos();
            bw.write("\nStock actual:");
            bw.newLine();
            System.out.println("\nStock actual:");
            if (celulares.isEmpty()) {
                bw.write("No hay celulares registrados.");
                bw.newLine();
                return;
            }
            celulares.stream()
                .sorted((a, b) -> Integer.compare(a.getStock(), b.getStock()))
                .map(c -> "- " + c.getMarca() + " " + c.getModelo() +
                          ": " + c.getStock() + " unidades" +
                          (c.getStock() < 5 ? " (ALERTA: bajo stock)" : ""))
                .forEach(linea -> {
                    try {
                        bw.write(linea);
                        bw.newLine();
                        System.out.println(linea);
                    } catch (IOException ex) {
                        System.out.println("Error escribiendo reporte 4: " + ex.getMessage());
                    }
                });
        } catch (Exception e) {
            System.out.println("Error en reporte 4: " + e.getMessage());
        }
    }
}
