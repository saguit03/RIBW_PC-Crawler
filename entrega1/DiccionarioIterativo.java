package entrega1;

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
        Queue<File> frontera = new LinkedList<>();
        frontera.add(original);

        while (!frontera.isEmpty()) {
            File fichero = frontera.poll();

            if (fichero == null) {
                continue;
            }

            System.out.println("Procesando fichero " + fichero.getAbsolutePath());
            if (fichero.isDirectory()) {
                String[] listaFicheros = fichero.list();
                for (int i = 0; i < listaFicheros.length; i++) {
                    frontera.add(new File(fichero.getAbsolutePath() + "\\" + listaFicheros[i]));
                }
            } else {
                try {
                    tokenizarFichero(map, fichero);
                } catch (Exception e) {
                    System.out.println("ERROR al tokenizar el fichero");
                }
            }
        }
    }
}
