package Data;

import java.io.File;

public class DataCtrl {

    private static DataCtrl instance = null;
    private IO io = new IO();

    private DataCtrl() {}

    public static DataCtrl getInstance() {
        if (instance == null) {
            instance = new DataCtrl();
        }
        return instance;
    }

    public byte[] getInputFile(File inputFile) {
        return io.getInputFile(inputFile);
    }

    public void outputFile(byte[] content, File destinyFile) { io.outputFile(content, destinyFile); }

    public void setInputCompressedFileStream(File compressedFile) {
       io.setInputCompressedFileStream(compressedFile);
    }

    public void closeInputCompressedFileStream() {
        io.closeInputCompressedFileStream();
    }

    public byte readFromInputCompressedFileStream() {
        return io.readFromInputCompressedFileStream();
    }

    public void readFromInputCompressedFileStream(byte[] array) {
        io.readFromInputCompressedFileStream(array);
    }
}