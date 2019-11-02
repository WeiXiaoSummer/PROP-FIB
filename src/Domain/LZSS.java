package Domain;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class LZSS extends Algorithm {

    private FileReader FIn;
    private FileWriter FOut;
    private StringBuilder Ventana;
    private short nextChar;


    public LZSS() {

    }


    public void comprimir(String InFile, String NewName, String NewPath) throws IOException {

        FIn = new FileReader(InFile);
        FOut = new FileWriter(NewPath + "\\" + NewName + ".lzss");
        Ventana = new StringBuilder();
        StringBuilder ActualMatch = new StringBuilder();

        int MIndex = 0;
        int tempIndex;
        int MidaOrig = 0;
        int MidaComp = 0;
        int MidaMatch = 0;

        while((nextChar =  (short) FIn.read()) != -1){
            ++MidaOrig;
            ActualMatch.append((char)nextChar);
            tempIndex = Ventana.indexOf(ActualMatch.toString());

            if(tempIndex != -1){
                MIndex = tempIndex;
                ++MidaMatch;
            }
            else{
                if (MidaMatch > 3) {  //Si la mida es mes gran que 3, codifica el match
                    char[] Codi = new char[3];
                    Codi[0] = 1; //Flag a 1
                    Codi[1] = (char)MIndex;
                    Codi[2] = (char)MidaMatch;
                    FOut.write(Codi);  //Escribim els 3 chars codificats
                    MidaComp += 3;

                    Ventana.append(ActualMatch.substring(0, MidaMatch));
                    while(Ventana.length() > 65536) Ventana.deleteCharAt(0);

                    while(ActualMatch.length() > 1) ActualMatch.deleteCharAt(0);
                    MIndex = -1;
                    MidaMatch = 1;

                } else {  //Si la mida no es mes gran que 3, escriu flag a 0 i char sense codificar
                    FOut.append((char)0);
                    FOut.append(ActualMatch.charAt(0));
                    MidaComp += 2;
                    Ventana.append(ActualMatch.charAt(0)); //Posem a la finestra el primer char del match
                    if (Ventana.length() > 65536)  Ventana.deleteCharAt(0);  //Eliminem de la finestra el primer element

                    ActualMatch.deleteCharAt(0);
                    if(ActualMatch.length() == 0) MidaMatch = 0;

                    MIndex = -1;
                }
            }
        }
        while(MidaMatch > 0){
            MIndex = Ventana.indexOf(ActualMatch.toString());

            if(MIndex != -1){
                if (MidaMatch > 3) {  //Si la mida es mes gran que 3, codifica el match
                    char[] Codi = new char[3];
                    Codi[0] = 1; //Flag a 1
                    Codi[1] = (char)MIndex;
                    Codi[2] = (char)MidaMatch;
                    FOut.write(Codi);  //Escribim els 3 chars codificats
                    MidaComp += 3;

                    Ventana.append(ActualMatch.substring(0, MidaMatch));
                    while(Ventana.length() > 65536) Ventana.deleteCharAt(0);

                    while(ActualMatch.length() > 0) ActualMatch.deleteCharAt(0);
                    MidaMatch = 0;

                } else {  //Si la mida no es mes gran que 3, escriu flag a 0 i char sense codificar
                    FOut.append((char)0);
                    FOut.append(ActualMatch.charAt(0));
                    MidaComp += 2;

                    Ventana.append(ActualMatch.charAt(0)); //Posem a la finestra el primer char del match
                    if (Ventana.length() > 65536)  Ventana.deleteCharAt(0);  //Eliminem de la finestra el primer element

                    ActualMatch.deleteCharAt(0);
                    if(ActualMatch.length() == 0) MidaMatch = 0;
                }
            }
            else{
                FOut.append((char)0);
                FOut.append(ActualMatch.charAt(0));
                MidaComp += 2;

                Ventana.append(ActualMatch.charAt(0)); //Posem a la finestra el primer char del match
                if (Ventana.length() > 65536)  Ventana.deleteCharAt(0);  //Eliminem de la finestra el primer element

                ActualMatch.deleteCharAt(0);
                --MidaMatch;
            }
        }
        //MAL
        System.out.println("Mida del Fitxer Original: " + MidaOrig);
        System.out.println("Mida del Fitxer Comprimit: " + MidaComp);
        double OpMat = ((double)MidaComp/(double)MidaOrig)*100;
        System.out.println("Percentatge de compressio: " + OpMat);
        FIn.close();
        FOut.close();
    }


    public void descomprimir(String InFile, String NewName, String NewPath) throws IOException {

        FIn = new FileReader(InFile);
        FOut = new FileWriter(NewPath + "\\" + NewName + ".txt");
        Ventana = new StringBuilder();
        int nextInt;

        while((nextChar = (short)FIn.read()) != -1){

            if(nextChar == 0){
                if((nextChar = (short) FIn.read()) != -1){
                    Ventana.append((char)nextChar);
                    if(Ventana.length() > 65536) Ventana.deleteCharAt(0);
                    FOut.write((char)nextChar);
                }
            }
            else if(nextChar == 1){
                int pos;
                int tam;

                if((nextInt = FIn.read()) != -1){
                    pos = nextInt;
                    if((nextChar = (short) FIn.read()) != -1){
                        tam = nextChar;
                        while(tam > 0){
                            FOut.write(Ventana.charAt(pos));
                            Ventana.append(Ventana.charAt(pos));
                            ++pos;
                            --tam;
                        }
                        while(Ventana.length() > 65536) Ventana.deleteCharAt(0);
                    }
                }
            }
        }
        FIn.close();
        FOut.close();
    }

}

