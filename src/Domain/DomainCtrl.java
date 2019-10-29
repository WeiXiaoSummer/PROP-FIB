package Domain;
import Data.DataCtrl;

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
        File file = new File();
        file.setFilePath(filePath);
        file.setFileContent(content);

        if (algoritmeType.equals("LZ78")) {
            lz78.comprimir(content, savePath);
        }else if (algoritmeType.equals("LZSS")) {

        }else if (algoritmeType.equals("JPEG")){

        }else {

        }

        saveFileTo(file.getFileContent(), savePath);
    }

    public void compressFolderTo(String folderPath, String savePath,String saveName){

    }

    public void decompressFileTo(String filePath, String savePath){

    }

    public void saveFileTo(String content, String savePath){
        DataCtrl.getInstance().outPutTextFile(content, savePath);
    }
}
