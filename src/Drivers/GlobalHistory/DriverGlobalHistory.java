package Drivers.GlobalHistory;

import Domain.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DriverGlobalHistory {

    private static GlobalHistory globalHistory = new GlobalHistory();
    public static void main(String[] args) {
        String nomClasse = "GlobalHistory";
        System.out.println("Driver " + nomClasse + ":");

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            boolean sortir = false;
            while (!sortir) {
                System.out.println("Tria una opció (número) seguit dels paràmetres necessaris per comprovar el mètode:\n-Els opcions amb" +
                        " * no cal introduir paràmetres sino que posteriorment haurà de triar una entrada predefinida\n" +
                        "-Els paràmetres no primitives es representen amb paràmetres primitives requerits per les seves constructores.\n" +
                        "Per exemple per representar CodeBits(char code, char bits) cal introduir en l'ordre code i bits.\n");
                System.out.println("\t 1) GlobalHistory()");
                System.out.println("\t 2) void addLocalHistory(LocalHistory LH)" + "LocalHistory necessita els seguents " +
                        "paràmetres per crear-se: String inputPath, String outPutPath, String fileExtension, String operation, " +
                        "String algorithm, double compressionRatio, double timeUsed");
                System.out.println("\t 3) ArrayList<ArrayList<Object>> getInformation ()");
                System.out.println("\t 4) ArrayList<LocalHistory> getGlobalHistory()");
                System.out.println("\t 5) void delete(LocalHistory localHistory)" + "LocalHistory necessita els seguents " +
                        "paràmetres per crear-se: String inputPath, String outPutPath, String fileExtension, String operation, " +
                        "String algorithm, double compressionRatio, double timeUsed");
                System.out.println("\t 6) void deleteAll()");

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
                            globalHistory = new GlobalHistory();
                            break;
                        case "2":
                            LocalHistory LH = new LocalHistory(paraules[1], paraules[2], paraules[3], paraules[4], paraules[5],
                                    Double.parseDouble(paraules[6]), Double.parseDouble(paraules[7]));
                            globalHistory.addLocalHistory(LH);
                            break;
                        case "3":
                            System.out.print("El contingut del historia és: " + globalHistory.getInformation());
                            break;
                        case "4":
                            System.out.print("El contingut del historia és: " + globalHistory.getInformation());
                            break;
                        case "5":
                            LH = new LocalHistory(paraules[1], paraules[2], paraules[3], paraules[4], paraules[5],
                                    Double.parseDouble(paraules[6]), Double.parseDouble(paraules[7]));
                            globalHistory.delete(LH);
                            break;
                        case "6":
                            System.out.print("Buit history");
                            globalHistory.deleteAll();
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

    public static void testConstructora() {
        globalHistory = new GlobalHistory();
    }

    public static void addLocalHistory() {}
}
