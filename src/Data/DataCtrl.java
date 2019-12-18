package Data;

import Commons.PersistenceLayerException;

public class DataCtrl {

    private static DataCtrl instance = null;

    private DataCtrl() {}

    public static DataCtrl getInstance() {
        if (instance == null) {
            instance = new DataCtrl();
        }
        return instance;
    }

    public byte[] getInputFile(String inputFilePath) throws PersistenceLayerException { return IO.getInstance().getInputFile(inputFilePath); }

    public void outputFile(byte[] content, String destinyFilePath) throws PersistenceLayerException { IO.getInstance().outputFile(content, destinyFilePath); }

    public void setInputCompressedFileStream(String compressedFilePath) throws PersistenceLayerException { IO.getInstance().setInputCompressedFileStream(compressedFilePath); }

    public void closeInputCompressedFileStream() throws PersistenceLayerException { IO.getInstance().closeInputCompressedFileStream(); }

    public byte readFromInputCompressedFileStream() throws PersistenceLayerException { return IO.getInstance().readFromInputCompressedFileStream(); }

    public void readFromInputCompressedFileStream(byte[] array) throws PersistenceLayerException { IO.getInstance().readFromInputCompressedFileStream(array); }

    public void checkFile(String filePath) throws PersistenceLayerException {IO.getInstance().checkFile(filePath);}
}