package Drivers.LocalHistory;

import Domain.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DriverLocalHistory {
    public static void main(String[] args) {
        String nomClasse = "LocalHistory";
        System.out.println("Driver " + nomClasse + ":");

        LocalHistory localHistory = new LocalHistory();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            boolean sortir = false;
            while (!sortir) {
                System.out.println("Tria una opció (número) seguit dels paràmetres necessaris per comprovar el mètode:");
                System.out.println("\t 1) LocalHistory()");
                System.out.println("\t 2) LocalHistory(String inputPath, String outputPath, String fileExtension, " +
                        "String operation, String algorithm, double compressionRatio, double timeUsed)");
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
                            localHistory = new LocalHistory();
                            break;
                        case "2":
                            localHistory = new LocalHistory(paraules[1], paraules[2], paraules[3], paraules[4], paraules[5],
                                    Double.parseDouble(paraules[6]),Double.parseDouble(paraules[7]));
                            break;
                        case "3":
                            System.out.println("Tota la informació de l'historia local: " + localHistory.getInformation() + ".");
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
