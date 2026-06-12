/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tecnostore.view;

/**
 *
 * @author merch
 */

import tecnostore.controller.VentaController;
import tecnostore.model.Venta;
import tecnostore.utils.ArchivoUtils;
import tecnostore.utils.ReporteUtils;
import tecnostore.dao.CelularDAO;
import tecnostore.model.Celular;
import java.util.List;
import java.util.Scanner;
import tecnostore.test.ReporteService;

public class MenuVentas {

    private VentaController controller;
    private CelularDAO celularDAO;
    private Scanner scanner;

    public MenuVentas(Scanner scanner) {
        this.controller = new VentaController();
        this.celularDAO = new CelularDAO();
        this.scanner = scanner;
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n=== MENU VENTAS ===");
            System.out.println("1. Registrar venta");
            System.out.println("2. Listar ventas");
            System.out.println("3. Celulares con stock bajo");
            System.out.println("4. Top 3 celulares mas vendidos");
            System.out.println("5. Ventas totales por mes");
            System.out.println("6. Generar reporte en archivo");
            System.out.println("7. Generar reporte global");
            System.out.println("0. Volver");
            System.out.print("Opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1: registrarVenta(); break;
                case 2: listar(); break;
                case 3: stockBajo(); break;
                case 4: top3(); break;
                case 5: ventasPorMes(); break;
                case 6: generarReporte(); break;
                case 7: ReporteService.getInstancia().generarReporteGlobal(); break;
                case 0: System.out.println("Volviendo..."); break;
                default: System.out.println("Opcion no válida.");
            }
        } while (opcion != 0);
    }

    private void registrarVenta() {
        
        System.out.print("ID del cliente: ");
        int idCliente = scanner.nextInt();
        
        System.out.print("ID del celular: ");
        int idCelular = scanner.nextInt();
        
        System.out.print("Cantidad: ");
        int cantidad = scanner.nextInt();
        
        scanner.nextLine();
        controller.registrarVenta(idCliente, idCelular, cantidad);
    }

    private void listar() {
        List<Venta> lista = controller.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }
        lista.forEach(v -> System.out.println(v));
    }

    private void stockBajo() {
        List<Celular> celulares = celularDAO.listarTodos();
        ReporteUtils.mostrarStockBajo(celulares);
    }

    private void top3() {
        List<Venta> ventas = controller.listarTodos();
        ReporteUtils.mostrarTop3Vendidos(ventas);
    }

    private void ventasPorMes() {
        List<Venta> ventas = controller.listarTodos();
        ReporteUtils.mostrarVentasPorMes(ventas);
    }

    private void generarReporte() {
        List<Venta> ventas = controller.listarTodos();
        ArchivoUtils.generarReporte(ventas);
    }
}