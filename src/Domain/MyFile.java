package Domain;

import java.io.File;

public class MyFile {
        private File file;
        private String fileContent;

        public MyFile(){}

        public MyFile (String filePath, String fileContent) {
            this.file = new File(filePath);
            this.fileContent = fileContent;
        }

        public File getFile() {
            return file;
        }

        public void setFile(File file) {
            this.file = file;
        }

        public void setFileContent(String fileContent) {
            this.fileContent = fileContent;
        }

        public String getFileContent() {
            return fileContent;
        }
}
