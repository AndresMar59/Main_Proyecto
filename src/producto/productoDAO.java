package producto;

import conexionBD.conexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class productoDAO {

    private boolean existeProductoPorId(Connection conn, int id) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT 1 FROM producto WHERE id=?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        }
    }

    private boolean existeCategoria(Connection conn, int categoriaId) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT 1 FROM categoria WHERE id=?")) {
            ps.setInt(1, categoriaId);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        }
    }

    private List<String> validarParaActualizar(Connection conn, producto p) throws SQLException {
        List<String> errores = new ArrayList<>();

        if (p == null) {
            errores.add("Producto nulo.");
            return errores;
        }
        if (p.getId() <= 0) errores.add("ID inválido.");
        else if (!existeProductoPorId(conn, p.getId())) errores.add("No existe un producto con ID " + p.getId());

        if (p.getNombre() == null || p.getNombre().trim().isEmpty())
            errores.add("El nombre es obligatorio.");
        else if (p.getNombre().trim().length() > 150)
            errores.add("El nombre no puede superar 150 caracteres.");

        if (p.getPrecio() <= 0) errores.add("El precio debe ser mayor a 0.");
        if (p.getCosto_Unitario() < 0) errores.add("El costo unitario no puede ser negativo.");
        if (p.getCantidad() < 0) errores.add("La cantidad no puede ser negativa.");
        if (p.getCantidadMinima() < 0) errores.add("La cantidad mínima no puede ser negativa.");

        if (p.getCategoria_id() <= 0) errores.add("El ID de categoría es obligatorio.");
        else if (!existeCategoria(conn, p.getCategoria_id()))
            errores.add("La categoría con ID " + p.getCategoria_id() + " no existe.");

        return errores;
    }

    public void agregarProducto(producto p) {
        String sql = "INSERT INTO producto (nombre, precio, Costo_Unitario, cantidad, cantidad_minima, categoria_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            System.out.println("➡ Insertando producto con categoria_id = " + p.getCategoria_id());

            pstmt.setString(1, p.getNombre());
            pstmt.setDouble(2, p.getPrecio());
            pstmt.setDouble(3, p.getCosto_Unitario());
            pstmt.setInt(4, p.getCantidad());
            pstmt.setInt(5, p.getCantidadMinima());
            pstmt.setInt(6, p.getCategoria_id());

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas > 0) System.out.println("Producto registrado exitosamente.");
            else System.out.println("No se pudo registrar el producto.");

        } catch (SQLException e) {
            System.out.println("Error al insertar: " + e.getMessage());
        }
    }

    public List<producto> obtenerProductos() {
        List<producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM producto";
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
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar: " + e.getMessage());
        }
        return lista;
    }

    public producto obtenerProductoPorID(int id) {
        producto p = null;
        String sql = "SELECT * FROM producto WHERE id = ?";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
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
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener: " + e.getMessage());
        }
        return p;
    }

    public List<String> actualizarProductoConValidacion(producto p) {
        List<String> errores = new ArrayList<>();
        String sql = "UPDATE producto SET nombre = ?, precio = ?, Costo_Unitario = ?, " +
                     "cantidad = ?, cantidad_minima = ?, categoria_id = ? WHERE id = ?";
        try (Connection conn = conexionBD.getConnection()) {
            if (conn == null) {
                errores.add("No hay conexión a la base de datos.");
                return errores;
            }
            errores = validarParaActualizar(conn, p);
            if (!errores.isEmpty()) return errores;

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, p.getNombre().trim());
                pstmt.setDouble(2, p.getPrecio());
                pstmt.setDouble(3, p.getCosto_Unitario());
                pstmt.setInt(4, p.getCantidad());
                pstmt.setInt(5, p.getCantidadMinima());
                pstmt.setInt(6, p.getCategoria_id());
                pstmt.setInt(7, p.getId());
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
            System.out.println("Producto actualizado correctamente.");
        }
    }

    public void eliminarProducto(int id) {
        String sql = "DELETE FROM producto WHERE id = ?";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int filas = pstmt.executeUpdate();
            if (filas > 0) System.out.println("Producto eliminado correctamente.");
            else System.out.println("No se encontró un producto con ese ID.");

        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }
    }

    public boolean registrarMovimiento(int idProducto, int cantidad, int tipo) {
        String sqlMovimiento = "INSERT INTO movimiento(id_producto, cantidad, tipo, fecha) VALUES(?, ?, ?, NOW())";
        String sqlProducto = (tipo == 1)
                ? "UPDATE producto SET cantidad = cantidad + ? WHERE id = ?"
                : "UPDATE producto SET cantidad = cantidad - ? WHERE id = ?";

        try (Connection conn = conexionBD.getConnection()) {
            conn.setAutoCommit(false);

            if (tipo == 2) {
                String sqlCheck = "SELECT cantidad FROM producto WHERE id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sqlCheck)) {
                    stmt.setInt(1, idProducto);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next() && cantidad > rs.getInt("cantidad")) {
                            System.out.println("Hay menos cantidad que lo solicitado");
                            return false;
                        }
                    }
                }
            }

            try (PreparedStatement stmtProducto = conn.prepareStatement(sqlProducto);
                 PreparedStatement stmtMovimiento = conn.prepareStatement(sqlMovimiento)) {

                stmtMovimiento.setInt(1, idProducto);
                stmtMovimiento.setInt(2, cantidad);
                stmtMovimiento.setInt(3, tipo);
                stmtMovimiento.executeUpdate();

                stmtProducto.setInt(1, cantidad);
                stmtProducto.setInt(2, idProducto);
                stmtProducto.executeUpdate();

                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Object[]> PrecioTotal() {
        String sql = "SELECT categoria_id, SUM(cantidad) AS total_cantidad, " +
                     "SUM(precio * cantidad) AS valor_total FROM producto GROUP BY categoria_id";
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
        String sql = "SELECT categoria_id, AVG(precio - Costo_Unitario) AS Margen_promedio, " +
                     "SUM(cantidad) AS total_cantidad FROM producto GROUP BY categoria_id";
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