package Data;

import java.io.InputStreamReader;

public class DataCtrl {

    private static DataCtrl instance = null;
    private IO io = new IO();

    private DataCtrl() {}

    public static DataCtrl getInstance() {
        if (instance == null) {
            instance = new DataCtrl();
        }
        return instance;
    }

    public String getInputTextFile(String Path) {
        return io.getInputTextFile(Path);
    }

    public void outPutTextFile(String content, String Path) {
        io.outputTextFile(content, Path);
    }


}
