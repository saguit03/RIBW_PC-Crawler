/*
 * SalvarObjeto.java: Guarda un objeto serializable en un fichero
 * (i) Felix R. Rguez., EPCC, Universidad de Extremadura, 2009-23
 * http://madiba.unex.es/
 */

import java.io.*;
import java.util.*;

public class SalvarObjeto {

    /**
     * Guarda un objeto serializable en un fichero.
     * @param fichero Fichero donde se guardará el objeto.
     * @param h Objeto serializable.
     */
    public static void salvarObjeto(String fichero, Hashtable <String, Object> h) {
        /*
         * en el caso de nuestro PC-Crawler ha de utilizarse la estructura Heap
         * Map <String, Integer> map
         */
        try {
            FileOutputStream fos = new FileOutputStream(fichero);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(h);
        }
        catch (Exception e) { System.out.println(e); }
    }

    public static void main (String args[]) {
        Hashtable <String, Object> h = new Hashtable <String, Object> ();
        h.put("String","Luis Rodriguez Duran");
        h.put("Integer",new Integer(23));
        h.put("Double",new Double(0.96));
        salvarObjeto("diccionario.ser", h);
    }
}
