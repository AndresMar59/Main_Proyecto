package producto;

public class producto {

    int id;
    String nombre;
    double precio;
    double Costo_Unitario;
    int cantidad;  //stock disponible
    int cantidad_minima; //stock minimo
    int categoria_id; //1=electronica, 2=ropa, 3=hogar, 4=deportes, 5=juguetes

    //Constructor

    public producto() {}

    public producto(int id, String nombre, double Costo_Unitario, double precio, int cantidad, int cantidad_minima ,
                    int categoria_id) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.Costo_Unitario = Costo_Unitario;
        this.cantidad = cantidad;
        this.categoria_id = categoria_id;
        this.cantidad_minima = cantidad_minima;

    }

    //Getters y Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {  // Quitar lo de set ID. Puede generar problema
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public double getPrecio() {
        return precio;
    }
    public void setPrecio(double precio) {
        this.precio = precio;
    }

     public double getCosto_Unitario() {
        return precio;
    }
    public void setCosto_Unitario(double precio) {
        this.precio = precio;
    }
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public int getCantidadMinima() {
        return cantidad_minima;
    }
    public void setCantidadMinima(int cantidad_minima) {
        this.cantidad_minima = cantidad_minima;
    }
    public int getCategoria_id() {
        return categoria_id;
    }
    public void setCategoria_id(int categoria_id) {  // Quitar esta. Puede haber problema de congruencia
        this.categoria_id = categoria_id;
    }
}
