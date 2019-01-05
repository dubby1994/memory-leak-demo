package cn.dubby.memory.leak.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * 未释放Inflater
 */
public class NativeMemoryLeakDemo3 {

    private static final Logger logger = LoggerFactory.getLogger(NativeMemoryLeakDemo3.class);

    public static void main(String[] args) throws DataFormatException {
        String data = "12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";

        StringBuilder sb = new StringBuilder(data);
        for (int i = 0; i < 100000; ++i) {
            sb.append(data);
        }

        data = sb.toString();

        logger.info("input length:{}", data.getBytes().length);
        Deflater deflater = new Deflater();
        deflater.setInput(data.getBytes());
        deflater.finish();

        byte[] result = new byte[102400];
        int resultLength = deflater.deflate(result);
        logger.info("result length:{}", resultLength);

        while (true) {
            inflater(result, resultLength);
        }
    }

    private static void inflater(byte[] deflaterData, int length) throws DataFormatException {
        Inflater inflater = new Inflater();
        inflater.setInput(deflaterData, 0, length);
        byte[] result = new byte[102400];
        int resultLength = inflater.inflate(result);

        logger.info("inflater length:{}", resultLength);

        //这里故意不释放
        //inflater.end();
    }

}
