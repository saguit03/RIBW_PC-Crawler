package entrega1.sesion1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Map;
import java.util.TreeMap;

public class DiccionarioInteger {
    public static Map<String, Integer> map = new TreeMap<String, Integer>();

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
                FichContPalabras.tokenizarFichero(map, fichero.getAbsolutePath());
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

    /**
     * Lee el contenido de un documento línea a línea.
     * @param documento Fichero textual.
     */
    public static void leerDocumento(File documento) {
        try {
            FileReader fr = new FileReader(documento);
            BufferedReader br = new BufferedReader(fr);
            String linea;
            while ((linea=br.readLine()) != null)
                System.out.println(linea);
            fr.close();
            br.close();
        } catch (Exception e) {
            System.out.println("ERROR al leer el documento");
        }
    }

}
