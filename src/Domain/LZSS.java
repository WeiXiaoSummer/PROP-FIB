package Domain;

public class LZSS extends Algorithm{

    public void comprimir(String InFile, String NewName, String NewPath){

        FileInputStream FIn = new FileInputStream(InFile);
        FileOutputStream FOut = new FileOutputStream(NewPath + NewName + ".lzss");
        bytebuffer Ventana = bytebuffer.allocate(4092);

        byte nextByte;
        String ActualMatch = "";
        int MIndex = 0, tempIndex = 0, MidaMatch = 0;

        while(FIn.read(nextByte) != -1){
            //Busquem en la finestra
            tempIndex = Ventana.indexOf(ActualMatch + (char)nextByte);

            if(tempIndex != -1){
                ActualMatch += (char)nextByte;
                MIndex = tempIndex;
                MidaMatch += 1;
            }
            else{  //No hi ha match mes llarg
                if(MidaMatch > 3){  //Si la mida es mes gran que 3, codifica el match
                    MIndex -= MidaMatch; //Tornem a la posicio inicial del match
                    byte[] Codi = byte[3];
                    Codi[0] = (byte)1; //Flag a 1
                    Codi[1] = (byte)(MIndex >> 4);
                    byte Aux = (byte)(((MIndex & 0b1111) << 4) || (MidaMatch & 0b1111));
                    Codi[2] = Aux;
                    FOut.write(Codi);  //Escribim els 3 bytes codificats

                    while(Ventana.size() + MidaMatch > 4092){
                        Ventana.delete(0);  //Eliminem de la finestra els MidaMatch primers elements
                    }
                    Ventana.put(ActualMatch); //Posem a la finestra els bytes del match

                    ActualMatch = "";
                    MidaMatch = 0;
                    MIndex = -1;
                }
                else{  //Si la mida no es mes gran que 3, escriu flag a 0 i byte sense codificar
                    FOut.write((byte)0);

                    if(Ventana.size() >= 4092) Ventana.delete(0);  //Eliminem de la finestra el primer element

                    if(MidaMatch > 2){
                        FOut.write(nextByte);  //Escribim el byte actual a FOut
                        Ventana.put(nextByte); //Posem a la finestra el byte actual
                    }
                    else{
                        FOut.write(ActualMatch[0]);
                        Ventana.put(ActualMatch[0]); //Posem a la finestra el primer byte del match
                    }

                    ActualMatch = "";
                    MidaMatch = 0;
                    MIndex = -1;
                }
            }
        }
        FIn.close();
        FOut.close();
    }

    public void descomprimir(String pathfile, String) {

        FileInputStream FIn = new FileInputStream(InFile);
        FileOutputStream FOut = new FileOutputStream(NewPath + NewName + ".lzss");
        bytebuffer Ventana = bytebuffer.allocate(4092);

    }

    public static void Main(String[] Args){

        Comprimir(Args[1], Args[2], Args[3]);

    }
}
