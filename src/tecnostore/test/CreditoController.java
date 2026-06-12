/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tecnostore.test;

/**
 *
 * @author camper
 */

import tecnostore.dao.ClienteDAO;
import tecnostore.dao.VentaDAO;
import tecnostore.model.Cliente;
import tecnostore.model.Venta;
import java.time.LocalDate;
import java.util.List;

public class CreditoController {

    private CreditoDAO creditoDAO;
    private ClienteDAO clienteDAO;
    private VentaDAO ventaDAO;

    public CreditoController() {
        this.creditoDAO = new CreditoDAO();
        this.clienteDAO = new ClienteDAO();
        this.ventaDAO   = new VentaDAO();
    }

    public void registrar(int idCliente, int idVenta, double montoTotal) {
        Cliente cliente = clienteDAO.buscarPorId(idCliente);
        if (cliente == null) {
            System.out.println("Error: cliente no encontrado.");
            return;
        }

        Venta venta = ventaDAO.listarTodos()
                              .stream()
                              .filter(v -> v.getId() == idVenta)
                              .findFirst()
                              .orElse(null);
        if (venta == null) {
            System.out.println("Error: venta no encontrada.");
            return;
        }

        if (montoTotal <= 0) {
            System.out.println("Error: el monto del credito debe ser mayor a 0.");
            return;
        }
        if (montoTotal > venta.getTotal()) {
            System.out.println("Error: el monto del credito ($" + montoTotal +
                               ") supera el total de la venta ($" + venta.getTotal() + ").");
            return;
        }

        Credito credito = new Credito(
            0, cliente, venta, montoTotal, montoTotal, LocalDate.now(), "PENDIENTE"
        );
        creditoDAO.agregar(credito);
    }

    public List<Credito> listarTodos() {
        return creditoDAO.listarTodos();
    }

    public List<Credito> listarPendientes() {
        List<Credito> pendientes = creditoDAO.listarPendientes();
        if (pendientes.isEmpty()) {
            System.out.println("No hay creditos pendientes registrados.");
        }
        return pendientes;
    }

    public Credito buscarPorId(int id) {
        Credito credito = creditoDAO.buscarPorId(id);
        if (credito == null) {
            System.out.println("Error: credito no encontrado.");
        }
        return credito;
    }

    public void abonar(int idCredito, double montoAbono) {
        Credito credito = creditoDAO.buscarPorId(idCredito);
        if (credito == null) {
            System.out.println("Error: credito no encontrado.");
            return;
        }
        if (credito.getEstado().equals("PAGADO")) {
            System.out.println("Error: este credito ya fue pagado completamente.");
            return;
        }
        if (montoAbono <= 0) {
            System.out.println("Error: el abono debe ser mayor a 0.");
            return;
        }
        if (montoAbono > credito.getSaldoPendiente()) {
            System.out.println("Error: el abono ($" + montoAbono +
                               ") supera el saldo pendiente ($" +
                               credito.getSaldoPendiente() + ").");
            return;
        }

        double nuevoSaldo = credito.getSaldoPendiente() - montoAbono;
        credito.setSaldoPendiente(nuevoSaldo);

        if (nuevoSaldo == 0) {
            credito.setEstado("PAGADO");
            System.out.println("Credito saldado completamente.");
        } else {
            System.out.println("Abono registrado. Saldo restante: $" + nuevoSaldo);
        }
        creditoDAO.actualizar(credito);
    }

    public void eliminar(int id) {
        Credito credito = creditoDAO.buscarPorId(id);
        if (credito == null) {
            System.out.println("Error: credito no encontrado.");
            return;
        }
        creditoDAO.eliminar(id);
    }

    public List<String> saldoTotalPorCliente() {
        return creditoDAO.saldoTotalPorCliente();
    }
}
