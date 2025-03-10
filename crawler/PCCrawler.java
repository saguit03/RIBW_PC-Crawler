package crawler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PCCrawler {
    public static TipoDiccionario tipoDiccionario = TipoDiccionario.NINGUNO;
    public static Diccionario diccionario = new DiccionarioBase();

    public static void showTipoDiccionario() {
        Scanner scanner = new Scanner(System.in);
        TipoDiccionario tipoDiccionario = TipoDiccionario.NINGUNO;

        while (tipoDiccionario == TipoDiccionario.NINGUNO) {
            System.out.println("Elige un tipo de diccionario:");
            TipoDiccionario[] valores = TipoDiccionario.values();
            // Mostrar opciones con índices
            for (int i = 0; i < valores.length; i++) {
                System.out.println(i + " - " + valores[i]);
            }
            System.out.print("Introduce el número correspondiente: ");
            try {
                int opcion = Integer.parseInt(scanner.nextLine());
                if (opcion >= 0 && opcion < valores.length) {
                    tipoDiccionario = valores[opcion];
                } else {
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Introduce un número.");
            }
        }
        scanner.close();
    }

    public static void showMenuPrincipal() {
        Scanner scanner = new Scanner(System.in);
        boolean terminar = false;
        int opcion = 0;
        while (!terminar) {
            System.out.println("------------------");
            System.out.println("CONSULTA DE TOKENS");
            System.out.println("------------------");
            System.out.println("0. Consultar diccionario");
            System.out.println("1. Buscar un token en el diccionario");
            System.out.println("2. Terminar consulta");
            System.out.print("Opción seleccionada: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 0:
                        diccionario.mostrarDiccionario();
                        break;
                    case 1:
                        buscarToken();
                    case 2:
                        terminar = true;
                        break;
                    default:
                        System.out.println("Elige una opción válida");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Introduce solo números enteros.");
            }
        }
        scanner.close();
    }

    public static void buscarToken() {
        boolean terminar = false;
        Scanner scanner = new Scanner(System.in);
        while (!terminar) {
            System.out.print("Token a consultar: ");
            String token = scanner.nextLine();
            if (token.equals("0")) {
                terminar = true;
            } else {
                buscarToken(token);
            }
        }
        scanner.close();
    }

    public static void buscarToken(String token) {
        String ocurrencia = diccionario.buscarToken(token.toLowerCase());

        if (ocurrencia != "") {
            System.out.println("Se ha encontrado el token «" + token + "»: "+ ocurrencia);
        } else {
            System.out.println("ERROR. No existe el token «" + token + "».");
        }
    }

    public static void showHelp() {
        System.out.println(">java PCCrawler [-menu] [-cargar] [-iter] [-recur] [-file nombre_archivo] [-all] [-search tokens_a_buscar]");
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            showHelp();
            return;
        }

        String nombreFichero = null;
        boolean nuevoDiccionario = false;
        boolean showAll = false;
        List<String> searchTokens = new ArrayList<>();
        boolean menu = false;

        // Procesar los argumentos
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-menu":
                    menu = true;
                    break;
                case "-cargar":
                    tipoDiccionario = TipoDiccionario.CARGADO;
                    break;
                case "-iter":
                    tipoDiccionario = TipoDiccionario.ITERATIVO;
                    break;
                case "-recur":
                    tipoDiccionario = TipoDiccionario.RECURSIVO;
                    break;
                case "-file":
                    if (i + 1 < args.length) {
                        nuevoDiccionario = true;
                        nombreFichero = args[++i]; // Obtener el siguiente argumento como nombre de archivo
                    } else {
                        System.err.println("Error: -file requiere un nombre de archivo.");
                        return;
                    }
                    break;
                case "-all":
                    showAll = true;
                    break;
                case "-search":
                    if (i + 1 < args.length) {
                        for (int j = ++i; j < args.length; j++) {
                            searchTokens.add(args[j]);
                        }
                        i = args.length;
                    } else {
                        System.err.println("Error: -search requiere una lista de tokens.");
                        return;
                    }
                    break;
                case "-help":
                case "-h":
                default:
                    showHelp();
                    break;
            }
        }

        switch (tipoDiccionario) {
            case CARGADO:
                diccionario.setMap(AlmacenarObjeto.cargarDiccionario("diccionario.ser"));
                break;
            case ITERATIVO:
                System.out.println("Creando diccionario iterativo...");
                diccionario = new DiccionarioIterativo();
                break;
            case RECURSIVO:

                System.out.println("Creando diccionario recursivo...");
                diccionario = new DiccionarioRecursivo();
                break;
            default:
                showTipoDiccionario();
                break;
        }

        if (nuevoDiccionario) {
            diccionario.leerFichero(new File(nombreFichero));
            AlmacenarObjeto.guardarDiccionario(diccionario.getMap(), "diccionario.ser");
        }

        if (showAll) {
            diccionario.mostrarDiccionario();
        }

        if (!searchTokens.isEmpty()) {
            for (String token : searchTokens) {
                buscarToken(token);
            }
        }

        if (menu) {
            showMenuPrincipal();
        }
    }

}
