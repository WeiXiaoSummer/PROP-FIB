package Drivers.JPEG;

public class CodeBits {
    private char code;
    private byte bits;

    public CodeBits(char code, byte bits) {
        this.code = code;
        this.bits = bits;
    }

    public char getCode() {
        return this.code;
    }

    public byte getBits() {
        return this.bits;
    }
}
