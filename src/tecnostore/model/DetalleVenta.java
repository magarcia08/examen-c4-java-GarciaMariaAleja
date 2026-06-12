/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tecnostore.model;

/**
 *
 * @author merch
 */

public class DetalleVenta {
    private int id;
    private int idVenta;
    private Celular celular;
    private int cantidad;
    private double subtotal;

    public DetalleVenta(int id, int idVenta, Celular celular, int cantidad) {
        this.id = id;
        this.idVenta = idVenta;
        this.celular = celular;
        this.cantidad = cantidad;
        this.subtotal = celular.getPrecio() * cantidad;
    }

    public int getId() {
        return id;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public Celular getCelular() {
        return celular;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        this.subtotal = celular.getPrecio() * cantidad;

    }

    

    @Override
    public String toString() {
        return "=== DETALLE VENTA ===" +
               "\nCelular     : " + celular.getMarca() + " " + celular.getModelo() +
               "\nCantidad    : " + cantidad +
               "\nSubtotal    : $" + subtotal;
    }
}
