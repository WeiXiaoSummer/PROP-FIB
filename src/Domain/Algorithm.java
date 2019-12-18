package Domain;

import Commons.DomainLayerException;

import java.io.ByteArrayOutputStream;

/**
 * Represents a generic compression algorithm class.
 */
public abstract class Algorithm {

    /**
     * Statistics of this algorithm.
     */
    protected GlobalStatistic globalStatistic; // represents the statistic of this this algorithm

    //------------------------------------------------Constructor-----------------------------------------------------//

    /**
     * Create a new Algorithm with the given GlobalStatistic object.
     * @param globalStatistic the GlobalStatistic object to be loaded.
     */
    public Algorithm(GlobalStatistic globalStatistic) { this.globalStatistic = globalStatistic; }

    //------------------------------------------------Constructor-----------------------------------------------------//


    //---------------------------------------------------Getters------------------------------------------------------//

    /**
     * Returns the attribute globalStatistic of this algorithm.
     * @return the globalStatisctic of this algorithm.
     */
    public GlobalStatistic getGlobalStatistic() { return this.globalStatistic; }

    //---------------------------------------------------Getters------------------------------------------------------//


    //---------------------------------------------------Setters------------------------------------------------------//

    /**
     * Sets the value of the attribute globalStatistic.
     * @param globalStatistic the GlobalStatistic to be set.
     */
    public void setGlobalStatistic(GlobalStatistic globalStatistic) { this.globalStatistic = globalStatistic; }

    //---------------------------------------------------Setters------------------------------------------------------//


    //--------------------------------------------Compression and Decompression---------------------------------------//

    /**
     * Compress the content of the inFile and write the result into the temporal buffer compressedFile.
     * @param inFile Fitxer to be compressed
     * @param compressedFile ByteArrayOutputStream to be wrote
     * @return the compression statistic in the form of a Object array:
     *         -(int)First position contains the original content size expressed in bytes.
     *         -(int)Second position contains the compressed content size expressed in bytes.
     *         -(double)Third position contains the compression time expressed in s.
     * @throws DomainLayerException
     */
    public abstract Object[] comprimir(Fitxer inFile, ByteArrayOutputStream compressedFile) throws DomainLayerException;

    /**
     * Decompress the compressed content and stores the decompressed content in the outPutFile
     * @param compressedContent data to be decompress
     * @param outPutFile Fitxer to be wrote
     * @return the decompression statistic in the form of a Object array:
     *         -(int)First position contains the decompressed content size expressed in bytes.
     *         -(int)Second position contains the compressed content size expressed in bytes.
     *         -(double)Third position contains the decompression time expressed in s.
     * @throws DomainLayerException
     */
    public abstract Object[] descomprimir(byte[] compressedContent, Fitxer outPutFile) throws DomainLayerException;

    //--------------------------------------------Compression and Decompression---------------------------------------//

}