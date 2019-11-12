package Data;

import java.io.*;

public class IO {

    public String getInputTextFile(String Path) {
        String content = "";
        /*try {
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
        return content;*/
        try {
            File file = new File(Path);
            BufferedReader bufferReader = new BufferedReader(new FileReader(file));
            String line = bufferReader.readLine();
            while (line != null) {
                content += line + "\n";
                line = bufferReader.readLine();
            }
            bufferReader.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        if (content.length()>0) return content.substring(0,content.length()-1);
        else return content;
    }

    public void outputTextFile(String content, String Path) {
        try {
            File outputFile = new File(Path);
            BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(Path, true));
            bufferWriter.write(content);
            bufferWriter.flush();
            bufferWriter.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
