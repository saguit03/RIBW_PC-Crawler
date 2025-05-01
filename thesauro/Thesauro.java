package thesauro;

/*
 * RIBW - PC Crawler
 * * Guillén Torrado, Sara
 * * Mocinha Sánchez, Daniel
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Thesauro {
    private static final String file = "thesauro.ser";
    private static Map<String, Sinonimo> map;

    /**
     * Crea un nuevo diccionario Thesauro o lo carga desde el archivo que contiene el mapa ya creado
     *
     * @throws Exception Si no encuentra el fichero o no se puede leer
     */
    public Thesauro() throws Exception {
        Map<String, Sinonimo> cargado = AlmacenarThesauro.cargarThesauro(file);
        if (cargado == null) {
            map = new TreeMap<>();
            if (leerFichero(new File("Thesaurus_es_ES.txt"))) {
                AlmacenarThesauro.guardarThesauro(map, file);
            }
        } else {
            this.setMap(cargado);
        }
    }

    /**
     * Busca el token indicado y devuelve true si está en el Thesauro, false en caso contrario
     */
    public static boolean buscarToken(String token) {
        return map.containsKey(token.toLowerCase());
    }

    public static List<String> getListaSinonimos(String token) {
        List<String> listaSinonimos = null;
        if (map.containsKey(token.toLowerCase())) {
            Sinonimo sinonimos = map.get(token.toLowerCase());
            listaSinonimos = new ArrayList<>(sinonimos.getListaSinonimos());
        }
        return listaSinonimos;
    }

    /**
     * Busca un token y lo devuelve junto a sus sinónimos, si los tiene
     *
     * @param token El token a buscar
     * @return El token y, si los tiene, sus sinónimos
     */
    public static String getToken(String token) {
        if (map.containsKey(token)) {
            Sinonimo sinonimos = map.get(token);
            StringBuilder sb = new StringBuilder();
            sb.append(token);
            if (sinonimos.getSize() > 0) {
                sb.append(" (");
                sb.append(sinonimos.getSize());
                sb.append(" sinónimos)" + " -> ");
                sb.append(sinonimos);
            }
            return sb.toString();
        } else {
            return "";
        }
    }

    /**
     * Muestra todos los tokens del Thesauro y sus sinónimos
     */
    public static void mostrarDiccionario() {
        System.out.println("************ THESAURO ************");
        map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(entrada -> {
                    String token = entrada.getKey();
                    System.out.println(getToken(token));
                    System.out.println("************");
                });
        System.out.println("Hay un total de " + map.size() + " términos");
    }

    /**
     * Muestra todos los tokens del diccionario y sus ocurrencias
     */
    public static void mostrarDiccionario(String cadena) {
        System.out.println("************ THESAURO ************");
        AtomicInteger cont = new AtomicInteger();
        map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .filter(entrada -> entrada.getKey().contains(cadena))
                .forEach(entrada -> {
                    cont.getAndIncrement();
                    String token = entrada.getKey();
                    System.out.println(getToken(token));
                    System.out.println("************");
                });
        System.out.println("Hay un total de " + cont + " términos que contienen «" + cadena + "»");
    }

    /**
     * Muestra todos los tokens del diccionario y sus ocurrencias
     */
    public static void mostrarDiccionario(int min) {
        System.out.println("************ THESAURO ************");
        AtomicInteger cont = new AtomicInteger();
        map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .filter(entrada -> entrada.getValue().getSize() > min)
                .forEach(entrada -> {
                    cont.getAndIncrement();
                    String token = entrada.getKey();
                    System.out.println(getToken(token));
                    System.out.println("************");
                });
        System.out.println("Hay un total de " + cont + " términos que tienen más de " + min + " sinónimos");
    }

    /**
     * Tokeniza un fichero de texto y almacena los resultados en un mapa.
     *
     * @param fichEntrada Fichero de texto a tokenizar.
     * @throws IOException Si no se puede leer el fichero.
     */
    public void tokenizarFichero(File fichEntrada) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fichEntrada));
        String linea;
        List<String> listaTokens = new ArrayList<String>();
        while ((linea = br.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(linea, "\n,;");
            while (st.hasMoreTokens()) {
                listaTokens.add(st.nextToken().toLowerCase());
            }
            Sinonimo listaSinonimos;
            for (String t : listaTokens) {
                String token = Sinonimo.quitarParentesis(t).toLowerCase();
                Object o = map.get(token);
                if (o == null) {
                    listaSinonimos = new Sinonimo(token, listaTokens);
                    map.put(token, listaSinonimos);
                } else {
                    listaSinonimos = (Sinonimo) o;
                    listaSinonimos.addSinonimos(token, listaTokens);
                }
            }
            listaTokens.clear();
        }

        br.close();
    }

    /**
     * Lee un documento de texto.
     *
     * @param fichero Documento cuyo contenido se pretende leer, tokenizar y guardar.
     */
    public boolean leerFichero(File fichero) {
        boolean done = false;
        try {
            tokenizarFichero(fichero);
            done = true;
        } catch (Exception e) {
            System.out.println("ERROR al tokenizar el fichero");
        }
        return done;
    }

    /**
     * Obtiene el mapa de términos y ocurrencias
     *
     * @return El mapa de términos y ocurrencias
     */
    public Map<String, Sinonimo> getMap() {
        return map;
    }

    /**
     * Actualiza el mapa de términos y ocurrencias
     *
     * @param map El nuevo mapa de términos y ocurrencias
     */
    public void setMap(Map<String, Sinonimo> map) {
        Thesauro.map = map;
    }

}
