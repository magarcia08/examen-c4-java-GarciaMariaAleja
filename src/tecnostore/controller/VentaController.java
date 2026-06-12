/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tecnostore.controller;

/**
 *
 * @author merch
 */

import tecnostore.dao.CelularDAO;
import tecnostore.dao.ClienteDAO;
import tecnostore.dao.VentaDAO;
import tecnostore.model.Celular;
import tecnostore.model.Cliente;
import tecnostore.model.DetalleVenta;
import tecnostore.model.Venta;
import java.util.List;

public class VentaController {

    private VentaDAO ventaDAO;
    private ClienteDAO clienteDAO;
    private CelularDAO celularDAO;

    public VentaController() {
        this.ventaDAO = new VentaDAO();
        this.clienteDAO = new ClienteDAO();
        this.celularDAO = new CelularDAO();
    }

    public void registrarVenta(int idCliente, int idCelular, int cantidad) {
        // Verificar que el cliente existe
        Cliente cliente = clienteDAO.buscarPorId(idCliente);
        if (cliente == null) {
            System.out.println("Error: cliente no encontrado.");
            return;
        }

        // Verificar que el celular existe
        Celular celular = celularDAO.buscarPorId(idCelular);
        if (celular == null) {
            System.out.println("Error: celular no encontrado.");
            return;
        }

        // Verificar stock suficiente
        if (celular.getStock() < cantidad) {
            System.out.println("Error: stock insuficiente. Stock disponible: " + celular.getStock());
            return;
        }

        // Crear la venta
        Venta venta = new Venta(0, cliente);

        // Crear el detalle
        DetalleVenta detalle = new DetalleVenta(0, 0, celular, cantidad);
        venta.agregarDetalle(detalle);

        // Guardar venta en BD y obtener id generado
        int idVenta = ventaDAO.agregar(venta);
        if (idVenta == -1) {
            System.out.println("Error: no se pudo registrar la venta.");
            return;
        }

        // Guardar detalle en BD
        ventaDAO.agregarDetalle(detalle, idVenta);

        // Actualizar stock del celular
        celular.setStock(celular.getStock() - cantidad);
        celularDAO.actualizar(celular);

        System.out.println("Venta registrada correctamente.");
        System.out.println("Total a pagar (con IVA 19%): $" + venta.getTotal());
    }

    public List<Venta> listarTodos() {
        return ventaDAO.listarTodos();
    }
}
