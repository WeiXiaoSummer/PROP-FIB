package Drivers.BitWriter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class DriverBitWriter {

    private static BitWriter bitWriter;

    public static void main(String[] args) {

        String nomClasse = "BitWriter";
        System.out.println("Driver " + nomClasse + ":");

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            boolean sortir = false;
            while (!sortir) {
                System.out.println("Tria una opció (número) seguit dels paràmetres necessaris per comprovar el mètode:\n-Els opcions amb" +
                        " * no cal introduir paràmetres sino que posteriorment haurà de triar una entrada predefinida\n" +
                        "-Els paràmetres no primitives es representen amb paràmetres primitives requerits per les seves constructores.\n" +
                        "Per exemple per representar CodeBits(char code, char bits) cal introduir en l'ordre code i bits.\n");
                System.out.println("\t 1) BitWriter()");
                System.out.println("\t 2) void write(CodeBits data)");
                System.out.println("\t 3) void flush()");
                System.out.println("\t 4) byte[] getOutput()");
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
                            testConstructor();
                            break;
                        case "2":
                            char code = (char)Integer.parseInt(paraules[1]);
                            byte bits = (byte)Integer.parseInt(paraules[2]);
                            CodeBits code_bits = new CodeBits(code, bits);
                            testWrite(code_bits);
                            break;
                        case "3":
                            testFlush();
                            break;
                        case "4":
                            testGetOutput();
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

    public static void testConstructor() {
        bitWriter = new BitWriter();
    }

    public static void testWrite(CodeBits codeBits) {
        bitWriter.write(codeBits);
    }

    public static void testFlush() {
        bitWriter.flush();
    }

    public static void testGetOutput() {
        byte[] result = bitWriter.getOutput();
        System.out.println("resultat = " + Arrays.toString(result));
    }
}
