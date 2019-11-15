package Drivers;

import Domain.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DriverFitxer {
    public static void main(String[] args) {
        String nomClasse = "Fitxer";
        System.out.println("Driver " + nomClasse + ":");

        Fitxer fitxer = new Fitxer();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            boolean sortir = false;
            while (!sortir) {
                System.out.println("Tria una opció (número) seguit dels paràmetres necessaris per comprovar el mètode:");
                System.out.println("\t 1) Fitxer()");
                System.out.println("\t 2) Fitxer (String filePath, String fileExtension, String fileContent)");
                System.out.println("\t 3) void setFilePath(String filePath)");
                System.out.println("\t 4) void setFileExtension(String fileExtension)");
                System.out.println("\t 5) void setFileContent(String fileContent)");
                System.out.println("\t 6) String getFilePath()");
                System.out.println("\t 7) String getFileExtension()");
                System.out.println("\t 8) String getFileContent()");

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
                            fitxer = new Fitxer();
                            break;
                        case "2":
                            fitxer = new Fitxer (paraules[1], paraules[2], paraules[3]);
                            break;
                        case "3":
                            fitxer.setFilePath(paraules[1]);
                            break;
                        case "4":
                            fitxer.setFileExtension(paraules[1]);
                            break;
                        case "5":
                            fitxer.setFileContent(paraules[1]);
                            break;
                        case "6":
                            System.out.println("Path del fitxer: " + fitxer.getFilePath() + ".");
                            break;
                        case "7":
                            System.out.println("Extensions del fitxer: " + fitxer.getFileExtension() + ".");
                            break;
                        case "8":
                            System.out.println("Contingut del fitxer: " + fitxer.getFileContent() + ".");
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
