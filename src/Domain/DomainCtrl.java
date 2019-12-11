package Domain;

import Data.DataCtrl;
import javafx.util.Pair;
import org.json.simple.JsonArray;
import org.json.simple.JsonObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class DomainCtrl {

    private static DomainCtrl instance = null;
    private GlobalHistory globalHistory;
    private LZ78 lz78;
    private LZSS lzss;
    private JPEG jpeg;

    private DomainCtrl(){}

    public static DomainCtrl getInstance() {
        if (instance == null) {
            instance = new DomainCtrl();
        }
        return instance;
    }

    public void initializeDomainCtrl() {
        lz78 = new LZ78(new GlobalStatistic(0,0,0,0,0,0,0));
        lzss = new LZSS(new GlobalStatistic(0,0,0,0,0,0,0));
        jpeg = new JPEG(new GlobalStatistic(0,0,0,0,0,0,0));
    }
    public void compress(File inFile, File targetDirectory, String saveName, String algorithmType) {
        Fitxer inputFile = new Fitxer(new File(inFile.getPath()), new byte[0]);
        Fitxer outputFile = new Fitxer(new File(targetDirectory.getPath() + File.separator + saveName + ".prop"), new byte[0]);
        ByteArrayOutputStream compressedFile = new ByteArrayOutputStream();
        LocalHistory localHistory;
        Pair<Double, Double> compressStatistic;
        byte nameLength = (byte) saveName.length();
        byte[] numFiles = ByteBuffer.allocate(4).putInt(1).array();
        try {
            compressedFile.write("PROPFOLD".getBytes()); compressedFile.write(nameLength);
            compressedFile.write(saveName.getBytes()); compressedFile.write(numFiles);
        }
        catch (Exception e) {e.printStackTrace();}
        if (inputFile.getFile().isFile()) {
            String fileType = getFileType(inputFile.getFile());
            compressStatistic = compressFile(inputFile, compressedFile, fileType, algorithmType);
            localHistory = new LocalHistory(inFile.getPath(), outputFile.getFile().getPath(), fileType, "Compression", algorithmType, compressStatistic.getKey(), compressStatistic.getValue());
        }
        else {
            compressStatistic = compressFolder(inputFile, compressedFile, algorithmType);
            localHistory = new LocalHistory(inFile.getPath(), outputFile.getFile().getPath(), "folder", "Compression", algorithmType, compressStatistic.getKey(), compressStatistic.getValue());
        }
        outputFile.setContent(compressedFile.toByteArray());
        DataCtrl.getInstance().outputFile(outputFile.getContent(), outputFile.getFile());
    }

    public void decompress(File inFile, File targetDirectory) {
        DataCtrl.getInstance().setInputCompressedFileStream(inFile);
        byte[] aux = new byte[4];
        DataCtrl.getInstance().readFromInputCompressedFileStream(aux);
        String check = new String(aux);
        DataCtrl.getInstance().readFromInputCompressedFileStream(aux);
        byte nameLength = DataCtrl.getInstance().readFromInputCompressedFileStream();
        byte[] getFolderName = new byte[nameLength];
        DataCtrl.getInstance().readFromInputCompressedFileStream(getFolderName);
        File targetFolder = new File(targetDirectory.getPath()+File.separator+new String(getFolderName));
        targetFolder.mkdir();
        decompressFolder(targetFolder);
        DataCtrl.getInstance().closeInputCompressedFileStream();
    }

    private Pair<Double, Double> compressFile(Fitxer inputFile, ByteArrayOutputStream compressedFile, String type, String algorithmType) {
        inputFile.setContent(DataCtrl.getInstance().getInputFile(inputFile.getFile()));
        if (type.equals("txt")) {
            if (algorithmType.equals("LZSS")) return lzss.comprimir(inputFile, compressedFile);
            return lz78.comprimir(inputFile, compressedFile);
        }
        return jpeg.comprimir(inputFile, compressedFile);
    }

    private Pair<Double, Double> compressFolder(Fitxer inputFolder, ByteArrayOutputStream compressedFile, String algorithmType) {
        File[] files = inputFolder.getFile().listFiles();
        byte nameLength = (byte) inputFolder.getFile().getName().length();
        byte[] numFiles = ByteBuffer.allocate(4).putInt(files.length).array();
        try {
            compressedFile.write("FOLD".getBytes()); compressedFile.write(nameLength);
            compressedFile.write(inputFolder.getFile().getName().getBytes()); compressedFile.write(numFiles);
        }
        catch (Exception e) {e.printStackTrace();}
        for (File f : files) {
            if (f.isFile()) {
                String fileType = getFileType(f);
                Fitxer inputFile = new Fitxer(f, new byte[0]);
                compressFile(inputFile, compressedFile, fileType, algorithmType);
            }
            else {
                Fitxer subFolder = new Fitxer(f, new byte[0]);
                compressFolder(subFolder, compressedFile, algorithmType);
            }
        }
        return new Pair<>(0.0, 0.0);
    }

    private Pair<Double, Double> decompressFile(Fitxer targetFile, String type) {
        byte[] getCompressedContentSize = new byte[4];
        DataCtrl.getInstance().readFromInputCompressedFileStream(getCompressedContentSize);
        int compressedContentSize = ByteBuffer.wrap(getCompressedContentSize).getInt();
        Pair<Double, Double> compressionStatistic = new Pair<>(0.0, 0.0);
        byte[] compressedContent;
        switch (type) {
            case "LZSS":
                compressedContent = new byte[compressedContentSize];
                DataCtrl.getInstance().readFromInputCompressedFileStream(compressedContent);
                compressionStatistic = lzss.descomprimir(compressedContent, targetFile);
                break;
            case "LZ78":
                compressedContent = new byte[compressedContentSize];
                DataCtrl.getInstance().readFromInputCompressedFileStream(compressedContent);
                compressionStatistic = lz78.descomprimir(compressedContent, targetFile);
                break;
            case "JPEG":
                compressedContent = new byte[compressedContentSize+8];
                DataCtrl.getInstance().readFromInputCompressedFileStream(compressedContent);
                compressionStatistic = jpeg.descomprimir(compressedContent, targetFile);
                break;
            default:
                break;
        }
        DataCtrl.getInstance().outputFile(targetFile.getContent(), targetFile.getFile());
        return compressionStatistic;
    }

    private Pair<Double, Double> decompressFolder (File targetDirectory) {
        byte[] getFolderSize = new byte[4];
        DataCtrl.getInstance().readFromInputCompressedFileStream(getFolderSize);
        int folderSize = ByteBuffer.wrap(getFolderSize).getInt();
        for(int i = 0; i < folderSize; ++i) {
            byte[] getType = new byte[4];
            DataCtrl.getInstance().readFromInputCompressedFileStream(getType);
            String type = new String(getType);
            byte nameLength = DataCtrl.getInstance().readFromInputCompressedFileStream();
            byte[] getName = new byte[nameLength];
            DataCtrl.getInstance().readFromInputCompressedFileStream(getName);
            File targetFile = new File(targetDirectory.getPath()+File.separator+new String(getName));
            Fitxer decompressedFile = new Fitxer(targetFile, new byte[0]);
            if (type.equals("FOLD")) {
                targetFile.mkdir();
                decompressFolder(targetFile);
            }
            else {
                decompressFile(decompressedFile, type);
            }
        }
        return new Pair<>(0.0, 0.0);
    }

    private String getFileType(File f) {
        String fileName = f.getName();
        int dotIndex = fileName.lastIndexOf('.');
        return fileName.substring(dotIndex+1);
    }

    /*private ArrayList<ArrayList<Object>> loadGlobalStatistics () {

    }*/

    public void saveGlobalStatistics() {
        JsonObject globalStatistics = new JsonObject();
        JsonArray LZSS = new JsonArray();
        ArrayList<Object> lzssStatistic = lzss.getGlobalStatistic();
        LZSS.add(lzssStatistic.get(0)); LZSS.add(lzssStatistic.get(1)); LZSS.add(lzssStatistic.get(2));

        JsonArray LZ78 = new JsonArray();
        ArrayList<Object> lz78Statistic = lz78.getGlobalStatistic();
        LZ78.add(lz78Statistic.get(0)); LZ78.add(lz78Statistic.get(1)); LZ78.add(lz78Statistic.get(2));

        JsonArray JPEG = new JsonArray();
        ArrayList<Object> jpegStatistic = jpeg.getGlobalStatistic();
        JPEG.add(jpegStatistic.get(0)); JPEG.add(jpegStatistic.get(1)); JPEG.add(jpegStatistic.get(2));

        globalStatistics.put("LZSS", LZSS);
        globalStatistics.put("LZ78", LZ78);
        globalStatistics.put("JPEG", JPEG);

        File statisticFile = new File("D:" + File.separator + "statistic.json");
        DataCtrl.getInstance().outputFile(globalStatistics.toString().getBytes(), statisticFile);
    }

    private void saveHistory() {
        JsonObject globalHistory = new JsonObject();
        ArrayList<ArrayList<Object>> history = this.globalHistory.getInformation();
        for (int i = 0; i < history.size();++i) {
            JsonArray localHistory = new JsonArray();
            for (int j = 0; j < history.get(i).size(); ++j) {
                localHistory.add(history.get(i).get(j));
            }
            globalHistory.put(Integer.toString(i), localHistory);
        }

        File globalHistoryFile = new File("D:" + File.separator + "globalHistory.json");
        DataCtrl.getInstance().outputFile(globalHistory.toString().getBytes(), globalHistoryFile);
    }
}
