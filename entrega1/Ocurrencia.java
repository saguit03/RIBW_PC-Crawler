package entrega1;

import java.io.File;
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class Ocurrencia implements Serializable {
        /**
         * Frecuencia de término global
         */
        Integer TF;

        /**
         * Frecuencia de término por documento.
         * String: Nombre de fichero
         * Integer: Frecuencia del término local
         */
        Map<String, Integer> map = new TreeMap<String, Integer>();

        int getTF(){return TF;}

        void incrementarFrecuencia(File fichero){
            incTF(fichero);
            incLTF(fichero);
        }

        void incTF(File fichero){
            TF++;
        }

        void incLTF(File fichero){
            String path = fichero.getAbsolutePath();
            if(map.containsKey(path)){
                map.put(path, map.get(path)+1);
            } else {
                map.put(path, 1);
            }
        }

        Ocurrencia(File fichero){
            TF = 1;
            map.put(fichero.getAbsolutePath(), 1);
        }


        @Override
        public String toString() {
            return "Ocurrencia{" +
                    "TF=" + TF +
                    ", map=" + map +
                    '}';
        }
}
