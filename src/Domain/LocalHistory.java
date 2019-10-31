package Domain;

import java.util.ArrayList;
import java.util.Arrays;

public class LocalHistory {
    private String inputPath;
    private String outPutPath;
    private String fileExtension; //file Type: ".txt" or ".ppm" or "folder"
    private String operation; // "Compression" or "Descompression"
    private String algorithm; // "LZ78" or "LZSS" or "JPEG"
    private double compressionRatio; //
    private double timeUsed; //

    public LocalHistory(String inputPath, String outPutPath, String fileExtension, String operation, String
                        algorithm, double compressionRatio, double timeUsed) {
        this.inputPath = inputPath;
        this.outPutPath = outPutPath;
        this.fileExtension = fileExtension;
        this.operation = operation;
        this.algorithm = algorithm;
        this.compressionRatio = compressionRatio;
        this.timeUsed = timeUsed;
    }

    public ArrayList<Object> getInformation() {
        ArrayList<Object> information = new ArrayList<>(Arrays.asList(inputPath, outPutPath, fileExtension, operation,
                algorithm, compressionRatio, timeUsed));
        return information;
    }
}
