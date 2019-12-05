package Drivers.LZ78;

import javafx.util.Pair;

import java.io.*;

public class IO {

    public String getInputFile(String Path) {
        String content = "";
        try {
            FileReader bufferReader = new FileReader(Path);
            short c = (short)bufferReader.read();
            while (c != -1) {
                content += (char)c;
                c = (short)bufferReader.read();
            }
            bufferReader.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return content;
    }

    public void outputFile(String content, String Path) {
        try {
            BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(Path, true));
            bufferWriter.write(content);
            bufferWriter.flush();
            bufferWriter.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Pair<Integer, Integer> getImgDimension(String Path) {
        Pair<Integer, Integer> Dimension = new Pair<>(0, 0);
        try (FileInputStream byteReader = new FileInputStream(Path)){
            Integer width = 0;
            Integer height = 0;
            byteReader.read();
            byteReader.read();
            byteReader.read();

            byte aux = (byte) byteReader.read();
            while (aux != ' ') {
                width = width * 10 + (aux - '0');
                aux = (byte) byteReader.read();
            }
            aux = (byte) byteReader.read();
            while (aux != '\n') {
                height = height * 10 + (aux - '0');
                aux = (byte) byteReader.read();
            }
            Dimension = new Pair<>(width, height);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return Dimension;
    }

    public static byte[] getInputImg(String Path, Integer width, Integer height){
        byte[] input = new byte[width*height*3];
        try (FileInputStream byteReader = new FileInputStream(Path)){
            int headerLength = 9 + width.toString().length()+height.toString().length();
            byteReader.skip(headerLength);
            byteReader.read(input);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return input;
    }

    public static void outPutImg(String Path, Integer width, Integer height, byte[] RGB) {
        try (FileOutputStream bufferWriter = new FileOutputStream(Path)){
            String header = "P6\n" + width.toString() + " " + height.toString() + "\n255\n";
            bufferWriter.write(header.getBytes());
            bufferWriter.write(RGB);
            bufferWriter.flush();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}