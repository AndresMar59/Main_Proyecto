package com.Dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexiónBD {
    private static final String URL = "jdbc:sqlite:src/main/resources/DataBse/Inventario.db";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL);
    }
} 






