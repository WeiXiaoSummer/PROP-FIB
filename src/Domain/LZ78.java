package Domain;
import java.util.*;

public class LZ78 extends Algorithm {
    public LZ78(){}

    public LZ78(int numCompression, int numDecompression, int totalCompressedData,
                int totalDecompressedData, double totalCompressionTime, double totalDecompressionTime,
                double averageCompressionRatio) {
        super(numCompression, numDecompression, totalCompressedData, totalDecompressedData, totalCompressionTime,
                totalDecompressionTime, averageCompressionRatio);
    }

    public String comprimir(String content) {
        long startTime=System.currentTimeMillis();
        Dictionary<String, Integer> dic = new Hashtable<>();
        String inChar;
        String prefix = "";
        String outStream = "";
        for (int i = 0; i < content.length(); ++i) {
            inChar = content.substring(i, i+1);
            if (((Hashtable<String, Integer>) dic).containsKey(prefix+inChar)) {
                if (i+1 == content.length()) outStream += toASCII(dic.get(prefix+inChar));
                prefix += inChar;
            }

            else {
                if (prefix.isEmpty()) outStream += (char) 0; //int key --> char key
                else {
                    outStream += toASCII(dic.get(prefix));
                }
                outStream += inChar;
                dic.put(prefix+inChar,dic.size()+1);
                prefix = "";
            }
        }
        long endTime=System.currentTimeMillis(); // get the time when end the compression
        double compressTime = (double)(endTime-startTime)* 0.001;
        System.out.print("Time:"+ compressTime +"s\n");
        System.out.print("length:"+outStream.length() +"\n");
        globalStatistic.setNumCompression(globalStatistic.getNumCompression()+1);
        globalStatistic.setTotalCompressedData(globalStatistic.getTotalCompressedData()+content.length());
        globalStatistic.setTotalCompressionTime(globalStatistic.getTotalCompressionTime()+compressTime);
        globalStatistic.setAverageCompressionRatio(globalStatistic.getTotalCompressedData()/globalStatistic.getTotalDecompressedData());
        return outStream;
    }

    public String descomprimir(String content) {
        long startTime=System.currentTimeMillis();
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
        long endTime=System.currentTimeMillis(); // get the time when end the compression
        double descompressTime = (double)(endTime-startTime)* 0.001;
        globalStatistic.setNumDecompression(globalStatistic.getNumDecompression()+1);
        globalStatistic.setTotalDecompressedData(globalStatistic.getTotalDecompressedData()+content.length());
        globalStatistic.setTotalDecompressionTime(globalStatistic.getTotalDecompressionTime()+descompressTime);
        globalStatistic.setAverageCompressionRatio(globalStatistic.getTotalCompressedData()/globalStatistic.getTotalDecompressedData());
        return outStream;
    }

    //change the int s to ASCII
    public char toASCII(int s) {
        char c = (char) s;
        return c;
    }

    //change the ASCII to int
    public int ASCIItoInt(char s) {
        return Integer.valueOf(s);
    }

}
