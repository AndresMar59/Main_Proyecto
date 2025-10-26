package inventario;

import producto.producto;
import producto.productoDAO;
import java.util.List;

//Servicio
public class inventario {

    private productoDAO productoDAO = new productoDAO();

    public void agregarProducto(producto p) {
        productoDAO.agregarProducto(p);
    }

    public List<producto> obtenerProductos() {
        return productoDAO.obtenerProductos();
    }

    public void actualizarProducto(producto p) {
        productoDAO.actualizarProducto(p);
    }

    public void eliminarProducto(int id) {
        productoDAO.eliminarProducto(id);
    }


    public void mostrarAlerta() {
        for (producto p : obtenerProductos()) {
            System.out.println("🔍 Verificando " + p.getNombre() +
                    " | Cantidad: " + p.getCantidad() +
                    " | Mínima: " + p.getCantidadMinima());

            if (p.getCantidad() < p.getCantidadMinima()) {
                System.out.println("⚠️ Alerta: " + p.getNombre() + " está por debajo del mínimo.");
            }
        }
    }


}
