package Drivers.JPEG;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class DriverJPEG {
    private static JPEG jpeg = new JPEG(0, 0, 0, 0, 0, 0, 0);
    private static IO io = new IO();

    public static void main(String[] args) {
        String nomClasse = "JPEG";
        System.out.println("Driver " + nomClasse + ":");

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            boolean sortir = false;
            while (!sortir) {
                System.out.println("Tria una opció (número) seguit dels paràmetres necessaris per comprovar el mètode:\n-Els opcions amb" +
                        " * no cal introduir paràmetres sino que posteriorment haurà de triar una entrada predefinida\n" +
                        "-Els paràmetres que són de classe Stub no falta introduir ja que tenen comportament per defecte.\n");
                System.out.println("\t 1) testejar comprimir i descomprimir amb fitxer .ppm prova1");
                System.out.println("\t 2) testejar comprimir i descomprimir amb fitxer .ppm prova2");
                System.out.println("\t 3) testejar comprimir i descomprimir amb fitxer .ppm prova3");
                System.out.println("\t 0) Sortir");

                String linea;
                String paraules[];
                String opcio;

                linea = br.readLine();
                paraules = linea.split(" ");
                opcio = paraules[0];
                try {
                    System.out.println("Opció " + opcio + " seleccionada.");
                    switch (opcio) {
                        case "1":
                            test1();
                            break;
                        case "2":
                            test2();
                            break;
                        case "3":
                            test3();
                            break;
                        case "0":
                            sortir = true;
                            break;
                        default:
                            System.out.println("La opció triada no existeix");
                            break;
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            System.out.println("Final del driver");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void test1() {
        System.out.println("Compression");
        File directory = new File("");
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
                "\noperation = Decompression\nalgorithm = JPEG\ncompression Ratio = "+compressRatioDe + "\nDecompress time = "+decompressTime+"\n");
        io.outPutImg(outPutFilePath, dimension.getKey(), dimension.getValue(), outPutImage.getImageContent());
    }

    public static void test2() {
        System.out.println("Compression");
        File directory = new File("");
        String filePath = directory.getAbsolutePath()+"\\Prova2.ppm";
        String savePath = directory.getAbsolutePath()+"\\Prova2.jpeg";
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
        String compressedFilePath = directory.getAbsolutePath()+"\\Prova2.jpeg";
        String outPutFilePath = directory.getAbsolutePath()+"\\Prova2Test.ppm";
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
                "\noperation = Decompression\nalgorithm = JPEG\ncompression Ratio = "+compressRatioDe + "\nDecompress time = "+decompressTime+"\n");
        io.outPutImg(outPutFilePath, dimension.getKey(), dimension.getValue(), outPutImage.getImageContent());
    }

    public static void test3() {
        System.out.println("Compression");
        File directory = new File("");
        String filePath = directory.getAbsolutePath()+"\\Prova3.ppm";
        String savePath = directory.getAbsolutePath()+"\\Prova3.jpeg";
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
        String compressedFilePath = directory.getAbsolutePath()+"\\Prova3.jpeg";
        String outPutFilePath = directory.getAbsolutePath()+"\\Prova3Test.ppm";
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
                "\noperation = Decompression\nalgorithm = JPEG\ncompression Ratio = "+compressRatioDe + "\nDecompress time = "+decompressTime+"\n");
        io.outPutImg(outPutFilePath, dimension.getKey(), dimension.getValue(), outPutImage.getImageContent());
    }
}
