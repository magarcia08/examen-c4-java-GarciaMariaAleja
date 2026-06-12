/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tecnostore.controller;

/**
 *
 * @author merch
 */
import tecnostore.dao.ClienteDAO;
import tecnostore.model.Cliente;
import java.util.List;

public class ClienteController {

    private ClienteDAO clienteDAO;

    public ClienteController() {
        this.clienteDAO = new ClienteDAO();
    }

    public void agregar(String nombre, String identificacion, String correo, String telefono) {
        if (nombre == null || nombre.trim().isEmpty()) {
            System.out.println("Error: el nombre no puede ser vacio");
            return;
        }
        if (clienteDAO.buscarPorIdentificacion(identificacion) != null) {
            System.out.println("Error: existe esta identificacion ");
            return;
        }
        if (!correo.contains("@") || !correo.contains(".")) {
            System.out.println("Error: el correo invalido ");
            return;
        }
        Cliente cliente = new Cliente(0, nombre, identificacion, correo, telefono);
        clienteDAO.agregar(cliente);
    }

    public List<Cliente> listarTodos() {
        return clienteDAO.listarTodos();
    }

    public Cliente buscarPorId(int id) {
        Cliente cliente = clienteDAO.buscarPorId(id);
        if (cliente == null) {
            System.out.println("Error: cliente no encontrado.");
        }
        return cliente;
    }

    public Cliente buscarPorIdentificacion(String identificacion) {
        Cliente cliente = clienteDAO.buscarPorIdentificacion(identificacion);
        if (cliente == null) {
            System.out.println("Error: cliente no encontrado.");
        }
        return cliente;
    }

    public void actualizar(int id, String nombre, String correo, String telefono) {
        Cliente cliente = clienteDAO.buscarPorId(id);
        if (cliente == null) {
            System.out.println("Error: cliente no encontrado.");
            return;
        }
        if (!correo.contains("@") || !correo.contains(".")) {
            System.out.println("Error: el correo no es válido.");
            return;
        }
        cliente.setNombre(nombre);
        cliente.setCorreo(correo);
        cliente.setTelefono(telefono);
        clienteDAO.actualizar(cliente);
    }

    public void eliminar(int id) {
        Cliente cliente = clienteDAO.buscarPorId(id);
        if (cliente == null) {
            System.out.println("Error: cliente no encontrado.");
            return;
        }
        clienteDAO.eliminar(id);
    }
}
