package Domain;

import java.util.ArrayList;
import java.util.Arrays;

public class LocalHistory {
    private String inputPath;
    private String outPutPath;
    private String fileExtension;
    private String operation;
    private String algorithm;
    private double compressionRatio;
    private double timeUsed;

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
