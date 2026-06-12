/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tecnostore.model;

/**
 *
 * @author merch
 */

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Venta {
    private int id;
    private Cliente cliente;
    private LocalDateTime fecha;
    private List<DetalleVenta> detalles;
    private double total;

    public Venta(int id, Cliente cliente) {
        this.id = id;
        this.cliente = cliente;
        this.fecha = LocalDateTime.now();
        this.detalles = new ArrayList<>();
        this.total = 0;
    }

    public void agregarDetalle(DetalleVenta detalle) {
        detalles.add(detalle);
        calcularTotal();
    }

    private void calcularTotal() {
        double subtotal = 0;
        for (DetalleVenta d : detalles) {
            subtotal += d.getSubtotal();
        }
        this.total = subtotal * 1.19;
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public double getTotal() {
        return total;
    }

 

    @Override
    public String toString() {
        return "=== VENTA ===" +
               "\nID          : " + id +
               "\nCliente     : " + cliente.getNombre() +
               "\nFecha       : " + fecha +
               "\nTotal       : $" + total;
    }
}