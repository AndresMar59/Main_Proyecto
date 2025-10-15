package com.Dao;

import com.BD.Loan;
import com.BD.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class LoanDAO {

    /**
     * Inserta un movimiento (Loan) en la tabla movimientos.
     * @param loan el objeto Loan
     * @param tipo "entrada" o "salida"
     * @param cantidad cantidad de producto movida
     */
    public void registrarMovimiento(Loan loan, String tipo, int cantidad) {
        String sql = "INSERT INTO movimientos (producto_id, tipo, cantidad, fecha) VALUES (?, ?, ?, ?)";

        try (Connection conn = conexiónBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            Producto producto = loan.getProducto();

            stmt.setString(1, producto.getId());
            stmt.setString(2, tipo);
            stmt.setInt(3, cantidad);
            stmt.setString(4, LocalDate.now().toString());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
