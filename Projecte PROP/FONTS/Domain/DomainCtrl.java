package Domain;

import Data.DataCtrl;
import javafx.util.Pair;

import java.io.IOException;

public class DomainCtrl {

    private static DomainCtrl instance = null;
    private LZ78 lz78 = new LZ78(0,0,0,0,0,0,0);
    private LZSS lzss = new LZSS(0,0,0,0,0,0,0);
    private JPEG jpeg = new JPEG(0,0,0,0,0,0,0);

    private DomainCtrl(){}

    public static DomainCtrl getInstance() {
        if (instance == null) {
            instance = new DomainCtrl();
        }
        return instance;
    }

    //Compress a file with the algorithm selected by the user, and save at the path selected by user
    public void compressFileTo(String filePath, String savePath, String algorithmType) throws IOException {
        if (algorithmType.equals("LZ78") || algorithmType.equals("LZSS")) {
            String content = loadFile(filePath); // get the content of file
            Fitxer file = new Fitxer(filePath, getFileType(filePath), content);
            Fitxer outFile = new Fitxer();
            long startTime = System.nanoTime(); // get the time when start the compression
            if (algorithmType.equals("LZ78")) {
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
            LocalHistory localHistory = new LocalHistory(filePath, savePath, getFileType(filePath), "Compression", algorithmType, (double)content.length()/(double)contentCompressed.length(), compressTime);
        }
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
            LocalHistory localHistory = new LocalHistory(filePath, savePath+".jpeg", getFileType(filePath), "Compression", algorithmType, compressRatio, compressTime);
            DataCtrl.getInstance().outPutImg(savePath+".jpeg", Dimension.getKey(), Dimension.getValue(), outPutFile.getImageContent());
        }
    }


    //Desompress a file and save at the path selected by user
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
        }
        else if (fileType.equals(".jpeg")) {
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
                LocalHistory localHistory = new LocalHistory(filePath, savePath, ".jpeg", "Decompression", "JPEG", compressRatio, decompressTime);
                DataCtrl.getInstance().outPutImg(savePath+".ppm", dimension.getKey(), dimension.getValue(), outPutImage.getImageContent());
            }
        }

    //load the content of file
    private String loadFile(String filePath) {
        return Data.DataCtrl.getInstance().getInputFile(filePath);
    }

    //Save the file to a path
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