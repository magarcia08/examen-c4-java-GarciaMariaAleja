/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tecnostore.model;

/**
 *
 * @author merch
 */

public class Cliente {
    private int id;
    private String nombre;
    private String identificacion;
    private String correo;
    private String telefono;

    public Cliente(int id, String nombre, String identificacion, String correo, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.correo = correo;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    
    
   @Override
public String toString() {
    return "--Cliente--" +
           "\nID          : " + id +
           "\nNombre      : " + nombre +
           "\nIdentidad   : " + identificacion +
           "\nCorreo      : " + correo +
           "\nTeléfono    : " + telefono;
}
}