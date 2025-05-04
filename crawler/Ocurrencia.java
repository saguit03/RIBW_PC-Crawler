package crawler;

/*
 * RIBW - PC Crawler
 * * Guillén Torrado, Sara
 * * Mocinha Sánchez, Daniel
 */

import java.io.File;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Ocurrencia implements Serializable, Comparable<Ocurrencia> {
    /**
     * Número total de veces que aparece un token en todos los documentos
     */
    Integer frecuenciaGlobal;

    /**
     * Frecuencia de término por documento.
     * Integer: Identificador de fichero
     * Integer: Frecuencia del término local
     */
    Map<Integer, Integer> map = new TreeMap<Integer, Integer>();

    /**
     * Constructor de Ocurrencia
     *
     * @param indice El índice de la ruta en LRU
     */
    Ocurrencia(int indice) {
        frecuenciaGlobal = 1;
        map.put(indice, 1);
    }

    public Map<Integer, Integer> getMapFrecuencias() {
        return map;
    }

    public void setMapFrecuencias(Map<Integer, Integer> map) {
        this.map = map;
    }

    public void updateMapFrecuencias(Ocurrencia ocurrencia) {
        Map<Integer, Integer> newMap = ocurrencia.getMapFrecuencias();
        for (Integer i : newMap.keySet()) {
            Integer frecuencia = this.map.putIfAbsent(i, newMap.get(i));
            if (frecuencia != null) {
                this.map.put(i, frecuencia + newMap.get(i));
            }
        }
    }

    public Set<Integer> getIntDocumentos() {
        return map.keySet();
    }

    /**
     * Incrementa la frecuencia global y local de un token.
     *
     * @param indice El índice de la ruta en LRU
     */
    void incrementarFrecuencia(int indice) {
        incrementarFrecuenciaGlobal();
        incrementarFrecuenciaLocal(indice);
    }

    /**
     * Actualiza la frecuencia global del token.
     */
    void incrementarFrecuenciaGlobal() {
        frecuenciaGlobal++;
    }

    /**
     * Actualiza la frecuencia local del token. Si no existía una ocurrencia del token, se inserta.
     *
     * @param indice El índice de la ruta en LRU
     */
    void incrementarFrecuenciaLocal(int indice) {
        if (map.containsKey(indice)) {
            map.put(indice, map.get(indice) + 1);
        } else {
            map.put(indice, 1);
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

    public void setFrecuenciaGlobal(Integer frecuenciaGlobal) {
        this.frecuenciaGlobal = frecuenciaGlobal;
    }

    /**
     * Obtiene la frecuencia de un token en un documento específico, dada su ruta absoluta
     *
     * @param path Ruta absoluta del documento
     * @return La frecuencia local del token dada una ruta absoluta de un documento
     */
    public int getFrecuenciaLocal(String path) {
        int indice = LRU.getRuta(path);
        int frecuencia = 0;
        if (map.containsKey(indice)) {
            frecuencia = map.get(indice);
        }
        return frecuencia;
    }

    /**
     * Obtiene la frecuencia de un token en un documento específico
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

        map.keySet().stream().sorted().forEach(
                k -> sb.append("\n\t").append(map.get(k)).append(" ocurrencia(s) en documento ")
                        .append(k).append(", correspondiente a la ruta: ").append(LRU.getRuta(k)));
        return sb.toString();
    }

    /**
     * Devuelve la representación en cadena de la ocurrencia.
     *
     * @return Cadena con la información de la ocurrencia.
     */
    @Override
    public String toString() {
        return frecuenciaGlobal + " ocurrencias, en " + map.size() + " documentos:" + getDocumentos();
    }

    @Override
    public int compareTo(Ocurrencia o) {
        return Integer.compare(o.getFrecuenciaGlobal(), this.getFrecuenciaGlobal());
    }
}
