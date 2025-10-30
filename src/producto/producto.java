package producto;

public class producto {

    private int id;
    private String nombre;
    private double precio;
    private double Costo_Unitario;
    private int cantidad;  //stock disponible
    private int cantidad_minima; //stock minimo
    private int categoria_id; //1=electronica, 2=ropa, 3=hogar, 4=deportes, 5=juguetes

    //Constructor

    public producto() {} //crea un producto vacío, para luego setear sus valores con los setters o desde unca consulta a la BD. - Crea el objeto sin darle valores todavia.
    /*Es */


    //Crea un producto con todos sus atributos - 
    public producto(int id, String nombre, double precio, double Costo_Unitario, int cantidad, int cantidad_minima ,
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
        return Costo_Unitario;
    }
    public void setCosto_Unitario(double Costo_Unitario) {
        this.Costo_Unitario = Costo_Unitario;
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
