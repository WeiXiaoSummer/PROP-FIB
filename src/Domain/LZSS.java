package Domain;

import Commons.DomainLayerException;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * Represents a LZSS compression algorithm class.
 */
public class LZSS extends Algorithm {

    /**
     * Window to search a match
     */
    private ArrayList<Byte> Ventana;

    /**
     * Object to write the output
     */
    private ByteArrayOutputStream outStream;

    /**
     * Size of the match
     */
    private int MidaMatch;

    /**
     * Actual match to search at window
     */
    private  ArrayList<Byte> ActualMatch;

    /**
     * Statistics of the compression or decompression process
     */
    private Object[] statistics;


    /**
     * Create a new LZSS with the given GlobalStatistic object.
     * @param estadistiques the GlobalStatistic object to be loaded.
     */
    public LZSS(GlobalStatistic estadistiques) {
        super(estadistiques);
    }

    /**
     Compress the content of the inFile and write the result into the temporal buffer compressedFile.
     * @param inFile Fitxer to be compressed
     * @param compressedFile ByteArrayOutputStream to be wrote
     * @return the compression statistic in the form of a Object array:
     *         -(int)First position contains the original content size expressed in bytes.
     *         -(int)Second position contains the compressed content size expressed in bytes.
     *         -(double)Third position contains the compression time expressed in s.
     * @throws DomainLayerException
     */
    @Override
    public Object[] comprimir(Fitxer inFile, ByteArrayOutputStream compressedFile) throws DomainLayerException {
        try {
            globalStatistic.addNumCompression();
            Object[] compressionStatistic = {0, 0, 0};
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
                    if (MidaMatch > 2) {  // If MatchSize > 2
                        codeMatch(MIndex);
                        Ventana.addAll(ActualMatch.subList(0, MidaMatch));
                        while (Ventana.size() > 4096) Ventana.remove(0);
                        while (ActualMatch.size() > 1) ActualMatch.remove(0);
                        MIndex = -1;
                        MidaMatch = 1;

                    } else {  // If MatchSize < 2
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
                    if (MidaMatch > 2) {  // If MatchSize > 2
                        codeMatch(MIndex);
                        while (MidaMatch > 0){
                            ActualMatch.remove(0);
                            --MidaMatch;
                        }

                    } else {  // If MatchSize < 2
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

            long endTime = System.currentTimeMillis(); // Get time when end the compression
            double compressTime = (double) (endTime - startTime) * 0.001;

            globalStatistic.setNumCompression(globalStatistic.getNumCompression() + 1);
            globalStatistic.setTotalCompressionTime(globalStatistic.getTotalCompressionTime() + compressTime);

            byte fileNameLength = (byte) inFile.getFile().getName().length();
            byte[] compressedContent = outStream.toByteArray();
            byte[] compressedContentSize = ByteBuffer.allocate(4).putInt(compressedContent.length).array();

            //output compressed file header and it's content
            compressedFile.write("LZSS".getBytes());
            compressedFile.write(fileNameLength);
            compressedFile.write(inFile.getFile().getName().getBytes());
            compressedFile.write(compressedContentSize);
            compressedFile.write(compressedContent);

            if (content.length > 0) {
                compressionStatistic[0] = content.length;
                compressionStatistic[1] = compressedContent.length;
                compressionStatistic[2] = (double) (endTime - startTime) * 0.001;
                globalStatistic.addTotalCompressedData(content.length);
                globalStatistic.addTotalCompressionTime((double) compressionStatistic[2]);
                globalStatistic.addTotalCompressionRatio(content.length / compressedContent.length);
            }

            return compressionStatistic;
        }
        catch (Exception e) {
            throw new DomainLayerException( "An error has occurred while compressing the file:\n"+inFile.getFile().getPath()+
                    "\nCompression aborted");
        }

    }

    /**
     * Decompress the compressed content and stores the decompressed content in the outPutFile
     * @param content data to be decompress
     * @param outputFile Fitxer to be wrote
     * @return the decompression statistic in the form of a Object array:
     *         -(int)First position contains the decompressed content size expressed in bytes.
     *         -(int)Second position contains the compressed content size expressed in bytes.
     *         -(double)Third position contains the decompression time expressed in s.
     * @throws DomainLayerException
     */
    @Override
    public Object[] descomprimir(byte[] content, Fitxer outputFile) throws DomainLayerException{
        try {
            long startTime = System.currentTimeMillis();
            Ventana = new ArrayList<>();
            outStream = new ByteArrayOutputStream();
            int i = 0;
            while (i < content.length) {
                byte nextByte = content[i];
                int aux1 = (nextByte & 0b01111111);
                ++i;
                if (nextByte == 0) {  // If it's a literal
                    if (i < content.length) {
                        nextByte = content[i];
                        ++i;
                        afegeixLiteralALaFinestra(nextByte);
                        outStream.write(nextByte);
                    }
                } else {  // If it's a code
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
            long endTime = System.currentTimeMillis(); // Get the time when end the compression
            double decompressTime = (double) (endTime - startTime) * 0.001;
            byte[] decompressedContent = outStream.toByteArray();
            outputFile.setContent(decompressedContent);
            globalStatistic.addNumDecompression();
            globalStatistic.addTotalDecompressedData(content.length);
            globalStatistic.addTotalDecompressionTime(decompressTime);
            Object[] compressionStatistic = {decompressedContent.length, content.length, decompressTime};
            return compressionStatistic;
        }
        catch (Exception e) {
            throw new DomainLayerException("An error has occurred while decompressing the file, decompression aborted.\n"+
                    e.getMessage());
        }
    }

    /**
     * Check if an array is sub-array of other
     * @param A source array
     * @param B array to be check
     * @return the position of B in A if B is into A, -1 otherwise
     */
    private int isSubArray(Object[] A, Object[] B) {
        int i = 0, j = 0, mark = 0;

        while (i < A.length && j < B.length) {
            // If matches, increment pointers
            if (A[i] == B[j]) {
                i++;
                j++;
                // If B is in A
                if (j == B.length) return mark;
            }
            // If not, increment i and reset j and mark
            else {
                i++;
                j = 0;
                mark = i;
            }
        }
        // If B isn't a subArray return -1
        return -1;
    }

    /**
     * Add a literal to the window and if it's full, delete the first element of the window
     * @param literal byte to be added in the window
     */
    private void afegeixLiteralALaFinestra(Byte literal) {
        Ventana.add(literal); // Posem a la finestra el primer byte del match
        if (Ventana.size() > 4096) Ventana.remove(0);  // Eliminem de la finestra el primer element
    }

    /**
     * Write byte 0 and the first literal of the match at output
     */
    private void escriu0ILiteral() {
        outStream.write((byte) 0);  // Flag a 0
        outStream.write(ActualMatch.get(0)); // Literal
    }

    /**
     * Delete the first literal of the match
     */
    private void eliminaLiteralDelMatch() {
        ActualMatch.remove(0);
        if (ActualMatch.size() == 0) MidaMatch = 0;
    }

    /**
     * Code the match and writes it to output
     * @param MIndex Position of the match into the window
     */
    private void codeMatch(int MIndex) {
        byte aux = (byte) (0b10000000 | (0b1111111 & (MIndex >> 5))); // Flag a 1 i meitat del pointer
        outStream.write(aux);
        aux = (byte) (((0b11111 & MIndex) << 3) | (0b111 & MidaMatch)); // Meitat del pointer i MatchSize
        outStream.write(aux);
    }

}
