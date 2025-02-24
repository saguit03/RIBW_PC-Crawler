package entrega1;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface Diccionario {

    /**
     * Muestra todos los tokens del diccionario y sus ocurrencias
     */
    void mostrarDiccionario();

    /**
     * Tokeniza un fichero de texto y almacena los resultados en un mapa.
     *
     * @param fichEntrada Fichero de texto a tokenizar.
     * @throws IOException Si no se puede leer el fichero.
     */
    void tokenizarFichero(File fichEntrada) throws IOException;

    /**
     * Lee un fichero válido cualquiera. Se considera válido si existe y tiene permisos de lectura.
     *
     * @param fichero Directorio o documento cuyo contenido se pretende leer, tokenizar y guardar.
     */
    void leerFichero(File fichero);


    /**
     * Busca el token indicado y devuelve su frecuencia. En caso de no encontrarse, devuelve 0.
     *
     * @param token Término del cual se pretende obtener su frecuencia.
     * @return La frecuencia del término.
     */
    int buscarToken(String token);

    /**
     * Obtiene el mapa de tokens y ocurrencias
     *
     * @return El mapa de tokens y ocurrencias
     */
    Map<String, Ocurrencia> getMap();

    void setMap(Map<String, Ocurrencia> map);
}
