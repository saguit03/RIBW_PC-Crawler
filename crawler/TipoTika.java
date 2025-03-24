package crawler;

/*
 * RIBW - PC Crawler
 * * Guillén Torrado, Sara
 * * Mocinha Sánchez, Daniel
 */

public enum TipoTika {
    NINGUNO(""),
    PDF(".pdf"),
    DOCX(".docx"),
    PPTX(".pptx"),
    XLSX(".xlsx"),
    XML(".xml"),
    EPUB(".epub");

    public static final int TOTAL_OPCIONES = values().length;
    private final String extension;

    TipoTika(String extension) {
        this.extension = extension;
    }

    public static TipoTika obtenerTipoTika(String nombreArchivo) {
        if (nombreArchivo == null || !nombreArchivo.contains(".")) {
            return TipoTika.NINGUNO;
        }
        String extension = nombreArchivo.substring(nombreArchivo.lastIndexOf(".")).toLowerCase();
        switch (extension) {
            case ".pdf":
                return TipoTika.PDF;
            case ".docx":
                return TipoTika.DOCX;
            case ".pptx":
                return TipoTika.PPTX;
            case ".xlsx":
                return TipoTika.XLSX;
            case ".xml":
                return TipoTika.XML;
            case ".epub":
                return TipoTika.EPUB;
            default:
                return TipoTika.NINGUNO;
        }
    }

    public String getExtension() {
        return extension;
    }
}

