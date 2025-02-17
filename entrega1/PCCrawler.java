package entrega1;

import java.io.File;
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class PCCrawler {
    public static void main (String [] args) throws Exception {
        if (args.length<1) {
            System.out.println("ERROR. Ejecutar: >java ListIt nombre_archivo");
            return;
        }
        File fichero = new File(args[0]);
        Diccionario.leerFichero(fichero);
        AlmacenarObjeto.salvarObjeto(Diccionario.map, "diccionario.ser");
        AlmacenarObjeto.cargarObjeto("diccionario.ser");

        System.out.println(Diccionario.buscarToken("gatos"));

    }

}
