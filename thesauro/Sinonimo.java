package thesauro;

/*
 * RIBW - PC Crawler
 * * Guillén Torrado, Sara
 * * Mocinha Sánchez, Daniel
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Sinonimo implements Serializable, Comparable<Sinonimo> {
    /**
     * Frecuencia de término por documento.
     * String: Sinónimo
     * Boolean: True si es figurativo, false en caso contrario
     */
    Map<String, Boolean> map = new TreeMap<String, Boolean>();

    /**
     * Constructor de Sinónimo
     */
    Sinonimo() {
        map = new TreeMap<String, Boolean>();
    }

    /**
     * Constructor de Sinónimo con una lista de sinónimos
     */
    Sinonimo(String token, List<String> listaSinonimos) {
        addSinonimos(token, listaSinonimos);
    }

    /**
     * Elimina los paréntesis y su interior de los sinónimos
     *
     * @param cadena La cadena a procesar
     * @return La cadena sin los caracteres entre paréntesis
     */
    public static String quitarParentesis(String cadena) {
        return cadena.replaceAll("\\([^)]*\\)", "").trim();
    }

    /**
     * Añade un sinónimo al mapa de sinónimos
     *
     * @param sinonimo El sinónimo a añadir
     */
    public void addSinonimo(String sinonimo) {
        map.put(quitarParentesis(sinonimo), sinonimo.contains("(fig.)"));
    }

    /**
     * Añade una lista de sinónimos al mapa de sinónimos,
     * eliminando el token al que pertenece el mapa, si estuviera en la lista
     *
     * @param token          El token al que pertenecen los sinónimos
     * @param listaSinonimos La lista de sinónimos a añadir
     */
    public void addSinonimos(String token, List<String> listaSinonimos) {
        for (String sinonimo : listaSinonimos) {
            addSinonimo(sinonimo);
        }
        removeSinonimo(token); // Para no incluir al propio token como sinónimo
    }

    /**
     * Elimina un sinónimo del mapa de sinónimos
     *
     * @param sinonimo El sinónimo a eliminar
     */
    public void removeSinonimo(String sinonimo) {
        map.remove(quitarParentesis(sinonimo));
    }

    /**
     * Busca un sinónimo
     *
     * @param sinonimo El token a buscar
     * @return true si es un sinónimo, false en caso contrario
     */
    public boolean buscarSinonimo(String sinonimo) {
        return map.containsKey(sinonimo);
    }

    /**
     * Comprueba si el sinónimo es figurativo
     *
     * @param sinonimo El token a buscar
     * @return true si es figurativo, false en caso contrario
     */
    public boolean esFigurativo(String sinonimo) {
        return map.get(sinonimo);
    }

    /**
     * Busca un sinónimo y lo devuelve si existe en el mapa
     *
     * @param sinonimo El token a buscar
     * @return El string del sinónimo si existe, una cadena vacía en caso contrario
     */
    protected String getSinonimo(String sinonimo) {
        if (map.containsKey(sinonimo)) {
            return sinonimo;
        } else return "";
    }

    /**
     * Escribe todos los sinónimos del mapa
     *
     * @return Todos los sinónimos del mapa
     */
    public String getSinonimos() {
        StringBuilder sb = new StringBuilder();
        map.keySet().stream().sorted().forEach(k -> sb.append(getSinonimo(k)).append("; "));
        return sb.toString();
    }

    public List<String> getListaSinonimos() {
        return new ArrayList<>(map.keySet());
    }

    /**
     * Obtiene el mapa actual
     *
     * @return El mapa actual
     */
    public Map<String, Boolean> getMap() {
        return map;
    }

    /**
     * Establece como mapa el nuevo mapa pasado por parámetro
     *
     * @param map El nuevo mapa
     */
    public void setMap(Map<String, Boolean> map) {
        this.map = map;
    }

    /**
     * Obtiene el tamaño del mapa actual
     *
     * @return El tamaño del mapa actual
     */
    public int getSize() {
        return map.size();
    }

    /**
     * Devuelve la representación en cadena del Sinónimo.
     *
     * @return Cadena con la información del Sinónimo.
     */
    @Override
    public String toString() {
        return getSinonimos();
    }

    /**
     * Compara dos listas de sinónimos en función de su tamaño
     *
     * @param s the object to be compared.
     * @return
     */
    @Override
    public int compareTo(Sinonimo s) {
        return Integer.compare(s.getMap().size(), this.getMap().size());
    }
}
