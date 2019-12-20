package Domain;

import Commons.DomainLayerException;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * Represents a LZ78 compression algorithm class.
 */
public class LZ78 extends Algorithm {

    public LZ78(GlobalStatistic estadistiques) {
        super(estadistiques);
    }

    /**
     * Compress the content of the inFile and write the result into the temporal buffer compressedFile.
     * @param file Fitxer to be compressed
     * @param compressedFile ByteArrayOutputStream to be wrote
     * @return the compression statistic in the form of a Object array:
     *          -(int)First position contains the original content size expressed in bytes.
     *          -(int)Second position contains the compressed content size expressed in bytes.
     *          -(double)Third position contains the compression time expressed in s.
     * @throws DomainLayerException
     */
    @Override
    public Object[] comprimir(Fitxer file, ByteArrayOutputStream compressedFile) throws DomainLayerException {
        try {
            long startTime=System.currentTimeMillis();
            byte[] text = file.getContent();
            globalStatistic.addNumCompression();
            Object[] compressionStatistic = {0d, 0d, 0d};

            // compress
            TrieTree trieTree = new TrieTree();
            byte[] compressedContent = (trieTree.comprimir(text)).toByteArray();

            // get the time when end the compression
            long endTime=System.currentTimeMillis();

            byte fileNameLength = (byte) file.getFile().getName().length();

            byte[] compressedContentSize = ByteBuffer.allocate(4).putInt(compressedContent.length).array();
            compressedFile.write("LZ78".getBytes());
            compressedFile.write(fileNameLength); compressedFile.write(file.getFile().getName().getBytes());
            compressedFile.write(compressedContentSize);
            compressedFile.write(compressedContent);

            if (text.length > 0) {
                compressionStatistic[0] = text.length;
                compressionStatistic[1] = compressedContent.length;
                compressionStatistic[2] = (double) (endTime - startTime) * 0.001;
                globalStatistic.addTotalCompressedData(text.length);
                globalStatistic.addTotalCompressionTime((double) compressionStatistic[2]);
                globalStatistic.addTotalCompressionRatio((double)text.length / (double)compressedContent.length);
            }
            return compressionStatistic;
        }
        catch (Exception e) {
            throw new DomainLayerException( "An error has occurred while compressing the file:\n\n"+file.getFile().getPath()+
                    "\n\nCompression aborted\n\n" + e.getMessage());}
    }

    /**
     * Decompress the compressed content and stores the decompressed content in the outPutFile
     * @param compressedContent data to be decompress
     * @param file data to be decompress
     * @return the decompression statistic in the form of a Object array:
     *          -(int)First position contains the decompressed content size expressed in bytes.
     *          -(int)Second position contains the compressed content size expressed in bytes.
     *          -(double)Third position contains the decompression time expressed in s.
     * @throws DomainLayerException
     */
    @Override
    public Object[] descomprimir(byte[] compressedContent, Fitxer file) throws DomainLayerException {
        try {
            long startTime=System.currentTimeMillis();
            byte[] text = compressedContent;
            ArrayList<byte[]> dic = new ArrayList<>();
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            int key;
            int i = 0;
            //the structure of text is a int, a char, a int, a char...
            while (i < text.length) {
                // now we read the int, it's 4 bytes
                // transfer byte[] to int
                key = byteToInt(text, i);
                i += 4;
                // it means that i'm reading some repeated words and I can get them in dic[key-1].
                if (key != 0) {
                    byte[] value = dic.get(key-1);
                    // output the repeated words
                    outStream.write(value);
                    if (i < text.length){
                        // add a byte into a byte[]
                        // and insert it new byte[] into dic
                        dic.add(addByte(value, text[i]));
                    }
                }
                else { // no repested words
                    byte[] in = new byte[1];
                    in[0] = text[i];
                    // and insert new byte[] into dic
                    dic.add(in);
                }
                // now we read the char, and output it.
                if (i+1 < text.length)outStream.write(text[i]);
                ++i;
            }
            long endTime=System.currentTimeMillis(); // get the time when end the compression
            double decompressTime = (double)(endTime-startTime)* 0.001;
            globalStatistic.addNumDecompression();
            globalStatistic.addTotalDecompressedData(compressedContent.length);
            globalStatistic.addTotalDecompressionTime(decompressTime);
            byte[] decompressedContent = outStream.toString().getBytes();
            file.setContent(decompressedContent);
            Object[] compressionStatistic = {(double)decompressedContent.length, (double)compressedContent.length, decompressTime};
            return compressionStatistic;
        }
        catch (Exception e) {
            throw new DomainLayerException("An error has occurred while decompressing the file:\n\n" + file.getFile().getName()+"\n\n" +
                    "The compressed content is corrupted, decompression aborted.");}
    }

    /**
     * transfer 4 bytes to int
     * @param bytes byte array to transfer
     * @param i indicate the position of bytes we wants to start the transfer
     * @return int transfered
     */
    private int byteToInt(byte[] bytes, int i) {
        return (bytes[i]&0xff)<<24
                | (bytes[i+1]&0xff)<<16
                | (bytes[i+2]&0xff)<<8
                | (bytes[i+3]&0xff);
    }

    /**
     * add a byte at end of byte[]
     * @param bytes the byte array witch we want to add a byte
     * @param b the byte witch we want to add at end of bytes
     * @return a byte array added the b at end.
     */
    private byte[] addByte(byte[] bytes, byte b) {
        byte[] in = new byte[bytes.length + 1];
        for (int i = 0; i < bytes.length; ++i) {
            in[i] = bytes[i];
        }
        in[bytes.length] = b;
        return in;
    }

}
