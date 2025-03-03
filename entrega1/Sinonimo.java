//package entrega1;
//
//import java.io.File;
//import java.io.Serializable;
//import java.util.Map;
//import java.util.TreeMap;
//
//public class Sinonimo implements Serializable {
//    /**
//     * Frecuencia de término por documento.
//     * String: Sinónimo
//     * Integer: Cercanía al token original
//     */
//    Map<String, Boolean> map = new TreeMap<String, Boolean>();
//
//    /**
//     * Constructor de Ocurrencia
//     *
//     */
//    Sinonimo(String sinonimo, boolean fig) {
//        map.put(sinonimo, fig);
//    }
//
//
//    public String getSinonimos() {
//        StringBuilder sb = new StringBuilder();
//        //TODO Fig: map.get(k)
//        map.keySet().stream().sorted().forEach(k -> sb.append("\n\t").append(k));
//        return sb.toString();
//    }
//
//    /**
//     * Devuelve la representación en cadena de la ocurrencia.
//     *
//     * @return Cadena con la información de la ocurrencia.
//     */
//    @Override
//    public String toString() {
//        //TODO
//        return getSinonimos();
//    }
//
//}
