package Domain;

public class LocalHistory {

    private String inputPath;    // indicates the path of the folder/file that we wish to compress/decompress
    private String outPutPath;   // indicates the path of the .PROP file obtained by compressing the input file/folder in
                                 // the case of a "Compression"; otherwise indicates the path of the folder obtained by
                                 // decompression the input .PROP file in the case of a "Decompression"
    private String fileExtension;    // indicates the type of the input -> {.txt, .ppm, .prop, folder}
    private String operation;        // indicates if it's a Compression or a Decompression
    private String algorithm;        // indicates which of the following compression algorithm is used -> {LZSS, LZ78, JPEG}
    private double compressionRatio; // indicates the Compression Ratio in the case of "Compression", 0 otherwise
    private double timeUsed;         // indicates the time used in this operation

    //-----------------------------------------------Constructor------------------------------------------------------//

    public LocalHistory(String inputPath, String outputPath, String fileExtension, String operation, String algorithm, double compressionRatio, double timeUsed) {
        this.inputPath = inputPath;
        this.outPutPath = outputPath;
        this.fileExtension = fileExtension;
        this.operation = operation;
        this.algorithm = algorithm;
        this.compressionRatio = compressionRatio;
        this.timeUsed = timeUsed;
    }

    //-----------------------------------------------Constructor------------------------------------------------------//


    //-------------------------------------------------Getters---------------------------------------------------------//

    public String getInputPath() { return this.inputPath; }

    public String getOutPutPath() { return this.outPutPath; }

    public String getFileExtension() { return this.fileExtension; }

    public String getOperation() { return this.operation; }

    public String getAlgorithm() { return this.algorithm; }

    public double getCompressionRatio() { return this.compressionRatio; }

    public double getTimeUsed() { return this.timeUsed; }


    //-------------------------------------------------Getters---------------------------------------------------------//


    //-------------------------------------------------Setters---------------------------------------------------------//

    public void setInputPath(String inputPath) { this.inputPath = inputPath; }

    public void setOutPutPath(String outPutPath) { this.outPutPath = outPutPath; }

    public void setFileExtension(String fileExtension) { this.fileExtension = fileExtension; }

    public void setOperation(String operation) { this.outPutPath = operation; }

    public void setAlgorithm(String algorithm) { this.algorithm = algorithm; }

    public void setCompressionRatio(double compressionRatio) { this.compressionRatio = compressionRatio; }

    public void setTimeUsed(double timeUsed) {this.timeUsed = timeUsed; }

    //-------------------------------------------------Setters---------------------------------------------------------//

}
