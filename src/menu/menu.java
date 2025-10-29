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


import java.util.Comparator;
import java.util.List; //Se importa de la carpeta inventario la clase inventario
import java.util.Scanner; //Se importa de la carpeta producto la clase producto
import producto.producto;//Se importa de la carpeta producto la clase productoDAO
import producto.productoDAO;  //esta importacion nos ayuda a que se puedan utilizar metodos como list<producto>, list es una interfaz que permite guardar varios objetos. Esta dentro del paquere java.util.
import Reportes.reportes; // Esta importación nos ayuda a utilizar los metodos de reporteria que podamos usar

public class menu {

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
                //System.out.println ("Registrar producto");
                registrarProducto();
                break;
            case 2:
                //System.out.println ("Listar productos");
                System.out.print("");
                obtenerProductos();
                System.out.println("Volviendo al menu principal...");
                System.out.println("──────────────────────────────────────────────────");
                break;
            case 3:
                //System.out.println ("Buscar producto");
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
                System.out.println(" ");
                bajostock();  //  Aquí se usa
                System.out.println("Volviendo al menu principal...");
                System.out.println("──────────────────────────────────────────────────");
                break;
            case 7:
                    System.out.println (" ");
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
                    System.out.println ("Error! Debe seleccionar una opcion del menu");
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
        System.out.println("╔═════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                               LISTA DE PRODUCTOS                                    ║");
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
    boolean bajoStockfound = false;

    System.out.println("╔════════════════════════════════════════════════════════════════════════════════════════════════╗");
    System.out.println("║                               PRODUCTOS CON BAJO STOCK                                         ║");
    System.out.println("╠══════╤════════════════════════════╤════════════╤════════════╤════════════╤════════════╤════════╣");

    // Encabezado
    System.out.printf("║ %-4s │ %-26s │ %-10s │ %-10s │ %-10s │ %-10s │ %-6s ║%n",
                      "ID", "Nombre", "Precio", "Costo", "Stock", "Mínimo", "CatID");

    System.out.println("╟──────┼────────────────────────────┼────────────┼────────────┼────────────┼────────────┼────────╢");

    for (producto p : productos) {
        if (p.getCantidad() < p.getCantidadMinima()) {
        System.out.printf("║ %-4d │ %-26s │ %-10.2f │ %-10.2f │ %-10d │ %-10d │ %-6d ║%n",
                    p.getId(),p.getNombre(),p.getPrecio(),p.getCosto_Unitario(),
                    p.getCantidad(),
                    p.getCantidadMinima(),
                    p.getCategoria_id());
            bajoStockfound = true; }}

    if (!bajoStockfound) {
    System.out.println("║                       No hay productos con bajo stock                                          ║");
    }

    System.out.println("╚══════╧════════════════════════════╧════════════╧════════════╧════════════╧════════════╧════════╝");
}


    private void filtrarProductos(){
      //  System.out.println("Buscar producto por ID");
        System.out.println("Ingrese el ID del producto.");

    int id = -1;
    boolean entradaValida = false;

        while(!entradaValida){
     //     System.out.println("Debes ingresas un numero");
            if (getInfo.hasNextInt()) {
                id = getInfo.nextInt();
                getInfo.hasNextLine();
                entradaValida = true;  
            } else { System.out.println("Debes ingresar un numero valido");
            getInfo.nextLine(); // limpiar entrada incorrecta
        }
    }
        producto encontrado = service.obtenerProductoPorID(id);

        if (encontrado != null) {
            final String RESET = "\u001B[0m";
final String CYAN = "\u001B[36m";
final String GREEN = "\u001B[32m";
final String YELLOW = "\u001B[33m";

System.out.println(CYAN + "\n══════════════════════════════════════");
System.out.println("           PRODUCTO ENCONTRADO         ");
System.out.println("══════════════════════════════════════" + RESET);
System.out.printf(GREEN + "%-20s: " + YELLOW + "%d%n", "ID", encontrado.getId());
System.out.printf(GREEN + "%-20s: " + YELLOW + "%s%n", "Nombre", encontrado.getNombre());
System.out.printf(GREEN + "%-20s: " + YELLOW + "%.2f%n", "Precio", encontrado.getPrecio());
System.out.printf(GREEN + "%-20s: " + YELLOW + "%.2f%n", "Costo unitario", encontrado.getCosto_Unitario());
System.out.printf(GREEN + "%-20s: " + YELLOW + "%d%n", "Cantidad", encontrado.getCantidad());
System.out.printf(GREEN + "%-20s: " + YELLOW + "%d%n", "Cantidad mínima", encontrado.getCantidadMinima());
System.out.printf(GREEN + "%-20s: " + YELLOW + "%d%n", "Categoría", encontrado.getCategoria_id());
System.out.println(RESET + CYAN + "══════════════════════════════════════\n"+RESET);

        }else {
            System.out.println("Producto no encontrado");
            System.out.println(" ");
            System.out.println("Volviendo al menu...");


        }
    }


    private void actualizarProducto() {
        System.out.println("Actualizar producto");
        obtenerProductos();
        System.out.println("Ingrese el ID del producto a actualizar: ");

        int id = -1;
        boolean entradaValida = false;

    Scanner getInfo = new Scanner(System.in);


        while(!entradaValida){
     //     System.out.println("Debes ingresas un numero");
            if (getInfo.hasNextInt()) {
                id = getInfo.nextInt();
                getInfo.nextLine();
                entradaValida = true;  
            } else { System.out.println("Debes ingresar un numero valido");
            getInfo.nextLine(); // limpiar entrada incorrecta
        }
    }      

       producto encontrado = service.obtenerProductoPorID(id);
       
    
    if (encontrado != null)  {  
        producto p = new producto();
     p.setId(id);
    
     System.out.println("Nuevo nombre: ");
     p.setNombre(getInfo.nextLine());

    double precio = 0;
    while (true) {
        System.out.print("Nuevo precio: ");
        if (getInfo.hasNextDouble()) {
            precio = getInfo.nextDouble();
            if (precio > 0) break;
            else System.out.println("El precio debe ser mayor que 0");
        } else {
            System.out.println("Debes ingresar un numero entero");
            getInfo.next(); // limpia el valor invalido
        }
    }

    double costo = 0;
    while (true) {
        System.out.print("Nuevo Costo Unitario: ");
        if (getInfo.hasNextDouble()) {
        costo = getInfo.nextDouble();
          if (costo < 0) {
            System.out.println("El costo unitario no puede ser negativo");
            continue; }
          if (costo >= precio) {
            System.out.println("El Costo unitario no puede ser mayor que el precio (" + precio +")");
            continue;
          }
            break;
          }    
            else {
            System.out.println("Debes ingresar un numero entero");
            getInfo.next(); // limpia el valor inválido
         } }

    int cantidadMinima = 0;
    while (true) {
        System.out.print("Nueva cantidad minima: ");
        if (getInfo.hasNextInt()) {
    cantidadMinima = getInfo.nextInt();
        if (cantidadMinima >= 0) break;
            else System.out.println("La cantidad minima no puede ser negativa");
        } else {
            System.out.println("Debes ingresar un numero entero");
            getInfo.next(); // limpia el valor inválido
        }
    }

     p.setPrecio(precio);
     p.setCosto_Unitario(costo);
     p.setCantidadMinima(cantidadMinima);
     p.setCategoria_id(seleccionarCategoria()); // Por defecto
    
    service.actualizarProducto(p);
    System.out.println("Producto actualizado exitosamente");
    }  
     else {
    System.out.println("Producto no encontrado. Saliendo al menú prinicipal");
    System.out.println("+----------------------------------------------------+"); } }   




    private void eliminarProducto(){
        System.out.println("Eliminar producto");
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

        return entrada; }


    private void mostrarReportes() {
    reportes reportes = new reportes();
    String home = System.getProperty("user.home");
    int opc = -1;


       
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║             MENÚ DE REPORTES         ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println("1. Valor total por categoría");
        System.out.println("2. Promedio de utilidad por categoría");
        System.out.println("0. Volver al menú principal");

         while (opc != 0) {
        System.out.print("Seleccione una opción: ");
        System.out.println("");

        if (!getInfo.hasNextInt()) {
            System.out.println("Debe ingresar un numero entero");
            System.out.println(" ");
            getInfo.nextLine(); // limpiar entrada incorrecta
            continue; // vuelve a mostrar el menú completo
        } 

        opc = getInfo.nextInt();
        getInfo.nextLine(); // limpiar buffer

        if (opc < 0 || opc > 2) {
            System.out.println("Opción inválida. Debe ser 0, 1 o 2");
            System.out.println(" ");
            continue; // vuelve al menú
        }

         switch (opc) {
            case 1 -> {System.out.println(" "); String rutaPrecio = home + "\\Downloads\\Reporte_Precios.pdf";
            reportes.PrecioTotalCategoria(rutaPrecio);
            }
            case 2 ->{ System.out.println(" ");
    String rutaUtilidad = home + "\\Downloads\\Reporte_Utilidad.pdf";
    reportes.AVG_Utilidad(rutaUtilidad);}
            case 0 -> {
            System.out.println(" ");    
            System.out.println("Volviendo al menú principal...");
            System.out.println(" "); break;
            }} }
}

private void registrarMovimiento() {
    obtenerProductos();
    System.out.println("Presione enter para registrar Movimiento de Inventario");
    getInfo.nextLine(); //Limpiar buffer
    int idP = -1;
    int cantidad = -1;
    int tipo = -1;
    boolean si = false;

       while (!si) {
        while (true) {
         System.out.print("Ingrese el ID del producto: ");
            if (!getInfo.hasNextInt()) {System.out.println("El ID del producto debe ser un numero entero"); System.out.println(" ");
                getInfo.nextLine(); // limpiar entrada incorrecta
                continue;
            }

            idP = getInfo.nextInt();
            getInfo.nextLine(); // limpiar buffer

            if (idP <= 0) {System.out.println("El ID debe ser mayor que cero"); System.out.println(" ");
                continue; }

             producto encontrado = service.obtenerProductoPorID(idP);
            if (encontrado == null) {
            System.out.println("Producto con ID " + idP + " no encontrado. Vuelve a intentar"); System.out.println(" ");
            continue;
            }
            break;
            }  // salida del bucle si todo es válido 
            
    
        while (true) {
            System.out.print("Ingrese la cantidad: ");
            if (!getInfo.hasNextInt()) {System.out.println("La cantidad debe ser un numero entero"); System.out.println(" ");
                getInfo.nextLine();
                continue;}

            cantidad = getInfo.nextInt();
            getInfo.nextLine();
            if (cantidad <= 0) {
                System.out.println("La cantidad debe ser mayor que cero"); System.out.println(" ");
                continue;}
            break;
        }

        // movimiento 
        while (true) {
            System.out.println("Seleccione tipo de movimiento");
            System.out.println("1. Entrada (sumar al stock)");
            System.out.println("2. Salida (restar del stock)");
            System.out.print("Opción: ");
          if (!getInfo.hasNextInt()) {
                System.out.println("Debe ingresar un número entre 1 y 2"); System.out.println(" ");
            getInfo.nextLine();
                continue;}

            tipo = getInfo.nextInt();
            getInfo.nextLine();
            if (tipo != 1 && tipo != 2) {
                System.out.println("Opción invalida, debe ser 1 o 2"); System.out.println(" ");
                continue;
            } break;
        }

        si = service.registrarMovimiento(idP, cantidad, tipo);

    if (si) {
     System.out.println("Movimiento registrado exitosamente");
     System.out.println(" ");     
     System.out.println("\nVolviendo al menú principal...");
    } else {
   System.out.println("Error al registrar movimiento. Intente nuevamente.\n"); } }}}
    
        
