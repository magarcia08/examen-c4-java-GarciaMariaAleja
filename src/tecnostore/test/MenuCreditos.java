/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tecnostore.test;

/**
 *
 * @author camper
 */

import java.util.List;
import java.util.Scanner;

public class MenuCreditos {

    private CreditoController controller;
    private Scanner scanner;

    public MenuCreditos(Scanner scanner) {
        this.controller = new CreditoController();
        this.scanner = scanner;
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n========================================");
            System.out.println("         GESTION DE CREDITOS            ");
            System.out.println("========================================");
            System.out.println("1. Registrar credito");
            System.out.println("2. Listar todos los creditos");
            System.out.println("3. Listar creditos pendientes");
            System.out.println("4. Buscar credito por ID");
            System.out.println("5. Registrar abono a credito");
            System.out.println("6. Ver saldo total adeudado por cliente");
            System.out.println("7. Eliminar credito");
            System.out.println("0. Volver");
            System.out.println("========================================");
            System.out.print("Opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1: registrar();        break;
                case 2: listarTodos();      break;
                case 3: listarPendientes(); break;
                case 4: buscarPorId();      break;
                case 5: abonar();           break;
                case 6: saldoPorCliente();  break;
                case 7: eliminar();         break;
                case 0: System.out.println("Volviendo..."); break;
                default: System.out.println("Opcion no valida.");
            }
        } while (opcion != 0);
    }

    private void registrar() {
        System.out.println("\n--- REGISTRAR CREDITO ---");
        System.out.print("ID del cliente: ");
        int idCliente = scanner.nextInt();
        scanner.nextLine();
        System.out.print("ID de la venta asociada: ");
        int idVenta = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Monto del credito: $");
        double monto = scanner.nextDouble();
        scanner.nextLine();
        controller.registrar(idCliente, idVenta, monto);
    }

    private void listarTodos() {
        System.out.println("\n--- TODOS LOS CREDITOS ---");
        List<Credito> lista = controller.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("No hay creditos registrados.");
            return;
        }
        lista.forEach(c -> System.out.println(c));
    }

    private void listarPendientes() {
        System.out.println("\n--- CREDITOS PENDIENTES ---");
        List<Credito> pendientes = controller.listarPendientes();
        if (pendientes.isEmpty()) return;
        pendientes.forEach(c -> System.out.println(c));
    }

    private void buscarPorId() {
        System.out.println("\n--- BUSCAR CREDITO ---");
        System.out.print("ID del credito: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Credito credito = controller.buscarPorId(id);
        if (credito != null) System.out.println(credito);
    }

    private void abonar() {
        System.out.println("\n--- REGISTRAR ABONO ---");
        System.out.print("ID del credito a abonar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Credito credito = controller.buscarPorId(id);
        if (credito == null) return;
        System.out.println(credito);
        System.out.print("Monto del abono: $");
        double abono = scanner.nextDouble();
        scanner.nextLine();
        controller.abonar(id, abono);
    }

    private void saldoPorCliente() {
        System.out.println("\n--- SALDO TOTAL ADEUDADO POR CLIENTE ---");
        List<String> resultado = controller.saldoTotalPorCliente();
        if (resultado.isEmpty()) {
            System.out.println("No hay saldos pendientes registrados.");
            return;
        }
        resultado.forEach(linea -> System.out.println(linea));
    }

    private void eliminar() {
        System.out.println("\n--- ELIMINAR CREDITO ---");
        System.out.print("ID del credito a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        controller.eliminar(id);
    }
}
