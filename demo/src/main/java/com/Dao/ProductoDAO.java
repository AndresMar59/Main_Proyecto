package com.Dao;

import com.BD.Producto;
import java.sql.*;
import java.util.*;


public class ProductoDAO {

    public void insertar(Producto p) {
        String sql = "INSERT INTO productos (id, name, precio, stock, habilitado) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = conexiónBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getId());
            stmt.setString(2, p.getName());
            stmt.setDouble(3, p.getPrecio());
            stmt.setInt(4, p.getStock());
            stmt.setBoolean(5, p.isHabilitado());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al insertar producto: " + e.getMessage());
        }
    }

    public List<Producto> obtenerTodos() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM productos";

        try (Connection conn = conexiónBD.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Producto p = new Producto(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                );
                p.setHabilitado(rs.getBoolean("habilitado"));
                lista.add(p); 
            } 
        } catch (SQLException e) {
            System.err.println("Error al obtener productos: " + e.getMessage());
    }

        return lista;
    }

    public Producto obtenerPorId(String id) {
        String sql = "SELECT * FROM productos WHERE id = ?";
        Producto p = null;

        try (Connection conn = conexiónBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                p = new Producto(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
                );
                p.setHabilitado(rs.getBoolean("habilitado"));
            }

        } catch (SQLException e) {
            System.err.println(" Error al obtener producto por ID: " + e.getMessage());
        }

        return p;
    }

    public void actualizar(Producto p) {
        String sql = "UPDATE productos SET precio=? WHERE id=?";

        try (Connection conn = conexiónBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(2, p.getPrecio());
            stmt.setString(1, p.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al actualizar producto: " + e.getMessage());
        }
    }

    public void eliminar(String id) {
        String sql = "DELETE FROM productos WHERE id = ?";

        try (Connection conn = conexiónBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println(" Error al eliminar producto: " + e.getMessage());
        }
    }
}
