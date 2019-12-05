package Drivers.LZSS;

/*
public class LZSS extends Algorithm {
      private StringBuilder Ventana;
    private char nextChar;
    private String outStream;


    public LZSS(int numCompression, int numDecompression, int totalCompressedData, int totalDecompressedData, double totalCompressionTime, double totalDecompressionTime, double averageCompressionRatio) {
        super(numCompression, numDecompression, totalCompressedData, totalDecompressedData, totalCompressionTime, totalDecompressionTime, averageCompressionRatio);
    }


    public Fitxer comprimir(Fitxer file) {
        String content = file.getFileContent();
        Ventana = new StringBuilder();
        StringBuilder ActualMatch = new StringBuilder();
        long startTime = System.currentTimeMillis();
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
                    while(Ventana.length() > 32768) Ventana.deleteCharAt(0);

                    while(ActualMatch.length() > 1) ActualMatch.deleteCharAt(0);
                    MIndex = -1;
                    MidaMatch = 1;

                } else {  //Si la mida no es mes gran que 3, escriu flag a 0 i char sense codificar
                    outStream += (char) 0;
                    outStream += ActualMatch.charAt(0);
                    Ventana.append(ActualMatch.charAt(0)); //Posem a la finestra el primer char del match
                    if (Ventana.length() > 32768)  Ventana.deleteCharAt(0);  //Eliminem de la finestra el primer element

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
                    while(Ventana.length() > 32768) Ventana.deleteCharAt(0);

                    while(ActualMatch.length() > 0) ActualMatch.deleteCharAt(0);
                    MidaMatch = 0;

                } else {  //Si la mida no es mes gran que 3, escriu flag a 0 i char sense codificar
                    outStream += (char) 0;
                    outStream += ActualMatch.charAt(0);

                    Ventana.append(ActualMatch.charAt(0)); //Posem a la finestra el primer char del match
                    if (Ventana.length() > 32768)  Ventana.deleteCharAt(0);  //Eliminem de la finestra el primer element

                    ActualMatch.deleteCharAt(0);
                    if(ActualMatch.length() == 0) MidaMatch = 0;
                }
            }
            else{
                outStream += (char) 0;
                outStream += ActualMatch.charAt(0);

                Ventana.append(ActualMatch.charAt(0)); //Posem a la finestra el primer char del match
                if (Ventana.length() > 32768)  Ventana.deleteCharAt(0);  //Eliminem de la finestra el primer element

                ActualMatch.deleteCharAt(0);
                --MidaMatch;
            }
        }

        long endTime=System.currentTimeMillis(); // get the time when end the compression
        double compressTime = (double)(endTime-startTime)* 0.001;
        System.out.println("Temps trigat: " + compressTime);
        globalStatistic.setNumCompression(globalStatistic.getNumCompression() + 1);
        globalStatistic.setTotalCompressionTime(globalStatistic.getTotalCompressionTime()+compressTime);
        double Ratio;
        if(content.length() != 0) {
            Ratio = ((double) content.length() / (double) outStream.length())*100;
            globalStatistic.setAverageCompressionRatio((globalStatistic.getAverageCompressionRatio() + Ratio) / 2);
            System.out.println("Ratio de compressió: " + Ratio);
        }
        return new Fitxer("", ".lzss", outStream);
    }


    public Fitxer descomprimir(Fitxer file) {
        String content = file.getFileContent();
        long startTime = System.currentTimeMillis();
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
                    if(Ventana.length() > 32768) Ventana.deleteCharAt(0);
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
                        while(Ventana.length() > 32768) Ventana.deleteCharAt(0);
                    }
                }
            }
        }
        long endTime=System.currentTimeMillis(); // get the time when end the compression
        double descompressTime = (double)(endTime-startTime)* 0.001;
        System.out.println("Temps trigat: " + descompressTime);
        globalStatistic.setNumDecompression(globalStatistic.getNumDecompression()+1);
        globalStatistic.setTotalDecompressionTime(globalStatistic.getTotalDecompressionTime() + descompressTime);
        return new Fitxer("", ".txt", outStream);
    }

}
*/