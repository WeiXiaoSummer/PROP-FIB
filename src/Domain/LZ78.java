package Domain;
import Domain.DomainCtrl;

import java.io.*;
import java.io.File;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

public class LZ78 extends Algorithm {
    public LZ78(int numCompression, int numDecompression, int totalCompressedData,
                int totalDecompressedData, double totalCompressionTime, double totalDecompressionTime,
                double averageCompressionRatio) {
        super(numCompression, numDecompression, totalCompressedData, totalDecompressedData,
                totalCompressionTime, totalDecompressionTime, averageCompressionRatio);
    }

    public void comprimir(String content, String savePath) {
        Dictionary<Integer, String> dic = new Hashtable();
        String inChar;
        String prefix = "";
        String outStream = "";
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
        DomainCtrl.getInstance().saveFileTo(outStream, savePath);
    }

    public static int findKey(Dictionary dic ,String s) {
        Enumeration<Integer> key = dic.keys();
        while (key.hasMoreElements()) {
            int k = key.nextElement();
            if (dic.get(k).equals(s)) {
                return k;
            }
        }
        return 0;
    }

    public static char toASCII(int s) {
        char c = (char) s;
        c += 32;
        return c;
    }

    public static void descomprimir(String text) {
        Dictionary<Integer, String> dic = new Hashtable();
        String outStream = "";
        int i = 0;
        while (i < text.length()) {
            if (i%2 == 0) {
                int key = ASCIItoInt(text.charAt(i));
                if (key == 0) {
                    outStream += text.charAt(i+1);
                    dic.put(dic.size()+1,text.substring(i+1,i+2));
                }
                else if (i+1 < text.length()){
                    outStream += dic.get(key) + text.charAt(i+1);
                    dic.put(dic.size()+1,dic.get(key) + text.substring(i+1,i+2));
                }
                else outStream += dic.get(key);
            }
            ++i;
        }
        System.out.print( "After descompression:\n");
        System.out.print(outStream +"\n");
        System.out.print("length:" +outStream.length() +"\n");
        outPutTextFile(outStream,"C:\\Users\\sheng\\Documents\\output.txt");
    }

    public static int ASCIItoInt(char s) {
        return Integer.valueOf(s-32);
    }
    public static String getInputTextFile(String Path) {
        String content = "";
        try {
            BufferedReader bufferReader = new BufferedReader(new FileReader(Path));
            String line = bufferReader.readLine();
            while (line != null) {
                content += line + "\r\n";
                line = bufferReader.readLine();
            }
            bufferReader.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return content.substring(0,content.length()-2);
    }

    public static void outPutTextFile(String content, String Path) {
        try {
            java.io.File file = new File(Path);
            BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(file, false));
            bufferWriter.write(content);
            bufferWriter.flush();
            bufferWriter.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
