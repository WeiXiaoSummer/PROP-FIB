package Data;

import Commons.PersistenceLayerException;

/**
 * This Class is used for wrap Data Layer's class's methods
 */

public class DataCtrl {
    /**
     * Singleton attribute of the class DataCtrl.
     */
    private static DataCtrl instance = null;

    //----------------------------------------------Singleton method--------------------------------------------------//

    /**
     * Creates a new Instance of the class.
     */
    private DataCtrl() {}

    /**
     * Returns the unique DataCtrl instance associated with this Class.
     * @return the unique DataCtrl instance associated with this Class.
     */
    public static DataCtrl getInstance() {
        if (instance == null) {
            instance = new DataCtrl();
        }
        return instance;
    }

    //----------------------------------------------Singleton method--------------------------------------------------//

    //----------------------------------------------Wrapped method's--------------------------------------------------//

    /**
     * This function wraps the IO method:: getInputFile(String inputFilePath).
     * @param inputFilePath path of the file to be read.
     * @return a byte array containing the bytes read from the file.
     * @throws PersistenceLayerException if the wrapped method has occurred a error.
     */
    public byte[] getInputFile(String inputFilePath) throws PersistenceLayerException { return IO.getInstance().getInputFile(inputFilePath); }

    /**
     * This function wraps the IO method: outputFile(byte[] content, String destinyPath).
     * @param content the data to be wrote.
     * @param destinyFilePath path of the file to be wrote.
     * @throws PersistenceLayerException if the wrapped method has occurred a error.
     */
    public void outputFile(byte[] content, String destinyFilePath) throws PersistenceLayerException { IO.getInstance().outputFile(content, destinyFilePath); }

    /**
     * This function wraps the IO method: checkFile(String filePath).
     * @param filePath path of the file to be checked.
     * @throws PersistenceLayerException if the wrapped method has occurred a error.
     */
    public void checkFile(String filePath) throws PersistenceLayerException {IO.getInstance().checkFile(filePath);}

    /**
     * This function wraps the IO method:: setInputCompressedFileStream
     * @param compressedFilePath path of the file to be opened for reading.
     * @throws PersistenceLayerException if the wrapped method has occurred a error.
     */
    public void setInputCompressedFileStream(String compressedFilePath) throws PersistenceLayerException { IO.getInstance().setInputCompressedFileStream(compressedFilePath); }

    /**
     * This function wraps the IO method:: closeInputCompressedFileStream()
     * @throws PersistenceLayerException if the wrapped method has occurred a error.
     */
    public void closeInputCompressedFileStream() throws PersistenceLayerException { IO.getInstance().closeInputCompressedFileStream(); }

    /**
     * This function wraps the IO method:: readFromInputCompressedFileStream()
     * @return the next byte of data, or -1 if the end of the file is reached.
     * @throws PersistenceLayerException if the wrapped method has occurred a error.
     */
    public byte readFromInputCompressedFileStream() throws PersistenceLayerException { return IO.getInstance().readFromInputCompressedFileStream(); }

    /**
     * This function wraps the IO method:: readFromInputCompressedFilesStream(byte[] array)
     * @param array the buffer into which the data is read.
     * @throws PersistenceLayerException if the wrapped method has occurred a error.
     */
    public void readFromInputCompressedFileStream(byte[] array) throws PersistenceLayerException { IO.getInstance().readFromInputCompressedFileStream(array); }

    //----------------------------------------------Wrapped method's--------------------------------------------------//

}