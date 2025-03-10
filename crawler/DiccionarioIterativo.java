package crawler;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

public class DiccionarioIterativo extends DiccionarioBase {
    /**
     * Lee un fichero válido cualquiera. Se considera válido si existe y tiene permisos de lectura.
     *
     * @param original Directorio o documento cuyo contenido se pretende leer, tokenizar y guardar.
     */
    @Override
    public void leerFichero(File original) {
        if (!original.exists() || !original.canRead()) {
            System.out.println("ERROR. No puedo leer " + original);
            return;
        }
        Queue<String> frontera = new LinkedList<>();
        frontera.add(original.getAbsolutePath());

        while (!frontera.isEmpty()) {
            String currentFile = frontera.poll();
            if (currentFile == null) {
                continue;
            }
            File fichero = new File(currentFile);

            System.out.println("Procesando fichero " + fichero.getAbsolutePath());
            if (fichero.isDirectory()) {
                String[] listaFicheros = fichero.list();
                for (int i = 0; i < listaFicheros.length; i++) {
                    frontera.offer(fichero.getAbsolutePath() + "\\" + listaFicheros[i]);
                }
            } else {
                try {
                    tokenizarFichero(fichero);
                } catch (Exception e) {
                    System.out.println("ERROR al tokenizar el fichero");
                }
            }
        }
    }
}
