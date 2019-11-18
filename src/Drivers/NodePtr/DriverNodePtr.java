package Drivers.NodePtr;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DriverNodePtr {

    private static NodePtr nodePtr;

    public static void main(String[] args) {
        String nomClasse = "NodePtr";
        System.out.println("Driver " + nomClasse + ":");

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            boolean sortir = false;
            while (!sortir) {
                System.out.println("Tria una opció (número) seguit dels paràmetres necessaris per comprovar el mètode:\n-Els opcions amb" +
                        " * no cal introduir paràmetres sino que posteriorment haurà de triar una entrada predefinida\n" +
                        "-Els paràmetres que són de classe Stub no falta introduir ja que tenen comportament per defecte.\n");
                System.out.println("\t 1) NodePtr()");
                System.out.println("\t 2) NodePtr(char value)");
                System.out.println("\t 3) void InsertLeft(char value)");
                System.out.println("\t 4) void InsertRight(char value)");
                System.out.println("\t 0) Sortir ");

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
        System.out.println((int)nodePtr.getValue() + " " + nodePtr.isLeaf() + " " + nodePtr.isRoot() + " " + nodePtr.isLeft() +
                " " + nodePtr.getParent() + " " + nodePtr.getlChildren() + " " + nodePtr.getrChildren());
    }

    public static void testConstructora2(char value) {
        nodePtr = new NodePtr(value);
        System.out.println((int)nodePtr.getValue() + " " + nodePtr.isLeaf() + " " + nodePtr.isRoot() + " " + nodePtr.isLeft() +
                " " + nodePtr.getParent() + " " + nodePtr.getlChildren() + " " + nodePtr.getrChildren() + " " + nodePtr);
    }

    public static void testInsertRight(char value) {
        nodePtr.insertRight(value);
        System.out.println((int)nodePtr.getrChildren().getValue() + " " + nodePtr.getrChildren().isLeaf() + " " + nodePtr.getrChildren().isRoot()+
                " " + nodePtr.getrChildren().isLeft() + " " + nodePtr.getrChildren().getParent() + " " + nodePtr.getrChildren().getlChildren() +
                " " + nodePtr.getrChildren().getrChildren() + " " + nodePtr.getrChildren());
        System.out.println((int)nodePtr.getrChildren().getParent().getValue() + " " + nodePtr.getrChildren().getParent().isLeaf() +
                " " + nodePtr.getrChildren().getParent().isRoot() + " " + nodePtr.getrChildren().getParent().isLeft() +
                " " + nodePtr.getrChildren().getParent().getParent() + " " + nodePtr.getrChildren().getParent().getlChildren() +
                " " + nodePtr.getrChildren().getParent().getrChildren()+" " + nodePtr.getrChildren().getParent());
    }

    public static void testInsertLeft(char value) {
        nodePtr.insertLeft(value);
        System.out.println((int)nodePtr.getlChildren().getValue() + " " + nodePtr.getlChildren().isLeaf() + " " + nodePtr.getlChildren().isRoot()+
                " " + nodePtr.getlChildren().isLeft() + " " + nodePtr.getlChildren().getParent() + " " + nodePtr.getlChildren().getlChildren() +
                " " + nodePtr.getlChildren().getlChildren() + " " + nodePtr.getlChildren());
        System.out.println((int)nodePtr.getlChildren().getParent().getValue() + " " + nodePtr.getlChildren().getParent().isLeaf() +
                " " + nodePtr.getlChildren().getParent().isRoot() + " " + nodePtr.getlChildren().getParent().isLeft() +
                " " + nodePtr.getlChildren().getParent().getParent() + " " + nodePtr.getlChildren().getParent().getlChildren() +
                " " + nodePtr.getlChildren().getParent().getrChildren()+" " + nodePtr.getlChildren().getParent());
    }

}
