package Drivers.DomainCtrl;

import javafx.util.Pair;

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

    public Pair<Integer, Integer> getImgDimension(String Path) { return io.getImgDimension(Path);}

    public byte[] getInputImg(String Path, Integer width, Integer height) { return io.getInputImg(Path, width, height);}

    public void outPutImg(String Path, Integer width, Integer height, byte[] RGB) {
        io.outPutImg(Path, width, height, RGB);
    }

}