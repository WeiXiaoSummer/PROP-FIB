package Domain;
import Data.DataCtrl;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class DomainCtrl {

    private static DomainCtrl instance = null;
    private LZ78 lz78;
    private LZSS lzss;
    private JPEG jpeg;
    private GlobalHistory globalHistory;

    private DomainCtrl(){}

    public static DomainCtrl getInstance() {
        if (instance == null) {
            instance = new DomainCtrl();
        }
        return instance;
    }

    public void initializeDomainCtrl() {
        //lz78 = new LZ78();
        lzss = new LZSS();
        jpeg = new JPEG();
        globalHistory = new GlobalHistory();
    }

    public void compressFileTo(String filePath, String savePath, String algoritmeType){
        String content = loadFile(filePath); // get the content of file
        MyFile file = new MyFile(filePath, content);
        if (algoritmeType.equals("AUTO")) {
            chooseTheBestAlgoritme(getFileType(filePath)); //random
        }
        String contentCompressed = null;
        long startTime = System.nanoTime(); // get the time when start the compression
        //compressed file with the algorithm we had selected
        switch(algoritmeType) {
            case "LZ78" :
                contentCompressed = lz78.comprimir(content);
            case "LZSS" :
                //contentCompressed = lzss.comprimir(content);
            case "JPEG" :
                //contentCompressed = jpeg.comprimir(content);
        }
        long endTime = System.nanoTime(); // get the time when end the compression
        double compressTime = (double)(endTime-startTime)/1000000;
        saveFileTo(contentCompressed, savePath);
        //history
        LocalHistory localHistory = new LocalHistory(filePath, savePath, getFileType(filePath), "Compression", algoritmeType,
                                                      content.length()/contentCompressed.length(), compressTime);
        globalHistory.addLocalHistory(localHistory);
    }

    /*public void compressFolderTo(String folderPath, String savePath){
        File file = new File(folderPath);
        java.io.File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                compressFileTo(tempList[i].toString(), folderPath, "AUTO");
            }
            if (tempList[i].isDirectory()) {
                compressFolderTo(tempList[i].toString(), savePath);
            }
        }
    }*/

    public void decompressFileTo(String filePath, String savePath){
        String content = DataCtrl.getInstance().getInputTextFile(filePath);
        MyFile myFile = new MyFile(filePath, content);
        String algorithm = null;
        String contentDescompressed = null;
        long startTime = System.nanoTime(); // get the time when start the descompression
        switch(content.substring(0,4)) {
            case "LZ78" :
                contentDescompressed = lz78.descomprimir(content.substring(4));
                algorithm = "LZ78";
            case "LZSS" :
                //contentDescompressed = lz78.descomprimir(content.substring(4));
                algorithm = "LZSS";
            case "JPEG" :
                //contentDescompressed = lz78.descomprimir(content.substring(4));
                algorithm = "JPEG";
        }
        long endTime = System.nanoTime(); // get the time when end the compression
        double descompressTime = (double)(endTime-startTime)/1000000;
        saveFileTo(content, savePath);
        // create a new history
        LocalHistory localHistory = new LocalHistory(filePath, savePath, getFileType(filePath), "Decompression", algorithm,
                                                      content.length()/contentDescompressed.length(), descompressTime);
        //add to history
        globalHistory.addLocalHistory(localHistory);
    }

    //get the content of file
    public String loadFile(String filePath) {
        return DataCtrl.getInstance().getInputTextFile(filePath); // go to the date layer for load the content of file
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

    public void saveFileTo(String content, String savePath){
        DataCtrl.getInstance().outPutTextFile(content, savePath);
    }

    public String getFileType(String filePath) {
        if (filePath.contains(".txt")) return ".txt";
        else if (filePath.contains(".ppm")) return ".ppm";
        else if (filePath.charAt(filePath.length()-4) != '.') return "folder";
        return null;
    }

    public String chooseTheBestAlgoritme(String fileType) {
        if (fileType.equals(".ppm")) return "JPEG";
        else{
            if ((int)Math.random()%2 == 0) return "LZ78";
            else return "LZSS";
        }
    }
}
