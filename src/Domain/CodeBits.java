package Domain;

/**
 * Represents the pair {code, length of this code expressed in bits}
 */
public class CodeBits {
    /**
     * Code of this codeBits
     */
    private char code;
    /**
     * Length of the code expressed in bit
     */
    private byte bits;

    /**
     * Creates a new CodeBits with a code of length bits
     * @param code code to be set
     * @param bits length of the code
     */
    public CodeBits(char code, byte bits) {
        this.code = code;
        this.bits = bits;
    }

    /**
     * Returns the code of this codeBits
     * @return the code
     */
    public char getCode() {
        return this.code;
    }

    /**
     * Returns the code length of this codeBits
     * @return the code length
     */
    public byte getBits() {
        return this.bits;
    }
}
