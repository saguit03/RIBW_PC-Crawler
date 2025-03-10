package thesauro;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class Sinonimo implements Serializable {
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

    public void addSinonimo(String sinonimo) {

        map.put(sinonimo.replaceAll("\\([^)]*\\)", "").trim(), sinonimo.contains("(fig.)"));
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

    /**
     * Devuelve la representación en cadena del Sinónimo.
     *
     * @return Cadena con la información del Sinónimo.
     */
    @Override
    public String toString() {
        return getSinonimos();
    }

}
