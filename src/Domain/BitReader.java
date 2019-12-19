package Domain;

import Commons.DomainLayerException;

/**
 * Class for reading bit streams
 */
public class BitReader {
    /**
     * data to be set for reading.
     */
    private byte[] input;
    /**
     * position of the actual read byte in the data.
     */
    private int actualByte = 0;
    /**
     * position of the actual read bit in the data.
     */
    private int actualBit = 0;
    /**
     * position of the actual read bit in the actual read byte.
     */
    private int readedBit = 0; //the bit-pos in the byte that we want to read
    /**
     * size of the data expressed in bits.
     */
    private int size = 0;

    //------------------------------------------------Constructor-----------------------------------------------------//

    /**
     * Creates a new BitReader and set it's data.
     * @param input data to be set.
     */
    public BitReader(byte[] input) {
        this.input = input;
        this.size = input.length << 3;
    }

    //------------------------------------------------Constructor-----------------------------------------------------//


    /**
     * Indicates if there is more bit to be read in this bitReader.
     * @return true if there is more bit to be read, false otherwise.
     */
    public boolean next() {
        return actualBit < size;
    }

    /**
     * Returns true if this bitReader has just read a 1, false otherwise.
     * @return returns true if this bitReader has just read a 1, false otherwise.
     * @throws DomainLayerException
     */
    public boolean readOne() throws ArrayIndexOutOfBoundsException {
        int isOne = (input[actualByte] >> (7-readedBit))&0x01;
        ++readedBit;
        ++actualBit;
        if(readedBit == 8) {
            ++actualByte;
            readedBit = 0;
        }
        return isOne == 1;
    }

    /**
     * Reads up to length bits from this bitReader and returns the corresponding integer value.
     * @param length length of the integer value expressed in bits.
     * @return the value of the corresponding integer value.
     */
    public int readInt(int length) throws ArrayIndexOutOfBoundsException{
        if(length == 0) return 0;
        boolean positive = readOne();
        int value = positive ? 0x1:0x0;
        int mask = (0x1 << length) - 1;
        --length;
        while (length > 0) {
            value <<= 1;
            if (readOne()) value  |= 0x1;
            --length;
        }
        if (positive) return value;
        return value-mask;
    }

    /**
     * Set the actual read byte to the byte at position pos in the data
     * @param pos position to be set
     */
    public void setActualBytePointer(int pos) {
        actualByte = pos;
    }
}
