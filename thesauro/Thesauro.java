package thesauro;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Thesauro {
    private static String file = "thesauro.ser";
    private static Map<String, Sinonimo> map = new TreeMap<>();
    private static boolean stopwords = false;

    public Thesauro() throws Exception {
        Map<String, Sinonimo> cargado = AlmacenarThesauro.cargarThesauro(file);
        if (cargado == null) {
            leerFichero(new File("Thesaurus_es_ES.txt"));
            AlmacenarThesauro.guardarThesauro(map, file);
        } else {
            this.setMap(cargado);
        }
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

        while ((linea = br.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(linea, "\n,;");
            String s = st.nextToken().toLowerCase();
            Object o = map.get(s);
            Sinonimo listaSinonimos;
            if (o == null) {
                listaSinonimos = new Sinonimo();
                map.put(s, listaSinonimos);
            } else {
                listaSinonimos = (Sinonimo) o;
            }
            while (st.hasMoreTokens()) {
                String tokenSinonimo = st.nextToken();
                listaSinonimos.addSinonimo(tokenSinonimo);
                // TODO ¿Añadir sinónimos al Thesauro?
            }
        }
        br.close();
    }

    /**
     * Lee un fichero válido cualquiera. Se considera válido si existe y tiene permisos de lectura.
     *
     * @param fichero Directorio o documento cuyo contenido se pretende leer, tokenizar y guardar.
     */

    public void leerFichero(File fichero) {
        try {
            tokenizarFichero(fichero);
        } catch (Exception e) {
            System.out.println("ERROR al tokenizar el fichero");
        }
    }

    /**
     * Busca el token indicado y devuelve true si está en el Thesauro, false en caso contrario
     */

    public boolean buscarToken(String token) {
        return map.containsKey(token.toLowerCase());
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
        this.map = map;
    }

}
