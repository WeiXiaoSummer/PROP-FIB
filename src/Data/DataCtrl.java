package Data;

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

    public String getInputFile(String Path) {
        return io.getInputFile(Path);
    }

    public void outputFile(String content, String Path) {
        io.outputFile(content, Path);
    }

}