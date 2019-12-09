package Domain;
import javafx.util.Pair;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

public class LZ78 extends Algorithm {

    public LZ78(int numCompression, int numDecompression, int totalCompressedData,
                int totalDecompressedData, double totalCompressionTime, double totalDecompressionTime,
                double averageCompressionRatio) {
        super(numCompression, numDecompression, totalCompressedData, totalDecompressedData, totalCompressionTime,
                totalDecompressionTime, averageCompressionRatio);
    }

    @Override
    public Pair<Double, Double> comprimir(Fitxer file, ByteArrayOutputStream compressedFile) throws IOException {
        long startTime=System.currentTimeMillis();
        byte[] text = file.getContent();

        TrieTree trieTree = new TrieTree();
        byte[] outStream = (trieTree.comprimir(text)).toByteArray();

        long endTime=System.currentTimeMillis(); // get the time when end the compression
        byte fileNameLength = (byte) file.getFile().getName().length();
        double compressTime = (double)(endTime-startTime)* 0.001;
        double compressRatio = text.length/outStream.length;

        try {
            byte[] compressedContent = outStream;
            byte[] compressedContentSize = ByteBuffer.allocate(4).putInt(compressedContent.length).array();
            compressedFile.write("LZ78".getBytes());
            compressedFile.write(fileNameLength); compressedFile.write(file.getFile().getName().getBytes());
            compressedFile.write(compressedContentSize);
            compressedFile.write(compressedContent);
        }
        catch (Exception e) {e.printStackTrace();}

        globalStatistic.setNumCompression(globalStatistic.getNumCompression()+1);
        globalStatistic.setTotalCompressedData(globalStatistic.getTotalCompressedData()+text.length);
        globalStatistic.setTotalCompressionTime(globalStatistic.getTotalCompressionTime()+compressTime);
        globalStatistic.setTotalCompressionRatio(globalStatistic.getTotalCompressionRatio()+compressRatio);
        Pair<Double, Double> compressionStatistic = new Pair<>(compressTime, compressRatio);
        return compressionStatistic;
    }

    @Override
    public Pair<Double, Double> descomprimir(byte[] compressedContent, Fitxer file) throws IOException {
        long startTime=System.currentTimeMillis();
        byte[] text = compressedContent;

        HashMap<Integer, byte[]> dic = new HashMap<>();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        int key = 0;
        int i = 0;
        boolean isInt = true;
        while (i < text.length) {
            if (isInt) {
                key = byteToInt(text, i);
                i += 4;
                if (key != 0) {
                    byte[] value = dic.get(key);
                    outStream.write(value);
                    if (i < text.length){
                        dic.put(dic.size()+1, addByte(value, text[i]));
                    }
                }
                else {
                    byte[] in = new byte[1];
                    in[0] = text[i];
                    dic.put(dic.size()+1, in);
                }isInt = false;
            }
            else {
                outStream.write(text[i]);
                ++i;
                isInt = true;
            }
        }
        long endTime=System.currentTimeMillis(); // get the time when end the compression
        double descompressTime = (double)(endTime-startTime)* 0.001;
        double compressRatio = outStream.toByteArray().length/text.length;
        globalStatistic.setNumDecompression(globalStatistic.getNumDecompression()+1);
        globalStatistic.setTotalDecompressedData(globalStatistic.getTotalDecompressedData()+compressedContent.length);
        globalStatistic.setTotalDecompressionTime(globalStatistic.getTotalDecompressionTime()+descompressTime);
        file.setContent(outStream.toString().getBytes());
        return new Pair<>(descompressTime, compressRatio);
    }

    public int byteToInt(byte[] bytes, int i) {
        return (bytes[i]&0xff)<<24
                | (bytes[i+1]&0xff)<<16
                | (bytes[i+2]&0xff)<<8
                | (bytes[i+3]&0xff);
    }
    public byte[] addByte(byte[] bytes, byte b) {
        byte[] in = new byte[bytes.length + 1];
        for (int i = 0; i < bytes.length; ++i) {
            in[i] = bytes[i];
        }
        in[bytes.length] = b;
        return in;
    }

}
