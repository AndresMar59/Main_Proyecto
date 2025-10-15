package com.BD;

public class Producto {
    private String id;
    private String name;
    private double precio;
    private Integer stock;
    private boolean habilitado; 

    public Producto(String id, String name, Double precio, Integer stock) {
        if (id == null || name == null || precio == null || stock == null || stock == 0) {
            throw new IllegalArgumentException("Productos no pueden tener parametros vacios"); 
        }
        this.id = id;
        this.name = name;
        this.precio = precio ;
        this.stock = stock;
        this.habilitado = true;
    }

    public String getId() { return id; } 
    public String getName() { return name; }
    public Double getPrecio() { return precio; }
    public boolean isHabilitado() { return habilitado; } 
    public Integer getStock() { return stock; }
    

    public void setStock(Integer stock) {
        this.stock = stock;
    }


    public void setHabilitado(boolean habilitado) { 
        this.habilitado = habilitado;
    }

    @Override
    public String toString() {          
        return name + " vale " + precio;
    }  

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Producto)) return false;
        
        Producto producto = (Producto) o; 
        return id.equals(producto.id);  
    }

    @Override
    public int hashCode() {   
        return id.hashCode(); 
    }
}

