package thesauro;

import java.io.Serializable;
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
     * Constructor de Sinónimo
     */
    Sinonimo(String token, List<String> listaSinonimos) {
        addSinonimos(token, listaSinonimos);
    }

    protected String quitarParentesis(String sinonimo) {
        return sinonimo.replaceAll("\\([^)]*\\)", "").trim();
    }

    public void addSinonimo(String sinonimo) {
        map.put(quitarParentesis(sinonimo), sinonimo.contains("(fig.)"));
    }


    public void addSinonimos(String token, List<String> listaSinonimos) {
        for (String sinonimo : listaSinonimos) {
            addSinonimo(sinonimo);
        }
        removeSinonimo(token); // Para no incluir al propio token como sinónimo
    }

    public void removeSinonimo(String sinonimo) {
        map.remove(quitarParentesis(sinonimo));
    }

    public boolean buscarSinonimo(String sinonimo) {
        return map.containsKey(sinonimo);
    }

    public boolean esFigurativo(String sinonimo) {
        return map.get(sinonimo);
    }

    protected String getSinonimoFigurativo(String sinonimo) {
        if (map.containsKey(sinonimo)) {
            return sinonimo;
        } else return "";
    }


    public String getSinonimos() {
        StringBuilder sb = new StringBuilder();
        map.keySet().stream().sorted().forEach(k -> sb.append(getSinonimoFigurativo(k)).append("; "));
        return sb.toString();
    }

    public Map<String, Boolean> getMap() {
        return map;
    }

    public int getSize() {
        return map.size();
    }

    public void setMap(Map<String, Boolean> map) {
        this.map = map;
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

    @Override
    public int compareTo(Sinonimo s) {
        return Integer.compare(s.getMap().size(), this.getMap().size());
    }
}
