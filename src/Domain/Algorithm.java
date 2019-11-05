package Domain;

import java.util.ArrayList;

public class Algorithm {

    protected GlobalStatistic globalStatistic;

    public Algorithm() {}

    public Algorithm(int numCompression, int numDecompression, int totalCompressedData,
                     int totalDecompressedData, double totalCompressionTime, double totalDecompressionTime,
                     double averageCompressionRatio) {
        globalStatistic = new GlobalStatistic(numCompression, numDecompression, totalCompressedData, totalDecompressedData,
                                              totalCompressionTime, totalDecompressionTime, averageCompressionRatio);
    }

    public ArrayList<Object> getGlobalStatistic() {
        return globalStatistic.getInformation();
    }
}
