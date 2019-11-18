package Drivers.BitReader;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DriverBitReader {

    private static BitReader bitReader = new BitReader(new byte[] {});

    public static void main(String[] args) {

        String nomClasse = "BitReader";
        System.out.println("Driver " + nomClasse + ":");

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            boolean sortir = false;
            while (!sortir) {
                System.out.println("Tria una opció (número) seguit dels paràmetres necessaris per comprovar el mètode:\n-Els opcions amb" +
                        " * no cal introduir paràmetres sino que posteriorment haurà de triar una entrada predefinida\n" +
                        "-Els paràmetres que són de classe Stub no falta introduir ja que tenen comportament per defecte.\n");
                System.out.println("\t 1) *BitReader(byte[] input)");
                System.out.println("\t 2) boolean next()");
                System.out.println("\t 3) boolean readOne()");
                System.out.println("\t 4) int readInt(int numberLength)");

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
                            System.out.println("\t 1) inicialitzar BitReader amb byte[] B = []");
                            System.out.println("\t 2) inicialitzar BitReader amb byte[] B = [0xAA, 0xFF, 0x33, 0xEE]");
                            System.out.println("\t 3) inicialitzar BitReader amb byte[] B = [0xA2, 0xEA, 0xFB, 0xCC]");
                            linea = br.readLine();
                            paraules = linea.split(" ");
                            opcio = paraules[0];
                            testConstructora(opcio);
                            break;
                        case "2":
                            testNext();
                            break;
                        case "3":
                            testReadOne();
                            break;
                        case "4":
                            testReadInt(paraules[1]);
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

    public static void testConstructora(String opcio) {
        switch (opcio) {
            case "1":
                bitReader = new BitReader(new byte[]{});
                break;
            case "2":
                bitReader = new BitReader(new byte[]{(byte)0xAA, (byte)0xFF, (byte)0x33, (byte)0xEE});
                break;
            case "3":
                bitReader = new BitReader(new byte[]{(byte)0xA2, (byte)0xEA, (byte)0xFB, (byte)0xCC});
                break;

        }
    }

    public static void testNext() {
        System.out.println(bitReader.next());
    }

    public static void testReadOne() {
        System.out.println(bitReader.readOne());
    }

    public static void testReadInt(String lenght) {
        System.out.println(bitReader.readInt(Integer.parseInt(lenght)));
    }

}
