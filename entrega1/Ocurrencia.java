package entrega1;

import java.io.File;
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class Ocurrencia implements Serializable {
    Integer frecuenciaGlobal;

    /**
     * Frecuencia de término por documento.
     * String: Nombre de fichero
     * Integer: Frecuencia del término local
     */
    Map<String, Integer> map = new TreeMap<String, Integer>();

    /**
     * Constructor de Ocurrencia
     *
     * @param fichero Documento de texto al cual pertenece el token.
     */
    Ocurrencia(File fichero) {
        frecuenciaGlobal = 1;
        map.put(fichero.getAbsolutePath(), 1);
    }

    /**
     * Incrementa la frecuencia global y local de un token.
     *
     * @param fichero Documento de texto al cual pertenece el token.
     */
    void incrementarFrecuencia(File fichero) {
        incFrecuenciaGlobal();
        incFrecuenciaLocal(fichero);
    }

    /**
     * Actualiza la frecuencia global del token.
     */
    void incFrecuenciaGlobal() {
        frecuenciaGlobal++;
    }

    /**
     * Actualiza la frecuencia local del token. Si no existía una ocurrencia del token, se inserta.
     *
     * @param fichero Documento de texto al cual pertenece el token.
     */
    void incFrecuenciaLocal(File fichero) {
        String path = fichero.getAbsolutePath();
        if (map.containsKey(path)) {
            map.put(path, map.get(path) + 1);
        } else {
            map.put(path, 1);
        }
    }

    /**
     * Obtiene la frecuencia global de un token.
     *
     * @return Frecuencia global del término.
     */
    public int getFrecuenciaGlobal() {
        return frecuenciaGlobal;
    }

    /**
     * Obtiene la frecuencia de un token en un documento específico, dada su ruta absoluta
     *
     * @param path Ruta absoluta del documento
     * @return La frecuencia local del token dada una ruta absoluta de un documento
     */
    public int getFrecuenciaLocal(String path) {
        int frecuencia = 0;
        if (map.containsKey(path)) {
            frecuencia = map.get(path);
        }
        return frecuencia;
    }

    /**
     * btiene la frecuencia de un token en un documento específico
     *
     * @param fichero Documento donde buscar la ocurrencia
     * @return La frecuencia local del token dado un documento
     */
    public int getFrecuenciaLocal(File fichero) {
        String path = fichero.getAbsolutePath();
        return getFrecuenciaLocal(path);
    }

    /**
     * Devuelve las rutas de Los documentos donde aparece el token y el número de ocurrencias en cada uno
     *
     * @return Las rutas de los documentos donde aparece el token y el número de ocurrencias en cada uno
     */
    public String getDocumentos() {
        StringBuilder sb = new StringBuilder();
        map.keySet().forEach(k -> sb.append("\n\t").append(map.get(k)).append(" ocurrencia(s) en ").append(k));
        return sb.toString();
    }

    /**
     * Devuelve la representación en cadena de la ocurrencia.
     *
     * @return Cadena con la información de la ocurrencia.
     */
    @Override
    public String toString() {
        return frecuenciaGlobal + " ocurrencias, en " + map.size() + " documentos: " + getDocumentos();
    }
}
