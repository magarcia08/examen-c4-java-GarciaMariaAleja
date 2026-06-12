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
import tecnostore.model.CategoriaGama;
import tecnostore.model.Celular;
import java.util.List;

public class CelularController {

    private CelularDAO celularDAO;

    public CelularController() {
        this.celularDAO = new CelularDAO();
    }

    public void agregar(String marca, String modelo, double precio,
                        int stock, String sistemaOperativo, CategoriaGama gama) {
        if (precio <= 0) {
            System.out.println("Error: el precio debe ser mayor a 0.");
            return;
        }
        if (stock < 0) {
            System.out.println("Error: el stock no puede ser negativo.");
            return;
        }
        Celular celular = new Celular(0, marca, modelo, precio, stock, sistemaOperativo, gama);
        celularDAO.agregar(celular);
    }

    public List<Celular> listarTodos() {
        return celularDAO.listarTodos();
    }

    public Celular buscarPorId(int id) {
        Celular celular = celularDAO.buscarPorId(id);
        if (celular == null) {
            System.out.println("Error: celular no encontrado.");
        }
        return celular;
    }

    public void actualizar(int id, double nuevoPrecio, int nuevoStock) {
        Celular celular = celularDAO.buscarPorId(id);
        if (celular == null) {
            System.out.println("Error: celular no encontrado.");
            return;
        }
        if (nuevoPrecio <= 0) {
            System.out.println("Error: el precio debe ser mayor a 0.");
            return;
        }
        if (nuevoStock < 0) {
            System.out.println("Error: el stock no puede ser negativo.");
            return;
        }
        celular.setPrecio(nuevoPrecio);
        celular.setStock(nuevoStock);
        celularDAO.actualizar(celular);
    }

    public void eliminar(int id) {
        Celular celular = celularDAO.buscarPorId(id);
        if (celular == null) {
            System.out.println("Error: celular no encontrado.");
            return;
        }
        celularDAO.eliminar(id);
    }
}