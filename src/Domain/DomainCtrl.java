package Domain;
import Data.DataCtrl;

import java.util.ArrayList;

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

    }

    public void compressFileTo(String filePath, String savePath, String algoritmeType){
        String content = DataCtrl.getInstance().getInputTextFile(filePath);
        File file = new File(filePath, content, getFileType(filePath));
        String algorithm;
        //falta funcion para calcular tiempo
        //falta funcion para calcular compressionRatio
        String contentCompressed = null;
        if (algoritmeType.equals("LZ78")) {
            contentCompressed = lz78.comprimir(content);
        }else if (algoritmeType.equals("LZSS")) {
            //contentCompressed = lzss.comprimir(content);
        }else if (algoritmeType.equals("JPEG")){
            //contentCompressed = jpeg.comprimir(content);
        }
        saveFileTo(contentCompressed, savePath);
        //history
        //LocalHistory localHistory = new LocalHistory(filePath, savePath, getFileType(filePath), "Compression", algoritmeType,
        //                                              double compressionRatio, double timeUsed)
        //globalHistory.addLocalHistory(localHistory);
    }

    public void compressFolderTo(String folderPath, String savePath,String saveName){

    }

    public void decompressFileTo(String filePath, String savePath){
        String content = DataCtrl.getInstance().getInputTextFile(filePath);
        File file = new File(filePath, content, getFileType(filePath));
        String algorithm;
        //falta funcion para calcular tiempo
        //falta funcion para calcular compressionRatio
        String algoritme = null;
        String contentDescompressed = null;
        if (content.contains("LZ78")) {
            contentDescompressed = lz78.descomprimir(content.substring(4));
            algorithm = "LZ78";
        }else if(content.contains("LZSS")) {
            //contentDescompressed = lz78.descomprimir(content.substring(4));
            algorithm = "LZSS";
        } else if (content.contains("JPEG")) {
            //contentDescompressed = lz78.descomprimir(content.substring(4));
            algorithm = "JPEG";
        }
        saveFileTo(content, savePath);
        //history
        //LocalHistory localHistory = new LocalHistory(filePath, savePath, getFileType(filePath), "Decompression", algoritme,
        //                                              double compressionRatio, double timeUsed)
        //globalHistory.addLocalHistory(localHistory);
    }

    public ArrayList<ArrayList<Object>> getHistory() {
        return globalHistory.getInformation();
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
}
