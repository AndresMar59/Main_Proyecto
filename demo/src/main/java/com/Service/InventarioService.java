package com.Service;


import com.BD.Producto;
import com.BD.Loan;
import com.Dao.LoanDAO;
import com.Dao.ProductoDAO;
import java.util.List;

public class InventarioService {
    private final ProductoDAO productoDAO = new ProductoDAO();

    public void agregarProducto(Producto p) {
        productoDAO.insertar(p);
    }

    public List<Producto> listarProductos() {
        return productoDAO.obtenerTodos();
    }

    public void BuscarID(Producto p) {
        productoDAO.obtenerPorId(p.getId());
    }

        public void ActualizarProducto(Producto p) {
        productoDAO.actualizar(p);
    } 

        public void EliminarProducto(Producto p) {
        productoDAO.eliminar(p.getId());;
    }

    public void registrarMovimiento(Loan loan, String tipo,  int cantidad) {
        Producto producto = loan.getProducto();
        if (cantidad < 0) { // salida
            producto.setStock(producto.getStock() + cantidad); // cantidad negativa
        } else { // entrada
            producto.setStock(producto.getStock() + cantidad);
        }

        if (producto.getStock() <= 0) {
            producto.setHabilitado(false);
            producto.setStock(0);
        } else {
            producto.setHabilitado(true);
        }

        productoDAO.actualizar(producto);

         LoanDAO loanDAO = new LoanDAO();
        loanDAO.registrarMovimiento(loan, tipo, cantidad);
    }



}
