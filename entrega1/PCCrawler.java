package entrega1;

import java.io.File;
import java.util.Scanner;

public class PCCrawler {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("ERROR. Ejecutar: >java PCCrawler nombre_directorio");
            return;
        }
        File fichero = new File(args[0]);
        Diccionario.leerFichero(fichero);
        AlmacenarObjeto.salvarObjeto(Diccionario.map, "diccionario.ser");
        AlmacenarObjeto.cargarObjeto("diccionario.ser");

        Scanner scanner = new Scanner(System.in);
        boolean terminarConsulta = false;

        while (!terminarConsulta) {
            System.out.println("------------------");
            System.out.println("CONSULTA DE TOKENS");
            System.out.println("------------------");
            System.out.println("0. Consultar diccionario");
            System.out.println("1. Buscar un token en el diccionario");
            System.out.println("2. Terminar consulta");
            System.out.print("Opción seleccionada: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 0:
                    Diccionario.mostrarDiccionario();
                    break;
                case 1:
                    System.out.print("Token a consultar: ");
                    String token = scanner.nextLine();
                    int frecuenciaGlobal = Diccionario.buscarToken(token);

                    if (frecuenciaGlobal != 0) {
                        System.out.println("El token «" + token + "» se encuentra " + frecuenciaGlobal + " veces.");
                    } else {
                        System.out.println("ERROR. No existe el token «" + token + "».");
                    }
                    break;

                case 2:
                    terminarConsulta = true;
                    System.out.println("Fin de la consulta.");
                    break;

                default:
                    System.out.println("ERROR. Opción no válida.");
                    break;
            }
        }
        scanner.close();
    }

}
