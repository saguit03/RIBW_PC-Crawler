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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static crawler.TipoTika.obtenerTipoTika;


public class TikaExample {

    public static String procesarTipoTika(String fichero) throws IOException, TikaException, SAXException {
        TipoTika tipo = obtenerTipoTika(fichero);
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        FileInputStream inputstream = new FileInputStream(new File(fichero));
        ParseContext pcontext = new ParseContext();
        String textoPlano;

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
                //Xml parser
                XMLParser xmlparser = new XMLParser();
                xmlparser.parse(inputstream, handler, metadata, pcontext);
                textoPlano = handler.toString();
                break;
            case EPUB:
                //parsing the document using PDF parser
                EpubParser epubParser = new EpubParser();
                epubParser.parse(inputstream, handler, metadata, pcontext);
                textoPlano = handler.toString();
                break;
            case NINGUNO:
            default:
                Tika tika = new Tika();
                File file = new File(fichero);
                textoPlano = tika.parseToString(file);
        }
        return textoPlano;
    }

}
