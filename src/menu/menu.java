package menu;

/* Aplicación de Gestión de Inventario
Requerimientos: Registro de productos, categorías, stock, alertas por bajo inventario.inventario.
Evaluación:
CRUD completo.
Uso de tablas dinámicas.
Reportes en PDF/Excel (opcional).


MENU
SISTEMA DE GESTIÓN DE INVENTARIO
====================
1. Registrar producto.producto
2. Listar productos
3. Buscar producto.producto por ID
4. Actualizar producto.producto
5. Eliminar producto.producto
---------------------------
6.Mostrar productos con bajo stock
7. Reporte: Inventario agrupado por categoría
0. Salir


 * */

import inventario.inventario; // Java.util es un paquete que contiene la clase que nos ayuda a ingresar texto directamente en la consola.

import java.util.Comparator;
import java.util.List; //Se importa de la carpeta inventario la clase inventario
import java.util.Scanner; //Se importa de la carpeta producto la clase producto
import producto.producto;//Se importa de la carpeta producto la clase productoDAO
import producto.productoDAO;  //esta importacion nos ayuda a que se puedan utilizar metodos como list<producto>, list es una interfaz que permite guardar varios objetos. Esta dentro del paquere java.util.
import Reportes.reportes; // Esta importación nos ayuda a utilizar los metodos de reporteria que podamos usar

public class menu {

    private inventario inventarioService = new inventario(); //Se llama aal intermediario Inventario
    private productoDAO service = new productoDAO();  // Se llama al que hace las consulta directamente con mysql
    Scanner getInfo = new Scanner(System.in); //Se llama a la clase de java.util para utilizar el ingreso de texto.


    public void mostrarMenu(){

//Variables
        int opcion = -1; //Se declara la variable que se utilizara para ingresar la opcion del menu, se uso -1 porque no es una opcion real del menu, al entrar una de las opciones del menu ya se cambia.

        //Menu - while para que no se cierra a no ser que el usuario quiera cerrarlo.
        while (true) {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║        SISTEMA DE GESTIÓN DE INVENTARIO          ║");
        System.out.println("╠══════════════════════════════════════════════════╣");
        System.out.println("║ 1. Registrar producto.                           ║");
        System.out.println("║ 2. Listar productos.                             ║");
        System.out.println("║ 3. Buscar producto.                              ║");
        System.out.println("║ 4. Actualizar producto.                          ║");
        System.out.println("║ 5. Eliminar producto.                            ║");
        System.out.println("║ 6. Mostrar productos con bajo stock.             ║");
        System.out.println("║ 7. Generar Reportes.                             ║");
        System.out.println("║ 8. Registrar Movimiento.                         ║");
        System.out.println("║ 9. Salir.                                        ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        //Validacion de que se ingrese la opcion correcta!
        try {
            System.out.print("Opcion: "); opcion = getInfo.nextInt();
            getInfo.nextLine();
        } catch(java.util.InputMismatchException e) {
            System.out.println("Error! Debe seleccionar una opcion del menu");
            System.out.println("──────────────────────────────────────────────────");
            getInfo.next();
            opcion = -1;
            continue;
        }
            //Paso
        switch(opcion) {
            case 1:
                //System.out.println ("Registrar producto.producto.");
                registrarProducto();
                break;
            case 2:
                //System.out.println ("Listar productos.");
                obtenerProductos();
                break;
            case 3:
                //System.out.println ("Buscar producto.producto.");
                filtrarProductos();
                break;
            case 4:
                //System.out.println ("Actualizar producto.producto.");
                actualizarProducto();
                break;
            case 5:
                //System.out.println ("Eliminar producto.producto.");
                eliminarProducto();
                break;
            case 6:
                //System.out.println ("Mostrar productos con bajo stock.");
                //bajostock();
                System.out.println("╔════════════════════════════════╗");
                System.out.println("║    Productos con Bajo Stock    ║");
                System.out.println("╚════════════════════════════════╝");
                bajostock();  //  Aquí se usa
                System.out.println("──────────────────────────────────────────────────");
                break;
            case 7:
                    System.out.println ("Reportes.");
                    mostrarReportes();
                    break;
                case 8:
                System.out.println("8. Registrar movimiento de inventario");
                registrarMovimiento();
                break;    
                case 9:
                    System.out.println ("Salir");
                    System.exit(0);
                    break;
                default:
                    System.out.println ("Opcion invalida");
            }

        }

    }

    private void registrarProducto(){
        producto p = new producto();
        //System.out.println(); // ← mejora visual

        System.out.println("Registrar nuevo producto");
        //getInfo.nextLine(); // <-- limpiar buffer antes de pedir el nombre
        System.out.println("Nombre: ");
        String nombre = validacionesDeCampos("texto");
        p.setNombre(nombre);
        System.out.println("Precio: ");
        double  precio = Double.parseDouble(validacionesDeCampos("precio"));
        p.setPrecio(precio);//
        System.out.println("Costo Unitario: ");
        double  costo = Double.parseDouble(validacionesDeCampos("costo"));
        p.setCosto_Unitario(costo);
        System.out.println("Cantidad: ");
        int cantidad = Integer.parseInt(validacionesDeCampos("entero"));
        p.setCantidad(cantidad);
        System.out.println("Cantidad Minima: ");
        int cantidadMinima = Integer.parseInt(validacionesDeCampos("entero"));
        p.setCantidadMinima(cantidadMinima);
        // Seleccionar categoría (validada dentro del metodo)
        int categoriaSeleccionada = seleccionarCategoria();
        p.setCategoria_id(categoriaSeleccionada);
        service.agregarProducto(p);
        System.out.println("Producto registrado exitosamente con categoria_id = " + p.getCategoria_id());


    }



    private void obtenerProductos() {
    List<producto> productos = service.obtenerProductos();

    if (productos.isEmpty()) {
        System.out.println("No hay productos registrados.");
    } else {
        System.out.println("╔══════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                               LISTA DE PRODUCTOS                                     ║");
        System.out.println("╠════╤════════════════════════════╤════════════╤════════════╤════════════╤════════════╣");
        System.out.printf("║ %-4s │ %-26s │ %-10s │ %-10s │ %-10s │ %-10s ║%n",
                "ID", "Nombre", "Precio", "Costo Unit.", "Stock", "Mínimo");
        System.out.println("╟────┼────────────────────────────┼────────────┼────────────┼────────────┼────────────╢");

        for (producto p : productos) {
            System.out.printf("║ %-4d │ %-26s │ %10.2f │ %10.2f │ %10.0f │ %10.0f ║%n",
                    p.getId(),
                    p.getNombre(),
                    p.getPrecio(),
                    p.getCosto_Unitario(),
             (double) p.getCantidad(),
        (double) p.getCantidadMinima());
        }

        System.out.println("╚════╧════════════════════════════╧════════════╧════════════╧════════════╧════════════╝");
    }
}


   private void bajostock() {
    List<producto> productos = service.obtenerProductos();

    // Ordenar productos por cantidad (stock) ascendente
    productos.sort(Comparator.comparingDouble(producto::getCantidad));

    boolean bajoStockfound = false;

    System.out.println("╔══════════════════════════════════════════════════════════════╗");
    System.out.println("║                  PRODUCTOS CON BAJO STOCK                    ║");
    System.out.println("╠══════╤════════════════════════════╤════════════╤════════════╣");
    System.out.printf("║ %-4s │ %-26s │ %-10s │ %-10s ║%n", "ID", "Nombre", "Stock", "Mínimo");
    System.out.println("╟──────┼────────────────────────────┼────────────┼────────────╢");

    for (producto p : productos) {
        if (p.getCantidad() < p.getCantidadMinima()) {
            System.out.printf("║ %-4d │ %-26s │ %-10.2f │ %-10.2f ║%n",
                    p.getId(), p.getNombre(), p.getCantidad(), p.getCantidadMinima());
            bajoStockfound = true;
        }
    }

    if (!bajoStockfound) {
        System.out.println("║              No hay productos con bajo stock                 ║");
    }

    System.out.println("╚══════╧════════════════════════════╧════════════╧════════════╝");
}

    private void filtrarProductos(){
        System.out.println("Buscar producto por ID");
        System.out.println("Ingrese el ID del producto.");

        if(!getInfo.hasNextLine()){
            System.out.println("Error: Debe ingresas un numero.");
            getInfo.next(); //Limpia la entrada
            return;
        }

        int id = getInfo.nextInt();
        getInfo.nextLine(); //Limpia buffer

        producto encontrado = service.obtenerProductoPorID(id);

        if (encontrado != null) {
            System.out.println("Producto Encontrado");
            System.out.println("--------------------------------------");
            System.out.println("ID: " + encontrado.getId());
            System.out.println("Nombre: " + encontrado.getNombre());
            System.out.println("Precio: " + encontrado.getPrecio());
            System.out.println("Costo: " + encontrado.getCosto_Unitario());
            System.out.println("Cantidad: " + encontrado.getCantidad());
            System.out.println("Cantidad Minima: " + encontrado.getCantidadMinima());
            System.out.println("Categoria: " + encontrado.getCategoria_id());
            System.out.println("--------------------------------------");

        }else {
            System.out.println("Producto no encontrado.");
        }
    }


    private void actualizarProducto(){
        System.out.println("Actualizar producto.producto");

        obtenerProductos();
        System.out.println("Ingrese el ID del producto.producto a actualizar: ");
        int id = getInfo.nextInt(); getInfo.nextLine(); //Limpiar buffer
        producto p = new producto();
        p.setId(id);
        System.out.println("Nuevo nombre: ");
        p.setNombre(getInfo.nextLine());
        System.out.println("Nuevo precio: ");
        p.setPrecio(getInfo.nextDouble());
        System.out.println("Nuevo Costo Unitario: ");
        p.setCosto_Unitario(getInfo.nextDouble());
        System.out.println("Nueva cantidad: ");
        p.setCantidad(getInfo.nextInt());
        System.out.println("Nueva cantidad minima: ");
        p.setCantidadMinima(getInfo.nextInt());
        p.setCategoria_id(seleccionarCategoria()); //Por defecto
        service.actualizarProducto(p);
        System.out.println("Producto actualizado exitosamente.");

    }

    private void eliminarProducto(){
        System.out.println("Eliminar producto.producto");
        obtenerProductos();
        System.out.println("Ingrese el ID del producto.producto a eliminar: ");
        int id = getInfo.nextInt(); getInfo.nextLine(); //Limpiar buffer
        service.eliminarProducto(id);
        System.out.println("Producto eliminado exitosamente.");
    }

    private int seleccionarCategoria(){
        int opc = -1;
        boolean valido = false;

        System.out.println("Seleccione una categoría:");
        System.out.println("1. Consolas");
        System.out.println("2. Equipos");
        System.out.println("3. Accesorios");
        System.out.println("4. Software");
        System.out.println("5. Audio");
        System.out.println("6. Impresoras");
        System.out.println("7. Consumibles");
        System.out.println("8. Componentes");


        while (!valido) {
            System.out.print("Opción: ");

            String entrada = validacionesDeCampos("entero");

            try {
                opc = Integer.parseInt(entrada);

                // Solo permite las categorías válidas
                if (opc >= 1 && opc <= 8) {
                    valido = true;
                } else {
                    System.out.println("Categoría inválida. Debe ser un número entre 1 y 8.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un número válido.");
            }
        }

        System.out.println("Categoría seleccionada ID: " + opc);
        return opc;
    }

    private void mostrarReportes() {
    reportes reportes = new reportes();
    String home = System.getProperty("user.home");
    System.out.println("Seleccione el tipo de reporte:");
    System.out.println("1. Valor total por categoría ");
    System.out.println("2. Propedio de utilidad por categoria");
    System.out.println("0. Volver al menú principal");

    int opc = getInfo.nextInt(); getInfo.nextLine();
    switch (opc) {
        case 1:
    String rutaPrecio = home + "\\Downloads\\Reporte_Precios.pdf";
    reportes.PrecioTotalCategoria(rutaPrecio);
    break;

case 2:
    String rutaUtilidad = home + "\\Downloads\\Reporte_Utilidad.pdf";
    reportes.AVG_Utilidad(rutaUtilidad);
    break;
        default:
            System.out.println("Volviendo al menú principal...");
    }

    }

    


private void registrarMovimiento() {
    System.out.println("Registrar Movimiento de Inventario");

    // 1. Pedir ID del producto
    System.out.print("Ingrese el ID del producto: ");
    int idProducto = getInfo.nextInt();
    getInfo.nextLine(); // limpiar buffer

    // 2. Pedir cantidad
    System.out.print("Ingrese la cantidad: ");
    int cantidad = getInfo.nextInt();
    getInfo.nextLine(); // limpiar buffer

    // 3. Preguntar tipo de movimiento
    System.out.println("Seleccione tipo de movimiento:");
    System.out.println("1. Entrada (sumar al stock)");
    System.out.println("2. Salida (restar del stock)");
    int tipo = getInfo.nextInt();
    getInfo.nextLine();

    // Validar opción
    if(tipo != 1 && tipo != 2){
        System.out.println("Opción inválida");
        return;
    }

    // 4. Llamar a DAO para registrar el movimiento y actualizar producto
    boolean exito = service.registrarMovimiento(idProducto, cantidad, tipo);

    if(exito){
        System.out.println(" Movimiento registrado correctamente.");
    } else {
        System.out.println(" Error al registrar movimiento.");
    } } 


//Nuevo metodo para no aceptar campos vacios, texto o numeros
private String validacionesDeCampos(String dato){

        String entrada = "";
        boolean valido = false;

        while (!valido) {
            entrada = getInfo.nextLine().trim();
            if (entrada.isEmpty()) {
                System.out.println("Todos los campos deben ser llenados");
                System.out.println("Ingrese el dato: ");
                continue;
            }


            switch (dato) {
                case "entero":
                    if (entrada.matches("\\d+")) {
                        valido = true;
                    }  else {
                        System.out.println("Ingrese solamente numeros");
                        System.out.println("Cantidad: ");


                    }
                    break;
                    
                case "precio":
                    if (entrada.matches("\\d+(\\.\\d+)?")) {
                        valido = true;
                    } else {
                        System.out.println("Error: Ingrese un precio válido:");
                    }
                    break;

                case "costo":
                    if (entrada.matches("\\d+(\\.\\d+)?")) {
                        valido = true;
                    } else {
                        System.out.println("Error: Ingrese un costo válido:");
                    }
                    break;

                case "texto":
                    if (entrada.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9\\s%\\-]+")) {
                        valido = true;
                    } else {
                        System.out.println("Error, ingrese la informacion otra vez");
                        System.out.println("Nombre: ");
                    }
                    break;
                default:
                    valido = true;
            }
        }

        return entrada;


    }

}