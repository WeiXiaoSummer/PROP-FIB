package Domain;

public class JPEG extends Algorithm {
    public JPEG(int numCompression, int numDecompression, int totalCompressedData,
                int totalDecompressedData, double totalCompressionTime, double totalDecompressionTime,
                double averageCompressionRatio) {
        super(numCompression, numDecompression, totalCompressedData, totalDecompressedData, totalCompressionTime,
                totalDecompressionTime, averageCompressionRatio);
    }
    @Override
    public Fitxer comprimir(Fitxer file) {
        return null;
    }

    @Override
    public Fitxer descomprimir(Fitxer file) {
        return null;
    }
}
