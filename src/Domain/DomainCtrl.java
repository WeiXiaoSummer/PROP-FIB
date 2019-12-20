package Domain;

import Commons.DomainLayerException;
import Commons.PersistenceLayerException;
import Data.DataCtrl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.util.Pair;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * This Class is used for wrap Domain Layer's class's methods
 */
public class DomainCtrl {

    /**
     * Singleton attribute of the class IO
     */
    private static DomainCtrl instance = null;

    /**
     * Attribute which stores local histories
     */
    private GlobalHistory globalHistory;

    /**
     * Attribute which stores available algorithms
     */
    private HashMap<String, Algorithm> algorithms;


    //----------------------------------------------Singleton method--------------------------------------------------//

    /**
     * Creates a new instance of the Class DomainCtrl
     */
    private DomainCtrl(){
        globalHistory = new GlobalHistory();
        algorithms = new HashMap<>();
    }

    /**
     * Returns the unique DomainCtrl instance associated with this Class
     * @return the unique DomainCtrl instance associated with this Class
     */
    public static DomainCtrl getInstance() {
        if (instance == null) {
            instance = new DomainCtrl();
        }
        return instance;
    }

    //----------------------------------------------Singleton method--------------------------------------------------//

    //------------------------------------------------Initializer-----------------------------------------------------//

    /**
     * Initializes the domain layer by loading necessary information
     * @throws DomainLayerException
     */
    public void initializeDomainCtrl() throws DomainLayerException {
        //loads globalStatistics and globalHistories
        ArrayList<GlobalStatistic> statistics = loadGlobalStatistics();
        algorithms.put("LZSS", new LZSS(statistics.get(0)));
        algorithms.put("LZ78", new LZ78(statistics.get(1)));
        algorithms.put("JPEG", new JPEG(statistics.get(2)));
        ArrayList<LocalHistory> globalHistory = loadGlobalHistory();
        this.globalHistory.setGlobalHistory(globalHistory);
    }
    /**
     * Loads algorithm's statistics by reading the default statistic.json file
     * @return an arrayList which includes the globalStatistic of each algorithm that this program has been recording since
     * the first execution.
     * @throws DomainLayerException
     */
    private ArrayList<GlobalStatistic> loadGlobalStatistics() throws DomainLayerException {
        //loads the globalStatistics stored in the default statistic.json file
        try {
            Gson gson = new Gson();
            byte[] jsonContent = DataCtrl.getInstance().getInputFile(".\\src\\Data\\statistic.json");
            Type arrayType = new TypeToken<ArrayList<GlobalStatistic>>() {}.getType();
            return gson.fromJson(new String(jsonContent), arrayType);
        }
        catch (PersistenceLayerException e) {
            throw new DomainLayerException(e.getMessage());
        }
    }

    /**
     * Loads histories by reading the default globalHistory.json
     * @return an arrayList which includes all the local histories that this program has been recording since the first
     * execution.
     * @throws DomainLayerException
     */
    private ArrayList<LocalHistory> loadGlobalHistory () throws DomainLayerException{
        //loads the globalHistories stored in the default globalHistory.json file
        try {
            Gson gson = new Gson();
            File statisticFile = new File(".\\src\\Data\\globalHistory.json");
            byte[] jsonContent = DataCtrl.getInstance().getInputFile(statisticFile.getPath());
            Type arrayType = new TypeToken<ArrayList<LocalHistory>>() {}.getType();
            return gson.fromJson(new String(jsonContent), arrayType);
        }
        catch (PersistenceLayerException e) {
            throw new DomainLayerException(e.getMessage());
        }
    }


    //------------------------------------------------Initializer-----------------------------------------------------//

    //-------------------------------------------------Finalizer------------------------------------------------------//

    /**
     * Stores important information before the program is closed
     * @throws DomainLayerException
     */
    public void closeAndSave() throws DomainLayerException{
        //stores globalStatistics and globalHistories to the corresponding .json file
        saveGlobalStatistics();
        saveGlobalHistory();
    }

    /**
     * Stores each algorithm's global statistic to the corresponding statistic.json file
     * @throws DomainLayerException
     */
    private void saveGlobalStatistics() throws DomainLayerException {
        //stores globalStatistics to the default statistic.json file
        try {
            Gson gson = new Gson();
            ArrayList<GlobalStatistic> globalStatistics = new ArrayList<>(Arrays.asList(algorithms.get("LZSS").getGlobalStatistic(),
                    algorithms.get("LZ78").getGlobalStatistic(), algorithms.get("JPEG").getGlobalStatistic()));
            Type arrayType = new TypeToken<ArrayList<GlobalStatistic>>() {}.getType();
            String jsonContent = gson.toJson(globalStatistics, arrayType);
            DataCtrl.getInstance().outputFile(jsonContent.getBytes(), ".\\src\\Data\\statistic.json");
        }
        catch (PersistenceLayerException e) {
            throw new DomainLayerException(e.getMessage());
        }
    }

    /**
     * Stores all the local histories to the corresponding globalHistory.json file
     * @throws DomainLayerException
     */
    private void saveGlobalHistory() throws DomainLayerException {
        //stores globalHistories to the default globalHistory.json file
        try {
            Gson gson = new Gson();
            Type arrayType = new TypeToken<ArrayList<LocalHistory>>() {}.getType();
            ArrayList<LocalHistory> globalHistory = this.globalHistory.getGlobalHistory();
            String jsonContent = gson.toJson(globalHistory, arrayType);
            DataCtrl.getInstance().outputFile(jsonContent.getBytes(), ".\\src\\Data\\globalHistory.json");
        }
        catch (PersistenceLayerException e) {
            throw new DomainLayerException(e.getMessage());
        }
    }

    //-------------------------------------------------Finalizer------------------------------------------------------//

    //---------------------------------------------Compression Methods------------------------------------------------//

    /**
     * Compress the file selected by the user and stores the compressed file to the target directory
     * @param inFilePath path of the file to be compressed
     * @param targetDirectoryPath path of the directory where the compressed file to be saved
     * @param saveName name of the compressed file to be saved
     * @param algorithmType name of the compression algorithm to be used
     * @return a Pair which contains this compression operation statistic
     *              - The key of this Pair contains the compression ratio
     *              - The value of this Pair contains the time used
     * @throws DomainLayerException
     * @throws PersistenceLayerException
     */
    public Pair<Double, Double> compress(String inFilePath, String targetDirectoryPath, String saveName, String algorithmType) throws DomainLayerException{
        try {
            DataCtrl.getInstance().checkFile(inFilePath);
            DataCtrl.getInstance().checkFile(targetDirectoryPath);

            //specifies the input file and the output directory
            Fitxer inputFile = new Fitxer(new File(inFilePath), new byte[0]);
            Fitxer outputFile = new Fitxer(new File(targetDirectoryPath + File.separator + saveName + ".prop"), new byte[0]);

            //ByteArrayOutputStream into which the compressed content is wrote
            ByteArrayOutputStream compressedFile = new ByteArrayOutputStream();
            LocalHistory localHistory;

            //creates a Object array into which the global compression statistic is stored. We stores in this Object array the total size of the
            //selected file, the total size of the corresponding compressed file and the total time used for compress this file
            Object[] compressStatistic = {0, 0, 0d};
            double compressionRatio, compressionTime;
            compressionRatio = compressionTime = 0;

            //write the global main header "PROP":
            //      -The key "PROP" is used in the decompress process for double check. While decompressing a compressed .PROP file, the program
            //      will first check if "PROP" is contained in the main header, if not it means that user has modified the original
            //      compressed content and the decompress process cannot be go on by the file corruption.

            //write the local file header: local file header is composed by
            //      4 bytes to indicate the type of this local file + 1 byte to indicate the length of the name of this local file +
            //      this local file's name + extra information which depends on the file type

            //our program wraps the object file into a folder and then compress this folder, so once the program has wrote the global header
            //the next thing to do is write the header for this folder, as we could see below:

            //the program writes first the global header "PROP", as we mentioned before this program wraps the selected file into a folder and then
            //compress this folder, so the next thing to do is to write the local header for this folder:
            //      -"FOLD" indicates that this is a file of the type folder, which means it can have a several files inside it
            //      -nameLength is byte which stores the name length
            //      -saveName stores the name
            //      -as this is a folder, the extra information that we should store is the total number of the files included in it,
            //      numFiles is 4 bytes array which stores this information
            byte nameLength = (byte) saveName.length();
            byte[] numFiles = ByteBuffer.allocate(4).putInt(1).array();
            compressedFile.write("PROPFOLD".getBytes()); compressedFile.write(nameLength);
            compressedFile.write(saveName.getBytes()); compressedFile.write(numFiles);

            //Compressing the content of the selected file and write the compressed content to the ByteOutPutStream mentioned before.
            //This compression process depends on the type of this file:

            //  -case if it's a .txt or .ppm file
            if (inputFile.getFile().isFile()) {
                String fileType = getFileType(inputFile.getFile());
                compressFile(inputFile, compressedFile, fileType, algorithmType, compressStatistic);
                if ((int) compressStatistic[0] != 0) compressionRatio = (double) ((int)compressStatistic[0]/(int)compressStatistic[1]);
                compressionTime = (double) compressStatistic[2];
                localHistory = new LocalHistory(inFilePath, outputFile.getFile().getPath(), fileType, "Compression", algorithmType, compressionRatio, compressionTime);
            }

            //  -case if it's a folder
            else {
                compressFolder(inputFile, compressedFile, algorithmType, compressStatistic);
                if ((int) compressStatistic[0] != 0) compressionRatio = (double) ((int)compressStatistic[0]/(int)compressStatistic[1]);
                compressionTime = (double) compressStatistic[2];
                localHistory = new LocalHistory(inFilePath, outputFile.getFile().getPath(), "FOLDER", "Compression", algorithmType, compressionRatio, compressionTime);
            }

            //the program has just done compression operation, this should be added and stored as a part of the global history
            globalHistory.addLocalHistory(localHistory);

            //out put the compressed content wrapped in a compressed .PROP file into the target directory
            outputFile.setContent(compressedFile.toByteArray());
            DataCtrl.getInstance().outputFile(outputFile.getContent(), outputFile.getFile().getPath());
            return new Pair<>(compressionRatio, compressionTime);
        }
        catch (PersistenceLayerException e) {throw new DomainLayerException(e.getMessage());}
        catch (DomainLayerException e) {throw e;}
        catch (Exception e) {throw new DomainLayerException("An unidentified error has occurred while compressing:\n\n" + inFilePath + "\n\n"+e.getMessage());}

    }

    /**
     * Compress the selected folder and write the compressed content into the temporal buffer compressedFile
     * @param inputFolder folder to be compressed
     * @param compressedFile byteArrayOutPutStream into which the compressed content is wrote
     * @param algorithmType name of the compression algorithm to be used
     * @param compressStatistic the global compression statistic. the local compression statistic generated in this compression process
     *                          should be added to the global compression statistic for compute the final global compression statistic
     * @throws DomainLayerException
     * @throws PersistenceLayerException
     */
    private void compressFolder(Fitxer inputFolder, ByteArrayOutputStream compressedFile, String algorithmType, Object[] compressStatistic) throws DomainLayerException{
        try {
            //get the files included in this folder
            File[] files = inputFolder.getFile().listFiles();
            //write header for this folder
            byte nameLength = (byte) inputFolder.getFile().getName().length();
            byte[] numFiles = ByteBuffer.allocate(4).putInt(files.length).array();
            compressedFile.write("FOLD".getBytes()); compressedFile.write(nameLength);
            compressedFile.write(inputFolder.getFile().getName().getBytes()); compressedFile.write(numFiles);
            //compress all the files included in this folder
            for (File f : files) {
                //case if it's a file
                if (f.isFile()) {
                    String fileType = getFileType(f);
                    Fitxer inputFile = new Fitxer(f, new byte[0]);
                    compressFile(inputFile, compressedFile, fileType, algorithmType, compressStatistic);
                }
                //case if it's a folder
                else {
                    Fitxer subFolder = new Fitxer(f, new byte[0]);
                    compressFolder(subFolder, compressedFile, algorithmType, compressStatistic);
                }
            }
        }
        catch (PersistenceLayerException e) { throw new DomainLayerException(e.getMessage());}
        catch (IOException e) { throw new DomainLayerException("An internal error has occurred while compressing " + inputFolder + "\n"+e.getMessage());}
        catch (DomainLayerException e) { throw e;}

    }

    /**
     * Compresses the selected file with the selected algorithm and writes the compressed content into the temporal buffer compressedFile
     * @param inputFile file to be compressed
     * @param compressedFile byteArrayOutPutStream into which the compressed content is wrote
     * @param type type of this file, {.txt, .ppm}
     * @param algorithmType name of the algorithm to be used
     * @param compressStatistic the global compression statistic, the local compression statistic generated in this compression process
     *                          should be added to the global compression statistic for compute the final global compression statistic
     * @throws DomainLayerException
     * @throws PersistenceLayerException
     */
    private void compressFile(Fitxer inputFile, ByteArrayOutputStream compressedFile, String type, String algorithmType, Object[] compressStatistic) throws DomainLayerException{
        try {
            //read the content of the input file
            inputFile.setContent(DataCtrl.getInstance().getInputFile(inputFile.getFile().getPath()));

            //compression statistic generated in this compression process
            Object[] compressedFileStatistic;
            if (type.equals("txt")) {
                if (algorithmType.equals("AUTO")) compressedFileStatistic = algorithms.get("LZ78").comprimir(inputFile, compressedFile);
                else compressedFileStatistic = algorithms.get(algorithmType).comprimir(inputFile, compressedFile);
            }
            else if (type.equals("ppm")) compressedFileStatistic = algorithms.get("JPEG").comprimir(inputFile, compressedFile);
            else throw new DomainLayerException("This folder contains files that can't be compressed:\n\n"+inputFile.getFile().getPath()+"\n\ncompression aborted.");

            //add the local compression statistic to the global compression statistic
            compressStatistic[0] = (int) compressStatistic[0] + (int) compressedFileStatistic[0];
            compressStatistic[1] = (int) compressStatistic[1] + (int) compressedFileStatistic[1];
            compressStatistic[2] = (double) compressStatistic[2] + (double) compressedFileStatistic[2];
        }
        catch (PersistenceLayerException e) {throw new DomainLayerException(e.getMessage());}
        catch (DomainLayerException e) {throw e;}

    }

    //---------------------------------------------Compression Methods------------------------------------------------//

    //--------------------------------------------Decompression Methods-----------------------------------------------//

    /**
     * Decompress the selected .PROP file and stores the decompressed folder to the target directory
     * @param inFilePath path of the .PROP file to be decompressed
     * @param targetDirectoryPath path of the target directory where the decompressed folder is stored
     * @return a Pair which contains this compression operation statistic
     *             - The key of this Pair contains the compression ratio
     *             - The value of this Pair contains the time used
     * @throws DomainLayerException
     */
    public Pair<Double, Double> decompress(String inFilePath, String targetDirectoryPath) throws DomainLayerException {
        try {
            DataCtrl.getInstance().checkFile(inFilePath);
            DataCtrl.getInstance().checkFile(targetDirectoryPath);

            //Create the decompression statistic
            //     - the first position contains the size of the decompressed folder expressed in bytes
            //     - the second position contains the size of the compressed file expressed in bytes
            //     - the third position contains the time used in
            Object[] decompressStatistic = {0, 0, 0d};

            //open an input stream with the input compressed file for read it's content
            DataCtrl.getInstance().setInputCompressedFileStream(inFilePath);

            //read the main header
            byte[] aux = new byte[4];
            DataCtrl.getInstance().readFromInputCompressedFileStream(aux);

            //check if the main header equals "PROP", in the case of no throws the exception
            String check = new String(aux);
            if (!check.equals("PROP")) throw new DomainLayerException(inFilePath+" is corrupted, decompression aborted.");

            //read the type section of this local file header, in this case we know it will be the "FOLD" as our program wraps
            //the selected file into a folder
            DataCtrl.getInstance().readFromInputCompressedFileStream(aux);

            //read the length of the name of this folder
            byte nameLength = DataCtrl.getInstance().readFromInputCompressedFileStream();
            byte[] getFolderName = new byte[nameLength];

            //read the name of this folder and create it
            DataCtrl.getInstance().readFromInputCompressedFileStream(getFolderName);
            File targetFolder = new File(targetDirectoryPath+File.separator+new String(getFolderName));
            targetFolder.mkdir();

            //decompress this compressed folder
            decompressFolder(targetFolder, decompressStatistic);

            //compute the decompression statistic and add this operation to the global history
            double compressionRatio, decompressionTime;
            compressionRatio = decompressionTime = 0;
            if ((int) decompressStatistic[0] != 0) compressionRatio = (double) ((int)decompressStatistic[0]/(int)decompressStatistic[1]);
            decompressionTime = (double) decompressStatistic[2];
            LocalHistory localHistory = new LocalHistory(inFilePath, targetDirectoryPath, "PROP", "Decompression", "AUTO", compressionRatio, decompressionTime);
            globalHistory.addLocalHistory(localHistory);

            //close the input stream to release the system resources
            DataCtrl.getInstance().closeInputCompressedFileStream();
            return new Pair<>(compressionRatio, decompressionTime);
        }
        catch (PersistenceLayerException e) {throw new DomainLayerException(e.getMessage());}
        catch (DomainLayerException e) {throw e;}
        catch (Exception e) {throw new DomainLayerException("An error has occurred while decompressing the file:\n\n"+inFilePath+"\n\nThe compressed" +
                " content is corrupted, decompression aborted.");}
    }

    /**
     * Decompress the specified compressed folder included in the selected .PROP file and stores it in the corresponding folder
     * @param targetDirectory target directory where the decompressed folder is stored
     * @param decompressStatistic the global decompression statistic. The local decompression statistic generated in this decompression process
     *                            should be added to the global decompression statistic for compute the final global decompression statistic
     * @throws DomainLayerException
     * @throws PersistenceLayerException
     */
    private void decompressFolder (File targetDirectory, Object[] decompressStatistic) throws DomainLayerException{
        try {
            //Get the total number of the files contained in this compressed folder
            byte[] getFolderSize = new byte[4];
            DataCtrl.getInstance().readFromInputCompressedFileStream(getFolderSize);
            int folderSize = ByteBuffer.wrap(getFolderSize).getInt();

            for(int i = 0; i < folderSize; ++i) {
                //Get the type of the actual compressed file, it can be {.TXT, .PPM, FOLDER}
                byte[] getType = new byte[4];
                DataCtrl.getInstance().readFromInputCompressedFileStream(getType);
                String type = new String(getType);

                //Get the name of the actual compressed content
                byte nameLength = DataCtrl.getInstance().readFromInputCompressedFileStream();
                byte[] getName = new byte[nameLength];
                DataCtrl.getInstance().readFromInputCompressedFileStream(getName);

                //Get the path of the file or the folder decompressed
                File targetFile = new File(targetDirectory.getPath()+File.separator+new String(getName));
                Fitxer decompressedFile = new Fitxer(targetFile, new byte[0]);

                //Case if it's a compressed folder
                if (type.equals("FOLD")) {
                    targetFile.mkdir();
                    decompressFolder(targetFile, decompressStatistic);
                }
                //Case if it's compressed .txt or .ppm file
                else {
                    decompressFile(decompressedFile, type, decompressStatistic);
                }
            }
        }
        catch (PersistenceLayerException e) { throw new DomainLayerException(e.getMessage()); }
        catch (DomainLayerException e) { throw e; }
    }

    /**
     * Decompress the specified compressed file included in the selected .PROP file and stores it in the corresponding folder.
     * @param targetFile target file which the decompressed content is wrote into.
     * @param type type of this compressed file, {.ppm, .txt}
     * @param decompressionStatistic the global decompression statistic. The local decompression statistic generated in this decompression process
     *                               should be added to the global decompression statistic for compute the final global decompression statistic
     * @throws DomainLayerException
     * @throws PersistenceLayerException
     */
    private void decompressFile(Fitxer targetFile, String type, Object[] decompressionStatistic) throws DomainLayerException{
        try {
            //Get the size of this compressed file
            byte[] getCompressedContentSize = new byte[4];
            DataCtrl.getInstance().readFromInputCompressedFileStream(getCompressedContentSize);
            int compressedContentSize = ByteBuffer.wrap(getCompressedContentSize).getInt();
            int offset = 0;
            if (type.equals("JPEG")) offset = 8;

            //Get the content of this compressed file
            byte[] compressedContent = new byte[compressedContentSize+offset];
            DataCtrl.getInstance().readFromInputCompressedFileStream(compressedContent);

            //Get the decompression statistic generated by decompress this file and add it to the global decompression statistic
            Object[] decompressedFileStatistic = algorithms.get(type).descomprimir(compressedContent, targetFile);
            decompressionStatistic[0] = (int) decompressionStatistic[0] + (int) decompressedFileStatistic[0];
            decompressionStatistic[1] = (int) decompressionStatistic[1] + (int) decompressedFileStatistic[1];
            decompressionStatistic[2] = (double) decompressionStatistic[2] + (double) decompressedFileStatistic[2];

            //Out put the decompressed file
            DataCtrl.getInstance().outputFile(targetFile.getContent(), targetFile.getFile().getPath());
        }
        catch (PersistenceLayerException e) { throw new DomainLayerException(e.getMessage()); }
        catch (DomainLayerException e) { throw e; }
    }

    //--------------------------------------------Decompression Methods-----------------------------------------------//

    //------------------------------------------------Getter Methods--------------------------------------------------//

    /**
     * Returns the name of the columns which should be displayed in the tableView of the historyView which shows the global
     * histories.
     * @return the name of the columns which should be displayed in the tableView of the history view which shows the global histories
     */
    public ArrayList<String> getHistoryColumnNames() {
        return this.globalHistory.getColumnNames();
    }

    /**
     * Returns the name of the columns which should be displayed in the tableView of the statisticView which shows the global statistic
     * of each algorithm.
     * @return the name of the columns which should be displayed in the tableView of the statisticView which shows the global statistic
     * of each algorithm
     */
    public String[] getStatisticColumnNames() {
        String[] statistcColumnNames = {"","Av.Comp. Speed", "Av.Decomp. Speed", "Av.Comp. Ratio"};
        return statistcColumnNames;
    }

    /**
     * Returns the name of the available algorithms that can be choosed for user.
     * @return the name of the available algorithms that can be choosed for user, the key of the pair contains the names of the .txt compression
     * algorithm and the value contains the names of the .ppm compression algorithm.
     */
    public Pair<String[], String[]> getAlgorithmsNames() {
        String[] txtAlgorithms = {"AUTO", "LZSS", "LZ78"};
        String[] ppmAlgorithms = {"JPEG"};
        return new Pair<>(txtAlgorithms, ppmAlgorithms);
    }

    /**
     * Returns the statistic of each algorithm.
     * @return a ArrayList of String arrays, each of these array are composed by:
     *      -Name of the algorithm
     *      -Average compression speed of this algorithm measured in MB/s
     *      -Average decompression speed of this algorithm measured in MB/s
     *      -Average compression ratio of this algorithm
     */
    public ArrayList<String[]> getStatistics() {
        ArrayList<String[]> statistics = new ArrayList<>();
        String[] algorithms = {"LZSS", "LZ78", "JPEG"};
        for (String algorithm : algorithms) {
            double averageCompressionSpeed, averageDecompressionSpeed, averageCompressionRatio;
            averageCompressionSpeed = averageDecompressionSpeed = averageCompressionRatio = 0d;
            GlobalStatistic algorithmStatistic = this.algorithms.get(algorithm).getGlobalStatistic();
            if (algorithmStatistic.getTotalCompressionTime() != 0d) averageCompressionSpeed = algorithmStatistic.getTotalCompressedData()/(algorithmStatistic.getTotalCompressionTime()*1000000);
            if (algorithmStatistic.getTotalDecompressionTime() != 0d) averageDecompressionSpeed = algorithmStatistic.getTotalDecompressedData()/(algorithmStatistic.getTotalDecompressionTime()*1000000);
            if (algorithmStatistic.getNumCompression() != 0d) averageCompressionRatio = algorithmStatistic.getTotalCompressionRatio()/algorithmStatistic.getNumCompression();
            String[] statistic = {algorithm, String.format("%.2f", averageCompressionSpeed)+" MB/s", String.format("%.2f", averageDecompressionSpeed)+" MB/s", String.format("%.2f", averageCompressionRatio)};
            statistics.add(statistic);
        }
        return statistics;
    }

    /**
     * Returns the global history.
     * @return the global history.
     */
    public ArrayList<String[]> getHistories() {
        ArrayList<String[]> histories = new ArrayList<>();
        ArrayList<LocalHistory> globalHistory = this.globalHistory.getGlobalHistory();
        for (int i = 0; i < globalHistory.size(); ++i) {
            LocalHistory lh = globalHistory.get(i);
            String[] localHistory = {lh.getInputPath(), lh.getOutPutPath(), lh.getOperation(), lh.getAlgorithm(),
                    String.format("%.2f", lh.getTimeUsed())+"s", lh.getFileExtension(), String.format("%.2f", lh.getCompressionRatio())};
            histories.add(localHistory);
        }
        return histories;
    }

    /**
     * Returns the original content of the selected file and it's corresponding content after applied the compress-decompress process;
     * @param filePath path of the file to be compared.
     * @param algorithm name of the algorithm to be used.
     * @return the original content and the content after applied the compress-decompress process.
     * @throws DomainLayerException
     * @throws PersistenceLayerException
     */
    public Pair<byte[], byte[]> getCompareFilesContent(String filePath, String algorithm) throws DomainLayerException{
        try {
            byte[] originalContent = DataCtrl.getInstance().getInputFile(filePath);
            Fitxer originalFile = new Fitxer(new File(filePath), originalContent);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            algorithms.get(algorithm).comprimir(originalFile, stream);
            int offset = 9+originalFile.getFile().getName().length();
            Fitxer decompressedFile = new Fitxer ( null, new byte[0]);
            byte[] compressedContentWithHeader = stream.toByteArray();
            byte[] compressedContentWithOutHeader = Arrays.copyOfRange(compressedContentWithHeader, offset, compressedContentWithHeader.length);
            algorithms.get(algorithm).descomprimir(compressedContentWithOutHeader, decompressedFile);
            return new Pair<>(originalContent, decompressedFile.getContent());
        }
        catch (PersistenceLayerException e) { throw new DomainLayerException(e.getMessage()); }
        catch (DomainLayerException e) { throw e; }
    }

    //------------------------------------------------Getter Methods--------------------------------------------------//

    /**
     * Clears all the histories stored in this program.
     */
    public void clearHistory() {
        this.globalHistory.clearHistory();
    }

    /**
     * Returns the extension of the given file.
     * @param f the file to be determinate.
     * @return the type of the given file, for example if the file has name xxx.txt it returns "txt".
     */
    private String getFileType(File f) {
        String fileName = f.getName();
        int dotIndex = fileName.lastIndexOf('.');
        return fileName.substring(dotIndex+1);
    }

}

