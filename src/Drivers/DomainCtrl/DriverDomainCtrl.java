package Drivers.DomainCtrl;

import java.io.*;
import java.util.Scanner;

public class DriverDomainCtrl {

    private static void mostraMenu(){
        System.out.println("Selecciona Acció: ");
        System.out.println("1) Comprimir");
        System.out.println("2) Descomprimir");
        System.out.println("3) Sortir");
    }

    public static void main(String[] Args) throws IOException {

        Scanner entrada = new Scanner(System.in);
        String Path;
        String NPath;
        String Alg;

        System.out.println("##############################");
        System.out.println("#     Compressor V.beta      #");
        System.out.println("##############################");
        System.out.println("");
        mostraMenu();

        String opcion = entrada.nextLine();
        int out = 0;
        while(out == 0) {
            switch (opcion) {
                case "1":
                    System.out.println("Has seleccionat: Comprimir");
                    System.out.println("Introdueix el path completa del fitxer a comprimir");
                    Path = entrada.nextLine();
                    System.out.println("Introdueix el path completa(amb el nom del fitxer" +
                            " pero no fa falta indicar tipus) on vulguis guardar el fitxer comprimit");
                    NPath = entrada.nextLine();
                    System.out.println("Introdueix l'algoritme de compressió: AUTO, LZ78, LZSS, JPEG(només per imatges)");
                    Alg = entrada.nextLine();
                    System.out.println("Comprimint...");
                    DomainCtrl.getInstance().compressFileTo(Path, NPath, Alg);
                    System.out.println("Compresió finalizada");
                    mostraMenu();
                    opcion = entrada.nextLine();
                    break;
                case "2":
                    System.out.println("Has seleccionat: Descomprimir");
                    System.out.println("Introdueix el path completa del fitxer a Descomprimir");
                    Path = entrada.nextLine();
                    System.out.println("Introdueix el path completa(amb el nom del fitxer" +
                            "pero no fa falta indicar tipus) on vulguis guardar el fitxer descomprimit");
                    NPath = entrada.nextLine();
                    System.out.println("Descomprimint...");
                    DomainCtrl.getInstance().decompressFileTo(Path, NPath);
                    System.out.println("Descompresió finalizada");
                    mostraMenu();
                    opcion = entrada.nextLine();
                    break;
                case "3":
                    System.out.println("Sortint...");
                    out = 1;
                    break;
                default:
                    System.out.println("Acció no valida \n");
                    mostraMenu();
                    opcion = entrada.nextLine();
            }
        }
    }
}
