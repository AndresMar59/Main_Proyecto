package producto;

public class producto {

    //Se siguen buenas practicas de encapsulamiento, por lo que los atributos son privados y se accede a ellos mediante getters y setters.3
    //Ademas que solo se puede acceder a los atributos desde dentro de la clase.
    //Atributos
    private int id;
    private String nombre;
    private double precio;
    private double Costo_Unitario;
    private int cantidad;  //stock disponible
    private int cantidad_minima; //stock minimo
    private int categoria_id; //1=electronica, 2=ropa, 3=hogar, 4=deportes, 5=juguetes

    //Constructor

    //Vacio, indispensable para ciertas operaciones como las consultas a la base de datos.
    public producto() {} //crea un producto vacío, para luego setear sus valores con los setters o desde unca consulta a la BD. - Crea el objeto sin darle valores todavia.
    /*
     * Con el constructor vacio se utiliza para crear objetos de la clase producto sin valores iniciales.
     * Esto es util cuando se quiere crear un objeto y luego asignar valores a sus atributos utilizando los setters.
     * En este caso algunas librerias or frameworks como JDBC requieren un constructor vacio para poder instanciar objetos de la clase. Y luego setear los valores.
     * En este caso, los datos vienen de consultas con ResultSet, por lo que primero se crea el objeto vacio y luego se llenan los datos.
     * O tambien, como es entrada de informacion, no se sabe que valores tendra el producto hasta q el usuario los ingrese.
     * El constructor lleno, por otro lado, se utiliza cuando se tienen todos los valores disponibles al momento de crear el objeto.
     * se utiliza cuando ya se tienen todos los datos en memoria. 
     */

     //Constructor lleno, se deja para propósitos de prueba o si se quiere crear un producto directamente con todos sus valores.
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

    //Getters y Setters - metodos controlados para acceder y modificar los atributos privados de la clase. -Siguiendo siempre los principios de encapsulamiento.
    //Get si se quiere obtener el valor del atributo.
    //Set si se quiere modificar el valor del atributo.    
    public int getId() {
        return id;
    }
    //Se remueve porque no se deberia modificar el ID una vez creado el producto.
    /*
    public void setId(int id) {  // Quitar lo de set ID. Puede generar problema
        this.id = id;
    } */
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
