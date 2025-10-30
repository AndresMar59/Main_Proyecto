package conexionBD; 

//
import java.sql.Connection;  //representa la conexión a la base de datos //enviar consultas y recibir resultados
import java.sql.DriverManager; //gestiona un conjunto de controladores de bases de datos JDBC drivers y establece conexiones con bases de datos
import java.sql.SQLException; //maneja errores relacionados con la base de datos


public class conexionBD {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/inventario_db";
    private static final String USUARIO = "root";
    private static final String CLAVE = "12345"; // o tu contraseña real


    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USUARIO, CLAVE);
           //System.out.println("Conexión exitosa a la base de datos MySQL");
        } catch (SQLException e) {
            System.out.println(" Error de conexión: " + e.getMessage());
        }
        return conn;
    }

}