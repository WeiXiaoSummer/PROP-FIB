package Domain;
import java.util.*;

public class LZ78 extends Algorithm {

    public LZ78(int numCompression, int numDecompression, int totalCompressedData,
                int totalDecompressedData, double totalCompressionTime, double totalDecompressionTime,
                double averageCompressionRatio) {
        super(numCompression, numDecompression, totalCompressedData, totalDecompressedData, totalCompressionTime,
                totalDecompressionTime, averageCompressionRatio);
    }

    @Override
    public Fitxer comprimir(Fitxer file) {
        long startTime=System.currentTimeMillis();
        String text = file.getFileContent();
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
        double compressTime = (double)(endTime-startTime)* 0.001;
        globalStatistic.setNumCompression(globalStatistic.getNumCompression()+1);
        globalStatistic.setTotalCompressedData(globalStatistic.getTotalCompressedData()+text.length());
        globalStatistic.setTotalCompressionTime(globalStatistic.getTotalCompressionTime()+compressTime);
        //globalStatistic.setAverageCompressionRatio(globalStatistic.getTotalCompressedData()/globalStatistic.getTotalDecompressedData());
        return new Fitxer(null, ".lz78", outStream.toString());
    }

    @Override
    public Fitxer descomprimir(Fitxer file) {
        long startTime=System.currentTimeMillis();
        String text = file.getFileContent();
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
        globalStatistic.setNumDecompression(globalStatistic.getNumDecompression()+1);
        globalStatistic.setTotalDecompressedData(globalStatistic.getTotalDecompressedData()+text.length());
        globalStatistic.setTotalDecompressionTime(globalStatistic.getTotalDecompressionTime()+descompressTime);
        //globalStatistic.setAverageCompressionRatio(globalStatistic.getTotalCompressedData()/globalStatistic.getTotalDecompressedData());
        return new Fitxer(null, ".txt", outStream.toString());
    }

    //change the int s to char
    private char InttoChar(int s) {
        char c = (char) s;
        return c;
    }

    //change the char to int
    private int chartoInt(char s) {
        return Integer.valueOf(s);
    }



}
