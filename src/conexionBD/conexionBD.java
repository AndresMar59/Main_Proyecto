package conexionBD;

import java.sql.Connection; //Ayuda a establecer la conexión con la base de datos, permite hacer consultas, ejecutar sentencias SQL, etc. como statement, preparedStatement, resultSet, etc.
import java.sql.DriverManager; //permite gestionar las conexiones a bases de datos mediante JDBC. Gestiona los controladores JDBC y establece conexiones con la base de datos.
import java.sql.SQLException; //maneja los errores y excepciones que pueden ocurrir al interactuar con bases de datos utilizando JDBC.


//Explicacion:
/*
 * Se establecen variables estaticas para la URL de la base de datos, el usuario y la clave.
 * El metodo getConnection() intenta establecer una conexion con la base de datos utilizando DriverManager.getConnection().
 * Si la conexion es exitosa, devuelve el objeto Connection. Si hay un error, captura la excepcion SQLException y muestra un mensaje de error.
 * El metodo getConnection() puede ser llamado desde otras clases para obtener una conexion a la base de datos.
 * La importacion SQLException normalmente se utiliza en un try-catch para manejar errores de conexion.
 * 
 */
public class conexionBD {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/inventario_db";
    private static final String USUARIO = "root";
    private static final String CLAVE = "12345"; // o tu contraseña real


    public static Connection getConnection() {
        Connection conn = null; //Inicializa la variable conn como null. - este es un objeto de tipo Connection que representa la conexion a la base de datos.
        try {
            conn = DriverManager.getConnection(URL, USUARIO, CLAVE);
           // System.out.println("Conexión exitosa a la base de datos MySQL");
        } catch (SQLException e) {
            System.out.println(" Error de conexión: " + e.getMessage()); //Si hay un error, captura la excepcion SQLException y muestra un mensaje de error.
        }
        return conn; //Devuelve el objeto Connection. Si la conexion falla, devuelve null.
    }

}