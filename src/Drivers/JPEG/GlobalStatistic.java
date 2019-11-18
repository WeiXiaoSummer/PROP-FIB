package Drivers.JPEG;

import java.util.ArrayList;
import java.util.Arrays;

public class GlobalStatistic {

    private int numCompression;
    private int numDecompression;
    private int totalCompressedData;
    private int totalDecompressedData;
    private double totalCompressionTime;
    private double totalDecompressionTime;
    private double averageCompressionRatio;

    public GlobalStatistic(){}

    public GlobalStatistic(int numCompression, int numDecompression, int totalCompressedData, int
                            totalDecompressedData, double totalCompressionTime, double totalDecompressionTime,
                           double averageCompressionRatio) {
        this.numCompression = numCompression;
        this.numDecompression = numDecompression;
        this.totalCompressedData = totalCompressedData;
        this.totalDecompressedData = totalDecompressedData;
        this.totalCompressionTime = totalCompressionTime;
        this.totalDecompressionTime = totalDecompressionTime;
        this.averageCompressionRatio = averageCompressionRatio;
    }

    public ArrayList<Object> getInformation() {
        double averageCompressionSpeed = totalCompressedData/totalCompressionTime;
        double averageDecompressionSpeed = totalDecompressedData/totalDecompressionTime;
        return new ArrayList<>(Arrays.asList(numCompression, numDecompression, totalCompressedData,
                totalDecompressedData, totalCompressionTime, totalDecompressionTime, averageCompressionSpeed, averageDecompressionSpeed));
    }

    public int getNumCompression() {
        return numCompression;
    }

    public int getNumDecompression() {
        return numDecompression;
    }

    public int getTotalCompressedData() {
        return totalCompressedData;
    }

    public int getTotalDecompressedData() {
        return totalDecompressedData;
    }

    public double getTotalCompressionTime() {
        return totalCompressionTime;
    }

    public double getTotalDecompressionTime() {
        return totalDecompressionTime;
    }

    public double getAverageCompressionRatio() {
        return averageCompressionRatio;
    }

    public void setNumCompression(int numCompression) {
        this.numCompression = numCompression;
    }

    public void setNumDecompression(int numDecompression) {
        this.numDecompression = numDecompression;
    }

    public void setTotalCompressedData(int totalCompressedData) {
        this.totalCompressedData = totalCompressedData;
    }

    public void setTotalDecompressedData(int totalDecompressedData) {
        this.totalDecompressedData = totalDecompressedData;
    }

    public void setTotalCompressionTime(double totalCompressionTime) {
        this.totalCompressionTime = totalCompressionTime;
    }

    public void setTotalDecompressionTime(double totalDecompressionTime) {
        this.totalDecompressionTime = totalDecompressionTime;
    }

    public void setAverageCompressionRatio(double averageCompressionRatio) {
        this.averageCompressionRatio = averageCompressionRatio;
    }

}
