package Domain;

import java.io.*;

public class LZSS extends Algorithm {

    private StringBuilder Ventana;
    private char nextChar;
    private String outStream;


    public LZSS(int numCompression, int numDecompression, int totalCompressedData, int totalDecompressedData, double totalCompressionTime, double totalDecompressionTime, double averageCompressionRatio) {
        super(numCompression, numDecompression, totalCompressedData, totalDecompressedData, totalCompressionTime, totalDecompressionTime, averageCompressionRatio);
    }

    public String comprimir(String content) throws IOException {
        Ventana = new StringBuilder();
        StringBuilder ActualMatch = new StringBuilder();
        long startTime = System.nanoTime();
        outStream = "";
        int MIndex = 0;
        int tempIndex;
        int MidaMatch = 0;
        for(int i = 0; i < content.length(); ++i){
            nextChar =  content.charAt(i);
            ActualMatch.append((char)nextChar);
            tempIndex = Ventana.indexOf(ActualMatch.toString());

            if(tempIndex != -1){
                MIndex = tempIndex;
                ++MidaMatch;
            }
            else{
                if (MidaMatch > 3) {  //Si la mida es mes gran que 3, codifica el match
                    outStream += (char) 1;
                    outStream += (char)MIndex;
                    outStream += (char)MidaMatch;

                    Ventana.append(ActualMatch.substring(0, MidaMatch));
                    while(Ventana.length() > 65536) Ventana.deleteCharAt(0);

                    while(ActualMatch.length() > 1) ActualMatch.deleteCharAt(0);
                    MIndex = -1;
                    MidaMatch = 1;

                } else {  //Si la mida no es mes gran que 3, escriu flag a 0 i char sense codificar
                    outStream += (char) 0;
                    outStream += ActualMatch.charAt(0);
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
                    outStream += (char) 1;
                    outStream += (char)MIndex;
                    outStream += (char)MidaMatch;

                    Ventana.append(ActualMatch.substring(0, MidaMatch));
                    while(Ventana.length() > 65536) Ventana.deleteCharAt(0);

                    while(ActualMatch.length() > 0) ActualMatch.deleteCharAt(0);
                    MidaMatch = 0;

                } else {  //Si la mida no es mes gran que 3, escriu flag a 0 i char sense codificar
                    outStream += (char) 0;
                    outStream += ActualMatch.charAt(0);

                    Ventana.append(ActualMatch.charAt(0)); //Posem a la finestra el primer char del match
                    if (Ventana.length() > 65536)  Ventana.deleteCharAt(0);  //Eliminem de la finestra el primer element

                    ActualMatch.deleteCharAt(0);
                    if(ActualMatch.length() == 0) MidaMatch = 0;
                }
            }
            else{
                outStream += (char) 0;
                outStream += ActualMatch.charAt(0);

                Ventana.append(ActualMatch.charAt(0)); //Posem a la finestra el primer char del match
                if (Ventana.length() > 65536)  Ventana.deleteCharAt(0);  //Eliminem de la finestra el primer element

                ActualMatch.deleteCharAt(0);
                --MidaMatch;
            }
        }

        long endTime = System.nanoTime();
        double compressTime = (double)(endTime-startTime)/1000000;

        globalStatistic.setNumCompression(globalStatistic.getNumCompression()+1);
        globalStatistic.setTotalCompressionTime(globalStatistic.getTotalCompressionTime()+compressTime);
        double Ratio;
        if(content.length() != 0) {
            Ratio = ((double) content.length() / (double) outStream.length())*100;
            globalStatistic.setAverageCompressionRatio((globalStatistic.getAverageCompressionRatio() + Ratio) /2);
        }
        return outStream;
    }

    public String descomprimir(String content) throws IOException {
        long startTime = System.nanoTime();
        Ventana = new StringBuilder();
        outStream = "";
        int i = 0;
        while(i < content.length()){
            nextChar = content.charAt(i);
            ++i;
            if(nextChar == 0){
                if(i < content.length()){
                    nextChar = content.charAt(i);
                    ++i;
                    Ventana.append(nextChar);
                    if(Ventana.length() > 65536) Ventana.deleteCharAt(0);
                    outStream += nextChar;
                }
            }
            else if(nextChar == 1){
                int pos;
                int tam;
                if(i < content.length()){
                    pos = content.charAt(i);
                    ++i;
                    if(i < content.length()){
                        tam = content.charAt(i);
                        ++i;
                        while(tam > 0){
                            outStream += Ventana.charAt(pos);
                            Ventana.append(Ventana.charAt(pos));
                            ++pos;
                            --tam;
                        }
                        while(Ventana.length() > 65536) Ventana.deleteCharAt(0);
                    }
                }
            }
        }
        long endTime = System.nanoTime();
        double descompressTime = (double)(endTime-startTime)/1000000;
        globalStatistic.setNumDecompression(globalStatistic.getNumDecompression()+1);
        globalStatistic.setTotalDecompressionTime(globalStatistic.getTotalDecompressionTime() + descompressTime);
        return outStream;
    }

}
