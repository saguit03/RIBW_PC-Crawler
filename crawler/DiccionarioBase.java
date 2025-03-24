package crawler;

/*
 * RIBW - PC Crawler
 * * Guillén Torrado, Sara
 * * Mocinha Sánchez, Daniel
 */

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.epub.EpubParser;
import org.apache.tika.parser.microsoft.ooxml.OOXMLParser;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.parser.xml.XMLParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;
import thesauro.Thesauro;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import static crawler.TipoTika.obtenerTipoTika;

public class DiccionarioBase implements Diccionario {

    private static final Thesauro thesauro;
    private static Map<String, Ocurrencia> map = new TreeMap<String, Ocurrencia>();

    static {
        try {
            thesauro = new Thesauro();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Muestra todos los tokens del diccionario y sus ocurrencias
     */
    @Override
    public void mostrarDiccionario() {
        System.out.println("************ DICCIONARIO ************");
        map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(entrada -> {
                    String token = entrada.getKey();
                    Ocurrencia ocurrencia = entrada.getValue();
                    System.out.println(token + " -> " + ocurrencia.toString());
                    System.out.println("************");
                });
        System.out.println("Hay un total de " + map.size() + " términos");
    }

    /**
     * Tokeniza un fichero y almacena los resultados en un mapa.
     *
     * @param fichEntrada Fichero de texto a tokenizar.
     * @throws IOException Si no se puede leer el fichero.
     */
    @Override
    public void tokenizarFichero(File fichEntrada) throws IOException, TikaException, SAXException {
        TipoTika tipo = obtenerTipoTika(fichEntrada.getPath());
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        FileInputStream inputstream = new FileInputStream(fichEntrada);
        ParseContext pcontext = new ParseContext();
        String textoPlano;

        int indice = LRU.getOrAddRuta(fichEntrada.getAbsolutePath());
        switch (tipo) {
            case PDF:
                PDFParser pdfparser = new PDFParser();
                pdfparser.parse(inputstream, handler, metadata, pcontext);
                textoPlano = handler.toString();
                break;
            case DOCX:
            case PPTX:
            case XLSX:
                OOXMLParser msofficeparser = new OOXMLParser();
                msofficeparser.parse(inputstream, handler, metadata, pcontext);
                textoPlano = handler.toString();
                break;
            case XML:
                XMLParser xmlparser = new XMLParser();
                xmlparser.parse(inputstream, handler, metadata, pcontext);
                textoPlano = handler.toString();
                break;
            case EPUB:
                EpubParser epubParser = new EpubParser();
                epubParser.parse(inputstream, handler, metadata, pcontext);
                textoPlano = handler.toString();
                break;
            case NINGUNO:
            default:
                Tika tika = new Tika();
                textoPlano = tika.parseToString(fichEntrada);
        }
        tokenizarTexto(textoPlano, indice);
    }

    /**
     * Tokeniza una cadena texto
     *
     * @param texto  El texto a tokenizar
     * @param indice El índice del documento al que pertenece el texto
     */
    private void tokenizarTexto(String texto, int indice) {
        StringTokenizer st = new StringTokenizer(texto, " ,.:;(){}¡!°\"¿?\t'%/\\|[]<=>&#+*$-¨^~\n@");
        while (st.hasMoreTokens()) {
            String s = st.nextToken();
            if (Thesauro.buscarToken(s.toLowerCase())) {
                Object o = map.get(s);
                if (o == null) {
                    map.put(s, new Ocurrencia(indice));
                } else {
                    Ocurrencia ocurrencia = (Ocurrencia) o;
                    ocurrencia.incrementarFrecuencia(indice);
                }
            }
        }
    }

    /**
     * Lee un fichero válido cualquiera. Se considera válido si existe y tiene permisos de lectura.
     *
     * @param fichero Directorio o documento cuyo contenido se pretende leer, tokenizar y guardar.
     */
    @Override
    public void leerFichero(File fichero) {
        System.out.println("Debe especificar si desea utilizar la implementación iterativa o recursiva.");
    }

    /**
     * Busca el token indicado y devuelve su frecuencia global. En caso de no encontrarse, devuelve 0.
     *
     * @param token Término del cual se pretende obtener su frecuencia.
     * @return La frecuencia global del término.
     */
    @Override
    public String buscarToken(String token) {
        if (map.containsKey(token)) {
            Ocurrencia ocurrencia = map.get(token);
            return ocurrencia.toString();
        } else {
            return "";
        }
    }

    /**
     * Obtiene el mapa de términos y ocurrencias
     *
     * @return El mapa de términos y ocurrencias
     */
    @Override
    public Map<String, Ocurrencia> getMap() {
        return map;
    }

    /**
     * Actualiza el mapa de términos y ocurrencias
     *
     * @param map El nuevo mapa de términos y ocurrencias
     */
    @Override
    public void setMap(Map<String, Ocurrencia> map) {
        DiccionarioBase.map = map;
    }

}
