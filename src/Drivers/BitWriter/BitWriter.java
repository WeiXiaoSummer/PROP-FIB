package Drivers.BitWriter;

import java.io.ByteArrayOutputStream;

public class BitWriter {
    private ByteArrayOutputStream Output;
    private int WriteBuffer;
    private int WriteBufferSize;

    public BitWriter() {
        this.Output = new ByteArrayOutputStream();
        this.WriteBuffer = 0;
        this.WriteBufferSize = 0;
    }

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

    public void flush() {
        if (WriteBufferSize != 0) {
            Byte oneByte = (byte)(WriteBuffer << (8-WriteBufferSize));
            WriteBuffer = WriteBuffer >> 8;
            WriteBufferSize = 0;
            Output.write(oneByte);
        }
    }

    public byte[] getOutput() {
        byte[] result = new byte[0];
        try {
            result = Output.toByteArray();
            Output.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
