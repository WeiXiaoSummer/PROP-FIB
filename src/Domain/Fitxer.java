package Domain;

import javafx.util.Pair;

import java.io.File;

public class Fitxer {
    private File file;
    private byte[] content;
    private Pair<Integer, Integer> dimension;

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

    public void setDimension(Pair<Integer, Integer> dimension) { this.dimension = dimension; }

    public File getFile() {return file;}

    public byte[] getContent() { return content;}

    public Pair<Integer, Integer> getDimension() { return dimension; }
}
