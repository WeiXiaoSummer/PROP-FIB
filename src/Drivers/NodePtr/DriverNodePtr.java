package Drivers.NodePtr;

import Domain.NodePtr;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DriverNodePtr {

    private static NodePtr nodePtr;

    public void main(String[] args) {
        String nomClasse = "NodePtr";
        System.out.println("Driver " + nomClasse + ":");

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            boolean sortir = false;
            while (!sortir) {
                System.out.println("Tria una opció (número) seguit dels paràmetres necessaris per comprovar el mètode:");
                System.out.println("\t 1) Constructora1()");
                System.out.println("\t 2) Constructora2(char value)");
                System.out.println("\t 3) void testInsertLeft(char value)");
                System.out.println("\t 4) void testInsertRight(char value)");
                System.out.println("\t 5) void testGetRightNode()");
                System.out.println("\t 0) Sortir ");

                String linea;
                String paraules[];
                String opcio;

                linea = br.readLine();
                paraules = linea.split(" ");
                opcio = paraules[0];
                try {
                    System.out.println("Opció " + opcio + " seleccionada.\n");
                    switch (opcio) {
                        case "1":
                            testConstructora1();
                            break;
                        case "2":
                            testConstructora2((char)Integer.parseInt(paraules[1]));
                            break;
                        case "3":
                            testInsertLeft((char)Integer.parseInt(paraules[1]));
                            break;
                        case "4":
                            testInsertRight((char)Integer.parseInt(paraules[1]));
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
        nodePtr = new NodePtr();
    }

    public static void testConstructora2(char value) {
        nodePtr = new NodePtr(value);
    }

    public static void testInsertRight(char value) {
        nodePtr.insertRight(value);
    }

    public static void testInsertLeft(char value) {
        nodePtr.insertLeft(value);
    }

}
