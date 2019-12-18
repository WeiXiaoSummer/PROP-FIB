package Domain;

public class GlobalStatistic {

    private int numCompression;             // indicates the total number of the compression realized by this compression algorithm
    private int numDecompression;           // indicates the total number of the decompression realized by this compression algorithm
    private int totalCompressedData;        // indicates the total number of the bytes compressed by this compression algorithm
    private int totalDecompressedData;      // indicates the total number of the bytes decompressed by this compression algorithm
    private double totalCompressionTime;    // indicates the total compression time used by this compression algorithm, measured in seconds
    private double totalDecompressionTime;  // indicates the total decompression time used by this compression algorithm, measured in seconds
    private double totalCompressionRatio;   // indicates the total compression ratio

    //------------------------------------------------Constructor-----------------------------------------------------//

    public GlobalStatistic(){}

    public GlobalStatistic (int numCompression, int numDecompression, int totalCompressedData, int
                            totalDecompressedData, double totalCompressionTime, double totalDecompressionTime,
                            double totalCompressionRatio) {
        this.numCompression = numCompression;
        this.numDecompression = numDecompression;
        this.totalCompressedData = totalCompressedData;
        this.totalDecompressedData = totalDecompressedData;
        this.totalCompressionTime = totalCompressionTime;
        this.totalDecompressionTime = totalDecompressionTime;
        this.totalCompressionRatio = totalCompressionRatio;
    }

    //------------------------------------------------Constructor-----------------------------------------------------//


    //---------------------------------------------------Getters------------------------------------------------------//

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

    public double getTotalCompressionRatio() {
        return totalCompressionRatio;
    }

    //---------------------------------------------------Getters------------------------------------------------------//


    //---------------------------------------------------Setters------------------------------------------------------//

    public void setNumCompression(int numCompression) { this.numCompression = numCompression; }

    public void setNumDecompression(int numDecompression) { this.numDecompression = numDecompression; }

    public void setTotalCompressedData(int totalCompressedData) { this.totalCompressedData = totalCompressedData; }

    public void setTotalDecompressedData(int totalDecompressedData) { this.totalDecompressedData = totalDecompressedData; }

    public void setTotalCompressionTime(double totalCompressionTime) { this.totalCompressionTime = totalCompressionTime; }

    public void setTotalDecompressionTime(double totalDecompressionTime) { this.totalDecompressionTime = totalDecompressionTime; }

    public void setTotalCompressionRatio(double totalCompressionRatio) { this.totalCompressionRatio = totalCompressionRatio; }

    //---------------------------------------------------Setters------------------------------------------------------//


    //---------------------------------------------------Adders-------------------------------------------------------//

    public void addNumCompression() { ++numCompression; }

    public void addNumDecompression() { ++numDecompression; }

    public void addTotalCompressedData(int compressedData) { totalCompressedData += compressedData; }

    public void addTotalDecompressedData(int deecompressedData) { totalDecompressedData += deecompressedData; }

    public void addTotalCompressionTime(double compressionTime) { totalCompressionTime += compressionTime; }

    public void addTotalDecompressionTime(double decompressionTime) { totalDecompressionTime += decompressionTime; }

    public void addTotalCompressionRatio(double compressionRatio) { totalCompressionRatio += compressionRatio; }

    //---------------------------------------------------Adders-------------------------------------------------------//
}
