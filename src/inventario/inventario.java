package inventario;

import producto.producto;
import producto.productoDAO;
import java.util.List;

//Servicio
public class inventario {

    private productoDAO productoDAO = new productoDAO();


    //Agregando validaciones para que el costo no sea mayor a precio.
    public void agregarProducto(producto p) {
        if (p.getCosto_Unitario() > p.getPrecio()) {
            System.out.println("Error: Costo unitario no puede ser mayor al Precio.");
            return;
        }
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
