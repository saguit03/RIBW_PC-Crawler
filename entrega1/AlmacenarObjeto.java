package entrega1;

import java.io.*;
import java.security.Key;
import java.util.*;
import java.util.Map;
import java.util.TreeMap;

public class AlmacenarObjeto {
    /**
     * Imprime el contenido de un objeto serializable previamente guardado en un fichero.
     * @param fichero Fichero que contiene el objeto serializable.
     */
    public static void cargarObjeto(String fichero){
        try {
            FileInputStream fis = new FileInputStream(fichero);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Map <String, Ocurrencia> map = (Map <String, Ocurrencia>) ois.readObject();
            System.out.println(map.toString());
        }
        catch (Exception e) { System.out.println(e); }
    }

    /**
     * Guarda un objeto serializable en un fichero.
     * @param fichero Fichero donde se guardará el objeto.
     * @param map Mapa serializable.
     */
    public static void salvarObjeto(Map <String, Ocurrencia> map, String fichero) throws Exception {
        FileOutputStream fos = new FileOutputStream(fichero);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(map);
        oos.close();
        fos.close();
    }
}
