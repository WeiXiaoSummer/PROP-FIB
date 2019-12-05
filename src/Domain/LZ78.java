package Domain;
import javafx.util.Pair;

import java.io.ByteArrayOutputStream;
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
    public Pair<Double, Double> comprimir(Fitxer file, ByteArrayOutputStream compressedFile) {
        long startTime=System.currentTimeMillis();
        String text = new String(file.getContent());
        HashMap<String, Integer> dic = new HashMap<>();
        String inChar;
        String prefix = "";
        StringBuilder outStream = new StringBuilder();
        int i = 0;
        while (i < text.length()) {
            inChar = text.substring(i, i+1);
            if (dic.containsKey(prefix+inChar)) {
                if (i+1 == text.length()) {
                    outStream.append(InttoChar(dic.get(prefix+inChar)));
                }
                prefix += inChar;
            }

            else {
                if (prefix.isEmpty()) {
                    outStream.append((char) 0); //int key --> char key
                }
                else {
                    outStream.append(InttoChar(dic.get(prefix)));
                }
                dic.put(prefix+inChar,dic.size()+1);
                outStream.append(inChar);
                prefix = "";
            }
            ++i;
        }
        long endTime=System.currentTimeMillis(); // get the time when end the compression
        byte fileNameLength = (byte) file.getFile().getName().length();
        double compressTime = (double)(endTime-startTime)* 0.001;
        double compressRatio = text.length()/outStream.length();

        try {
            byte[] compressedContent = outStream.toString().getBytes("UTF-8");
            byte[] compressedContentSize = ByteBuffer.allocate(4).putInt(compressedContent.length).array();
            compressedFile.write("LZ78".getBytes());
            compressedFile.write(fileNameLength); compressedFile.write(file.getFile().getName().getBytes());
            compressedFile.write(compressedContentSize); compressedFile.write(compressedContent);
        }
        catch (Exception e) {e.printStackTrace();}

        globalStatistic.setNumCompression(globalStatistic.getNumCompression()+1);
        globalStatistic.setTotalCompressedData(globalStatistic.getTotalCompressedData()+text.length());
        globalStatistic.setTotalCompressionTime(globalStatistic.getTotalCompressionTime()+compressTime);
        globalStatistic.setTotalCompressionRatio(globalStatistic.getTotalCompressionRatio()+compressRatio);
        Pair<Double, Double> compressionStatistic = new Pair<>(compressTime, compressRatio);
        return compressionStatistic;
    }

    @Override
    public Pair<Double, Double> descomprimir(byte[] compressedContent, Fitxer file) {
        long startTime=System.currentTimeMillis();
        String text = "";

        try {
            text = new String(compressedContent, "UTF-8");
        }
        catch (Exception e) {e.printStackTrace();}

        HashMap<Integer, String> dic = new HashMap<>();
        StringBuilder outStream = new StringBuilder();
        int i = 0;
        while (i < text.length()) {
            if (i%2 == 0) {
                int key = chartoInt(text.charAt(i));
                if (key == 0) {
                    outStream.append(text.charAt(i+1));
                    dic.put(dic.size()+1,text.substring(i+1,i+2));
                }
                else if (i+1 < text.length()){
                    outStream.append(dic.get(key) + text.charAt(i+1));
                    dic.put(dic.size()+1,dic.get(key) + text.substring(i+1,i+2));
                }
                else outStream.append(dic.get(key));
            }
            ++i;
        }
        long endTime=System.currentTimeMillis(); // get the time when end the compression
        double descompressTime = (double)(endTime-startTime)* 0.001;
        double compressRatio = outStream.length()/text.length();
        globalStatistic.setNumDecompression(globalStatistic.getNumDecompression()+1);
        globalStatistic.setTotalDecompressedData(globalStatistic.getTotalDecompressedData()+compressedContent.length);
        globalStatistic.setTotalDecompressionTime(globalStatistic.getTotalDecompressionTime()+descompressTime);
        file.setContent(outStream.toString().getBytes());
        return new Pair<>(descompressTime, compressRatio);
    }

    //change the int s to char
    private char InttoChar(int i) {
        char c = (char) i;
        return c;
    }

    //change the char to int
    private int chartoInt(char c) {
        return Integer.valueOf(c);
    }



}
