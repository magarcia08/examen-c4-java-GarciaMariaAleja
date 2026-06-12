/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tecnostore.utils;

/**
 *
 * @author merch
 */

import tecnostore.model.CategoriaGama;
import tecnostore.model.Celular;

public class CelularFactory {

    public static Celular crearCelular(String tipo, int id, String marca,
                                       String modelo, double precio, int stock) {
        
        switch (tipo.toUpperCase()) {
            case "ALTA":
                return new Celular(id, marca, modelo, precio, stock, 
                        "Android", 
                        CategoriaGama.ALTA);
            case "MEDIA":
                return new Celular(id, marca, modelo, precio, stock, 
                        "Android", 
                        CategoriaGama.MEDIA);
            case "BAJA":
                return new Celular(id, marca, modelo, precio, stock, 
                        "Android", CategoriaGama.BAJA);
            case "IOS":
                return new Celular(id, marca, modelo, precio, stock, 
                        "iOS", CategoriaGama.ALTA);
            default:
                throw new IllegalArgumentException("Tipo de celular no reconocido: " + tipo);
        }
    }
}