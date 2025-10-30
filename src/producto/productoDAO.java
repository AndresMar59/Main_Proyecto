package producto;

import conexionBD.conexionBD; //import la clase conexionBD del paquete conexionBD para gestionar la conexión a la base de datos.

import util.colores; //importa la clase colores del paquete util para usar colores en la consola.
//importa las clases necesarias de java.sql para manejar la base de datos.
import java.sql.Connection; //importa la clase Connection de java.sql para manejar conexiones a la base de datos.
import java.sql.PreparedStatement; //importa la clase PreparedStatement de java.sql para ejecutar sentencias SQL precompiladas. Envia datos a la base de datos.
//Es decir que se usa para enviar instrucciones SQL a la base de datos de manera eficiente y segura.
//Es util porque escapa automaticamente los caracteres especiales, ayudando a prevenir ataques de inyección SQL.
import java.sql.ResultSet; //importa la clase ResultSet de java.sql para manejar los resultados de las consultas SQL SELECTO * FROM .Traer datos desde la base de datos.
//Es una estructura en memoria que contiene el resultado de una consulta SQL ejecutada contra la base de datos.
//Es una tabla virtual que permite recorrer y acceder a los datos fila por fila.
//una tabla sql se convierte en una lista de objetos en java.
import java.sql.Statement; //importa la clase Statement de java.sql para ejecutar sentencias SQL simples sin parametros. Ejecuta consultas SQL directas
//Es una interfaz que permite enviar instrucciones SQL DIRECTAMENTE a la base de datos y obtener resultados.
//JDBC: Java Database Connectivity.
//Se usa con comandos sql como SELECT, INSERT, UPDATE, DELETE.
import java.sql.SQLException;//importa la clase SQLException de java.sql para manejar errores relacionados con la base de datos.

import java.util.ArrayList;//importa la clase ArrayList de java.util para usar listas dinámicas.
import java.util.List;//importa la interfaz List de java.util para usar listas genéricas.

/**
 * DAO (Data Access Object) para la clase producto.
 * Proporciona métodos para realizar operaciones CRUD en la base de datos relacionadas con productos.
 * Se comunica directamente con la base de datos utilizando JDBC, ejecutando consultas SQL, devolviendo o guardando datos.
 * Estructura: Menu principal -> DAO -> conexionBD -> Base de datos.
 */

public class productoDAO {

    public void agregarProducto(producto p) {
        String sql = "INSERT INTO producto (nombre, precio, Costo_Unitario, cantidad, cantidad_minima, categoria_id) VALUES (?,?,?,?, ?, ?)"; //prepara la consulta SQL para insertar un nuevo producto en la tabla producto.
        try (Connection conn = conexionBD.getConnection(); //obtiene una conexion a la base de datos llamando al metodo getConnection() de la clase conexionBD.
             PreparedStatement pstmt = conn.prepareStatement(sql)) { //prepara consulta SQL con parametros seguros. Luego se llenan con valores reales en tiempo de ejecucion. Consulta preparada.

            //System.out.println("➡ Insertando producto.producto con categoria_id = " + p.getCategoria_id()); //

            //Establece los valores de los parametros en la consulta SQL utilizando los getters del objeto producto p. //setString es para cadenas de texto, el set significa que se va a setear un valor en la consulta SQL.
            //Se van rellenando los ? en la consulta SQL.
            pstmt.setString(1, p.getNombre()); //Establece los valores de los parametros en la consulta SQL utilizando los getters del objeto producto p. //setString es para cadenas de texto, el set significa que se va a setear un valor en la consulta SQL.
            pstmt.setDouble(2, p.getPrecio());
            pstmt.setDouble(3, p.getCosto_Unitario());
            pstmt.setInt(4, p.getCantidad());
            pstmt.setInt(5, p.getCantidadMinima());
            pstmt.setInt(6, p.getCategoria_id());

            int filasAfectadas = pstmt.executeUpdate(); //Ejecuta la consulta SQL preparada. Devuelve el numero de filas afectadas por la operacion (insert, update, delete).

            if (filasAfectadas > 0) { //Si se afecto al menos una fila, el producto se inserto correctamente.
                System.out.println(colores.GREEN+ "Producto registrado exitosamente en la base de datos."+colores.RESET);
            } else {
                System.out.println(colores.YELLOW+ "No se pudo registrar el producto."+colores.RESET);
            }

        } catch (SQLException e) {
            System.out.println("Error al insertar: " + e.getMessage()); //Si hay un error al insertar, captura la excepcion SQLException y muestra un mensaje de error.

        }
    }


    public List<producto> obtenerProductos() {//se utiliza import java.util.List; para devolver una lista de productos.
        List<producto> producto = new ArrayList<>(); //Crea una lista vacia para almacenar los productos obtenidos de la base de datos. //es un objeto dinamico que puede crecer segun se agreguen productos.
        String sql = "SELECT * FROM producto"; //prepara la consulta SQL para obtener todos los productos de la tabla producto.
        try (Connection conn = conexionBD.getConnection();//obtiene una conexion a la base de datos de llamando al metodo getConnection() de la clase conexionBD.
             Statement stmt = conn.createStatement();//crea un objeto Statement para ejecutar la consulta SQL.
             ResultSet rs = stmt.executeQuery(sql)) {//ejecuta la consulta SQL y obtiene los resultados en un objeto ResultSet.
            while (rs.next()) {//itera sobre los resultados del ResultSet. El rs.next() mueve el cursor a la siguiente fila y devuelve true si hay una fila disponible.
                producto p = new producto(//Crea un nuevo objeto producto utilizando los datos del ResultSet. Esta seria la lista de productos ahora asignado como p.
                        rs.getInt("id"), //obtiene el valor de la columna "id" como entero. El getInt es para obtener valores enteros desde la base de datos.
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getDouble("Costo_Unitario"),
                        rs.getInt("cantidad"),
                        rs.getInt("cantidad_minima"),
                        rs.getInt("categoria_id")
                );
                producto.add(p);//Agrega el objeto producto a la lista de productos.
            }
        } catch (SQLException e) {//Si hay un error al obtener los productos, captura la excepcion SQLException y muestra un mensaje de error.
            System.out.println("Error al listar: " + e.getMessage());
        }
         return producto; //Devuelve la lista de productos obtenidos de la base de datos.
    }
         
    public producto obtenerProductoPorID(int id) {
        producto p = null;
        String sql = "SELECT * FROM producto WHERE id = ?";

        try (Connection conn = conexionBD.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, id);  // Este set es sobre la query?
            ResultSet rs = pstmt.executeQuery();  

            if(rs.next()){
                p = new producto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                         rs.getDouble("Costo_Unitario"),
                        rs.getInt("cantidad"),
                        rs.getInt("cantidad_minima"),
                        rs.getInt("categoria_id")
                );
            }
        }catch(SQLException e){
            System.out.println("Error al obtener: " + e.getMessage());
        }
        return p;
    }


    private boolean existeProductoPorId(Connection conn, int id) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT 1 FROM producto WHERE id=?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        }
    }

  

     public List<String> actualizarProductoConValidacion(producto p) {
        List<String> errores = new ArrayList<>();
        String sql = "UPDATE producto SET nombre = ?, precio = ?, Costo_Unitario = ?, cantidad_minima = ?, categoria_id = ? WHERE id = ?";
        try (Connection conn = conexionBD.getConnection()) {
            if (conn == null) {
                errores.add("No hay conexión a la base de datos.");
                return errores;
            }
          //  errores = validarParaActualizar(conn, p);
           // if (!errores.isEmpty()) return errores;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, p.getNombre().trim());
                pstmt.setDouble(2, p.getPrecio());
                pstmt.setDouble(3, p.getCosto_Unitario());
              //  pstmt.setInt(4, p.getCantidad());
                pstmt.setInt(4, p.getCantidadMinima());
                pstmt.setInt(5, p.getCategoria_id());
                pstmt.setInt(6, p.getId());
                int filas = pstmt.executeUpdate();
                if (filas == 0) errores.add("No se actualizó ningún registro (¿ID inexistente?).");
            }
        } catch (SQLException e) {
            errores.add("Error SQL: " + e.getMessage());
        }
        return errores;
    }



    public void actualizarProducto(producto p) {
        List<String> errores = actualizarProductoConValidacion(p);
        if (!errores.isEmpty()) {
            System.out.println("No se actualizó: " + String.join(" | ", errores));
        } else {
            System.out.println(colores.GREEN+"Producto actualizado correctamente."+colores.RESET);
        }
    }


    public void eliminarProducto(int id) {
        String sql = "DELETE FROM producto WHERE id = ?";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id); // Esto cambia el estado?
            //pstmt.executeUpdate();
            int filas = pstmt.executeUpdate(); 

            if (filas > 0) {
                System.out.println("-------------------------------------");
                System.out.println(colores.GREEN+"Producto eliminado correctamente."+colores.RESET);
            } else {
                System.out.println("-------------------------------------");
                System.out.println(colores.YELLOW+"No se encontró un producto con ese ID."+colores.RESET);
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }
    }

    public boolean registrarMovimiento(int idProducto, int cantidad, int tipo) {
    //  1 -> Entrada 
    // 2 -> Salida 
    String sqlMovimiento = "INSERT INTO movimiento(id_producto ,cantidad,tipo,fecha) VALUES(?,?,?, NOW())";
    String sqlProducto;
    if(tipo == 1){ sqlProducto = "UPDATE producto SET cantidad = cantidad + ? WHERE id = ?";
    } else { sqlProducto = "UPDATE producto SET cantidad = cantidad - ? WHERE id = ?";
    }
    Connection conn = null; 
    try {
    conn = conexionBD.getConnection();
    conn.setAutoCommit(false);

    if(tipo == 2) {
    String sqlCheck = "SELECT cantidad FROM producto WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sqlCheck)) {
        stmt.setInt(1, idProducto);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()) {
        int stockActual = rs.getInt("cantidad");
    if(cantidad > stockActual) {
         System.out.println("Hay menos cantidad que lo solicitado");
                    return false;  }}}}// Sale del método antes de hacer cambios
                    

    try (PreparedStatement stmtProducto = conn.prepareStatement(sqlProducto);
         PreparedStatement stmtMovimiento = conn.prepareStatement(sqlMovimiento)) {

        // Insertar movimiento
        stmtMovimiento.setInt(1, idProducto);
        stmtMovimiento.setInt(2, cantidad);
        stmtMovimiento.setInt(3, tipo);
        stmtMovimiento.executeUpdate();

        // Actualizar producto
        stmtProducto.setInt(1, cantidad);
        stmtProducto.setInt(2, idProducto);
        stmtProducto.executeUpdate();

        conn.commit();
        return true;
    }

}     catch (SQLException e) {
      e.printStackTrace();
     try {
        if(conn != null) conn.rollback(); 
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
     return false;

}    finally {
    try {
        if(conn != null) conn.close();
    } catch (SQLException e) {
        e.printStackTrace(); }}
}

    


    public List<Object[]> PrecioTotal() {
    String sql = "SELECT p.categoria_id, c.nombre AS categoria_nombre , SUM(p.cantidad) AS total_cantidad, SUM(p.precio * p.cantidad) AS valor_total FROM producto p INNER JOIN categoria c ON p.categoria_id = c.id GROUP BY p.categoria_id, c.nombre ";
    List<Object[]> resultados = new ArrayList<>();
    try (Connection conn = conexionBD.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
       while (rs.next()) {
        resultados.add(new Object[]{
                rs.getString("categoria_nombre"),
                rs.getInt("total_cantidad"),
                rs.getDouble("valor_total")
            });
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return resultados;
}


    public List<Object[]> AVG_PromedioCategoria() {
    String sql = " SELECT p.categoria_id, c.nombre AS categoria_nombre, AVG(p.Precio - p.Costo_unitario) AS Margen_promedio, SUM(p.cantidad) AS total_cantidad FROM producto p INNER JOIN categoria c ON p.categoria_id = c.id GROUP BY p.categoria_id, c.nombre";

    List<Object[]> resultados = new ArrayList<>();

    try (Connection conn = conexionBD.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            resultados.add(new Object[]{
                rs.getString("categoria_nombre"),
                rs.getInt("total_cantidad"),
                rs.getDouble("Margen_promedio")
            });
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return resultados; } }