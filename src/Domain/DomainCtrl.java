package Domain;

import Data.DataCtrl;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;

public class DomainCtrl {

    private static DomainCtrl instance = null;
    private LZ78 lz78 = new LZ78(0,0,0,0,0,0,0);
    private LZSS lzss = new LZSS(0,0,0,0,0,0,0);
    private JPEG jpeg = new JPEG(0,0,0,0,0,0,0);
    private GlobalHistory globalHistory = new GlobalHistory();

    private DomainCtrl(){}

    public static DomainCtrl getInstance() {
        if (instance == null) {
            instance = new DomainCtrl();
        }
        return instance;
    }

    public void compressFileTo(String filePath, String savePath, String algoritmeType) throws IOException {
        if (algoritmeType.equals("LZ78") || algoritmeType.equals("LZSS")) {
            String content = loadFile(filePath); // get the content of file
            Fitxer file = new Fitxer(filePath, getFileType(filePath), content);
            Fitxer outFile = new Fitxer();
            long startTime = System.nanoTime(); // get the time when start the compression
            if (algoritmeType.equals("LZ78")) {
                outFile = lz78.comprimir(file);
                savePath += ".lz78";
            }
            else {
                outFile = lzss.comprimir(file);
                savePath += ".lzss";
            }
            long endTime = System.nanoTime(); // get the time when end the compression
            double compressTime = (double)(endTime-startTime)/1000000000;
            saveFileTo(outFile, savePath);
            //history
            String contentCompressed = outFile.getFileContent();
            LocalHistory localHistory = new LocalHistory(filePath, savePath, getFileType(filePath), "Compression", algoritmeType, (double)content.length()/(double)contentCompressed.length(), compressTime);
            globalHistory.addLocalHistory(localHistory);
        }
        /*String content = loadFile(filePath); // get the content of file
        Fitxer file = new Fitxer(filePath, getFileType(filePath), content);
        Fitxer outFile = new Fitxer();
        long startTime = System.nanoTime(); // get the time when start the compression
        //Choose the right algorithm and compressed file
        switch (algoritmeType) {
            case "LZ78":
                outFile = lz78.comprimir(file);
                savePath += ".lz78";
                break;
            case "LZSS":
                outFile = lzss.comprimir(file);
                savePath += ".lzss";
                break;
            case "JPEG":
                //outFile = jpeg.comprimir(file);
                savePath += ".jpeg";
                break;
            default:
        }*/
        else {
            Pair<Integer, Integer> Dimension = DataCtrl.getInstance().getImgDimension(filePath);
            byte[] RGB = DataCtrl.getInstance().getInputImg(filePath, Dimension.getKey(), Dimension.getValue());
            Fitxer inputImage = new Fitxer();
            inputImage.setFileExtension(".ppm");
            inputImage.setFilePath(filePath);
            inputImage.setImageContent(RGB);
            inputImage.setDimension(Dimension);
            long startTime = System.nanoTime();
            Fitxer outPutFile = jpeg.comprimir(inputImage);
            long endTime = System.nanoTime();
            double compressTime = (double)(endTime-startTime)/1000000000;
            double compressRatio =  (double)(RGB.length)/(double)outPutFile.getImageContent().length;
            LocalHistory localHistory = new LocalHistory(filePath, savePath+".jppeg", getFileType(filePath), "Compression", algoritmeType, compressRatio, compressTime);
            globalHistory.addLocalHistory(localHistory);
            DataCtrl.getInstance().outPutImg(savePath+".jppeg", Dimension.getKey(), Dimension.getValue(), outPutFile.getImageContent());
        }
    }

    public void compressFolderTo(String folderPath, String savePath) throws IOException {
        java.io.File file = new java.io.File(folderPath);
        java.io.File[] tempList = file.listFiles();

        assert tempList != null;
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
        String fileType = getFileType(filePath);
        assert fileType != null;
        if (filePath.equals(".lz78") || filePath.equals(".lzss")) {
            String content = Data.DataCtrl.getInstance().getInputFile(filePath);
            Fitxer file = new Fitxer(filePath, getFileType(filePath), content);
            String algoritme;
            Fitxer outFile;
            long startTime = System.nanoTime(); // get the time when start the descompression
            if (fileType.equals(".lz78")) {
                outFile = lz78.descomprimir(file);
                algoritme = "LZ78";
                savePath += ".txt";
            }
            else {
                outFile = lzss.descomprimir(file);
                algoritme = "LZSS";
                savePath += ".txt";
            }
            long endTime = System.nanoTime(); // get the time when end the compression
            double descompressTime = (double)(endTime-startTime)/1000000000;
            saveFileTo(outFile, savePath);
            String contentOut = outFile.getFileContent();
            // create a new history
            LocalHistory localHistory = new LocalHistory(filePath, savePath, getFileType(filePath), "Decompression", algoritme,
                    (double)content.length()/(double)contentOut.length(), descompressTime);
            //add to history
            globalHistory.addLocalHistory(localHistory);
        }
        else if (fileType.equals(".jppeg")) {
                Pair<Integer, Integer> dimension = DataCtrl.getInstance().getImgDimension(filePath);
                byte[] compressedImg = DataCtrl.getInstance().getInputImg(filePath, dimension.getKey(), dimension.getValue());
                Fitxer inputCompressedImg = new Fitxer();
                inputCompressedImg.setDimension(dimension);
                inputCompressedImg.setImageContent(compressedImg);
                long startTime = System.nanoTime();
                Fitxer outPutImage = jpeg.descomprimir(inputCompressedImg);
                long endTime = System.nanoTime();
                double decompressTime = (double)(endTime-startTime)/1000000000;
                double compressRatio =  (double)compressedImg.length/(double)outPutImage.getImageContent().length;
                LocalHistory localHistory = new LocalHistory(filePath, savePath, ".jppeg", "Decompression", "JPEG", compressRatio, decompressTime);
                globalHistory.addLocalHistory(localHistory);
                DataCtrl.getInstance().outPutImg(savePath+".ppm", dimension.getKey(), dimension.getValue(), outPutImage.getImageContent());
            }
        }
        /*switch (Objects.requireNonNull(getFileType(filePath))){
            case ".lz78":
                outFile = lz78.descomprimir(file);
                algoritme = "LZ78";
                savePath += ".txt";
                break;
            case ".lzss":
                outFile = lzss.descomprimir(file);
                algoritme = "LZSS";
                savePath += ".txt";
                break;
            case ".jpeg":
                outFile = jpeg.descomprimir(file);
                algoritme = "JPEG";
                savePath += ".ppm";
                break;
            case "folder":
                break;
            default:
        }
        long endTime = System.nanoTime(); // get the time when end the compression
        double descompressTime = (double)(endTime-startTime)/1000000;
        saveFileTo(outFile, savePath);
        String contentOut = outFile.getFileContent();
        // create a new history
        LocalHistory localHistory = new LocalHistory(filePath, savePath, getFileType(filePath), "Decompression", algoritme,
                (double)content.length()/(double)contentOut.length(), descompressTime);
        //add to history
        globalHistory.addLocalHistory(localHistory);*/


    //get the content of file
    private String loadFile(String filePath) {
        return Data.DataCtrl.getInstance().getInputFile(filePath); // go to the date layer for load the content of file
    }

    // get the statistic of the algorithm we have selected
    public ArrayList<Object> GetStatistic(String algorithm){
        switch(algorithm) {
            case "LZ78" :
                return getStatisticLZ78();
            case "LZSS" :
                return getStatisticLZSS();
            case "JPEG" :
                return getStatisticJPEG();
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

    private void saveFileTo(Fitxer file, String savePath){
        String content = file.getFileContent();
        Data.DataCtrl.getInstance().outputFile(content, savePath);
    }

    private String getFileType(String filePath) {
        if (filePath.contains(".txt")) return ".txt";
        else if (filePath.contains(".ppm")) return ".ppm";
        else if (filePath.contains(".lzss")) return ".lzss";
        else if (filePath.contains(".lz78")) return ".lz78";
        else if (filePath.contains(".jppeg")) return ".jppeg";
        else if (filePath.charAt(filePath.length()-4) != '.') return "folder";
        return null;
    }
}