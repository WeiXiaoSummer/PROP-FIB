package Drivers;

import Domain.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DriverGlobalStatistic {
    public static void main(String[] args) {
        String nomClasse = "GlobalStatistic";
        System.out.println("Driver " + nomClasse + ":");

        GlobalStatistic globalStatistic = new GlobalStatistic();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            boolean sortir = false;
            while (!sortir) {
                System.out.println("Tria una opció (número) seguit dels paràmetres necessaris per comprovar el mètode:");
                System.out.println("\t 1) GlobalStatistic()");
                System.out.println("\t 2) GlobalStatistic (int numCompression, int numDecompression, int totalCompressedData, " +
                        "int totalDecompressedData, double totalCompressionTime, double totalDecompressionTime," +
                        "double averageCompressionRatio)");
                System.out.println("\t 3) ArrayList<Object> getInformation()");
                System.out.println("\t 4) int getNumCompression()");
                System.out.println("\t 5) int getNumDecompression()");
                System.out.println("\t 6) int getTotalCompressedData()");
                System.out.println("\t 7) int getTotalDecompressedData()");
                System.out.println("\t 8) double getTotalCompressionTime()");
                System.out.println("\t 9) double getTotalDecompressionTime()");
                System.out.println("\t 10) double getAverageCompressionRatio()");
                System.out.println("\t 11) void setNumCompression(int numCompression)");
                System.out.println("\t 12) void setNumDecompression(int numDecompression)");
                System.out.println("\t 13) void setTotalCompressedData(int totalCompressedData)");
                System.out.println("\t 14) void setTotalDecompressedData(int totalDecompressedData)");
                System.out.println("\t 15) void setTotalCompressionTime(double totalCompressionTime)");
                System.out.println("\t 16) void setTotalDecompressionTime(double totalDecompressionTime)");
                System.out.println("\t 17) void setAverageCompressionRatio(double averageCompressionRatio)");

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
                            globalStatistic = new GlobalStatistic();
                            break;
                        case "2":
                            globalStatistic = new GlobalStatistic (Integer.parseInt(paraules[1]), Integer.parseInt(paraules[2]),
                                    Integer.parseInt(paraules[3]),Integer.parseInt(paraules[4]), Double.parseDouble(paraules[5]),
                                    Double.parseDouble(paraules[6]),Double.parseDouble(paraules[7]));
                            break;
                        case "3":
                            System.out.println("Tota la informació de l'estadística global: " + globalStatistic.getInformation() + ".");
                            break;
                        case "4":
                            System.out.println("Nombre de compressions de l'estadística global: " + globalStatistic.getNumCompression() + ".");
                            break;
                        case "5":
                            System.out.println("Nombre de descompressions de l'estadística global: " + globalStatistic.getNumDecompression() + ".");
                            break;
                        case "6":
                            System.out.println("Nombre total de Bytes comprimits de l'estadística global: " + globalStatistic.getTotalCompressedData() + ".");
                            break;
                        case "7":
                            System.out.println("Nombre total de Bytes descomprimits de l'estadística global: " + globalStatistic.getTotalDecompressedData() + ".");
                            break;
                        case "8":
                            System.out.println("Temps total de compressió de l'estadística global: " + globalStatistic.getTotalCompressionTime() + ".");
                            break;
                        case "9":
                            System.out.println("Temps total de descompressió de l'estadística global: " + globalStatistic.getTotalDecompressionTime() + ".");
                            break;
                        case "10":
                            System.out.println("Relació mitjana de compressió de l'estadística global: " + globalStatistic.getAverageCompressionRatio() + ".");
                            break;
                        case "11":
                            globalStatistic.setNumCompression(Integer.parseInt(paraules[1]));
                            break;
                        case "12":
                            globalStatistic.setNumDecompression(Integer.parseInt(paraules[1]));
                            break;
                        case "13":
                            globalStatistic.setTotalCompressedData(Integer.parseInt(paraules[1]));
                            break;
                        case "14":
                            globalStatistic.setTotalDecompressedData(Integer.parseInt(paraules[1]));
                            break;
                        case "15":
                            globalStatistic.setTotalCompressionTime(Double.parseDouble(paraules[1]));
                            break;
                        case "16":
                            globalStatistic.setTotalDecompressionTime(Double.parseDouble(paraules[1]));
                            break;
                        case "17":
                            globalStatistic.setAverageCompressionRatio(Double.parseDouble(paraules[1]));
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
}
