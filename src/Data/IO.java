package Data;

import Commons.PersistenceLayerException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This Class implements methods for reading and writing files
 */
public class IO {
    /**
     * Singleton attribute of the class IO
     */
    private static IO instance = null;

    /**
     * Attribute which is applied to save the actual opened FileInputStream
     */
    private FileInputStream compressedFile;

    //----------------------------------------------Singleton method--------------------------------------------------//

    /**
     * Create a new instance of the Class IO
     */
    private IO() {}

    /**
     * Returns the unique IO instance associated with this Class
     * @return the unique IO instance associated with this Class
     */
    public static IO getInstance() {
        if (instance == null) instance = new IO();
        return instance;
    }

    //----------------------------------------------Singleton method--------------------------------------------------//

    //---------------------------------------Basic methods for read and write files-----------------------------------//

    /**
     * Reads all the bytes from a file.
     * @param inputFilePath path of the file to be read.
     * @return a byte array containing the bytes read from the file.
     * @throws PersistenceLayerException if file with path = inputFilePath doesn't exists.
     */
    public byte[] getInputFile(String inputFilePath) throws PersistenceLayerException {
        try {
            byte[] content = Files.readAllBytes(Paths.get(inputFilePath));
            return content;
        }
        catch (IOException e) {
            throw new PersistenceLayerException("An error has occurred while attempting to read from the file " + inputFilePath + ":\n" + e.getMessage());
        }
    }

    /**
     * writes content.length bytes from the specified byte array to the file with path = destinyFilePath.
     * @param content the data to be wrote.
     * @param destinyFilePath path of the file to be wrote.
     * @throws PersistenceLayerException if an I/O error occurs.
     */
    public void outputFile(byte[] content, String destinyFilePath) throws PersistenceLayerException {
        try {
            FileOutputStream outputStream = new FileOutputStream(destinyFilePath);
            outputStream.write(content);
            outputStream.flush();
            outputStream.close();
        }
        catch (IOException e) {
            throw new PersistenceLayerException(
                    "An error has occurred while attempting to write to file " + destinyFilePath + " :\n" + e.getMessage());
        }
    }

    public void checkFile(String filePath) throws PersistenceLayerException{
        File file = new File(filePath);
        if (!file.exists()) throw new PersistenceLayerException("Selected file/folder doesn't exists!\n"+filePath+"\nPlease select another one");
    }
    //---------------------------------------Basic methods for read and write files-----------------------------------//

    //---------------------------------------Methods used to read an compressed file----------------------------------//

    /**
     * Creates a FileInputStream by opening a connection to the file with path = compressedFilePath, only should be used
     * in the case of reading a .PROP compressed File.
     * @param compressedFilePath path of the file to be opened for reading.
     * @throws PersistenceLayerException if the file with path = compressedFilePath doesn't exists.
     */
    public void setInputCompressedFileStream(String compressedFilePath) throws PersistenceLayerException{
        try {
            this.compressedFile = new FileInputStream(compressedFilePath);
        }
        catch (IOException e) {
            throw new PersistenceLayerException("An error has occurred while attempting to open the compressed file:\n" + e.getMessage());
        }
    }

    /**
     * Closes this file input stream and releases any system resources associated with the stream.
     * @throws PersistenceLayerException if an I/O error occurs.
     */
    public void closeInputCompressedFileStream() throws PersistenceLayerException{
        try {
            this.compressedFile.close();
        }
        catch (IOException e) {
            throw new PersistenceLayerException("An error has occurred while attempting to close the compressed file:\n" + e.getMessage());
        }
    }

    /**
     * Reads a byte of data from the opened input stream.
     * @return the next byte of data, or -1 if the end of the file is reached.
     * @throws PersistenceLayerException
     */

    public byte readFromInputCompressedFileStream() throws PersistenceLayerException{
        try {
            byte aux = (byte) this.compressedFile.read();
            return aux;
        }
        catch (IOException e) {
            throw new PersistenceLayerException("An error has occurred while attempting to read the compressed file:\n" + e.getMessage());
        }
    }

    /**
     * Reads up to array.length bytes of data form the opened input stream.
     * @param array the buffer into which the data is read.
     * @throws PersistenceLayerException if an I/O error occurs.
     */
    public void readFromInputCompressedFileStream(byte[] array) throws PersistenceLayerException{
        try {
            this.compressedFile.read(array);
        }
        catch (IOException e) {
            throw new PersistenceLayerException("An error has occurred while attempting to read the compressed file:\n" + e.getMessage());
        }
    }

    //---------------------------------------Methods used to read an compressed file----------------------------------//
}