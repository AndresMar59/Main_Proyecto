package com.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class conexiónBD {

    private static final String URL = "jdbc:sqlite:C:/Users/franc/OneDrive/Documentos/Proyecto_Oficial/Main_Proyecto/demo/src/main/resources/DataBse/Inventario.db";

    public static Connection conectar() throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        try (Statement st = conn.createStatement()) {
            st.execute("PRAGMA foreign_keys = ON;");
        }
        return conn;
    }

    public static void inicializarTablas() {
        String sqlProducto = """
            CREATE TABLE IF NOT EXISTS Producto (
                id          INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre      TEXT NOT NULL,
                descripcion TEXT,
                cantidad    INTEGER NOT NULL DEFAULT 0,
                precio      REAL NOT NULL CHECK(precio >= 0)
            );
            """;

        String sqlMovimiento = """
            CREATE TABLE IF NOT EXISTS Movimiento (
                id           INTEGER PRIMARY KEY AUTOINCREMENT,
                id_producto  INTEGER NOT NULL,
                tipo         TEXT NOT NULL CHECK(tipo IN ('entrada','salida')),
                cantidad     INTEGER NOT NULL CHECK(cantidad > 0),
                fecha        TEXT NOT NULL,
                FOREIGN KEY (id_producto) REFERENCES Producto(id)
                    ON DELETE CASCADE ON UPDATE CASCADE
            );
            """;

        String idx1 = "CREATE INDEX IF NOT EXISTS idx_movimiento_id_producto ON Movimiento(id_producto);";
        String idx2 = "CREATE INDEX IF NOT EXISTS idx_movimiento_fecha ON Movimiento(fecha);";

        try (Connection conn = conectar(); Statement st = conn.createStatement()) {
            st.execute(sqlProducto);
            st.execute(sqlMovimiento);
            st.execute(idx1);
            st.execute(idx2);
            System.out.println("Tablas creadas o verificadas con éxito");
        } catch (SQLException e) {
            System.out.println("Error al inicializar tablas: " + e.getMessage());
        }
    }
}
