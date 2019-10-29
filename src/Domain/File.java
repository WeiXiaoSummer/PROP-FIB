package Domain;

public class File {
    private String filePath;
    private String fileExtension;
    private String fileContent;

    public File(){}

    public File (String filePath, String fileExtension, String fileContent) {
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

    public String getFileContent() {
        return fileContent;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileExtension() {
        return fileExtension;
    }
}
