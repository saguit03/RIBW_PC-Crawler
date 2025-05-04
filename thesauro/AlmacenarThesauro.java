package thesauro;

/*
 * RIBW - PC Crawler
 * * Guillén Torrado, Sara
 * * Mocinha Sánchez, Daniel
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

public class AlmacenarThesauro {
    /**
     * Imprime el contenido de un objeto serializable previamente guardado en un fichero.
     *
     * @param fichero Fichero que contiene el objeto serializable.
     */
    public static Map<String, Sinonimo> cargarThesauro(String fichero) {
        Map<String, Sinonimo> map = null;
        try {
            FileInputStream fis = new FileInputStream(fichero);
            ObjectInputStream ois = new ObjectInputStream(fis);
            map = (Map<String, Sinonimo>) ois.readObject();
            ois.close();
            fis.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return map;
    }

    /**
     * Guarda un objeto serializable en un fichero.
     *
     * @param fichero Fichero donde se guardará el objeto.
     * @param map     Mapa serializable.
     */
    public static void guardarThesauro(Map<String, Sinonimo> map, String fichero) throws Exception {
        FileOutputStream fos = new FileOutputStream(fichero);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(map);
        oos.close();
        fos.close();
    }
}
