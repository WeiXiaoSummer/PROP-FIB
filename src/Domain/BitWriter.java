package Domain;

import Commons.DomainLayerException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Class for write bit streams.
 */
public class BitWriter {
    /**
     * ByteArrayOutputStream which data is write into.
     */
    private ByteArrayOutputStream Output;
    /**
     * temporal buffer for store bytes.
     */
    private int WriteBuffer;
    /**
     * temporal buffer size expressed in bits.
     */
    private int WriteBufferSize;

    /**
     * Creates a new BitWriter.
     */
    public BitWriter() {
        this.Output = new ByteArrayOutputStream();
        this.WriteBuffer = 0;
        this.WriteBufferSize = 0;
    }

    /**
     * Write codeBits into this bitWriter.
     * @param data data to be wrote.
     */
    public void write(CodeBits data) {
        WriteBufferSize += data.getBits();
        WriteBuffer <<= data.getBits();
        WriteBuffer |= data.getCode();

        while (WriteBufferSize >= 8) {
            WriteBufferSize -= 8;
            Byte oneByte = (byte)(WriteBuffer >> WriteBufferSize);
            Output.write(oneByte);
        }
    }

    /**
     * Writes array.length bytes from the specified byte array into this bitWriter.
     * @param array the data to be wrote.
     * @throws DomainLayerException
     */
    public void write(byte[] array) throws DomainLayerException {
        try {
            Output.write(array);
        }
        catch (IOException e) {
            throw new DomainLayerException("An error has occurred during the JPEG process, operation aborted\n"+e.getMessage());
        }
    }

    /**
     * Writes a byte into this bitWriter.
     * @param b the byte to be wrote.
     */
    public void write(byte b) {
        Output.write(b);
    }

    /**
     * Flushes the writeBuffer by writing all it's content into the Output.
     */
    public void flush() {
        if (WriteBufferSize != 0) {
            Byte oneByte = (byte)(WriteBuffer << (8-WriteBufferSize));
            WriteBuffer = WriteBuffer >> 8;
            WriteBufferSize = 0;
            Output.write(oneByte);
        }
    }

    /**
     * Returns the current contents of this bitWriter, as a byte array
     * @return the current contents of this bitWriter, as a byte array
     * @throws DomainLayerException
     */
    public byte[] getOutput() throws DomainLayerException{
        try {
            byte[] result = Output.toByteArray();
            Output.close();
            return result;
        }
        catch (IOException e) {
            throw new DomainLayerException("An error has occurred during the JPEG process, operation aborted\n"+e.getMessage());
        }
    }

}
