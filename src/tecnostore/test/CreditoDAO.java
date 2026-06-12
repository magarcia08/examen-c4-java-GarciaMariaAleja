/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tecnostore.test;

/**
 *
 * @author camper
 */

import tecnostore.db.ConexionDB;
import tecnostore.model.Cliente;
import tecnostore.model.Venta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CreditoDAO {

    private Connection connection;

    public CreditoDAO() {
        this.connection = ConexionDB.getInstancia().getConnection();
    }

    public void agregar(Credito credito) {
        String sql = "INSERT INTO creditos (id_cliente, id_venta, monto_total, " +
                     "saldo_pendiente, fecha_credito, estado) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, credito.getCliente().getId());
            ps.setInt(2, credito.getVenta().getId());
            ps.setDouble(3, credito.getMontoTotal());
            ps.setDouble(4, credito.getSaldoPendiente());
            ps.setDate(5, Date.valueOf(credito.getFechaCredito()));
            ps.setString(6, credito.getEstado());
            ps.executeUpdate();
            System.out.println("Credito registrado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al agregar credito: " + e.getMessage());
        }
    }

    public List<Credito> listarTodos() {
        List<Credito> lista = new ArrayList<>();
        String sql = "SELECT cr.id, cr.monto_total, cr.saldo_pendiente, " +
                     "       cr.fecha_credito, cr.estado, " +
                     "       cl.id AS id_cliente, cl.nombre, cl.identificacion, " +
                     "       cl.correo, cl.telefono, " +
                     "       v.id AS id_venta, v.total AS total_venta " +
                     "FROM creditos cr " +
                     "JOIN clientes cl ON cr.id_cliente = cl.id " +
                     "JOIN ventas   v  ON cr.id_venta   = v.id";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar creditos: " + e.getMessage());
        }
        return lista;
    }

    public List<Credito> listarPendientes() {
        List<Credito> lista = new ArrayList<>();
        String sql = "SELECT cr.id, cr.monto_total, cr.saldo_pendiente, " +
                     "       cr.fecha_credito, cr.estado, " +
                     "       cl.id AS id_cliente, cl.nombre, cl.identificacion, " +
                     "       cl.correo, cl.telefono, " +
                     "       v.id AS id_venta, v.total AS total_venta " +
                     "FROM creditos cr " +
                     "JOIN clientes cl ON cr.id_cliente = cl.id " +
                     "JOIN ventas   v  ON cr.id_venta   = v.id " +
                     "WHERE cr.estado = 'PENDIENTE'";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar creditos pendientes: " + e.getMessage());
        }
        return lista;
    }

    public List<String> saldoTotalPorCliente() {
        List<String> resultado = new ArrayList<>();
        String sql = "SELECT cl.nombre, cl.identificacion, " +
                     "       SUM(cr.saldo_pendiente) AS saldo_total " +
                     "FROM creditos cr " +
                     "JOIN clientes cl ON cr.id_cliente = cl.id " +
                     "WHERE cr.estado = 'PENDIENTE' " +
                     "GROUP BY cl.id, cl.nombre, cl.identificacion " +
                     "ORDER BY saldo_total DESC";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                String linea = rs.getString("nombre") +
                               " (" + rs.getString("identificacion") + ")" +
                               " -> Saldo adeudado: $" + rs.getDouble("saldo_total");
                resultado.add(linea);
            }
        } catch (SQLException e) {
            System.out.println("Error al calcular saldo por cliente: " + e.getMessage());
        }
        return resultado;
    }

    public Credito buscarPorId(int id) {
        String sql = "SELECT cr.id, cr.monto_total, cr.saldo_pendiente, " +
                     "       cr.fecha_credito, cr.estado, " +
                     "       cl.id AS id_cliente, cl.nombre, cl.identificacion, " +
                     "       cl.correo, cl.telefono, " +
                     "       v.id AS id_venta, v.total AS total_venta " +
                     "FROM creditos cr " +
                     "JOIN clientes cl ON cr.id_cliente = cl.id " +
                     "JOIN ventas   v  ON cr.id_venta   = v.id " +
                     "WHERE cr.id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            System.out.println("Error al buscar credito: " + e.getMessage());
        }
        return null;
    }

    public void actualizar(Credito credito) {
        String sql = "UPDATE creditos SET saldo_pendiente = ?, estado = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, credito.getSaldoPendiente());
            ps.setString(2, credito.getEstado());
            ps.setInt(3, credito.getId());
            ps.executeUpdate();
            System.out.println("Credito actualizado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar credito: " + e.getMessage());
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM creditos WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Credito eliminado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar credito: " + e.getMessage());
        }
    }

    private Credito mapear(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente(
            rs.getInt("id_cliente"),
            rs.getString("nombre"),
            rs.getString("identificacion"),
            rs.getString("correo"),
            rs.getString("telefono")
        );
        Venta venta = new Venta(rs.getInt("id_venta"), cliente);

        return new Credito(
            rs.getInt("id"),
            cliente,
            venta,
            rs.getDouble("monto_total"),
            rs.getDouble("saldo_pendiente"),
            rs.getDate("fecha_credito").toLocalDate(),
            rs.getString("estado")
        );
    }
}
