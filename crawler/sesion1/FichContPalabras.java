package crawler.sesion1;
/*
 * FichContPalabras.java: Contabiliza palabras contenidas en un fichero
 * (i) Felix R. Rguez., EPCC, Universidad de Extremadura, 2009-23
 * http://madiba.unex.es/
 */

import java.io.*;
import java.util.*;

public class FichContPalabras {


    /**
     * Tokeniza un fichero de texto y devuelve un mapa con las palabras y su frecuencia.
     *
     * @param fichEntrada Fichero de texto a tokenizar.
     * @param map         Mapa ya inicializado con las palabras y su frecuencia.
     * @throws IOException Si no se puede leer el fichero.
     */
    public static void tokenizarFichero(Map<String, Integer> map, String fichEntrada) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fichEntrada));
        String linea;

        while ((linea = br.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(linea, " ,.:;(){}¡!°\"¿?\t'%/\\|[]<=>&#+*$-¨^~\n@");
            while (st.hasMoreTokens()) {
                String s = st.nextToken();
                Object o = map.get(s);
                if (o == null) map.put(s, Integer.valueOf(1));
                else {
                    Integer cont = (Integer) o;
                    map.put(s, Integer.valueOf(cont.intValue() + 1));
                }
            }
        }
        br.close();
    }

    /**
     * Tokeniza un fichero de texto y devuelve un mapa con las palabras y su frecuencia.
     *
     * @param fichEntrada Fichero de texto a tokenizar.
     * @return Map <String, Integer> Mapa con las palabras y su frecuencia.
     * @throws IOException Si no se puede leer el fichero.
     */
    public static Map<String, Integer> tokenizarFichero(String fichEntrada) throws IOException {
        Map<String, Integer> map = new TreeMap<String, Integer>();
        tokenizarFichero(map, fichEntrada);
        return map;
    }

    /**
     * Imprime un mapa en un fichero de texto.
     *
     * @param fichSalida Fichero de texto de salida.
     * @param map        Mapa a imprimir.
     * @throws IOException Si no se puede escribir en el fichero.
     */
    public static void imprimirFichero(String fichSalida, Map<String, Integer> map) throws IOException {
        List<String> claves = new ArrayList<String>(map.keySet());
        Collections.sort(claves);

        PrintWriter pr = new PrintWriter(new FileWriter(fichSalida));
        Iterator<String> i = claves.iterator();
        while (i.hasNext()) {
            Object k = i.next();
            pr.println(k + " : " + map.get(k));
        }
        pr.close();
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("ERROR. Utilizar: >java FichContPalabras fichero_entrada fichero_salida");
            return;
        }
        Map<String, Integer> map = tokenizarFichero(args[0]);
        imprimirFichero(args[1], map);
    }
}
