/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tecnostore.utils;

/**
 *
 * @author merch
 */

import tecnostore.model.Celular;
import tecnostore.model.Venta;
import tecnostore.model.DetalleVenta;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReporteUtils {

    // Celulares con stock bajo (menos de 5 unidades)
    public static void mostrarStockBajo(List<Celular> celulares) {
        System.out.println("=== CELULARES CON STOCK BAJO ===");
        celulares.stream()
                .filter(c -> c.getStock() < 5)
                .forEach(c -> System.out.println(c));
    }

    // Top 3 celulares mas vendidos
    public static void mostrarTop3Vendidos(List<Venta> ventas) {
        System.out.println("=== TOP 3 CELULARES MAS VENDIDOS ===");
        ventas.stream()
                .flatMap(v -> v.getDetalles().stream())
                .collect(Collectors.groupingBy(
                        d -> d.getCelular().getModelo(),
                        Collectors.summingInt(DetalleVenta::getCantidad)
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(3)
                .forEach(e -> System.out.println(e.getKey() + " - Vendidos: " + e.getValue()));
    }

    // Ventas totales por mes
    public static void mostrarVentasPorMes(List<Venta> ventas) {
        System.out.println("=== VENTAS TOTALES POR MES ===");
        ventas.stream()
                .collect(Collectors.groupingBy(
                        v -> v.getFecha().getMonth(),
                        Collectors.summingDouble(Venta::getTotal)
                ))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> System.out.println(e.getKey() + " : $" + e.getValue()));
    }
}
