package Domain;

public class LocalHistory {

    /**
     * indicates the path of the folder/file that we wish to compress/decompress
     */
    private String inputPath;

    /**
     * indicates the path of the .PROP file obtained by compressing the input file/folder in
     * the case of a "Compression"; otherwise indicates the path of the folder obtained by
     * decompression the input .PROP file in the case of a "Decompression"
     */
    private String outPutPath;

    /**
     * indicates the type of the input -> {.txt, .ppm, .prop, folder}
     */
    private String fileExtension;

    /**
     * indicates if it's a Compression or a Decompression
     */
    private String operation;

    /**
     * indicates which of the following compression algorithm is used -> {LZSS, LZ78, JPEG}
     */
    private String algorithm;

    /**
     * indicates the Compression Ratio in the case of "Compression", 0 otherwise
     */
    private double compressionRatio;

    /**
     * indicates the time used in this operation
     */
    private double timeUsed;

    /**
     * indicates the date that the this operation has done
     */
    private String date;

    //-----------------------------------------------Constructor------------------------------------------------------//

    /**
     * construct LocalHistory with parameters
     * @param inputPath the path of the folder/file that we wish to compress/decompress
     * @param outputPath the path to save compressed/decompressed folder/file
     * @param fileExtension the type of the input file
     * @param operation it's a Compression or a Decompression
     * @param algorithm algorithm is used
     * @param compressionRatio the Compression Ratio in the case of "Compression", 0 otherwise
     * @param timeUsed the time used in this operation
     */
    public LocalHistory(String inputPath, String outputPath, String fileExtension, String operation, String algorithm, double compressionRatio, double timeUsed, String date) {
        this.inputPath = inputPath;
        this.outPutPath = outputPath;
        this.fileExtension = fileExtension;
        this.operation = operation;
        this.algorithm = algorithm;
        this.compressionRatio = compressionRatio;
        this.timeUsed = timeUsed;
        this.date = date;
    }

    //-----------------------------------------------Constructor------------------------------------------------------//


    //-------------------------------------------------Getters---------------------------------------------------------//

    /**
     * get the path of file that compressed/decompressed
     * @return the path of file that compressed/decompressed
     */
    public String getInputPath() { return this.inputPath; }

    /**
     * get the path to save the file compressed/decompressed
     * @return the path to save the file compressed/decompressed
     */
    public String getOutPutPath() { return this.outPutPath; }

    /**
     * get the type of the input file
     * @return the type of the input file: .txt, .ppm, .prop or folder
     */
    public String getFileExtension() { return this.fileExtension; }

    /**
     * get the operation we do
     * @return compression or decompression
     */
    public String getOperation() { return this.operation; }

    /**
     * get the algorithm we used
     * @return LZSS, LZ78 or JPEG
     */
    public String getAlgorithm() { return this.algorithm; }

    /**
     * get the ratio of compression/decompression
     * @return the ratio of compression
     */
    public double getCompressionRatio() { return this.compressionRatio; }

    /**
     * get the time to compress/decompress input file used
     * @return the time to compress/decompress input file used
     */
    public double getTimeUsed() { return this.timeUsed; }


    /**
     * get the date
     */
    public String getDate() { return this.date; }
    //-------------------------------------------------Getters---------------------------------------------------------//


    //-------------------------------------------------Setters---------------------------------------------------------//

    /**
     * set the path of file that compressed/decompressed
     * @param inputPath path of input file to compress/decompress
     */
    public void setInputPath(String inputPath) { this.inputPath = inputPath; }

    /**
     * set the path to save compressed/decompressed file
     * @param outPutPath the path to save file
     */
    public void setOutPutPath(String outPutPath) { this.outPutPath = outPutPath; }

    /**
     * set the type of file
     * @param fileExtension .txt, .ppm, .prop or folder
     */
    public void setFileExtension(String fileExtension) { this.fileExtension = fileExtension; }

    /**
     * set the operation to do
     * @param operation Compression or Decompression
     */
    public void setOperation(String operation) { this.outPutPath = operation; }

    /**
     * set algorithm to use
     * @param algorithm LZSS, LZ78 or JPEG
     */
    public void setAlgorithm(String algorithm) { this.algorithm = algorithm; }

    /**
     * set ratio of compression
     * @param compressionRatio ratio of compress a file
     */
    public void setCompressionRatio(double compressionRatio) { this.compressionRatio = compressionRatio; }

    /**
     * set the time of operation used
     * @param timeUsed the time of operation used
     */
    public void setTimeUsed(double timeUsed) {this.timeUsed = timeUsed; }

    /**
     *
     */
    public void setDate(String date) {this.date = date;}

    //-------------------------------------------------Setters---------------------------------------------------------//

}
