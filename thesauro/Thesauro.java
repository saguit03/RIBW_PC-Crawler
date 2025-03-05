//package entrega1;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.Map;
//import java.util.StringTokenizer;
//import java.util.TreeMap;
//
//public class Thesauro {
//
//    private static Map<String, Sinonimo> map = new TreeMap<>();
//
//    /**
//     * Muestra todos los tokens del diccionario y sus ocurrencias
//     */
//
//    public void mostrarDiccionario() {
//        System.out.println("************ DICCIONARIO ************");
//    }
//
//    /**
//     * Tokeniza un fichero de texto y almacena los resultados en un mapa.
//     *
//     * @param fichEntrada Fichero de texto a tokenizar.
//     * @throws IOException Si no se puede leer el fichero.
//     */
//
//    public void tokenizarFichero(File fichEntrada) throws IOException {
//        BufferedReader br = new BufferedReader(new FileReader(fichEntrada));
//        String linea;
//
//        while ((linea = br.readLine()) != null) {
//            StringTokenizer st = new StringTokenizer(linea, "\n,;");
//            while (st.hasMoreTokens()) {
//                String s = st.nextToken();
//                Object o = map.get(s);
//                if (o == null) {
//                    map.put(s, new Sinonimo(fichEntrada));
//                } else {
//                    Sinonimo sinonimo = (Sinonimo) o;
//                    sinonimo.incrementarFrecuencia(fichEntrada);
//                }
//            }
//        }
//        br.close();
//    }
//
//    /**
//     * Lee un fichero válido cualquiera. Se considera válido si existe y tiene permisos de lectura.
//     *
//     * @param fichero Directorio o documento cuyo contenido se pretende leer, tokenizar y guardar.
//     */
//
//    public void leerFichero(File fichero) {
//        try {
//            tokenizarFichero(fichero);
//        } catch (Exception e) {
//            System.out.println("ERROR al tokenizar el fichero");
//        }
//    }
//
//    /**
//     * Busca el token indicado y devuelve su frecuencia global. En caso de no encontrarse, devuelve 0.
//     *
//     * @param token Término del cual se pretende obtener su frecuencia.
//     * @return La frecuencia global del término.
//     */
//
//    public String buscarToken(String token) {
//        if (map.containsKey(token)) {
//            return "Ok";
//        } else {
//            return "";
//        }
//    }
//
//    /**
//     * Obtiene el mapa de términos y ocurrencias
//     *
//     * @return El mapa de términos y ocurrencias
//     */
//
//    public Map<String, Sinonimo> getMap() {
//        return map;
//    }
//
//    /**
//     * Actualiza el mapa de términos y ocurrencias
//     *
//     * @param map El nuevo mapa de términos y ocurrencias
//     */
//
//    public void setMap(Map<String, Sinonimo> map) {
//        this.map = map;
//    }
//
//}
