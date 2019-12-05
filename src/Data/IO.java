package Data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class IO {

    private FileInputStream compressedFile;

    public byte[] getInputFile(File inputFile) {
        byte[] content = new byte[0];
        try {
            content = Files.readAllBytes(Paths.get(inputFile.getPath()));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return content;
    }

    public void outputFile(byte[] content, File destinyFile) {
        try {
            FileOutputStream outputStream = new FileOutputStream(destinyFile);
            outputStream.write(content);
            outputStream.flush();
            outputStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setInputCompressedFileStream(File compressedFile) {
        try {
            this.compressedFile = new FileInputStream(compressedFile);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeInputCompressedFileStream() {
        try {
            this.compressedFile.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte readFromInputCompressedFileStream() {
        byte aux = 0;
        try {
            aux = (byte) this.compressedFile.read();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return aux;
    }

    public void readFromInputCompressedFileStream(byte[] array) {
        try {
            this.compressedFile.read(array);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}