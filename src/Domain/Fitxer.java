package Domain;

import java.io.File;

public class Fitxer {
    private File file;
    private byte[] content;

    /**
     * construct a Fitxer without parameter
     */
    public Fitxer(){
    }

    /**
     * construct a Fitxer with parameters
     * @param file a file
     * @param content the content of file
     */
    public Fitxer(File file, byte[] content) {
        this.file = file;
        this.content = content;
    }

    /**
     * set attribute file of Fitxer
     * @param file file we want to set
     */
    public void setFile(File file) { this.file = file; }

    /**
     * set attibute content of Fitxer
     * @param content content we want to set
     */
    public void setContent(byte[] content) {
        this.content = content;
    }

    /**
     * get attribute file of Fitxer
     * @return file of Fitxer
     */
    public File getFile() {return file;}

    /**
     * get attribute content of Fitxer
     * @return content of Fitxer
     */
    public byte[] getContent() { return content;}

}
