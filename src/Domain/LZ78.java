package Domain;
import Domain.DomainCtrl;

import java.io.*;
import java.io.File;
import java.util.*;

public class LZ78 extends Algorithm {

    public LZ78(int numCompression, int numDecompression, int totalCompressedData,
                int totalDecompressedData, double totalCompressionTime, double totalDecompressionTime,
                double averageCompressionRatio) {
        super(numCompression, numDecompression, totalCompressedData, totalDecompressedData, totalCompressionTime,
                totalDecompressionTime, averageCompressionRatio);
    }

    public String comprimir(String content) {
        long startTime = System.nanoTime();
        Dictionary<Integer, String> dic = new Hashtable();
        String inChar;
        String prefix = "";
        String outStream = "LZ78";
        for (int i = 0; i < content.length(); ++i) {
            inChar = content.substring(i, i+1);
            if (((Hashtable<Integer, String>) dic).containsValue(prefix+inChar)) {
                if (i+1 == content.length()) outStream += toASCII(findKey(dic,prefix+inChar));
                prefix += inChar;
            }

            else {
                if (prefix.isEmpty()) outStream += toASCII(0); //int key --> char key
                else {
                    outStream += toASCII(findKey(dic,prefix));
                }
                outStream += inChar;
                dic.put(dic.size()+1, prefix+inChar);
                prefix = "";
            }
        }
        //staticglobal
        long endTime = System.nanoTime();
        double compressTime = (double)(endTime-startTime)/1000000;
        globalStatistic.setNumCompression(globalStatistic.getNumCompression()+1);
        globalStatistic.setTotalCompressedData(globalStatistic.getTotalCompressedData()+content.length());
        globalStatistic.setTotalCompressionTime(globalStatistic.getTotalCompressionTime()+compressTime);
        globalStatistic.setAverageCompressionRatio(globalStatistic.getTotalCompressedData()/globalStatistic.getTotalDecompressedData());
        return outStream;
    }

    public String descomprimir(String content) {
        long startTime = System.nanoTime();
        Dictionary<Integer, String> dic = new Hashtable();
        String outStream = "";
        int i = 0;
        while (i < content.length()) {
            if (i%2 == 0) {
                int key = ASCIItoInt(content.charAt(i));
                if (key == 0) {
                    outStream += content.charAt(i+1);
                    dic.put(dic.size()+1,content.substring(i+1,i+2));
                }
                else if (i+1 < content.length()){
                    outStream += dic.get(key) + content.charAt(i+1);
                    dic.put(dic.size()+1,dic.get(key) + content.substring(i+1,i+2));
                }
                else outStream += dic.get(key);
            }
            ++i;
        }
        long endTime = System.nanoTime();
        double descompressTime = (double)(endTime-startTime)/1000000;
        globalStatistic.setNumDecompression(globalStatistic.getNumDecompression()+1);
        globalStatistic.setTotalDecompressedData(globalStatistic.getTotalDecompressedData()+content.length());
        globalStatistic.setTotalDecompressionTime(globalStatistic.getTotalDecompressionTime()+descompressTime);
        globalStatistic.setAverageCompressionRatio(globalStatistic.getTotalCompressedData()/globalStatistic.getTotalDecompressedData());
        return outStream;
    }

    // get the key of value s in the dictionary dic
    public int findKey(Dictionary dic ,String s) {
        Enumeration<Integer> key = dic.keys();
        while (key.hasMoreElements()) {
            int k = key.nextElement();
            if (dic.get(k).equals(s)) {
                return k;
            }
        }
        return 0;
    }

    //change the int s to ASCII
    public char toASCII(int s) {
        char c = (char) s;
        c += 32;
        return c;
    }

    //change the ASCII to int
    public int ASCIItoInt(char s) {
        return Integer.valueOf(s-32);
    }

}
