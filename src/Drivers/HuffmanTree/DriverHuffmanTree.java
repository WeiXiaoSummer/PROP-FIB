package Drivers.HuffmanTree;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DriverHuffmanTree {

    private static HuffmanTree huffmanTree;
    private static BitReader bitReader = new BitReader();

    public static void main(String[] args) {
        String nomClasse = "BitWriter";
        System.out.println("Driver " + nomClasse + ":");

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            boolean sortir = false;
            while (!sortir) {
                System.out.println("Tria una opció (número) seguit dels paràmetres necessaris per comprovar el mètode:\n-Els opcions amb" +
                        " * no cal introduir paràmetres sino que posteriorment haurà de triar una entrada predefinida\n" +
                        "-Els paràmetres que són de classe Stub no falta introduir ja que tenen comportament per defecte.\n");
                System.out.println("\t 1) *HuffmanTree(char[] codesPerBitSize, char[] huffmanValues)");
                System.out.println("\t 2) public NodePtr getRightNode(NodePtr actualNode)");
                System.out.println("\t 3) public int decodeHuffmanCode(BitReader bitReader)");
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
                            System.out.println("\t Opció 1: inicialitzar HuffmanTree amb la taula de Huffman de Luminance DC Coefficients;");
                            System.out.println("\t Opció 2: inicialitzar HuffmanTree amb la taula de Huffman de Luminance AC Coefficients;");
                            System.out.println("\t Opció 3: inicialitzar HuffmanTree amb la taula de Huffman de Chrominance DC Coefficients;");
                            System.out.println("\t Opció 4: inicialitzar HuffmanTree amb la taula de Huffman de Chrominance AC Coefficients;");
                            linea = br.readLine();
                            paraules = linea.split(" ");
                            opcio = paraules[0];
                            testConstructora(Integer.parseInt(opcio));
                            break;
                        case "2":
                            System.out.println("\t Aquesta funció per defecte retorna el node dret del fill esquerre del root");
                            testGetRightNode();
                        case "3":
                            testDecodeHuffmanCode();
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

    public static void testConstructora(int option) {
        switch (option) {
            case 1:
                char DCLuminanceCodesPerBitSize[] = {0,1,5,1,1,1,1,1,1};
                char DcLuminanceValues[] = {0,1,2,3,4,5,6,7,8,9,10,11};
                huffmanTree = new HuffmanTree(DCLuminanceCodesPerBitSize, DcLuminanceValues);
                break;
            case 2:
                char ACLuminanceCodesPerBitSize[] = {0,2,1,3,3,2,4,3,5,5,4,4,0,0,1,125};
                char ACLuminanceValues[] =
                        {0x01,0x02,0x03,0x00,0x04,0x11,0x05,0x12,0x21,0x31,0x41,0x06,0x13,0x51,0x61,0x07,0x22,0x71,0x14,0x32,0x81,0x91,0xA1,0x08,
                                0x23,0x42,0xB1,0xC1,0x15,0x52,0xD1,0xF0,0x24,0x33,0x62,0x72,0x82,0x09,0x0A,0x16,0x17,0x18,0x19,0x1A,0x25,0x26,0x27,0x28,
                                0x29,0x2A,0x34,0x35,0x36,0x37,0x38,0x39,0x3A,0x43,0x44,0x45,0x46,0x47,0x48,0x49,0x4A,0x53,0x54,0x55,0x56,0x57,0x58,0x59,
                                0x5A,0x63,0x64,0x65,0x66,0x67,0x68,0x69,0x6A,0x73,0x74,0x75,0x76,0x77,0x78,0x79,0x7A,0x83,0x84,0x85,0x86,0x87,0x88,0x89,
                                0x8A,0x92,0x93,0x94,0x95,0x96,0x97,0x98,0x99,0x9A,0xA2,0xA3,0xA4,0xA5,0xA6,0xA7,0xA8,0xA9,0xAA,0xB2,0xB3,0xB4,0xB5,0xB6,
                                0xB7,0xB8,0xB9,0xBA,0xC2,0xC3,0xC4,0xC5,0xC6,0xC7,0xC8,0xC9,0xCA,0xD2,0xD3,0xD4,0xD5,0xD6,0xD7,0xD8,0xD9,0xDA,0xE1,0xE2,
                                0xE3,0xE4,0xE5,0xE6,0xE7,0xE8,0xE9,0xEA,0xF1,0xF2,0xF3,0xF4,0xF5,0xF6,0xF7,0xF8,0xF9,0xFA };
                huffmanTree = new HuffmanTree(ACLuminanceCodesPerBitSize, ACLuminanceValues);
                break;
            case 3:
                char DCChrominanceCodesPerBitSize[] = {0,3,1,1,1,1,1,1,1,1,1};
                char DcChrominanceValues[] = {0,1,2,3,4,5,6,7,8,9,10,11};
                huffmanTree = new HuffmanTree(DCChrominanceCodesPerBitSize, DcChrominanceValues);
                break;
            case 4:
                char ACChrominanceCodesPerBitSize[] = {0,2,1,2,4,4,3,4,7,5,4,4,0,1,2,119};
                char ACChrominanceValues[] =
                        { 0x00,0x01,0x02,0x03,0x11,0x04,0x05,0x21,0x31,0x06,0x12,0x41,0x51,0x07,0x61,0x71,0x13,0x22,0x32,0x81,0x08,0x14,0x42,0x91, // same number of symbol, just different order
                                0xA1,0xB1,0xC1,0x09,0x23,0x33,0x52,0xF0,0x15,0x62,0x72,0xD1,0x0A,0x16,0x24,0x34,0xE1,0x25,0xF1,0x17,0x18,0x19,0x1A,0x26, // (which is more efficient for AC coding)
                                0x27,0x28,0x29,0x2A,0x35,0x36,0x37,0x38,0x39,0x3A,0x43,0x44,0x45,0x46,0x47,0x48,0x49,0x4A,0x53,0x54,0x55,0x56,0x57,0x58,
                                0x59,0x5A,0x63,0x64,0x65,0x66,0x67,0x68,0x69,0x6A,0x73,0x74,0x75,0x76,0x77,0x78,0x79,0x7A,0x82,0x83,0x84,0x85,0x86,0x87,
                                0x88,0x89,0x8A,0x92,0x93,0x94,0x95,0x96,0x97,0x98,0x99,0x9A,0xA2,0xA3,0xA4,0xA5,0xA6,0xA7,0xA8,0xA9,0xAA,0xB2,0xB3,0xB4,
                                0xB5,0xB6,0xB7,0xB8,0xB9,0xBA,0xC2,0xC3,0xC4,0xC5,0xC6,0xC7,0xC8,0xC9,0xCA,0xD2,0xD3,0xD4,0xD5,0xD6,0xD7,0xD8,0xD9,0xDA,
                                0xE2,0xE3,0xE4,0xE5,0xE6,0xE7,0xE8,0xE9,0xEA,0xF2,0xF3,0xF4,0xF5,0xF6,0xF7,0xF8,0xF9,0xFA };
                huffmanTree = new HuffmanTree(ACChrominanceCodesPerBitSize, ACChrominanceValues);
                break;
            default:
                System.out.println("La opció triada no existeix");
                break;
        }
    }

    public static void testGetRightNode() {
        System.out.println(huffmanTree.getRoot().getrChildren());
        System.out.println(huffmanTree.getRightNode(huffmanTree.getRoot().getlChildren()));
    }

    public static void testDecodeHuffmanCode() {
        System.out.println(huffmanTree.decodeHuffmanCode(bitReader));
    }
}
