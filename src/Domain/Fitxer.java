package Domain;

import java.io.File;

public class Fitxer {
    private File file;
    private byte[] content;

    public Fitxer(){
    }

    public Fitxer(File file, byte[] content) {
        this.file = file;
        this.content = content;
    }

    public void setFile(File file) { this.file = file; }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public File getFile() {return file;}

    public byte[] getContent() { return content;}

}
