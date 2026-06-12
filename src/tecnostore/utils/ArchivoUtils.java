/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tecnostore.utils;

/**
 *
 * @author merch
 */

import tecnostore.model.Venta;
import tecnostore.model.DetalleVenta;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ArchivoUtils {

private static final String RUTA = "src/reportes/reporte_ventas.txt";

    public static void generarReporte(List<Venta> ventas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA, true))) {
            bw.write("========================================");
            bw.newLine();
            bw.write("        REPORTE DE VENTAS TECNOSTORE    ");
            bw.newLine();
            bw.write("========================================");
            bw.newLine();

            for (Venta venta : ventas) {
                bw.write("Venta ID : " + venta.getId());
                bw.newLine();
                bw.write("Cliente : " + venta.getCliente().getNombre());
                bw.newLine();
                bw.write("Fecha   : " + venta.getFecha());
                bw.newLine();
                bw.write("----------------------------------------");
                bw.newLine();

                for (DetalleVenta detalle : venta.getDetalles()) {
                    bw.write(" Celular : " + detalle.getCelular().getMarca()
                             + " " + detalle.getCelular().getModelo());
                    bw.newLine();
                    bw.write("Cantidad : " + detalle.getCantidad());
                    bw.newLine();
                    bw.write("Subtotal : $" + detalle.getSubtotal());
                    bw.newLine();
                }

                bw.write("Total (IVA): $" + venta.getTotal());
                bw.newLine();
                bw.write("========================================");
                bw.newLine();
            }

            System.out.println("Reporte generadoen: " + RUTA);
        } catch (IOException e) {
            System.out.println("Error al generar: " + e.getMessage());
        }
    }
}
