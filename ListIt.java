/*
 * ListIt.java: Lista contenido de ficheros textuales
 * (i) Felix R. Rodriguez, EPCC, Universidad de Extremadura, 2009-23
 * http://madiba.unex.es/
 */

import java.io.*;

class ListIt {

    /*
     * Lee un fichero válido cualquiera (directorio o documento). Se considera válido si existe y tiene permisos de lectura.
     *  
     * @param fichero Directorio o documento cuyo contenido se pretende leer,
     * tokenizar y guardar.
     * 
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
            /* Interesante filtrar previamente archivos solo textuales, como los
             * .txt, .java, .c, .cpp, etc., empleando metodos de la clase String:
             * lastIndexOf('.'), substring (posic) y equals(".txt")...
             */
            leerDocumento(fichero);
        }
    }

    /*
     * Lee el contenido de un directorio.
     * 
     * @param directorio Lista de ficheros.
     * 
    */
    public static void leerDirectorio(File directorio) {
        String [] listaFicheros = directorio.list();
        for (int i=0; i<listaFicheros.length; i++){
            System.out.println("-----"+listaFicheros[i]+"-----");
            leerFichero(new File(directorio.getPath()+"/"+listaFicheros[i]));
            System.out.println("---------------");
        }
    }

    /*
     * Lee el contenido de un documento línea a línea.
     * 
     * @param documento Fichero textual.
     * 
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

    public static void main (String [] args) throws Exception {
        if (args.length<1) {
            System.out.println("ERROR. Ejecutar: >java ListIt nombre_archivo");
            return;
        }
        leerFichero(new File(args[0]));
    }
}
