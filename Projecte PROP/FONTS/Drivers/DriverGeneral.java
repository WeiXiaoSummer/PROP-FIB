package Drivers;

import java.io.*;
import java.util.Scanner;

public class DriverGeneral {

    private static void mostraMenu(){
        System.out.println("Selecciona Acció: ");
        System.out.println("1) Comprimir");
        System.out.println("2) Descomprimir");
        //System.out.println("3) Estadistiques Globals");
        //System.out.println("4) Historial");
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
                    System.out.println("Introdueix el path del fitxer a comprimir");
                    Path = entrada.nextLine();
                    System.out.println("Introdueix el path on vulguis guardar el fitxer comprimit");
                    NPath = entrada.nextLine();
                    System.out.println("Introdueix l'algoritme de compressió: AUTO, LZ78, LZSS, JPEG(només per imatges)");
                    Alg = entrada.nextLine();
                    System.out.println("Comprimint...");
                    Domain.DomainCtrl.getInstance().compressFileTo(Path, NPath, Alg);
                    System.out.println("Compresió finalizada");
                    mostraMenu();
                    opcion = entrada.nextLine();
                    break;
                case "2":
                    System.out.println("Has seleccionat: Descomprimir");
                    System.out.println("Introdueix el path del fitxer a descomprimir");
                    Path = entrada.nextLine();
                    System.out.println("Introdueix el path on vulguis guardar el fitxer descomprimit");
                    NPath = entrada.nextLine();
                    System.out.println("Descomprimint...");
                    Domain.DomainCtrl.getInstance().decompressFileTo(Path, NPath);
                    System.out.println("Descompresió finalizada");
                    mostraMenu();
                    opcion = entrada.nextLine();
                    break;
                /*case "3":
                    System.out.println("Has seleccionat: Estadistiques Globals");
                    System.out.println("Introdueix l'algoritme del que vols veure les estadistiques");
                    Alg = entrada.nextLine();
                    ArrayList<Object> Stats = Domain.DomainCtrl.getInstance().GetStatistic(Alg);

                    for(int i = 0; i < Stats.size(); ++i){
                        System.out.print(Stats.
                    }

                    mostraMenu();
                    opcion = entrada.nextLine();
                    break;
                case "4":
                    System.out.println("Has seleccionat: Historial");
                    ArrayList<ArrayList<Object>> Hist = Domain.DomainCtrl.getInstance().getHistory();

                    for(int i = 0; i < Hist.size(); ++i){
                        System.out.print(Hist
                    }

                    mostraMenu();
                    opcion = entrada.nextLine();
                    break;*/
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
