/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tecnostore.dao;

/**
 *
 * @author merch
 */

import tecnostore.db.ConexionDB;
import tecnostore.model.CategoriaGama;
import tecnostore.model.Celular;
import tecnostore.model.Cliente;
import tecnostore.model.DetalleVenta;
import tecnostore.model.Venta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO {

    private Connection connection;

    public VentaDAO() {
        this.connection = ConexionDB.getInstancia().getConnection();
    }

    // INSERT VENTA
    public int agregar(Venta venta) {
        String sql = "INSERT INTO ventas (id_cliente, fecha, total) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, venta.getCliente().getId());
            ps.setTimestamp(2, Timestamp.valueOf(venta.getFecha()));
            ps.setDouble(3, venta.getTotal());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error al agregar venta: " + e.getMessage());
        }
        return -1;
    }

    // INSERT DETALLE
    public void agregarDetalle(DetalleVenta detalle, int idVenta) {
        String sql = "INSERT INTO detalle_ventas (id_venta, id_celular, cantidad, subtotal) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idVenta);
            ps.setInt(2, detalle.getCelular().getId());
            ps.setInt(3, detalle.getCantidad());
            ps.setDouble(4, detalle.getSubtotal());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al agregar detalle: " + e.getMessage());
        }
    }

    // SELECT ALL
    public List<Venta> listarTodos() {
        List<Venta> lista = new ArrayList<>();
        String sql = "SELECT v.*, c.nombre, c.identificacion, c.correo, c.telefono " +
                     "FROM ventas v " +
                     "JOIN clientes c ON v.id_cliente = c.id";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Venta venta = mapear(rs);
                List<DetalleVenta> detalles = listarDetallesPorVenta(venta.getId());
                detalles.forEach(d -> venta.agregarDetalle(d));
                lista.add(venta);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar ventas: " + e.getMessage());
        }
        return lista;
    }

    // SELECT DETALLES POR VENTA
    public List<DetalleVenta> listarDetallesPorVenta(int idVenta) {
        List<DetalleVenta> detalles = new ArrayList<>();
        String sql = "SELECT dv.*, c.marca, c.modelo, c.precio, c.stock, " +
                     "c.sistema_operativo, c.gama " +
                     "FROM detalle_ventas dv " +
                     "JOIN celulares c ON dv.id_celular = c.id " +
                     "WHERE dv.id_venta = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idVenta);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Celular celular = new Celular(
                    rs.getInt("id_celular"),
                    rs.getString("marca"),
                    rs.getString("modelo"),
                    rs.getDouble("precio"),
                    rs.getInt("stock"),
                    rs.getString("sistema_operativo"),
                    CategoriaGama.valueOf(rs.getString("gama"))
                );
                DetalleVenta detalle = new DetalleVenta(
                    rs.getInt("id"),
                    idVenta,
                    celular,
                    rs.getInt("cantidad")
                );
                detalles.add(detalle);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar detalles: " + e.getMessage());
        }
        return detalles;
    }

    // MAPEAR
    private Venta mapear(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente(
            rs.getInt("id_cliente"),
            rs.getString("nombre"),
            rs.getString("identificacion"),
            rs.getString("correo"),
            rs.getString("telefono")
        );
        return new Venta(rs.getInt("id"), cliente);
    }
}