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
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CelularDAO {

    private Connection connection;

    public CelularDAO() {
        this.connection = ConexionDB.getInstancia().getConnection();
    }

    // INSERT
    public void agregar(Celular celular) {
        String sql = "INSERT INTO celulares (marca, modelo, sistema_operativo, gama, precio, stock) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, celular.getMarca());
            ps.setString(2, celular.getModelo());
            ps.setString(3, celular.getSistemaOperativo());
            ps.setString(4, celular.getGama().name());
            ps.setDouble(5, celular.getPrecio());
            ps.setInt(6, celular.getStock());
            ps.executeUpdate();
            System.out.println("Celular agregado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al agregar celular: " + e.getMessage());
        }
    }

    // SELECT ALL
    public List<Celular> listarTodos() {
        List<Celular> lista = new ArrayList<>();
        String sql = "SELECT * FROM celulares";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar celulares: " + e.getMessage());
        }
        return lista;
    }

    // SELECT BY ID
    public Celular buscarPorId(int id) {
        String sql = "SELECT * FROM celulares WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapear(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar celular: " + e.getMessage());
        }
        return null;
    }

    // UPDATE
    public void actualizar(Celular celular) {
        String sql = "UPDATE celulares SET precio = ?, stock = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, celular.getPrecio());
            ps.setInt(2, celular.getStock());
            ps.setInt(3, celular.getId());
            ps.executeUpdate();
            System.out.println("Celular actualizado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar celular: " + e.getMessage());
        }
    }

    // DELETE
    public void eliminar(int id) {
        String sql = "DELETE FROM celulares WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Celular eliminado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar celular: " + e.getMessage());
        }
    }

    // MAPEAR ResultSet a Celular
    private Celular mapear(ResultSet rs) throws SQLException {
        return new Celular(
            rs.getInt("id"),
            rs.getString("marca"),
            rs.getString("modelo"),
            rs.getDouble("precio"),
            rs.getInt("stock"),
            rs.getString("sistema_operativo"),
            CategoriaGama.valueOf(rs.getString("gama"))
        );
    }
}