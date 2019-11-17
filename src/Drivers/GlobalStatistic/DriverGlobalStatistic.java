package Drivers.GlobalStatistic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DriverGlobalStatistic {

    private static GlobalStatistic globalStatistic = new GlobalStatistic();

    public static void main(String[] args) {
        String nomClasse = "GlobalStatistic";
        System.out.println("Driver " + nomClasse + ":");

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            boolean sortir = false;
            while (!sortir) {
                System.out.println("Tria una opció (número) seguit dels paràmetres necessaris per comprovar el mètode:\n-Els opcions amb" +
                        " * no cal introduir paràmetres sino que posteriorment haurà de triar una entrada predefinida\n" +
                        "-Els paràmetres no primitives es representen amb paràmetres primitives requerits per les seves constructores.\n" +
                        "Per exemple per representar CodeBits(char code, char bits) cal introduir en l'ordre code i bits.\n");
                System.out.println("\t 1) GlobalStatistic()");
                System.out.println("\t 2) GlobalStatistic (int numCompression, int numDecompression, int totalCompressedData, " +
                        "int totalDecompressedData, \n\t    double totalCompressionTime, double totalDecompressionTime," +
                        "double averageCompressionRatio)");
                System.out.println("\t 3) ArrayList<Object> getInformation()");
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
                            testConstructora1();
                            break;
                        case "2":
                            testConstructora2(paraules);
                            break;
                        case "3":
                            testGetInformation();
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

    public static void testConstructora1() {
        globalStatistic = new GlobalStatistic();
    }

    public static void testConstructora2(String[] paraules) {
        globalStatistic = new GlobalStatistic(Integer.parseInt(paraules[1]), Integer.parseInt(paraules[2]),
                Integer.parseInt(paraules[3]),Integer.parseInt(paraules[4]), Double.parseDouble(paraules[5]),
                Double.parseDouble(paraules[6]),Double.parseDouble(paraules[7]));
    }

    public static void testGetInformation() {
        ArrayList<Object> information = globalStatistic.getInformation();
        System.out.println("Tota la informació de l'estadística global: " + information + ".");
    }
}
