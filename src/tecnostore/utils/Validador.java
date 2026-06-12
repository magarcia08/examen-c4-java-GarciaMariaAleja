/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tecnostore.utils;

/**
 *
 * @author merch
 */

public class Validador {

    public static boolean esCorreoValido(String correo) {
        return correo != null && correo.contains("@") && correo.contains(".");
    }

    public static boolean esPrecioValido(double precio) {
        return precio > 0;
    }

    public static boolean esStockValido(int stock) {
        return stock >= 0;
    }

    public static boolean esTextoValido(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    public static boolean esIdentificacionValida(String identificacion) {
        return identificacion != null && identificacion.matches("[0-9]{7,10}");
    }
}
