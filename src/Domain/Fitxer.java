package Domain;

import javafx.util.Pair;

public class Fitxer {
    private String filePath;
    private String fileExtension;
    private String fileContent;
    private byte[] imageContent;
    private Pair<Integer, Integer> dimension;

    public Fitxer(){
    }

    public Fitxer(String filePath, String fileExtension, String fileContent) {
        this.filePath = filePath;
        this.fileExtension = fileExtension;
        this.fileContent = fileContent;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public void setImageContent(byte[] imageContent) { this.imageContent = imageContent; }

    public void setDimension(Pair<Integer, Integer> dimension) {this.dimension = dimension;}

    public String getFileContent() {
        return fileContent;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public byte[] getImageContent() { return imageContent;}

    public Pair<Integer, Integer> getDimension() {return dimension;}
}
