package Drivers.LZ78;

import Domain.Fitxer;
import Drivers.GlobalStatistic.GlobalStatistic;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DriverLZ78 {
    public static LZ78 lz78 = new LZ78(0,0,0,0,0,0,0);
    public static void main(String[] args) {
        String nomClasse = "LZ78";
        System.out.println("Driver " + nomClasse + ":");

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            boolean sortir = false;
            while (!sortir) {
                System.out.println("Tria una opció (número) seguit dels paràmetres necessaris per comprovar el mètode:\n-Els opcions amb" +
                        " * no cal introduir paràmetres sino que posteriorment haurà de triar una entrada predefinida\n" +
                        "-Els paràmetres que són de classe Stub no falta introduir ja que tenen comportament per defecte.\n");
                System.out.println("\t 1) LZ78(int numCompression, int numDecompression, int totalCompressedData,\n" +
                        "                int totalDecompressedData, double totalCompressionTime, double totalDecompressionTime,\n" +
                        "                double averageCompressionRatio)");
                System.out.println("\t 2) Comprimir i descomprimir el fitxer1;");
                System.out.println("\t 3) Comprimir i descomprimir el fitxer2;");
                System.out.println("\t 4) Comprimir i descomprimir el fitxer3;");
                System.out.println("\t 0) Sortir");

                String linea;
                String[] paraules;
                String opcio;

                linea = br.readLine();
                paraules = linea.split(" ");
                opcio = paraules[0];
                try {
                    System.out.println("Opció " + opcio + " seleccionada.");
                    switch (opcio) {
                        case "1":
                            testConstructora(paraules);
                            break;
                        case "2":
                            testComprimirIDescomprimir(Integer.parseInt(opcio)-1);
                            break;
                        case "3":
                            testComprimirIDescomprimir(Integer.parseInt(opcio)-1);
                            break;
                        case "4":
                            testComprimirIDescomprimir(Integer.parseInt(opcio)-1);
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

    public static void testConstructora(String[] paraules) {
        LZ78 lz78 = new LZ78(Integer.parseInt(paraules[1]), Integer.parseInt(paraules[2]),
                Integer.parseInt(paraules[3]),Integer.parseInt(paraules[4]), Double.parseDouble(paraules[5]),
                Double.parseDouble(paraules[6]),Double.parseDouble(paraules[7]));
    }

    public static void testComprimirIDescomprimir(int opcio) {
        IO io = new IO();
        File directory = new File("src\\Drivers\\LZ78");
        switch (opcio) {
            case 1:
                String pathIn = directory.getAbsolutePath()+"\\Prova1.txt";
                String content = io.getInputFile(pathIn);
                Fitxer fitxerIn = new Fitxer(pathIn,null, content);
                long startTime=System.currentTimeMillis();
                Fitxer fitxerOut = lz78.comprimir(fitxerIn);
                long endTime=System.currentTimeMillis(); // get the time when end the compression
                double Time = (double)(endTime-startTime)* 0.001;

                String pathOut = directory.getAbsolutePath()+"\\Compressió1.lz78";
                io.outputFile(fitxerOut.getFileContent(), pathOut);

                System.out.println("input fila Path = " + pathIn + "\nsava path = " + pathOut + "\nfile extension = .lz78" +
                        "\noperation = Compression\nalgorithm = LZ78\ncompression Ratio = "+fitxerIn.getFileContent().length()/fitxerOut.getFileContent().length()
                        + "\nCompress time = "+Time);

                pathIn = directory.getAbsolutePath()+"\\Compressió1.lz78";
                content = io.getInputFile(pathIn);
                fitxerIn = new Fitxer(pathIn,null, content);
                startTime=System.currentTimeMillis();
                fitxerOut = lz78.descomprimir(fitxerIn);
                endTime=System.currentTimeMillis(); // get the time when end the compression
                Time = (double)(endTime-startTime)* 0.001;

                pathOut = directory.getAbsolutePath()+"\\Descompressió1.txt";
                io.outputFile(fitxerOut.getFileContent(), pathOut);
                System.out.println("input fila Path = " + pathIn + "\nsava path = " + pathOut + "\nfile extension = .txt" +
                        "\noperation = Descompression\nalgorithm = LZ78\ncompression Ratio = "+fitxerOut.getFileContent().length()/fitxerIn.getFileContent().length()
                        + "\nDecompress time = "+Time);
                break;
            case 2:
                pathIn = directory.getAbsolutePath()+"\\Prova2.txt";
                content = io.getInputFile(pathIn);
                fitxerIn = new Fitxer(pathIn,null, content);
                startTime=System.currentTimeMillis();
                fitxerOut = lz78.comprimir(fitxerIn);
                endTime=System.currentTimeMillis(); // get the time when end the compression
                Time = (double)(endTime-startTime)* 0.001;

                pathOut = directory.getAbsolutePath()+"\\Compressió2.lz78";
                io.outputFile(fitxerOut.getFileContent(), pathOut);

                System.out.println("input fila Path = " + pathIn + "\nsava path = " + pathOut + "\nfile extension = .lz78" +
                        "\noperation = Compression\nalgorithm = LZ78\ncompression Ratio = "+fitxerIn.getFileContent().length()/fitxerOut.getFileContent().length()
                        + "\nCompress time = "+Time);

                pathIn = directory.getAbsolutePath()+"\\Compressió2.lz78";
                content = io.getInputFile(pathIn);
                fitxerIn = new Fitxer(pathIn,null, content);
                startTime=System.currentTimeMillis();
                fitxerOut = lz78.descomprimir(fitxerIn);
                endTime=System.currentTimeMillis(); // get the time when end the compression
                Time = (double)(endTime-startTime)* 0.001;

                pathOut = directory.getAbsolutePath()+"\\Descompressió2.txt";
                io.outputFile(fitxerOut.getFileContent(), pathOut);
                System.out.println("input fila Path = " + pathIn + "\nsava path = " + pathOut + "\nfile extension = .txt" +
                        "\noperation = Descompression\nalgorithm = LZ78\ncompression Ratio = "+fitxerOut.getFileContent().length()/fitxerIn.getFileContent().length()
                        + "\nDecompress time = "+Time);
                break;
            case 3:
                pathIn = directory.getAbsolutePath()+"\\Prova3.txt";
                content = io.getInputFile(pathIn);
                fitxerIn = new Fitxer(pathIn,null, content);
                startTime=System.currentTimeMillis();
                fitxerOut = lz78.comprimir(fitxerIn);
                endTime=System.currentTimeMillis(); // get the time when end the compression
                Time = (double)(endTime-startTime)* 0.001;

                pathOut = directory.getAbsolutePath()+"\\Compressió3.lz78";
                io.outputFile(fitxerOut.getFileContent(), pathOut);

                System.out.println("input fila Path = " + pathIn + "\nsava path = " + pathOut + "\nfile extension = .lz78" +
                        "\noperation = Compression\nalgorithm = LZ78\ncompression Ratio = "+fitxerIn.getFileContent().length()/fitxerOut.getFileContent().length()
                        + "\nCompress time = "+Time);

                pathIn = directory.getAbsolutePath()+"\\Compressió3.lz78";
                content = io.getInputFile(pathIn);
                fitxerIn = new Fitxer(pathIn, null, content);
                startTime=System.currentTimeMillis();
                fitxerOut = lz78.descomprimir(fitxerIn);
                endTime=System.currentTimeMillis(); // get the time when end the compression
                Time = (double)(endTime-startTime)* 0.001;

                pathOut = directory.getAbsolutePath()+"\\Descompressió3.txt";
                io.outputFile(fitxerOut.getFileContent(), pathOut);
                System.out.println("input fila Path = " + pathIn + "\nsava path = " + pathOut + "\nfile extension = .txt" +
                        "\noperation = Descompression\nalgorithm = LZ78\ncompression Ratio = "+fitxerOut.getFileContent().length()/fitxerIn.getFileContent().length()
                        + "\nDecompress time = "+Time);
                break;

        }
    }
}
