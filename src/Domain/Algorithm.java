package Domain;

import javafx.util.Pair;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Algorithm {

    protected GlobalStatistic globalStatistic;

    public Algorithm(int numCompression, int numDecompression, int totalCompressedData,
                     int totalDecompressedData, double totalCompressionTime, double totalDecompressionTime,
                     double averageCompressionRatio) {
        globalStatistic = new GlobalStatistic(numCompression, numDecompression, totalCompressedData, totalDecompressedData,
                totalCompressionTime, totalDecompressionTime, averageCompressionRatio);
    }

    public ArrayList<Object> getGlobalStatistic() {
        return globalStatistic.getInformation();
    }

    public void setGlobalStatistic(int numCompression, int numDecompression, int totalCompressedData,
                                                int totalDecompressedData, double totalCompressionTime, double totalDecompressionTime,
                                                double averageCompressionRatio) {
        globalStatistic = new GlobalStatistic(numCompression, numDecompression, totalCompressedData, totalDecompressedData,
                totalCompressionTime, totalDecompressionTime, averageCompressionRatio);
    }

    public abstract Pair<Double, Double> comprimir(Fitxer inFile, ByteArrayOutputStream compressedFile) throws IOException;
    public abstract Pair<Double, Double> descomprimir(byte[] compressedContent, Fitxer outPutFile) throws IOException;
}