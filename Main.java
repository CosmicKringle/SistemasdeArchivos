import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        ArbolArchivos sistema = new ArbolArchivos("Documentos");

        boolean continuar = true;

        while (continuar) {
            System.out.println("\n SISTEMA DE ARCHIVOS ABB");
            System.out.println("Directorio raiz: " + sistema.getRaiz().getNombre());
            System.out.println("1. Crear directorio");
            System.out.println("2. Insertar archivo");
            System.out.println("3. Buscar elemento");
            System.out.println("4. Eliminar elemento");
            System.out.println("5. Recorrido inorden");
            System.out.println("6. Recorrido preorden");
            System.out.println("7. Recorrido postorden");
            System.out.println("8. Informacion del elemento");
            System.out.println("9. Salir");
            System.out.println("10. Mostrar estructura del arbol");
            System.out.println("Seleccione una opcion");


            String opcion = entrada.nextLine().trim();

            switch (opcion) {
                case "1": {
                    System.out.print("Nombre del directorio: ");
                    String nombre = entrada.nextLine();
                    
                    try {
                        sistema.insertar(nombre, "", 0, true);
                        System.out.println("Directorio creado correctamente.");
                    } catch (IllegalArgumentException error) {
                        System.out.println("ERROR: " + error.getMessage());
                    }
                    break;
                }
                case "2": {
                    System.out.print("nombre del archivo (sin extension): ");
                    String nombre = entrada.nextLine();

                    System.out.print("Extension (sin punto): ");
                    String extension = entrada.nextLine();

                    System.out.print("Tamano en bytes: ");
                    String textoTamanio = entrada.nextLine().trim();

                    try {
                        long tamanio = Long.parseLong(textoTamanio);
                        sistema.insertar(nombre, extension, tamanio, false);
                        System.out.println("Archivo inseertado correctamente");
                    } catch (NumberFormatException error) {
                        System.out.println("ERROR: El tamano debe ser un numero entero valido");
                    } catch (IllegalArgumentException error) {
                        System.out.println("ERROR: " + error.getMessage());
                    }
                    break;
                }
                case "3":
                case "8": {
                    System.out.println("Nombre del elemento: ");
                    String nombre = entrada.nextLine();

                    try {
                        Nodo encontrado = sistema.buscar(nombre);

                        if(encontrado == null) {
                            System.out.println("El elemento no existe en el sistema de archivos");
                        } else {
                            mostrarInformacion(encontrado);
                        }
                    } catch (IllegalArgumentException error) {
                        System.out.println("ERROR: " + error.getMessage());
                    }
                    break;
                }
                case "4": {
                    System.out.print("Nombre del elemtno a eliminar: ");
                    String nombre = entrada.nextLine();

                    try {
                        Nodo encontrado = sistema.buscar(nombre);

                        if (encontrado == null) {
                            System.out.println( "El elemento no existe en el sistema de archivos");
                        } else {
                            mostrarInformacion(encontrado);
                            System.out.print("Eliminar? (S/N): ");
                            String respuesta = entrada.nextLine().trim();

                            if (respuesta.equalsIgnoreCase("S")) {
                                if (sistema.eliminar(nombre)) {
                                    System.out.println( "Elemento eliminado correctamente");
                                }
                            } else {
                                System.out.println("Eliminacion cancelada");
                            }
                        }
                    } catch (IllegalArgumentException error) {
                        System.out.println("ERROR: " + error.getMessage());
                    }
                    break;
                }
                case "5":
                    mostrarRecorrido("INORDEN", sistema.inorden());
                    break;
                case "6":
                    mostrarRecorrido("PREORDEN", sistema.preorden());
                    break;
                case "7":
                    mostrarRecorrido("POSTORDEN", sistema.postorden());
                    break;
                case "9":
                    continuar = false;
                    System.out.println("Saliendo del programa");
                    break;
                case "10": 
                    mostrarArbol(sistema.getRaiz());
                    break;
                default:
                    System.out.println("Opcion invalida. Intente nuevamente.");
            }
        }
        entrada.close();
    }

    public static void mostrarRecorrido(String titulo, java.util.List<Nodo> nodos) {
        System.out.println("\n" + titulo);
        System.out.printf("%-15s %-8s %-12s %-12s%n", "Nombre", "Exit", "Bytes", "Tipo");

        for (Nodo nodo : nodos) {
            System.out.printf("%-15s %-8s %-12d %-12s%n", nodo.getNombre(), nodo.isDirectorio() ? "-" : nodo.getExtension(), nodo.getTamanio(), nodo.isDirectorio() ? "Directorio" : "Archivo");
        }
    }
    public static void mostrarInformacion(Nodo nodo) {
        System.out.println("\n Informacion del Elemento");
        System.out.println("Nombre: " + nodo.getNombre());
        System.out.println("Extencion: " + (nodo.isDirectorio() ? "-" : nodo.getExtension()));
        System.out.println("Tipo: " + (nodo.isDirectorio() ? "Directorio" : "Archivo"));
        System.out.println("Tamano: " + nodo.getTamanio() + "bytes");
    }
    public static void mostrarArbol(Nodo raiz) {
        System.out.println("Estructura del ABB");
        mostrarRama(raiz, "", "Raiz");
    }
    public static void mostrarRama(Nodo nodo, String sangria, String posicion) {
        if (nodo == null) {
            return;
        }
        String etiqueta = nodo.isDirectorio() ? "[Directorio]" + nodo.getNombre() : "[Archivo]" + nodo.getNombre() + "." + nodo.getExtension();
        System.out.println(sangria + posicion + ": " + etiqueta);
        mostrarRama(nodo.getIzquierdo(), sangria + "    ", "Izq");
        mostrarRama(nodo.getDerecho(), sangria + "    ", "Der");
    }
 }