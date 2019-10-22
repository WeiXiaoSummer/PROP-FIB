package Data;

import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;

public class IO {

    public String getInputTextFile(String Path) {
        String content = "";
        try {
            BufferedReader bufferReader = new BufferedReader(new FileReader(Path));
            String line = bufferReader.readLine();
            while (line != null) {
                content += line;
                line = bufferReader.readLine();
            }
            bufferReader.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return content;
    }

    public void outPutTextFile(String content, String Path) {
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
