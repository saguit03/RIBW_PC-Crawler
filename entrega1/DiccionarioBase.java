package entrega1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class DiccionarioBase implements Diccionario {

    public Map<String, Ocurrencia> map = new TreeMap<String, Ocurrencia>();

    /**
     * Muestra todos los tokens del diccionario y sus ocurrencias
     */
    @Override
    public void mostrarDiccionario() {
        System.out.println("************ DICCIONARIO ************");
        for (Map.Entry<String, Ocurrencia> entrada : map.entrySet()) {
            String token = entrada.getKey();
            Ocurrencia ocurrencia = entrada.getValue();
            System.out.println(token + " -> " + ocurrencia.toString());
            System.out.println("************");
        }
    }

    /**
     * Tokeniza un fichero de texto y almacena los resultados en un mapa.
     *
     * @param fichEntrada Fichero de texto a tokenizar.
     * @param map         Mapa ya inicializado que almacena los tokens y su frecuencia.
     * @throws IOException Si no se puede leer el fichero.
     */
    @Override
    public void tokenizarFichero(Map<String, Ocurrencia> map, File fichEntrada) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fichEntrada));
        String linea;

        while ((linea = br.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(linea, " ,.:;(){}¡!°\"¿?\t'%/\\|[]<=>&#+*$-¨^~\n@");
            while (st.hasMoreTokens()) {
                String s = st.nextToken();
                Object o = map.get(s);
                if (o == null) {
                    map.put(s, new Ocurrencia(fichEntrada));
                } else {
                    Ocurrencia ocurrencia = (Ocurrencia) o;
                    ocurrencia.incrementarFrecuencia(fichEntrada);
                }
            }
        }
        br.close();
    }

    /**
     * Lee un fichero válido cualquiera. Se considera válido si existe y tiene permisos de lectura.
     *
     * @param fichero Directorio o documento cuyo contenido se pretende leer, tokenizar y guardar.
     */
    @Override
    public void leerFichero(File fichero) {
        System.out.println("Debe especificar si desea utilizar la implementación iterativa o recursiva.");
    }

    /**
     * Busca el token indicado y devuelve su frecuencia. En caso de no encontrarse, devuelve 0.
     *
     * @param token Término del cual se pretende obtener su frecuencia.
     * @return La frecuencia del término.
     */
    @Override
    public int buscarToken(String token) {
        if (map.containsKey(token)) {
            Ocurrencia ocurrencia = map.get(token);
            return ocurrencia.getFrecuenciaGlobal();
        } else {
            return 0;
        }
    }

    @Override
    public Map<String, Ocurrencia> getMap() {
        return map;
    }

}
