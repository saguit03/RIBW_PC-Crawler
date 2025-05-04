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
import java.util.*;

import static crawler.TipoTika.obtenerTipoTika;

public class DiccionarioBase implements Diccionario {

    private static final Thesauro thesauro;
    private static final int ngramaSize = 2;
    private static final int threshold = 3; // Debe ser mayor que 0
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
     * Muestra todos los bigramas del diccionario y sus ocurrencias
     */
    @Override
    public void mostrarBigramas() {
        System.out.println("************ DICCIONARIO ************");
        map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(entrada -> {
                    String token = entrada.getKey();
                    if (token.contains(" ")) {
                        Ocurrencia ocurrencia = entrada.getValue();
                        System.out.println(token + " -> " + ocurrencia.toString());
                        System.out.println("************");
                    }
                });
        System.out.println("Hay un total de " + map.size() + " bigramas");
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
        StringTokenizer st = new StringTokenizer(texto, " ,.:;(){}¡!°\"¿?\t\r'%/\\|[]<=>&#+*$-¨^~\n@");
        List<String> bigrama = new ArrayList<>();
        String token, bitoken;
        int cont = 0;
        while (st.hasMoreTokens()) {
            if (cont > threshold) {
                bigrama.clear();
                cont = 0;
            }

            token = st.nextToken();
            if (Thesauro.buscarToken(token.toLowerCase())) {
                bigrama.add(token);
                crearOcurrencia(token, indice);
                if (bigrama.size() == ngramaSize) {
                    bitoken = bigrama.get(0) + " " + bigrama.get(1);
                    crearOcurrencia(bitoken, indice);
                    bigrama.remove(0);
                    cont--;
                }
                cont--;
            }

            cont++;
        }
    }

    /**
     * Crea o actualiza la ocurrencia de la cadena ngrama
     *
     * @param ngrama Cadena de texto (unigrama, bigrama...)
     * @param indice El índice del documento al que pertenece el texto
     */
    private void crearOcurrencia(String ngrama, int indice) {
        Object o = map.get(ngrama);
        if (o == null) {
            map.put(ngrama, new Ocurrencia(indice));
        } else {
            Ocurrencia ocurrencia = (Ocurrencia) o;
            ocurrencia.incrementarFrecuencia(indice);
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

    @Override
    public Set<String> buscarMultitermino(String multitermino) {
        StringTokenizer st = new StringTokenizer(multitermino, " ");
        String token;
        Map<String, Ocurrencia> documentoFrecuencia = new TreeMap<>();
        while (st.hasMoreTokens()) {
            token = st.nextToken();
            actualizarFrecuenciaMultitermino(token, documentoFrecuencia);
        }
        return getDocumentosMultitermino(documentoFrecuencia);
    }

    private void actualizarFrecuenciaMultitermino(String token, Map<String, Ocurrencia> documentoFrecuencia) {
        if (map.containsKey(token)) {
            Ocurrencia ocurrencia = map.get(token);
            documentoFrecuencia.put(token, ocurrencia);
        }
    }

    private void actualizarFrecuenciaMultitermino(String token, Map<String, Ocurrencia> documentoFrecuencia, String sinonimo) {
        if (map.containsKey(sinonimo)) {
            Ocurrencia ocurrencia = map.get(sinonimo);
            if (documentoFrecuencia.containsKey(token)) {
                ocurrencia.updateMapFrecuencias(documentoFrecuencia.get(token));
            }
            documentoFrecuencia.put(token, ocurrencia);
        }
    }

    private Set<String> getDocumentosMultitermino(Map<String, Ocurrencia> documentoFrecuencia) {
        Map<Integer, Integer> multitermino = new TreeMap<>();

        for (Ocurrencia ocurrencia : documentoFrecuencia.values()) {
            for (Integer i : ocurrencia.getIntDocumentos()) {
                if (multitermino.containsKey(i)) {
                    multitermino.put(i, multitermino.get(i) + 1);
                } else {
                    multitermino.put(i, 1);
                }
            }
        }

        Set<String> documentos = new TreeSet<>();
        for (Integer i : multitermino.keySet()) {
            if (multitermino.get(i) >= documentoFrecuencia.size()) {
                documentos.add(LRU.getRuta(i));
            }
        }
        return documentos;
    }

    @Override
    public Set<String> buscarMultiterminoSinonimo(String multitermino) {
        StringTokenizer st = new StringTokenizer(multitermino, " ");
        Map<String, Ocurrencia> documentoFrecuencia = new TreeMap<>();
        List<String> listaSinonimos;
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            actualizarFrecuenciaMultitermino(token, documentoFrecuencia);
            listaSinonimos = Thesauro.getListaSinonimos(token);
            for (String sinonimo : listaSinonimos) {
                actualizarFrecuenciaMultitermino(token, documentoFrecuencia, sinonimo);
            }
        }
        return getDocumentosMultitermino(documentoFrecuencia);
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
