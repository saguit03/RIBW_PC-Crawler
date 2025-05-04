package crawler;

/*
 * RIBW - PC Crawler
 * * Guillén Torrado, Sara
 * * Mocinha Sánchez, Daniel
 */

import thesauro.Thesauro;

import java.io.File;
import java.util.*;

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
            System.out.println("0. Terminar consulta");
            System.out.println("1. Consultar diccionario");
            System.out.println("2. Buscar un token (o BIGRAMA) en el diccionario");
            System.out.println("3. Consultar Thesauro");
            System.out.println("4. Buscar un token en el Thesauro");
            System.out.println("5. Mostrar índice de documentos");
            System.out.println("6. Buscar un token y sus SINÓNIMOS");
            System.out.println("7. Mostrar bigramas");
            System.out.println("8. Buscar multitérminos");
            System.out.println("9. Buscar multitérminos con SINONIMIA");
            System.out.print("Opción seleccionada: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 0:
                        terminar = true;
                        break;
                    case 1:
                        diccionario.mostrarDiccionario();
                        break;
                    case 2:
                        buscarToken(scanner);
                        break;
                    case 3:
                        consultarThesauro(scanner);
                        break;
                    case 4:
                        buscarThesauro(scanner);
                        break;
                    case 5:
                        LRU.mostrarLRU();
                        break;
                    case 6:
                        buscarSinonimos(scanner);
                        break;
                    case 7:
                        diccionario.mostrarBigramas();
                        break;
                    case 8:
                        buscarMultitermino(scanner, false);
                        break;
                    case 9:
                        buscarMultitermino(scanner, true);
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

    public static void buscarToken(Scanner scanner) {
        boolean terminar = false;
        while (!terminar) {
            System.out.print("Token a consultar (escribe 0 para terminar): ");
            String token = scanner.nextLine();
            if (token.equals("0")) {
                terminar = true;
            } else {
                buscarToken(token, true);
            }
        }
    }

    public static boolean buscarToken(String token, boolean mostrarError) {
        boolean found = false;
        String ocurrencia = diccionario.buscarToken(token.toLowerCase());
        if (ocurrencia != "") {
            System.out.println("** Se ha encontrado el token «" + token + "»: " + ocurrencia);
            found = true;
        } else {
            if (mostrarError)
                System.out.println("** No existe el token «" + token + "».");
        }
        return found;
    }

    public static void buscarThesauro(Scanner scanner) {
        boolean terminar = false;
        while (!terminar) {
            System.out.print("Token a consultar en el Thesauro (escribe 0 para terminar): ");
            String token = scanner.nextLine();
            if (token.equals("0")) {
                terminar = true;
            } else {
                buscarThesauro(token);
            }
        }
    }

    public static void buscarThesauro(String token) {
        String sinonimos = Thesauro.getToken(token.toLowerCase());
        if (sinonimos != "") {
            System.out.println("«" + token + "» existe en el Thesauro");
            if (!token.equals(sinonimos)) {
                System.out.println(sinonimos);
            }
        } else {
            System.out.println("ERROR. No existe «" + token + "» en el Thesauro.");
        }
    }

    public static void buscarSinonimos(Scanner scanner) {
        boolean terminar = false;
        while (!terminar) {
            System.out.print("Token a consultar (escribe 0 para terminar): ");
            String token = scanner.nextLine();
            if (token.equals("0")) {
                terminar = true;
            } else {
                buscarSinonimos(token);
            }
        }
    }

    public static void buscarSinonimos(String token) {
        List<String> listaSinonimos = Thesauro.getListaSinonimos(token);
        buscarToken(token, true);
        System.out.println("---- Buscando sinónimos del término «" + token + "»...");
        int cont = 0;
        for (String sinonimo : listaSinonimos) {
            if (buscarToken(sinonimo, false))
                cont++;
        }
        if (cont == 0)
            System.out.println("*** No se han encontrado sinónimos de «" + token + "»");
    }

    public static void mostrarDocumentosMultitermino(String multitermino, boolean sinonimia) {
        Set<String> documentos;
        if (sinonimia) {
            documentos = diccionario.buscarMultiterminoSinonimo(multitermino);
        } else {
            documentos = diccionario.buscarMultitermino(multitermino);
        }
        if (documentos.isEmpty()) {
            System.out.println("No se encontraron documentos que contuviesen simultáneamente: " + multitermino);
        } else {
            for (String documento : documentos) {
                System.out.println("- " + documento);
            }
            System.out.println("***** Se encontraron un total de [" + documentos.size() + "] documentos que contuviesen simultáneamente: " + multitermino);
        }
    }

    public static void buscarMultitermino(Scanner scanner, boolean sinonimia) {
        boolean terminar = false;
        while (!terminar) {
            System.out.print("Tokens a consultar SEPARADOS POR ESPACIOS (escribe 0 para terminar): ");
            String multitermino = scanner.nextLine();
            if (multitermino.equals("0")) {
                terminar = true;
            } else {
                mostrarDocumentosMultitermino(multitermino, sinonimia);
            }
        }
    }

    public static void consultarThesauro(Scanner scanner) {
        boolean terminar = false;
        while (!terminar) {
            System.out.print("Escribe una cadena para buscar en el Thesauro (escribe 0 para terminar): ");
            String token = scanner.nextLine();
            if (token.equals("0")) {
                terminar = true;
            } else {
                Thesauro.mostrarDiccionario(token.toLowerCase());
            }
        }
    }

    public static void showHelp() {
        System.out.println(">java PCCrawler [-menu] [-cargar] [-iter] [-recur] [-sinonimia] [-file nombre_archivo] [-all] [-search tokens_a_buscar][-multi tokens_a_buscar]");
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
        boolean multi = false;
        boolean sinonimia = false;

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
                case "-sinonimia":
                    sinonimia = true;
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
                        searchTokens.addAll(Arrays.asList(args).subList(++i, args.length));
                        i = args.length;
                    } else {
                        System.err.println("Error: -search requiere una lista de tokens.");
                        return;
                    }
                    break;
                case "-multi":
                    multi = true;
                    if (i + 1 < args.length) {
                        searchTokens.addAll(Arrays.asList(args).subList(++i, args.length));
                        i = args.length;
                    } else {
                        System.err.println("Error: -multi requiere una lista de tokens.");
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
                nuevoDiccionario = false;
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
            if (multi) {
                StringBuilder multitermino = new StringBuilder();
                for (String token : searchTokens) {
                    multitermino.append(token).append(" ");
                }
                mostrarDocumentosMultitermino(multitermino.toString(), sinonimia);
            } else {
                for (String token : searchTokens) {
                    buscarToken(token, true);
                }
            }
        }

        if (menu) {
            showMenuPrincipal();
        }
    }

}
