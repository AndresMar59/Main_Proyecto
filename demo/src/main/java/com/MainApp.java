package com;

import com.Dao.conexiónBD;

public class MainApp {
    public static void main(String[] args) {
        System.out.println("Iniciando prueba de base de datos...");
        conexiónBD.inicializarTablas();
        System.out.println("Base de datos lista.");
    }
}
