package Domain;

import javafx.util.Pair;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class LZSS extends Algorithm {

    private ArrayList<Byte> Ventana;
    private ByteArrayOutputStream outStream;
    private int MidaMatch;
    private  ArrayList<Byte> ActualMatch;

    public LZSS(int numCompression, int numDecompression, int totalCompressedData, int totalDecompressedData, double totalCompressionTime, double totalDecompressionTime, double averageCompressionRatio) {
        super(numCompression, numDecompression, totalCompressedData, totalDecompressedData, totalCompressionTime, totalDecompressionTime, averageCompressionRatio);
    }

    public Pair<Double, Double> comprimir(Fitxer inFile, ByteArrayOutputStream compressedFile) {
        long startTime = System.currentTimeMillis();

        // Init variables
        byte[] content = inFile.getContent();
        Ventana = new ArrayList<>();
        ActualMatch = new ArrayList<>();
        outStream = new ByteArrayOutputStream();
        int MIndex = 0;
        int tempIndex;
        MidaMatch = 0;

        for (byte next : content) {
            ActualMatch.add(next);
            tempIndex = isSubArray(Ventana.toArray(), ActualMatch.toArray());

            if (tempIndex != -1 && MidaMatch < 7) {
                MIndex = tempIndex;
                ++MidaMatch;
            } else {
                if (MidaMatch > 2) {  // Si la mida es mes gran que 3
                    codeMatch(MIndex);
                    Ventana.addAll(ActualMatch.subList(0, MidaMatch));
                    while (Ventana.size() > 4096) Ventana.remove(0);
                    while (ActualMatch.size() > 1) ActualMatch.remove(0);
                    MIndex = -1;
                    MidaMatch = 1;

                } else {  // Si la mida no es mes gran que 3
                    escriu0ILiteral();
                    afegeixLiteralALaFinestra(ActualMatch.get(0));
                    eliminaLiteralDelMatch();
                    MIndex = -1;
                }
            }
        }
        while (ActualMatch.size() > 0) {
            MIndex = isSubArray(Ventana.toArray(), ActualMatch.toArray());

            if (MIndex != -1) {
                if (MidaMatch > 2) {  // Si la mida es mes gran que 3
                    codeMatch(MIndex);
                    while (MidaMatch > 0){
                        ActualMatch.remove(0);
                        --MidaMatch;
                    }

                } else {  // Si la mida no es mes gran que 3
                    escriu0ILiteral();
                    afegeixLiteralALaFinestra(ActualMatch.get(0));
                    eliminaLiteralDelMatch();
                }
            } else {
                escriu0ILiteral();
                afegeixLiteralALaFinestra(ActualMatch.get(0));
                ActualMatch.remove(0);
                --MidaMatch;
            }
        }

        long endTime = System.currentTimeMillis(); // get the time when end the compression
        double compressTime = (double) (endTime - startTime) * 0.001;

        globalStatistic.setNumCompression(globalStatistic.getNumCompression() + 1);
        globalStatistic.setTotalCompressionTime(globalStatistic.getTotalCompressionTime() + compressTime);

        double Ratio = 0.0;
        if (content.length != 0) {
            Ratio = ((double) outStream.size()  / (double) content.length) * 100;
            globalStatistic.setTotalCompressionRatio((globalStatistic.getTotalCompressionRatio() + Ratio)/2);
        }

        //output compressed file header and it's content
        try {
            byte fileNameLength = (byte) inFile.getFile().getName().length();
            byte[] compressedContent = outStream.toByteArray();
            byte[] compressedContentSize = ByteBuffer.allocate(4).putInt(compressedContent.length).array();
            compressedFile.write("LZSS".getBytes());
            compressedFile.write(fileNameLength);
            compressedFile.write(inFile.getFile().getName().getBytes());
            compressedFile.write(compressedContentSize);
            compressedFile.write(compressedContent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Pair<>(compressTime, Ratio);
    }


    public Pair<Double, Double> descomprimir(byte[] content, Fitxer outputFile) {
        long startTime = System.currentTimeMillis();
        Ventana = new ArrayList<>();
        outStream = new ByteArrayOutputStream();
        int i = 0;
        while (i < content.length) {
            byte nextByte = content[i];
            int aux1 = (nextByte & 0b01111111);
            ++i;
            if (nextByte == 0) {
                if (i < content.length) {
                    nextByte = content[i];
                    ++i;
                    afegeixLiteralALaFinestra(nextByte);
                    outStream.write(nextByte);
                }
            } else {
                int pos;
                int tam;
                if (i < content.length) {
                    byte aux0 = content[i];
                    ++i;
                    //Decode aux1 and aux0
                    tam = 0b111 & aux0;
                    pos = ((0b11111 & (aux0 >> 3)) | (aux1 << 5));

                    while (tam > 0) {
                        outStream.write(Ventana.get(pos));
                        Ventana.add(Ventana.get(pos));
                        ++pos;
                        --tam;
                    }
                    while (Ventana.size() > 4096) Ventana.remove(0);
                }
            }
        }
        long endTime = System.currentTimeMillis(); // get the time when end the compression
        double descompressTime = (double) (endTime - startTime) * 0.001;
        outputFile.setContent(outStream.toByteArray());
        globalStatistic.setTotalDecompressedData(globalStatistic.getTotalDecompressedData() + content.length);
        globalStatistic.setNumDecompression(globalStatistic.getNumDecompression() + 1);
        globalStatistic.setTotalDecompressionTime(globalStatistic.getTotalDecompressionTime() + descompressTime);

        return new Pair<>(descompressTime, (double) outputFile.getContent().length / content.length);
    }


    // Function to check if an array is subArray of another one
    private int isSubArray(Object[] A, Object[] B) {
        // Two pointers
        int i = 0, j = 0;
        // A mark pointer
        int mark = 0;

        while (i < A.length && j < B.length) {
            // If matches increment pointers
            if (A[i] == B[j]) {
                i++;
                j++;
                // If array B is completely traversed
                if (j == B.length) return mark;
            }
            // If not, increment i and reset j and mark pointers
            else {
                i++;
                j = 0;
                mark = i;
            }
        }
        // If it isn't a subArray return -1
        return -1;
    }


    private void afegeixLiteralALaFinestra(Byte literal) {
        Ventana.add(literal); //Posem a la finestra el primer char del match
        if (Ventana.size() > 4096) Ventana.remove(0);  //Eliminem de la finestra el primer element
    }


    private void escriu0ILiteral() {
        outStream.write((byte) 0);
        outStream.write(ActualMatch.get(0));
    }


    private void eliminaLiteralDelMatch() {
        ActualMatch.remove(0);
        if (ActualMatch.size() == 0) MidaMatch = 0;
    }


    private void codeMatch(int MIndex) {
        byte aux = (byte) (0b10000000 | (0b1111111 & (MIndex >> 5)));
        outStream.write(aux);
        aux = (byte) (((0b11111 & MIndex) << 3) | (0b111 & MidaMatch));
        outStream.write(aux);
    }

}
