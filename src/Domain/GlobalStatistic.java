package Domain;

public class GlobalStatistic {

    /**
     * indicates the total number of the compression realized by this compression algorithm
     */
    private int numCompression;
    /**
     * indicates the total number of the decompression realized by this compression algorithm
     */
    private int numDecompression;
    /**
     * indicates the total number of the bytes compressed by this compression algorithm
     */
    private int totalCompressedData;
    /**
     * indicates the total number of the bytes decompressed by this compression algorithm
     */
    private int totalDecompressedData;
    /**
     * indicates the total compression time used by this compression algorithm, measured in seconds
     */
    private double totalCompressionTime;
    /**
     * indicates the total decompression time used by this compression algorithm, measured in seconds
     */
    private double totalDecompressionTime;
    /**
     * indicates the total compression ratio
     */
    private double totalCompressionRatio;

    //------------------------------------------------Constructor-----------------------------------------------------//

    /**
     * construct a GlobalStatistic without parameter
     */
    public GlobalStatistic(){}

    /**
     * construct a GlobalStatistic with parameters
     * @param numCompression indicates the total number of the compression realized by this compression algorithm
     * @param numDecompression indicates the total number of the decompression realized by this compression algorithm
     * @param totalCompressedData indicates the total number of the bytes compressed by this compression algorithm
     * @param totalDecompressedData indicates the total number of the bytes decompressed by this compression algorithm
     * @param totalCompressionTime indicates the total compression time used by this compression algorithm, measured in seconds
     * @param totalDecompressionTime indicates the total decompression time used by this compression algorithm, measured in seconds
     * @param totalCompressionRatio indicates the total compression ratio
     */
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

    /**
     * get the total number of the compression realized
     * @return attribute numCompression
     */
    public int getNumCompression() {
        return numCompression;
    }

    /**
     * get the total number of the decompression realized
     * @return attribute numDecompression
     */
    public int getNumDecompression() {
        return numDecompression;
    }

    /**
     * get the total number of the decompression realized
     * @return attribute totalCompressedData
     */
    public int getTotalCompressedData() {
        return totalCompressedData;
    }

    /**
     * get the total number of the bytes decompressed
     * @return attribute totalDescompressedData
     */
    public int getTotalDecompressedData() {
        return totalDecompressedData;
    }

    /**
     * get the total compression time used
     * @return attribute totalCompressionTime
     */
    public double getTotalCompressionTime() {
        return totalCompressionTime;
    }

    /**
     * get the total decompression time used
     * @return attribute totalDecompressionTime
     */
    public double getTotalDecompressionTime() {
        return totalDecompressionTime;
    }

    /**
     * get the total compression ratio
     * @return attribute totalCompressionRatio
     */
    public double getTotalCompressionRatio() {
        return totalCompressionRatio;
    }

    //---------------------------------------------------Getters------------------------------------------------------//


    //---------------------------------------------------Setters------------------------------------------------------//

    /**
     * get the total number of the compression realized
     * @param numCompression new numCompression to be edited
     */
    public void setNumCompression(int numCompression) { this.numCompression = numCompression; }

    /**
     * set the total number of the decompression realized
     * @param numDecompression new numDecompression to be edited
     */
    public void setNumDecompression(int numDecompression) { this.numDecompression = numDecompression; }

    /**
     * set the total number of the decompression realized
     * @param totalCompressedData new numDecompression to be edited
     */
    public void setTotalCompressedData(int totalCompressedData) { this.totalCompressedData = totalCompressedData; }

    /**
     * set the total number of the bytes decompressed
     * @param totalDecompressedData new totalDecompressedData to be edited
     */
    public void setTotalDecompressedData(int totalDecompressedData) { this.totalDecompressedData = totalDecompressedData; }

    /**
     * set the total compression time used
     * @param totalCompressionTime new totalCompressionTime to be edited
     */
    public void setTotalCompressionTime(double totalCompressionTime) { this.totalCompressionTime = totalCompressionTime; }

    /**
     * set the total decompression time used
     * @param totalDecompressionTime new totalDecompressionTime to be edited
     */
    public void setTotalDecompressionTime(double totalDecompressionTime) { this.totalDecompressionTime = totalDecompressionTime; }

    /**
     * set the total compression ratio
     * @param totalCompressionRatio new totalCompressionRatio to be edited
     */
    public void setTotalCompressionRatio(double totalCompressionRatio) { this.totalCompressionRatio = totalCompressionRatio; }

    //---------------------------------------------------Setters------------------------------------------------------//


    //---------------------------------------------------Adders-------------------------------------------------------//

    /**
     * add one to attribute numCompression
     */
    public void addNumCompression() { ++numCompression; }

    /**
     * add one to attribute numDecompression
     */
    public void addNumDecompression() { ++numDecompression; }

    /**
     * add number of newly compressed data to totalCompressedData
     * @param compressedData number of newly compressed data
     */
    public void addTotalCompressedData(int compressedData) { totalCompressedData += compressedData; }

    /**
     * add number of newly decompressed data to totalDecompressedData
     * @param deecompressedData number of newly decompressed data
     */
    public void addTotalDecompressedData(int deecompressedData) { totalDecompressedData += deecompressedData; }

    /**
     * add time of newly compressed data used to totalCompressionTime
     * @param compressionTime time of newly compressed data used
     */
    public void addTotalCompressionTime(double compressionTime) { totalCompressionTime += compressionTime; }

    /**
     * add time of newly decompressed data used to totalDecompressionTime
     * @param decompressionTime time of newly decompressed data used
     */
    public void addTotalDecompressionTime(double decompressionTime) { totalDecompressionTime += decompressionTime; }

    /**
     * add ratio of newly compressed data to totalCompressionRatio
     * @param compressionRatio ratio of newly compressed data
     */
    public void addTotalCompressionRatio(double compressionRatio) { totalCompressionRatio += compressionRatio; }

    //---------------------------------------------------Adders-------------------------------------------------------//
}
