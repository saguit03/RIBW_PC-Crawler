package crawler;

import java.io.File;

public class DiccionarioRecursivo extends DiccionarioBase {
    /**
     * Lee un fichero válido cualquiera. Se considera válido si existe y tiene permisos de lectura.
     *
     * @param fichero Directorio o documento cuyo contenido se pretende leer, tokenizar y guardar.
     */
    @Override
    public void leerFichero(File fichero) {
        if (!fichero.exists() || !fichero.canRead()) {
            System.out.println("ERROR. No puedo leer " + fichero);
            return;
        }
        if (fichero.isDirectory()) {
            leerDirectorio(fichero);
        } else {
            try {
                tokenizarFichero(fichero);
            } catch (Exception e) {
                System.out.println("ERROR al tokenizar el fichero");
            }
        }
    }

    /**
     * Lee el contenido de un directorio.
     *
     * @param directorio Lista de ficheros.
     */
    public void leerDirectorio(File directorio) {
        String[] listaFicheros = directorio.list();
        for (int i = 0; i < listaFicheros.length; i++) {
            System.out.println(listaFicheros[i] + "-----");
            leerFichero(new File(directorio.getPath() + "/" + listaFicheros[i]));
        }
    }
}
