package entrega1;

import java.io.File;
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class Ocurrencia implements Serializable {

        Integer frecuenciaGlobal;

        /**
         * Frecuencia de término por documento.
         * String: Nombre de fichero
         * Integer: Frecuencia del término local
         */
        Map<String, Integer> map = new TreeMap<String, Integer>();

        /**
         * Constructor de  Ocurrencia
         * @param fichero Documento de texto al cual pertenece el token.
         */
        Ocurrencia(File fichero){
            frecuenciaGlobal = 1;
            map.put(fichero.getAbsolutePath(), 1);
        }

        /**
         * Obtiene la frecuencia global de un token.
         * @return Frecuencia del término.
         */
        int getFrecuenciaGlobal(){
            return frecuenciaGlobal;
        }

        /**
         * Incrementa la frecuencia global y local de un token.
         * @param fichero Documento de texto al cual pertenece el token.
         */
        void incrementarFrecuencia(File fichero){
            incFrecuenciaGlobal();
            incFrecuenciaLocal(fichero);
        }

        /**
         * Actualiza la frecuencia global del token.
         */
        void incFrecuenciaGlobal(){
            frecuenciaGlobal++;
        }

        /**
         * Actualiza la frecuencia local del token. Si no existía una ocurrencia del token, se inserta.
         * @param fichero Documento de texto al cual pertenece el token.
         */
        void incFrecuenciaLocal(File fichero){
            String path = fichero.getAbsolutePath();
            if(map.containsKey(path)){
                map.put(path, map.get(path)+1);
            } else {
                map.put(path, 1);
            }
        }

        @Override
        public String toString() {
            return "Ocurrencia{\n" +
                    "\tFrecuenciaGlobal= " + frecuenciaGlobal + ",\n" +
                    "\tDocumento= " + map + "\n" +
                    "}";
        }
}
