package Domain;

import Commons.DomainLayerException;
import javafx.util.Pair;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import static java.lang.Math.*;

public class JPEG extends Algorithm {

    public JPEG(GlobalStatistic globalStatistic) {
        super(globalStatistic);
    }

    /**
     * Overrides the superclass method, compresses the input .ppm file inputImg and writes the result into the temporal
     * buffer compressedFile.
     * @param inputImg Fitxer to be compressed.
     * @param compressedFile ByteArrayOutputStream to be wrote.
     * @return the compression statistic in the form of a Object array:
     *         -(int)First position contains the original content size expressed in bytes.
     *         -(int)Second position contains the compressed content size expressed in bytes.
     *         -(double)Third position contains the compression time expressed in s.
     * @throws DomainLayerException if a error occurs during the compression of the inputImg.
     */
    @Override
    public Object[] comprimir(Fitxer inputImg, ByteArrayOutputStream compressedFile) throws DomainLayerException {
        try {
            globalStatistic.addNumCompression();
            Object[] compressionStatistic = {0d, 0d, 0d};
            long startTime=System.currentTimeMillis();
            byte[] input = inputImg.getContent();

            Pair<Integer, Integer> dimension = getDimension(input);
            System.out.println(dimension);
            int offset = 9 + dimension.getKey().toString().length() + dimension.getValue().toString().length();
            byte[] compressedContent = Compress(input, dimension.getKey(), dimension.getValue(), offset);
            long endTime=System.currentTimeMillis();

            byte fileNameLength = (byte) inputImg.getFile().getName().length();
            byte[] outPutWidth =  ByteBuffer.allocate(4).putInt(dimension.getKey()).array();
            byte[] outPutHeight = ByteBuffer.allocate(4).putInt(dimension.getValue()).array();
            byte[] compressedContentSize = ByteBuffer.allocate(4).putInt(compressedContent.length).array();

            compressedFile.write("JPEG".getBytes());
            compressedFile.write(fileNameLength); compressedFile.write(inputImg.getFile().getName().getBytes());
            compressedFile.write(compressedContentSize); compressedFile.write(outPutWidth);
            compressedFile.write(outPutHeight); compressedFile.write(compressedContent);

            compressionStatistic[0] = (double)input.length;
            compressionStatistic[1] = (double)compressedContent.length;
            compressionStatistic[2] = (double) (endTime - startTime) * 0.001;
            globalStatistic.addTotalCompressedData(input.length);
            globalStatistic.addTotalCompressionTime((double) compressionStatistic[2]);
            globalStatistic.addTotalCompressionRatio((double)input.length / (double)compressedContent.length);

            return compressionStatistic;
        }
        catch (DomainLayerException e) { throw e;}
        catch (IOException e) {
            throw new DomainLayerException("An error has occurred while compressing the file:\n\n"+inputImg.getFile().getPath()+
                    "\n\nCompression aborted\n\nsee below\n\n" + e.toString()); }
        catch (Exception e) {
            throw new DomainLayerException("An error has occurred while compressing the file:\n\n"+inputImg.getFile().getPath()+"\n\nThis PPM file " +
                    "has incorrect form, Compression aborted.\n\n");
        }
    }

    /**
     * Override the superclass method, decompress the compressed .ppm content and stores the decompressed content into the outPutFile.
     * @param compressedContent data to be decompress.
     * @param outPutFile Fitxer to be wrote.
     * @return the decompression statistic in the form of a Object array:
     *         -(int)First position contains the decompressed content size expressed in bytes.
     *         -(int)Second position contains the compressed content size expressed in bytes.
     *         -(double)Third position contains the decompression time expressed in s.
     * @throws DomainLayerException if a error occurs during the process of the decompression
     */
    @Override
    public Object[] descomprimir(byte[] compressedContent, Fitxer outPutFile) throws DomainLayerException{
        try {
            long startTime=System.currentTimeMillis();
            byte[] getWidth = Arrays.copyOfRange(compressedContent, 0, 4);
            byte[] getHeight = Arrays.copyOfRange(compressedContent, 4, 8);
            Integer width = ByteBuffer.wrap(getWidth).getInt();
            Integer height = ByteBuffer.wrap(getHeight).getInt();
            byte[] header = ("P6\n"+width.toString()+" "+height.toString()+"\n255\n").getBytes();
            byte[] decompressedContent = new byte[width*height*3+header.length];
            for(int i = 0; i < header.length; ++i) {
                decompressedContent[i] = header[i];
            }
            int offset = header.length;
            DeCompress(compressedContent, decompressedContent, width, height, offset);
            long endTime=System.currentTimeMillis(); // get the time when end the compression
            double decompressTime = (double)(endTime-startTime)* 0.001;
            globalStatistic.addNumDecompression();
            globalStatistic.addTotalDecompressedData(compressedContent.length);
            globalStatistic.addTotalDecompressionTime(decompressTime);
            outPutFile.setContent(decompressedContent);
            Object[] compressionStatistic = {(double)decompressedContent.length, (double)compressedContent.length, decompressTime};
            return compressionStatistic;
        }
        catch (DomainLayerException e) {throw e;}
        catch (ArrayIndexOutOfBoundsException e) {
            throw new DomainLayerException("An error has occurred while decompressing the file:\n\n" + outPutFile.getFile().getName()+"\n\n" +
                    "The compressed content is corrupted, decompression aborted.");
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new DomainLayerException("An unidentified error has occurred while decompressing the file, decompression aborted.\n\nsee below\n\n"+
                    e.toString()); }
    }

    /**
     * Returns the dimension of a .ppm file.
     * @param imgContent the content of the input .ppm file.
     * @return a Pair which contains the width and the height of the input .ppm file.
     */
    private Pair<Integer, Integer> getDimension(byte[] imgContent) {
        int width = 0;
        int height = 0;
        int pos = 3;
        while (imgContent[pos] != ' ') {
            width = width * 10 + (imgContent[pos] - '0');
            ++pos;
        }
        ++pos;
        while (imgContent[pos] != '\n') {
            height = height * 10 + (imgContent[pos] - '0');
            ++pos;
        }
        return new Pair<>(width, height);
    }

    //-----------------------------------------------Initializer------------------------------------------------------//

    /**
     * Initializes the Luminance Quantization Table and the Chrominance Quantization table.
     * @param YQuantMatrix the luminance quantization table to be initialized.
     * @param CrCbQuantMatrix the chrominance quantization table to be initialized.
     */
    //Initialize Luminance Quantization Table and Chrominance Quantization Table
    private void initializeQuantizationTable(float YQuantMatrix[][], float CrCbQuantMatrix[][]) {
        //Arai, Agui and Nakajima Scale factor
        float aanSf[] = {1f, 1.387039845f, 1.306562965f, 1.175875602f, 1f, 0.785694958f, 0.541196100f, 0.275899379f};

        //Default quantization table for Luminance provided by JPEG
        float[][] YQuant = {{16, 11, 10, 16, 24, 40, 51, 61},
                {12, 12, 14, 19, 26, 58, 60, 55},
                {14, 13, 16, 24, 40, 57, 69, 56},
                {14, 17, 22, 29, 51, 87, 80, 62},
                {18, 22, 37, 56, 68, 109, 103, 77},
                {24, 35, 55, 64, 81, 104, 113, 92},
                {49, 64, 78, 87,103,121,120,101},
                {72, 92, 95, 98,112,100,103, 99}};

        //Default quantization table for Chrominance provided by JPEG
        float[][] CrCbQuant = {{17, 18, 24, 47, 99, 99, 99, 99},
                {18, 21, 26, 66, 99, 99, 99, 99},
                {24, 26, 56, 99, 99, 99, 99, 99},
                {47, 66, 99, 99, 99, 99, 99, 99},
                {99, 99, 99, 99, 99, 99, 99, 99},
                {99, 99, 99, 99, 99, 99, 99, 99},
                {99, 99, 99, 99, 99, 99, 99, 99},
                {99, 99, 99, 99, 99, 99, 99, 99}};

        //Adjust quantization tables with AAN scaling factors to simplify DCT
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                YQuantMatrix[i][j] = 1f/(aanSf[i]*aanSf[j]*8f*YQuant[i][j]);
                CrCbQuantMatrix[i][j] = 1f/(aanSf[i]*aanSf[j]*8f*CrCbQuant[i][j]);
            }
        }

    }

    /**
     * Initializes the value-length-code table
     * @param VLCTable table to be initialized.
     */
    //Initialize Value-Length-Code Table
    private void initializeVLCTable(CodeBits[] VLCTable) {
        byte numBits = 1;
        int mask = 1;
        for(int value = 1; value < 2048; ++value) {
            if (value > mask) {
                numBits++;
                mask = (mask << 1) | 1;
            }
            VLCTable[value+2048] = new CodeBits((char)(value), numBits);
            VLCTable[value] = new CodeBits((char)(mask-value), numBits);
        }
    }

    /**
     * Initializes the given huffman table.
     * @param numCodes indicates how many codes of the length x it has.
     * @param values values to be added to the huffman table.
     * @param huffmantable table to be initialized.
     */
    //Initialize Huffman Table
    private void initializeMyHuffmanTable(char[] numCodes, char[] values, CodeBits[] huffmantable) {
        char huffmanCode = 0;
        int valuePos = 0;
        for (byte numBits = 1; numBits <= 16 & numBits <= numCodes.length; ++numBits) {
            for (int i = 0; i < numCodes[numBits-1]; ++i) {
                huffmantable[values[valuePos++]] = new CodeBits(huffmanCode++, numBits);
            }
            huffmanCode <<= 1;
        }
    }

    //-----------------------------------------------Initializer------------------------------------------------------//

    //----------------------------------------Discrete Cosine Transform-----------------------------------------------//

    /**
     * Applies 2D DCT transform to the given 8x8 block.
     * @param Block block to be transformed.
     * @param byRow if true it will applies a 2D DCT by row, otherwise by column.
     */
    private void DCTTransform(float[][] Block, boolean byRow) {

        float sqrtHalfSqrt = 1.306562965f; //    sqrt((2 + sqrt(2)) / 2) = cos(pi * 1 / 8) * sqrt(2)
        float invSqrt      = 0.707106781f; //                1 / sqrt(2) = cos(pi * 2 / 8)
        float halfSqrtSqrt = 0.382683432f; //      sqrt(2 - sqrt(2)) / 2 = cos(pi * 3 / 8)
        float invSqrtSqrt  = 0.541196100f; //      1 / sqrt(2 - sqrt(2)) = cos(pi * 3 / 8) * sqrt(2)

        int RowChange = byRow ? 1 : 0;
        int ColumnChange = byRow ? 0 : 1;

        for(int i = 0; i < 8; ++i) {
            float number_0 = Block[i*RowChange][i*ColumnChange];
            float number_1 = Block[i*RowChange+ColumnChange][i*ColumnChange+RowChange];
            float number_2 = Block[i*RowChange+2*ColumnChange][i*ColumnChange+2*RowChange];
            float number_3 = Block[i*RowChange+3*ColumnChange][i*ColumnChange+3*RowChange];
            float number_4 = Block[i*RowChange+4*ColumnChange][i*ColumnChange+4*RowChange];
            float number_5 = Block[i*RowChange+5*ColumnChange][i*ColumnChange+5*RowChange];
            float number_6 = Block[i*RowChange+6*ColumnChange][i*ColumnChange+6*RowChange];
            float number_7 = Block[i*RowChange+7*ColumnChange][i*ColumnChange+7*RowChange];

            //Starting DCT 8-point Transform
            float add0_7 = number_0 + number_7;
            float add1_6 = number_1 + number_6;
            float add2_5 = number_2 + number_5;
            float add3_4 = number_3 + number_4;

            float sub0_7 = number_0 - number_7;
            float sub1_6 = number_1 - number_6;
            float sub2_5 = number_2 - number_5;
            float sub3_4 = number_3 - number_4;

            float add07_34 = add0_7 + add3_4;   float add16_25 = add1_6 + add2_5;
            float sub07_34 = add0_7 - add3_4;   float sub16_25 = add1_6 - add2_5;

            float add_Sub25_Sub34 = sub2_5 + sub3_4;
            float add_Sub16_Sub25 = sub1_6 + sub2_5;
            float add_Sub16_Sub07 = sub1_6 + sub0_7;

            float z1 = (sub16_25 + sub07_34) * invSqrt;
            float z2 = (add_Sub25_Sub34 - add_Sub16_Sub07) * halfSqrtSqrt;
            float z3 = add_Sub25_Sub34 * invSqrtSqrt + z2;
            float z4 = add_Sub16_Sub25 * invSqrt;
            float z5 = add_Sub16_Sub07 * sqrtHalfSqrt + z2;
            float z6 = sub0_7 + z4;
            float z7 = sub0_7 - z4;

            Block[i*RowChange][i*ColumnChange] = add07_34 + add16_25;
            Block[i*RowChange+ColumnChange][i*ColumnChange+RowChange] = z6 + z5;
            Block[i*RowChange+2*ColumnChange][i*ColumnChange+2*RowChange] = sub07_34 + z1;
            Block[i*RowChange+3*ColumnChange][i*ColumnChange+3*RowChange] = z7 - z3;
            Block[i*RowChange+4*ColumnChange][i*ColumnChange+4*RowChange] = add07_34 - add16_25;
            Block[i*RowChange+5*ColumnChange][i*ColumnChange+5*RowChange] = z7 + z3;
            Block[i*RowChange+6*ColumnChange][i*ColumnChange+6*RowChange] = sub07_34 - z1;
            Block[i*RowChange+7*ColumnChange][i*ColumnChange+7*RowChange] = z6 - z5;
        }
    }

    /**
     * Applies the 2D inverse DCT transform to the given 8x8 block.
     * @param Block block to be transformed.
     * @param byRow if true it will applies a 2D inverse DCT transform, otherwise by column.
     */
    private void IDCTTransform(float[][] Block, boolean byRow) {
        int RowChange = byRow ? 1 : 0;
        int ColumnChange = byRow ? 0 : 1;

        for(int i = 0; i < 8; ++i) {
            float number_0 = Block[i*RowChange][i*ColumnChange];
            float number_1 = Block[i*RowChange+ColumnChange][i*ColumnChange+RowChange];
            float number_2 = Block[i*RowChange+2*ColumnChange][i*ColumnChange+2*RowChange];
            float number_3 = Block[i*RowChange+3*ColumnChange][i*ColumnChange+3*RowChange];
            float number_4 = Block[i*RowChange+4*ColumnChange][i*ColumnChange+4*RowChange];
            float number_5 = Block[i*RowChange+5*ColumnChange][i*ColumnChange+5*RowChange];
            float number_6 = Block[i*RowChange+6*ColumnChange][i*ColumnChange+6*RowChange];
            float number_7 = Block[i*RowChange+7*ColumnChange][i*ColumnChange+7*RowChange];

            // Even part
            float tmp10 = number_0 + number_4;
            float tmp11 = number_0 - number_4;

            float tmp13 = number_2 + number_6;
            float tmp12 =(number_2 - number_6) * 1.414213562f - tmp13;

            float tmp0 = tmp10 + tmp13;
            float tmp3 = tmp10 - tmp13;
            float tmp1 = tmp11 + tmp12;
            float tmp2 = tmp11 - tmp12;

            // Odd part
            float z13 = number_5 + number_3;
            float z10 = number_5 - number_3;
            float z11 = number_1 + number_7;
            float z12 = number_1 - number_7;

            float tmp7 = z11 + z13;
            tmp11 = (z11 - z13) * 1.414213562f;

            float z5 = (z10 + z12) * 1.847759065f;
            tmp10 = 1.082392200f * z12 - z5;
            tmp12 = -2.613125930f * z10 + z5;

            float tmp6 = tmp12 - tmp7;
            float tmp5 = tmp11 - tmp6;
            float tmp4 = tmp10 + tmp5;

            Block[i*RowChange][i*ColumnChange] = (float)((tmp0 + tmp7) * 0.125);
            Block[i*RowChange+ColumnChange][i*ColumnChange+RowChange] = (float)((tmp1 + tmp6) * 0.125);
            Block[i*RowChange+2*ColumnChange][i*ColumnChange+2*RowChange] = (float)((tmp2 + tmp5) * 0.125);
            Block[i*RowChange+3*ColumnChange][i*ColumnChange+3*RowChange] = (float)((tmp3 - tmp4) * 0.125);
            Block[i*RowChange+4*ColumnChange][i*ColumnChange+4*RowChange] = (float)((tmp3 + tmp4) * 0.125);
            Block[i*RowChange+5*ColumnChange][i*ColumnChange+5*RowChange] = (float)((tmp2 - tmp5) * 0.125);
            Block[i*RowChange+6*ColumnChange][i*ColumnChange+6*RowChange] = (float)((tmp1 - tmp6) * 0.125);
            Block[i*RowChange+7*ColumnChange][i*ColumnChange+7*RowChange] = (float)((tmp0 - tmp7) * 0.125);
        }
    }

    //----------------------------------------Discrete Cosine Transform-----------------------------------------------//


    //-----------------------------------------Block Encoder and Decoder-----------------------------------------------//

    /**
     * Encodes the given block and write the result into bitWriter.
     * @param Block the block to be encoded.
     * @param QuantMatrix the quantization matrix to be used.
     * @param lastDC the last encoded DC value.
     * @param Zigzag the zigzag matrix.
     * @param huffmanDC the DC huffman table to be used.
     * @param huffmanAC the AC huffman table to be used.
     * @param VLCTable the Value-Length-code table to be used.
     * @param bitWriter the bit writer into which the data is wrote.
     * @return the DC value of this block.
     */
    private int EncodeBlock(float[][] Block, float[][] QuantMatrix, int lastDC, int[] Zigzag, CodeBits[] huffmanDC,
                           CodeBits[] huffmanAC, CodeBits[] VLCTable, BitWriter bitWriter) {
        //DCT: by rows
        DCTTransform(Block, true);
        //DCT: by columns
        DCTTransform(Block, false);
        //Quantize

        for(int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                Block[i][j] = round(Block[i][j]*QuantMatrix[i][j]);
            }
        }

        //Encode DC and doing the zigzag
        byte posNonZero = 0; //indicate the position of the last non-zero number in the zigzag matrix
        byte posZigZag = 0;
        //Creating the zigzag matrix
        int zigzag[][] = new int[8][8];
        for(byte i = 0; i < 8; ++i) {
            for (byte j = 0; j < 8; ++j) {
                int posIzigZag = Zigzag[posZigZag]/8;
                int posJzigZag = Zigzag[posZigZag]%8;
                zigzag[i][j] = (int)Block[posIzigZag][posJzigZag];
                if (zigzag[i][j] != 0) posNonZero = posZigZag;
                ++posZigZag;
            }
        }
        //Encode the difference between the actual DC value and last DC value
        int diff = zigzag[0][0] - lastDC;
        if (diff == 0) {
            bitWriter.write(huffmanDC[0]);
        }
        else {
            CodeBits code = diff < 0 ? VLCTable[diff*-1] : VLCTable[diff+2048];
            bitWriter.write(huffmanDC[code.getBits()]);
            bitWriter.write(code);
        }
        //Encoding the rest AC values
        char offset = 0;
        for (byte i = 1; i <= posNonZero; ++i) {
            while (zigzag[i/8][i%8] == 0) {
                offset += 0x10; //Huffman Code is composed by offset(number of zeros)|code for number
                if (offset > 0xf0) {
                    bitWriter.write(huffmanAC[0xf0]);
                    offset = 0;
                }
                i++;
            }
            CodeBits EncodedNonZeroNumber;
            if (zigzag[i/8][i%8] < 0) EncodedNonZeroNumber = VLCTable[-zigzag[i/8][i%8]];
            else EncodedNonZeroNumber = VLCTable[zigzag[i/8][i%8]+2048];
            bitWriter.write(huffmanAC[offset+EncodedNonZeroNumber.getBits()]);
            bitWriter.write(EncodedNonZeroNumber);
            offset = 0;
        }
        bitWriter.write(huffmanAC[0x00]);
        return zigzag[0][0];
    }

    /**
     * Decodes the actual encode blocked read from the bitReader.
     * @param block block into which the decompressed content is wrote.
     * @param bitReader bitReader from which the compressed content is read.
     * @param DC the DC huffman tree to be used for decompression.
     * @param AC the AC huffman tree to be used for decompression.
     * @param quantMatrix quantization matrix to be used for inverse-quantization process.
     * @param lastDC the last decoded DC value.
     * @param Zigzag the Zigzag matrix
     * @return the decoded DC value of this block.
     * @throws DomainLayerException if the compressed content is corrupted.
     */
    private int decodeBlock(float[][] block, BitReader bitReader, HuffmanTree DC, HuffmanTree AC, float[][] quantMatrix, int lastDC, int[] Zigzag) throws DomainLayerException {
        int length = DC.decodeHuffmanCode(bitReader);
        int diff = bitReader.readInt(length);
        int actualDC = lastDC + diff;
        block[0][0] = actualDC;
        int EOB = AC.decodeHuffmanCode(bitReader);
        int count = 1;
        while (EOB != 0)  {
            int numOfZeros = (EOB & 0xf0) >> 4;
            int codeLength = (EOB & 0xf);
            while (numOfZeros > 0) {
                block[Zigzag[count]/8][Zigzag[count]%8] = 0;
                ++count;
                --numOfZeros;
            }
            if (codeLength > 0) {
                block[Zigzag[count]/8][Zigzag[count]%8] = bitReader.readInt(codeLength);
                ++count;
            }
            EOB = AC.decodeHuffmanCode(bitReader);
        }
        while (count < 64) {
            block[Zigzag[count]/8][Zigzag[count]%8] = 0;
            ++count;
        }

        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                block[i][j] = block[i][j]/quantMatrix[i][j];
            }
        }

        IDCTTransform(block, true);
        IDCTTransform(block, false);

        return actualDC;
    }


    /**
     * Gets a 8x8 block with ID <rowID, columnID> form the original image content.
     * @param rowID row ID of this block.
     * @param columnID column ID of this block.
     * @param realHeight height of the image.
     * @param realWidth width of th image.
     * @param RGB the RGB content of the image.
     * @param offset the offset to be applied.
     * @return 8x8 block read from the original image content.
     */
    private byte[][] getBlockWithID(int rowID, int columnID, int realHeight, int realWidth, byte RGB[], int offset) {
        byte[][] block = new byte[8][24];
        int rowPos, columnPos, i, j;
        for (i = 0, rowPos = rowID << 3; i < 8 && rowPos < realHeight; ++i, ++rowPos) {
            for (j = 0, columnPos = columnID * 24; j < 22 && columnPos < realWidth; j += 3, columnPos += 3) {
                block[i][j] = RGB[rowPos*realWidth+columnPos+offset];
                block[i][j+1] = RGB[rowPos*realWidth+columnPos+1+offset];
                block[i][j+2] = RGB[rowPos*realWidth+columnPos+2+offset];
            }
            while (j < 22) {
                block[i][j] = block[i][j+1] = block[i][j+2] = (byte)0x00;
                j += 3;
            }
        }
        while (i <8) {
            Arrays.fill(block[i], (byte)0x00);
            ++i;
        }
        return block;
    }

    //----------------------------------------Block Encoder and Decoder-----------------------------------------------//

    //------------------------------------------Color Space Transform-------------------------------------------------//

    /**
     * Transforms a 24x8 RGB block into the corresponding Y, Cr, Cb components and stores into the Y, Cr, Cb matrix.
     * @param RGB the RGB block to be transformed.
     * @param Y the Y component.
     * @param Cr the Cr component.
     * @param Cb the Cb component.
     */
    private void YCrCbTransform(byte [][] RGB, float[][] Y, float[][] Cr, float[][] Cb) {
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 22; j += 3) {
                int posJ = j/3;
                float R = RGB[i][j]&0xff;
                float G = RGB[i][j+1]&0xff;
                float B = RGB[i][j+2]&0xff;
                Y[i][posJ] = (float)(0.299*R + 0.587*G+0.114*B-128);
                Cr[i][posJ] = (float) (0.5*R - 0.4187*G -0.0813*B);
                Cb[i][posJ] = (float) (-0.1687*R - 0.3313*G + 0.5*B);
            }
        }
    }


    /**
     * Transforms the Y, Cr, Cb components into the corresponding R, G, B component.
     * @param R the R component.
     * @param G the G component.
     * @param B the B component.
     * @param Y the Y component.
     * @param Cr the Cr component.
     * @param Cb the Cb component.
     */
    private void RGBTransform(byte [][] R, byte[][] G, byte[][] B, float[][] Y, float[][] Cr, float[][] Cb) {

        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                float y = Y[i][j]+128;
                float cr = Cr[i][j];
                float cb = Cb[i][j];
                int r = max(0, min((int)round(y+1.402*cr),255));
                int g = max(0, min((int)round(y-0.34414*cb-0.71414*cr),255));
                int b = max(0, min((int)round(y+1.772 * cb), 255));
                R[i][j] = (byte) r;
                G[i][j] = (byte) g;
                B[i][j] = (byte) b;
            }
        }
    }

    //------------------------------------------Color Space Transform-------------------------------------------------//

    //--------------------------------------- Compressor and Decompressor---------------------------------------------//

    /**
     * Compresses the given RGB content of a .ppm image and returns the compressed content contained in a byte array.
     * @param RGB data to be compress.
     * @param width width of the image.
     * @param height height of the image.
     * @param offSet offset to be applied.
     * @return a byte array which contains the compressed content.
     * @throws DomainLayerException if a error occurs during the compression.
     */
    private byte[] Compress(byte RGB[], int width, int height, int offSet) throws DomainLayerException{
        int realHeight = height;
        int realWidth = width*3;
        int multipleOfEight = width%8;
        if (multipleOfEight != 0) width = (width+8-multipleOfEight)*3;
        else width *= 3;
        multipleOfEight = height%8;
        if(multipleOfEight != 0) height = height + 8 - multipleOfEight;

        BitWriter bitWriter = new BitWriter();

        char DCLuminanceCodesPerBitSize[] = {0,1,5,1,1,1,1,1,1};
        char DcLuminanceValues[] = {0,1,2,3,4,5,6,7,8,9,10,11};

        char ACLuminanceCodesPerBitSize[] = {0,2,1,3,3,2,4,3,5,5,4,4,0,0,1,125};
        char ACLuminanceValues[] =
                {0x01,0x02,0x03,0x00,0x04,0x11,0x05,0x12,0x21,0x31,0x41,0x06,0x13,0x51,0x61,0x07,0x22,0x71,0x14,0x32,0x81,0x91,0xA1,0x08, // 16*10+2 symbols because
                        0x23,0x42,0xB1,0xC1,0x15,0x52,0xD1,0xF0,0x24,0x33,0x62,0x72,0x82,0x09,0x0A,0x16,0x17,0x18,0x19,0x1A,0x25,0x26,0x27,0x28, // upper 4 bits can be 0..F
                        0x29,0x2A,0x34,0x35,0x36,0x37,0x38,0x39,0x3A,0x43,0x44,0x45,0x46,0x47,0x48,0x49,0x4A,0x53,0x54,0x55,0x56,0x57,0x58,0x59, // while lower 4 bits can be 1..A
                        0x5A,0x63,0x64,0x65,0x66,0x67,0x68,0x69,0x6A,0x73,0x74,0x75,0x76,0x77,0x78,0x79,0x7A,0x83,0x84,0x85,0x86,0x87,0x88,0x89, // plus two special codes 0x00 and 0xF0
                        0x8A,0x92,0x93,0x94,0x95,0x96,0x97,0x98,0x99,0x9A,0xA2,0xA3,0xA4,0xA5,0xA6,0xA7,0xA8,0xA9,0xAA,0xB2,0xB3,0xB4,0xB5,0xB6, // order of these symbols was determined empirically by JPEG committee
                        0xB7,0xB8,0xB9,0xBA,0xC2,0xC3,0xC4,0xC5,0xC6,0xC7,0xC8,0xC9,0xCA,0xD2,0xD3,0xD4,0xD5,0xD6,0xD7,0xD8,0xD9,0xDA,0xE1,0xE2,
                        0xE3,0xE4,0xE5,0xE6,0xE7,0xE8,0xE9,0xEA,0xF1,0xF2,0xF3,0xF4,0xF5,0xF6,0xF7,0xF8,0xF9,0xFA };

        char DCChrominanceCodesPerBitSize[] = {0,3,1,1,1,1,1,1,1,1,1};
        char DcChrominanceValues[] = {0,1,2,3,4,5,6,7,8,9,10,11};

        char ACChrominanceCodesPerBitSize[] = {0,2,1,2,4,4,3,4,7,5,4,4,0,1,2,119};
        char ACChrominanceValues[] =
                { 0x00,0x01,0x02,0x03,0x11,0x04,0x05,0x21,0x31,0x06,0x12,0x41,0x51,0x07,0x61,0x71,0x13,0x22,0x32,0x81,0x08,0x14,0x42,0x91, // same number of symbol, just different order
                        0xA1,0xB1,0xC1,0x09,0x23,0x33,0x52,0xF0,0x15,0x62,0x72,0xD1,0x0A,0x16,0x24,0x34,0xE1,0x25,0xF1,0x17,0x18,0x19,0x1A,0x26, // (which is more efficient for AC coding)
                        0x27,0x28,0x29,0x2A,0x35,0x36,0x37,0x38,0x39,0x3A,0x43,0x44,0x45,0x46,0x47,0x48,0x49,0x4A,0x53,0x54,0x55,0x56,0x57,0x58,
                        0x59,0x5A,0x63,0x64,0x65,0x66,0x67,0x68,0x69,0x6A,0x73,0x74,0x75,0x76,0x77,0x78,0x79,0x7A,0x82,0x83,0x84,0x85,0x86,0x87,
                        0x88,0x89,0x8A,0x92,0x93,0x94,0x95,0x96,0x97,0x98,0x99,0x9A,0xA2,0xA3,0xA4,0xA5,0xA6,0xA7,0xA8,0xA9,0xAA,0xB2,0xB3,0xB4,
                        0xB5,0xB6,0xB7,0xB8,0xB9,0xBA,0xC2,0xC3,0xC4,0xC5,0xC6,0xC7,0xC8,0xC9,0xCA,0xD2,0xD3,0xD4,0xD5,0xD6,0xD7,0xD8,0xD9,0xDA,
                        0xE2,0xE3,0xE4,0xE5,0xE6,0xE7,0xE8,0xE9,0xEA,0xF2,0xF3,0xF4,0xF5,0xF6,0xF7,0xF8,0xF9,0xFA };

        CodeBits huffmanLuminanceDC[] = new CodeBits[256];
        CodeBits huffmanLuminanceAC[] = new CodeBits[256];
        CodeBits huffmanChrominanceDC[] = new CodeBits[256];
        CodeBits huffmanChrominanceAC[] = new CodeBits[256];

        initializeMyHuffmanTable(DCLuminanceCodesPerBitSize, DcLuminanceValues, huffmanLuminanceDC);
        initializeMyHuffmanTable(ACLuminanceCodesPerBitSize, ACLuminanceValues, huffmanLuminanceAC);
        initializeMyHuffmanTable(DCChrominanceCodesPerBitSize, DcChrominanceValues, huffmanChrominanceDC);
        initializeMyHuffmanTable(ACChrominanceCodesPerBitSize, ACChrominanceValues, huffmanChrominanceAC);

        float[][] YQuantMatrix = new float[8][8];
        float[][] CrCbQuantMatrix = new float[8][8];

        initializeQuantizationTable(YQuantMatrix, CrCbQuantMatrix);

        CodeBits[] VLCTable = new CodeBits[4096];

        initializeVLCTable(VLCTable);

        int[] Zigzag = {0, 1, 8, 16, 9, 2, 3, 10,
                17, 24, 32, 25, 18, 11, 4, 5,
                12, 19, 26, 33, 40, 48, 41, 34,
                27, 20, 13, 6, 7, 14, 21, 28,
                35, 42, 49, 56, 57, 50, 43, 36,
                29, 22, 15, 23, 30, 37, 44, 51,
                58, 59, 52, 45, 38, 31, 39, 46,
                53, 60, 61, 54, 47, 55, 62, 63};

        int rowID = height/8;
        int columnID = width/24;

        int lastDCY, lastDCCr, lastDCCb;
        lastDCY = lastDCCr = lastDCCb = 0;

        byte[][] rgb;
        float[][] Y = new float[8][8];
        float[][] Cr = new float[8][8];
        float[][] Cb = new float[8][8];

        for (int i = 0; i < rowID; ++i) {
            for (int j = 0; j < columnID; ++j) {
                rgb = getBlockWithID(i, j,realHeight,realWidth,RGB, offSet);
                YCrCbTransform(rgb, Y, Cr, Cb);
                lastDCY = EncodeBlock(Y, YQuantMatrix, lastDCY, Zigzag, huffmanLuminanceDC, huffmanLuminanceAC, VLCTable, bitWriter);
                lastDCCr = EncodeBlock(Cr, CrCbQuantMatrix, lastDCCr,Zigzag, huffmanChrominanceDC, huffmanChrominanceAC, VLCTable, bitWriter);
                lastDCCb = EncodeBlock(Cb, CrCbQuantMatrix, lastDCCb, Zigzag, huffmanChrominanceDC, huffmanChrominanceAC, VLCTable, bitWriter);
            }
        }
        bitWriter.flush();
        return bitWriter.getOutput();
    }

    /**
     * Decompresses the given compressed image and write the decompressed content into a byte array and returns this one.
     * @param InPut data to be decompress.
     * @param outPut buffer which stores the decompressed content.
     * @param width width of the image.
     * @param height height of the image.
     * @param offset offset to be applied.
     * @throws DomainLayerException if a error occurs during the decompression.
     */
    private void DeCompress(byte[] InPut, byte[] outPut, int width, int height, int offset) throws DomainLayerException{
        System.out.println(width);
        BitReader bitReader = new BitReader(InPut);
        if(width > 0) bitReader.setActualBytePointer(8);
        int lastRow = height;
        int lastColumn = width*3;
        int multipleOfEight = width%8;
        if (multipleOfEight != 0) width = (width+8-multipleOfEight)*3;
        else width *= 3;
        multipleOfEight = height%8;
        if(multipleOfEight != 0) height = height + 8 - multipleOfEight;

        char DCLuminanceCodesPerBitSize[] = {0,1,5,1,1,1,1,1,1};
        char DcLuminanceValues[] = {0,1,2,3,4,5,6,7,8,9,10,11};

        char ACLuminanceCodesPerBitSize[] = {0,2,1,3,3,2,4,3,5,5,4,4,0,0,1,125};
        char ACLuminanceValues[] =
                {0x01,0x02,0x03,0x00,0x04,0x11,0x05,0x12,0x21,0x31,0x41,0x06,0x13,0x51,0x61,0x07,0x22,0x71,0x14,0x32,0x81,0x91,0xA1,0x08, // 16*10+2 symbols because
                        0x23,0x42,0xB1,0xC1,0x15,0x52,0xD1,0xF0,0x24,0x33,0x62,0x72,0x82,0x09,0x0A,0x16,0x17,0x18,0x19,0x1A,0x25,0x26,0x27,0x28, // upper 4 bits can be 0..F
                        0x29,0x2A,0x34,0x35,0x36,0x37,0x38,0x39,0x3A,0x43,0x44,0x45,0x46,0x47,0x48,0x49,0x4A,0x53,0x54,0x55,0x56,0x57,0x58,0x59, // while lower 4 bits can be 1..A
                        0x5A,0x63,0x64,0x65,0x66,0x67,0x68,0x69,0x6A,0x73,0x74,0x75,0x76,0x77,0x78,0x79,0x7A,0x83,0x84,0x85,0x86,0x87,0x88,0x89, // plus two special codes 0x00 and 0xF0
                        0x8A,0x92,0x93,0x94,0x95,0x96,0x97,0x98,0x99,0x9A,0xA2,0xA3,0xA4,0xA5,0xA6,0xA7,0xA8,0xA9,0xAA,0xB2,0xB3,0xB4,0xB5,0xB6, // order of these symbols was determined empirically by JPEG committee
                        0xB7,0xB8,0xB9,0xBA,0xC2,0xC3,0xC4,0xC5,0xC6,0xC7,0xC8,0xC9,0xCA,0xD2,0xD3,0xD4,0xD5,0xD6,0xD7,0xD8,0xD9,0xDA,0xE1,0xE2,
                        0xE3,0xE4,0xE5,0xE6,0xE7,0xE8,0xE9,0xEA,0xF1,0xF2,0xF3,0xF4,0xF5,0xF6,0xF7,0xF8,0xF9,0xFA };

        char DCChrominanceCodesPerBitSize[] = {0,3,1,1,1,1,1,1,1,1,1};
        char DcChrominanceValues[] = {0,1,2,3,4,5,6,7,8,9,10,11};

        char ACChrominanceCodesPerBitSize[] = {0,2,1,2,4,4,3,4,7,5,4,4,0,1,2,119};
        char ACChrominanceValues[] =
                { 0x00,0x01,0x02,0x03,0x11,0x04,0x05,0x21,0x31,0x06,0x12,0x41,0x51,0x07,0x61,0x71,0x13,0x22,0x32,0x81,0x08,0x14,0x42,0x91, // same number of symbol, just different order
                        0xA1,0xB1,0xC1,0x09,0x23,0x33,0x52,0xF0,0x15,0x62,0x72,0xD1,0x0A,0x16,0x24,0x34,0xE1,0x25,0xF1,0x17,0x18,0x19,0x1A,0x26, // (which is more efficient for AC coding)
                        0x27,0x28,0x29,0x2A,0x35,0x36,0x37,0x38,0x39,0x3A,0x43,0x44,0x45,0x46,0x47,0x48,0x49,0x4A,0x53,0x54,0x55,0x56,0x57,0x58,
                        0x59,0x5A,0x63,0x64,0x65,0x66,0x67,0x68,0x69,0x6A,0x73,0x74,0x75,0x76,0x77,0x78,0x79,0x7A,0x82,0x83,0x84,0x85,0x86,0x87,
                        0x88,0x89,0x8A,0x92,0x93,0x94,0x95,0x96,0x97,0x98,0x99,0x9A,0xA2,0xA3,0xA4,0xA5,0xA6,0xA7,0xA8,0xA9,0xAA,0xB2,0xB3,0xB4,
                        0xB5,0xB6,0xB7,0xB8,0xB9,0xBA,0xC2,0xC3,0xC4,0xC5,0xC6,0xC7,0xC8,0xC9,0xCA,0xD2,0xD3,0xD4,0xD5,0xD6,0xD7,0xD8,0xD9,0xDA,
                        0xE2,0xE3,0xE4,0xE5,0xE6,0xE7,0xE8,0xE9,0xEA,0xF2,0xF3,0xF4,0xF5,0xF6,0xF7,0xF8,0xF9,0xFA };

        HuffmanTree LuminanceDCTree = new HuffmanTree(DCLuminanceCodesPerBitSize, DcLuminanceValues);
        HuffmanTree LuminanceACTree = new HuffmanTree(ACLuminanceCodesPerBitSize, ACLuminanceValues);
        HuffmanTree ChrominanceDCTree = new HuffmanTree(DCChrominanceCodesPerBitSize, DcChrominanceValues);
        HuffmanTree ChrominanceACTree = new HuffmanTree(ACChrominanceCodesPerBitSize, ACChrominanceValues);

        float[][] YQuantMatrix = new float[8][8];

        float[][] CrCbQuantMatrix = new float[8][8];

        initializeQuantizationTable(YQuantMatrix, CrCbQuantMatrix);

        int[] Zigzag = {0, 1, 8, 16, 9, 2, 3, 10,
                17, 24, 32, 25, 18, 11, 4, 5,
                12, 19, 26, 33, 40, 48, 41, 34,
                27, 20, 13, 6, 7, 14, 21, 28,
                35, 42, 49, 56, 57, 50, 43, 36,
                29, 22, 15, 23, 30, 37, 44, 51,
                58, 59, 52, 45, 38, 31, 39, 46,
                53, 60, 61, 54, 47, 55, 62, 63};

        float[][] Y = new float[8][8];
        float[][] Cr = new float[8][8];
        float[][] Cb = new float[8][8];

        byte[][] R = new byte[8][8];
        byte[][] G = new byte[8][8];
        byte[][] B = new byte[8][8];

        int numBlock = (width*height)/64;
        int rowPos, columnPos, i, j, aux;
        rowPos = columnPos = i = j = aux = 0;
        int lastDCY, lastDCCb, lastDCCr;
        lastDCY = lastDCCb = lastDCCr = 0;

        while (numBlock > 0) {
            lastDCY = decodeBlock(Y,bitReader, LuminanceDCTree, LuminanceACTree, YQuantMatrix, lastDCY, Zigzag);
            lastDCCr = decodeBlock(Cr, bitReader,ChrominanceDCTree, ChrominanceACTree, CrCbQuantMatrix, lastDCCr, Zigzag);
            lastDCCb = decodeBlock(Cb, bitReader, ChrominanceDCTree, ChrominanceACTree, CrCbQuantMatrix, lastDCCb, Zigzag);

            RGBTransform(R, G, B, Y, Cr, Cb);
            for(i = 0; i < 8 && rowPos < lastRow; ++i, ++rowPos) {
                aux = columnPos;
                for (j = 0; j < 8 && aux < lastColumn; ++j, aux+=3) {
                    outPut[rowPos*lastColumn+aux + offset] = R[i][j];
                    outPut[rowPos*lastColumn+aux+1 + offset] = G[i][j];
                    outPut[rowPos*lastColumn+aux+2 + offset] = B[i][j];
                }
            }
            rowPos -= 8;
            columnPos += 24;
            if (columnPos >= lastColumn) {
                columnPos = 0;
                rowPos += 8;
            }
            numBlock -= 3;
        }
    }
    //--------------------------------------- Compressor and Decompressor---------------------------------------------//
}
