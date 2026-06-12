/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tecnostore.view;

/**
 *
 * @author merch
 */

import tecnostore.controller.ClienteController;
import tecnostore.model.Cliente;
import java.util.List;
import java.util.Scanner;

public class MenuClientes {

    private ClienteController controller;
    private Scanner scanner;

    public MenuClientes(Scanner scanner) {
        this.controller = new ClienteController();
        this.scanner = scanner;
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n=== MENU CLIENTES ===");
            System.out.println("1. Agregar cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Buscar cliente por ID");
            System.out.println("4. Buscar cliente por identificacion");
            System.out.println("5. Actualizar cliente");
            System.out.println("6. Eliminar cliente");
            System.out.println("0. Volver");
            System.out.print("Opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1: agregar(); break;
                case 2: listar(); break;
                case 3: buscarPorId(); break;
                case 4: buscarPorIdentificacion(); break;
                case 5: actualizar(); break;
                case 6: eliminar(); break;
                case 0: System.out.println("Volviendo..."); break;
                default: System.out.println("Opcion no válida.");
            }
        } while (opcion != 0);
    }

    private void agregar() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Identificacion: ");
        String identificacion = scanner.nextLine();
        
        System.out.print("Correo: ");
        String correo = scanner.nextLine();
        
        System.out.print("Telefono: ");
        String telefono = scanner.nextLine();
        controller.agregar(nombre, identificacion, correo, telefono);
    }

    private void listar() {
        List<Cliente> lista = controller.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        lista.forEach(c -> System.out.println(c));
    }

    private void buscarPorId() {
        System.out.print("ID del cliente: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Cliente cliente = controller.buscarPorId(id);
        if (cliente != null) {
            System.out.println(cliente);
        }
    }

    private void buscarPorIdentificacion() {
        System.out.print("Identificacion del cliente: ");
        String identificacion = scanner.nextLine();
        Cliente cliente = controller.buscarPorIdentificacion(identificacion);
        if (cliente != null) {
            System.out.println(cliente);
        }
    }

    private void actualizar() {
        System.out.print("ID del cliente a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nuevo nombre: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Nuevo correo: ");
        String correo = scanner.nextLine();
        
        System.out.print("Nuevo telefono: ");
        String telefono = scanner.nextLine();
        controller.actualizar(id, nombre, correo, telefono);
    }

    private void eliminar() {
        System.out.print("ID del cliente a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        controller.eliminar(id);
    }
}