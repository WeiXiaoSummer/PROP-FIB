/*import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DriverAlgorithm {
    public static void main(String[] args) {
        String nomClasse = "Algorithm";
        System.out.println("Driver " + nomClasse + ":");

        //Algorithm a = new Algorithm();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            boolean sortir = false;
            while (!sortir) {
                System.out.println("Tria una opció (número) seguit dels paràmetres necessaris per comprovar el mètode:");
                System.out.println("\t 1) Algorithm()");
                System.out.println("\t 2) Algorithm(int numCompression, int numDecompression, int totalCompressedData," +
                        "                     int totalDecompressedData, double totalCompressionTime, double totalDecompressionTime," +
                        "                     double averageCompressionRatio)");
                System.out.println("\t 3) ArrayList<Object> getGlobalStatistic()");

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
                            a = new Algorithm();
                            break;
                        case "2":
                            int numCompression = Integer.parseInt(paraules[1]);
                            int numDecompression = Integer.parseInt(paraules[2]);
                            int totalCompressedData = Integer.parseInt(paraules[3]);
                            int totalDecompressedData = Integer.parseInt(paraules[4]);
                            double totalCompressionTime = Double.parseDouble(paraules[5]);
                            double totalDecompressionTime = Double.parseDouble(paraules[6]);
                            double averageCompressionRatio = Double.parseDouble(paraules[7]);
                            a = new Algorithm(numCompression, numDecompression, totalCompressedData, totalDecompressedData, totalCompressionTime,
                                    totalDecompressionTime, averageCompressionRatio);
                            break;
                        case "3":
                            System.out.println("Estadística global de l'algorisme actual: " + a.getGlobalStatistic() + ".");
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
*/
