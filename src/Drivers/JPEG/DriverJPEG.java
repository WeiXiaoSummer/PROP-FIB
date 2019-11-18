package Drivers.JPEG;

import javafx.util.Pair;

import java.io.File;

public class DriverJPEG {
    private static JPEG jpeg = new JPEG(0, 0, 0, 0, 0, 0, 0);
    private static IO io = new IO();

    public static void main(String[] args) {
        test1();
    }

    public static void test1() {
        System.out.println("Compression");
        File directory = new File("src\\Drivers\\JPEG");
        String filePath = directory.getAbsolutePath()+"\\Prova1.ppm";
        String savePath = directory.getAbsolutePath()+"\\Prova1.jpeg";
        Pair<Integer, Integer> Dimension = io.getImgDimension(filePath);
        byte[] RGB = io.getInputImg(filePath, Dimension.getKey(), Dimension.getValue());
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
        LocalHistory localHistory = new LocalHistory(filePath, savePath, ".jpeg", "Compression" , "JPEG", compressRatio, compressTime);
        System.out.print("input fila Path = " + filePath + "\nsava path = " + savePath + "\nfile extension = .jpeg" +
                "\noperation = Compression\nalgorithm = JPEG\ncompression Ratio = "+compressRatio + "\ncompress time = "+compressTime);
        io.outPutImg(savePath, Dimension.getKey(), Dimension.getValue(), outPutFile.getImageContent());

        System.out.println("\n\nDecompression");
        String compressedFilePath = directory.getAbsolutePath()+"\\Prova1.jpeg";
        String outPutFilePath = directory.getAbsolutePath()+"\\Prova1Test.ppm";
        Pair<Integer, Integer> dimension = io.getImgDimension(compressedFilePath);
        byte[] compressedImg = io.getInputImg(compressedFilePath, dimension.getKey(), dimension.getValue());
        Fitxer inputCompressedImg = new Fitxer();
        inputCompressedImg.setDimension(dimension);
        inputCompressedImg.setImageContent(compressedImg);
        long startTimeDe = System.nanoTime();
        Fitxer outPutImage = jpeg.descomprimir(inputCompressedImg);
        long endTimeDe = System.nanoTime();
        double decompressTime = (double)(endTimeDe-startTimeDe)/1000000000;
        double compressRatioDe =  (double)outPutImage.getImageContent().length/(double)compressedImg.length;
        LocalHistory localHistoryDe = new LocalHistory(compressedFilePath, outPutFilePath, ".ppm", "Decompression", "JPEG", compressRatioDe, decompressTime);
        System.out.print("input fila Path = " + compressedFilePath + "\nsava path = " + outPutFilePath + "\nfile extension = .ppm" +
                "\noperation = Decompression\nalgorithm = JPEG\ncompression Ratio = "+compressRatioDe + "\nDecompress time = "+decompressTime);
        io.outPutImg(outPutFilePath, dimension.getKey(), dimension.getValue(), outPutImage.getImageContent());
    }
}
