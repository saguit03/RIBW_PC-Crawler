package entrega1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Diccionario {
    public static Map<String, Ocurrencia> map = new TreeMap<String, Ocurrencia>();

    /**
     * Tokeniza un fichero de texto y devuelve un mapa con las palabras y su frecuencia.
     * @param fichEntrada Fichero de texto a tokenizar.
     * @param map Mapa ya inicializado con las palabras y su frecuencia.
     * @throws IOException Si no se puede leer el fichero.
     */
    public static void tokenizarFichero(Map <String, Ocurrencia> map, File fichEntrada) throws IOException {
        BufferedReader br = new BufferedReader (new FileReader (fichEntrada));
        String linea;

        while ( (linea = br.readLine () ) != null) {
            StringTokenizer st = new StringTokenizer (linea, " ,.:;(){}¡!°\"¿?\t'%/\\|[]<=>&#+*$-¨^~\n@");
            while (st.hasMoreTokens () ) {
                String s = st.nextToken();
                Object o = map.get(s);
                if (o == null){
                    map.put (s, new Ocurrencia(fichEntrada));
                }
                else {
                    Ocurrencia ocurrencia = (Ocurrencia) o;
                    ocurrencia.incrementarFrecuencia(fichEntrada);

                }
            }
        }
        br.close ();
    }

    /**
     * Lee un fichero válido cualquiera (directorio o documento). Se considera válido si existe y tiene permisos de lectura.
     * @param fichero Directorio o documento cuyo contenido se pretende leer, tokenizar y guardar.
     */
    public static void leerFichero(File fichero){
        if (!fichero.exists() || !fichero.canRead()) {
            System.out.println("ERROR. No puedo leer " + fichero);
            return;
        }
        if (fichero.isDirectory()) {
            leerDirectorio(fichero);
        }
        else {
            try {
                tokenizarFichero(map, fichero);
            } catch (Exception e) {
                System.out.println("ERROR al tokenizar el fichero");
            }
        }
    }

    /**
     * Lee el contenido de un directorio.
     * @param directorio Lista de ficheros.
     */
    public static void leerDirectorio(File directorio) {
        String [] listaFicheros = directorio.list();
        for (int i=0; i<listaFicheros.length; i++){
            System.out.println( listaFicheros[i]+"-----");
            leerFichero(new File(directorio.getPath()+"/"+listaFicheros[i]));
        }
    }

    public static int buscarToken(String token){
        if(map.containsKey(token)){
            Ocurrencia ocurrencia = map.get(token);
            return ocurrencia.getTF();
        } else {
            return 0;
        }
    }

}
