package Data;

import java.io.*;

public class IO {

    public String getInputTextFile(String Path) {
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
