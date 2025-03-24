package crawler;

/*
 * RIBW - PC Crawler
 * * Guillén Torrado, Sara
 * * Mocinha Sánchez, Daniel
 */

import java.util.ArrayList;
import java.util.List;

/**
 * LRU - Localizador de Recursos Uniforme (en inglés URL, Uniform Resource Locator)
 */
public class LRU {
    static List<String> rutas = new ArrayList<String>();

    /**
     * Obtiene el índice de una ruta
     * Si la ruta no existe en la lista, se añade
     *
     * @param ruta La ruta a obtener
     * @return El índice de la ruta en la lista
     */
    public static int getOrAddRuta(String ruta) {
        boolean found = false;
        int indice = 0;
        while (!found && indice < rutas.size()) {
            if (rutas.get(indice).equals(ruta)) {
                found = true;
            }
            indice++;
        }
        if (!found) {
            rutas.add(ruta);
        }
        return indice;
    }

    /**
     * Obtiene el índice de una ruta
     *
     * @param ruta La ruta a obtener
     * @return El índice de la ruta, o -1 si no está en la lista
     */
    public static int getRuta(String ruta) {
        boolean found = false;
        int indice = 0;
        while (!found && indice < rutas.size()) {
            if (rutas.get(indice).equals(ruta)) {
                found = true;
            }
            indice++;
        }
        if (!found) {
            indice = -1;
        }
        return indice;
    }

    /**
     * Dado un índice, devuelve su ruta
     *
     * @param indice La posición de la ruta en la lista
     * @return La ruta
     */
    public static String getRuta(int indice) {
        try {
            return rutas.get(indice);
        } catch (IndexOutOfBoundsException e) {
            return "ÍNDICE INVÁLIDO";
        }
    }

    public static List<String> getList() {
        return rutas;
    }

    public static void setList(List<String> list) {
        rutas = list;
    }

    /**
     * Muestra todas las rutas con su índice
     */
    public static void mostrarLRU() {
        System.out.println("************ LRU ************");
        for (int indice = 0; indice < rutas.size(); indice++) {
            System.out.println("Documento " + indice + ": " + rutas.get(indice));
        }
    }

}
