package Domain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class DomainCtrl {

    private static DomainCtrl instance = null;
    private LZ78 lz78 = new LZ78(0,0,0,0,0,0,0);
    private LZSS lzss = new LZSS(0,0,0,0,0,0,0);
    private JPEG jpeg = new JPEG(0,0,0,0,0,0,0);
    private GlobalHistory globalHistory = new GlobalHistory();

    private DomainCtrl(){

    }

    public static DomainCtrl getInstance() {
        if (instance == null) {
            instance = new DomainCtrl();
        }
        return instance;
    }

    public void compressFileTo(String filePath, String savePath, String algoritmeType) throws IOException {
        String content = loadFile(filePath); // get the content of file
        //Fitxer file = new Fitxer(filePath, content, getFileType(filePath));
        String contentCompressed = "";
        long startTime = System.nanoTime(); // get the time when start the compression
        //Choose the right algorithm and compressed file
        switch (algoritmeType) {
            case "LZ78":
                contentCompressed = lz78.comprimir(content);
                savePath += ".lz78";
                break;
            case "LZSS":
                contentCompressed = lzss.comprimir(content);
                savePath += ".lzss";
                break;
            case "JPEG":
                //contentCompressed = jpeg.comprimir(content);
                savePath += ".jpeg";
                break;
        }
        long endTime = System.nanoTime(); // get the time when end the compression
        double compressTime = (double)(endTime-startTime)/1000000;
        saveFileTo(contentCompressed, savePath);
        //history
        LocalHistory localHistory = new LocalHistory(filePath, savePath, getFileType(filePath), "Compression", algoritmeType, (double)content.length()/(double)contentCompressed.length(), compressTime);
        globalHistory.addLocalHistory(localHistory);
    }

    public void compressFolderTo(String folderPath, String savePath) throws IOException {
        java.io.File file = new java.io.File(folderPath);
        java.io.File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                compressFileTo(tempList[i].toString(), folderPath, "AUTO");
            }
            if (tempList[i].isDirectory()) {
                compressFolderTo(tempList[i].toString(), tempList[i].toString());
            }
        }
    }

    public void decompressFileTo(String filePath, String savePath) throws IOException {
        String content = Data.DataCtrl.getInstance().getInputTextFile(filePath);
        //Fitxer file = new Fitxer(filePath, content, getFileType(filePath));
        String algoritme = "";
        String contentDescompressed = null;
        long startTime = System.nanoTime(); // get the time when start the descompression
        switch (Objects.requireNonNull(getFileType(filePath))){
            case ".lz78":
                contentDescompressed = lz78.descomprimir(content);
                algoritme = "LZ78";
                savePath += ".txt";
                break;
            case ".lzss":
                contentDescompressed = lzss.descomprimir(content);
                algoritme = "LZSS";
                savePath += ".txt";
                    break;
            case ".jpeg":
                //contentDescompressed = jpeg.descomprimir(content);
                algoritme = "JPEG";
                savePath += ".ppm";
                break;
            default:
        }
        long endTime = System.nanoTime(); // get the time when end the compression
        double descompressTime = (double)(endTime-startTime)/1000000;
        saveFileTo(contentDescompressed, savePath);
        // create a new history
        assert contentDescompressed != null;
        LocalHistory localHistory = new LocalHistory(filePath, savePath, getFileType(filePath), "Decompression", algoritme,
                (double)content.length()/(double)contentDescompressed.length(), descompressTime);
        //add to history
        globalHistory.addLocalHistory(localHistory);
    }

    //get the content of file
    private String loadFile(String filePath) {
        return Data.DataCtrl.getInstance().getInputTextFile(filePath); // go to the date layer for load the content of file
    }

    // get the statistic of the algorithm we have selected
    public ArrayList<Object> loadStatistic(String algorithm){
        switch(algorithm)
        {
            case "LZ78" :
                return lz78.getGlobalStatistic();
            case "LZSS" :
                return lzss.getGlobalStatistic();
            case "JPEG" :
                return jpeg.getGlobalStatistic();
        }
        return null;
    }

    public ArrayList<ArrayList<Object>> getHistory() {
        return globalHistory.getInformation();
    }

    public ArrayList<Object> getStatisticLZ78 () {
        return lz78.getGlobalStatistic();
    }

    public ArrayList<Object> getStatisticLZSS() {
        return lzss.getGlobalStatistic();
    }

    public ArrayList<Object> getStatisticJPEG() {
        return jpeg.getGlobalStatistic();
    }

    private void saveFileTo(String content, String savePath){
        Data.DataCtrl.getInstance().outPutTextFile(content, savePath);
    }

    private String getFileType(String filePath) {
        if (filePath.contains(".txt")) return ".txt";
        else if (filePath.contains(".ppm")) return ".ppm";
        else if (filePath.contains(".lzss")) return ".lzss";
        else if (filePath.contains(".lz78")) return ".lz78";
        else if (filePath.contains(".jpeg")) return ".jpeg";
        else if (filePath.charAt(filePath.length()-4) != '.') return "folder";
        return null;
    }
}
