/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tecnostore.test;

/**
 *
 * @author camper
 */

import java.time.LocalDate;
import tecnostore.model.Cliente;
import tecnostore.model.Venta;

public class Credito {

    private int id;
    private Cliente cliente;
    private Venta venta;
    private double montoTotal;
    private double saldoPendiente;
    private LocalDate fechaCredito;
    private String estado;

    public Credito(int id, Cliente cliente, Venta venta,
                   double montoTotal, double saldoPendiente,
                   LocalDate fechaCredito, String estado) {
        this.id = id;
        this.cliente = cliente;
        this.venta = venta;
        this.montoTotal = montoTotal;
        this.saldoPendiente = saldoPendiente;
        this.fechaCredito = fechaCredito;
        this.estado = estado;
    }

    public int getId()                  { return id; }
    public Cliente getCliente()         { return cliente; }
    public Venta getVenta()             { return venta; }
    public double getMontoTotal()       { return montoTotal; }
    public double getSaldoPendiente()   { return saldoPendiente; }
    public LocalDate getFechaCredito()  { return fechaCredito; }
    public String getEstado()           { return estado; }

    public void setSaldoPendiente(double saldoPendiente) {
        this.saldoPendiente = saldoPendiente;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "=== CREDITO ===" +
               "\nID              : " + id +
               "\nCliente         : " + cliente.getNombre() +
               "\nIdentificacion  : " + cliente.getIdentificacion() +
               "\nVenta ID        : " + venta.getId() +
               "\nMonto Total     : $" + montoTotal +
               "\nSaldo Pendiente : $" + saldoPendiente +
               "\nFecha Credito   : " + fechaCredito +
               "\nEstado          : " + estado;
    }
}
