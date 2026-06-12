/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tecnostore.view;

/**
 *
 * @author merch
 */

import tecnostore.controller.CelularController;
import tecnostore.model.CategoriaGama;
import tecnostore.model.Celular;
import tecnostore.utils.CelularFactory;
import java.util.List;
import java.util.Scanner;

public class MenuCelulares {

    private CelularController controller;
    private Scanner scanner;

    public MenuCelulares(Scanner scanner) {
        this.controller = new CelularController();
        this.scanner = scanner;
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n=== MENU CELULARES ===");
            System.out.println("1. Agregar celular");
            System.out.println("2. Listar celulares");
            System.out.println("3. Buscar celular por ID");
            System.out.println("4. Actualizar celular");
            System.out.println("5. Eliminar celular");
            System.out.println("0. Volver");
            System.out.print("Opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1: agregar(); break;
                case 2: listar(); break;
                case 3: buscar(); break;
                case 4: actualizar(); break;
                case 5: eliminar(); break;
                case 0: System.out.println("Volviendo..."); break;
                default: System.out.println("Opcion no válida.");
            }
        } while (opcion != 0);
    }

    private void agregar() {
        System.out.print("Marca: ");
        String marca = scanner.nextLine();
        
        System.out.print("Modelo: ");
        String modelo = scanner.nextLine();
        
        System.out.print("Precio: ");
        double precio = scanner.nextDouble();
        
        System.out.print("Stock: ");
        int stock = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Tipo (ALTA, MEDIA, BAJA, IOS): ");
        String tipo = scanner.nextLine();

        try {
            Celular celular = CelularFactory.crearCelular(tipo, 0, marca, modelo, precio, stock);
            controller.agregar(celular.getMarca(), celular.getModelo(), celular.getPrecio(),
                    celular.getStock(), celular.getSistemaOperativo(), celular.getGama());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listar() {
        List<Celular> lista = controller.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("No hay celulares registrados.");
            return;
        }
        lista.forEach(c -> System.out.println(c));
    }

    private void buscar() {
        System.out.print("ID del celular: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Celular celular = controller.buscarPorId(id);
        if (celular != null) {
            System.out.println(celular);
        }
    }

    private void actualizar() {
        System.out.print("ID del celular a actualizar: ");
        int id = scanner.nextInt();
        System.out.print("Nuevo precio: ");
        double precio = scanner.nextDouble();
        System.out.print("Nuevo stock: ");
        int stock = scanner.nextInt();
        scanner.nextLine();
        controller.actualizar(id, precio, stock);
    }

    private void eliminar() {
        System.out.print("ID del celular a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        controller.eliminar(id);
    }
}
