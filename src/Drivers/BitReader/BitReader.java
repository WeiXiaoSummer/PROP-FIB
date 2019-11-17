package Drivers.BitReader;

public class BitReader {
    private byte[] input;
    private int actualByte = 0;
    private int actualBit = 0;
    private int readedBit = 0; //the bit-pos in the byte that we want to read
    private int size = 0;

    public BitReader(byte[] input) {
        this.input = input;
        this.size = input.length << 3;
    }

    public boolean next() {
        return actualBit < size;
    }

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

    public int readInt(int length) {
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
}
