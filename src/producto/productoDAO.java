package producto;

import conexionBD.conexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//import conexionBD.conexionBD; // <-- importante



public class productoDAO {

    public void agregarProducto(producto p) {
        String sql = "INSERT INTO producto (nombre, precio, Costo_Unitario, cantidad, cantidad_minima, categoria_id) VALUES (?,?,?,?, ?, ?)";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            System.out.println("➡ Insertando producto.producto con categoria_id = " + p.getCategoria_id()); // 👈 debug line

            pstmt.setString(1, p.getNombre());
            pstmt.setDouble(2, p.getPrecio());
            pstmt.setDouble(3, p.getCosto_Unitario());
            pstmt.setInt(4, p.getCantidad());
            pstmt.setInt(5, p.getCantidadMinima());
            pstmt.setInt(6, p.getCategoria_id());
            //pstmt.executeUpdate();

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Producto registrado exitosamente en la base de datos.");
            } else {
                System.out.println("No se pudo registrar el producto.producto.");
            }

        } catch (SQLException e) {
            System.out.println("Error al insertar: " + e.getMessage());

        }
    }


    public List<producto> obtenerProductos() {
        List<producto> producto = new ArrayList<>();
        String sql = "SELECT * FROM producto"; //SELECT p.*, c.nombre AS categoria FROM producto.producto p JOIN categoria c ON p.categoria_id = c.id
        try (Connection conn = conexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                producto p = new producto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getDouble("Costo_Unitario"),
                        rs.getInt("cantidad"),
                        rs.getInt("cantidad_minima"),
                        rs.getInt("categoria_id")
                );
                producto.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar: " + e.getMessage());
        }
        return producto;
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

    public void actualizarProducto(producto p) {
        String sql = "UPDATE producto SET nombre = ?, precio = ?,Costo_Unitario = ?, cantidad = ?, cantidad_minima = ?, categoria_id = ? WHERE id = ?";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, p.getNombre());
            pstmt.setDouble(2, p.getPrecio());
            pstmt.setDouble(2, p.getCosto_Unitario());
            pstmt.setInt(3, p.getCantidad());
            pstmt.setInt(4, p.getCantidadMinima());
            pstmt.setInt(5, p.getCategoria_id());
            pstmt.setInt(6, p.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar: " + e.getMessage());
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
                System.out.println("Producto eliminado correctamente.");
            } else {
                System.out.println("No se encontró un producto.producto con ese ID.");
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
    String sql = "SELECT categoria_id, SUM(cantidad) AS total_cantidad, SUM(precio * cantidad) AS valor_total FROM producto GROUP BY categoria_id";
    List<Object[]> resultados = new ArrayList<>();
    try (Connection conn = conexionBD.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
       while (rs.next()) {
        resultados.add(new Object[]{
                rs.getInt("categoria_id"),
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
    String sql = "SELECT categoria_id, AVG(Precio - Costo_unitario) as Margen_promedio, SUM(cantidad) AS total_cantidad FROM producto GROUP BY categoria_id";
    List<Object[]> resultados = new ArrayList<>();
    try (Connection conn = conexionBD.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
       while (rs.next()) {
        resultados.add(new Object[]{
                rs.getInt("categoria_id"),
                rs.getInt("total_cantidad"),
                rs.getDouble("Margen_promedio")
            });
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return resultados;
}

}
