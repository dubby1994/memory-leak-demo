package cn.dubby.memory.leak.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.DataFormatException;

public class NativeMemoryLeakDemo4 {

    private static final Logger logger = LoggerFactory.getLogger(NativeMemoryLeakDemo4.class);

    private static Unsafe unsafe;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
            System.out.println(unsafe);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws DataFormatException, InterruptedException {
        List<Long> list = new LinkedList<>();
        long size = 1024 * 1024;
        while (true) {
            long address = allocateMemory(size);
            list.add(address);
            Thread.sleep(1);
            logger.info("size:{}", list.size());
        }
    }

    private static long allocateMemory(long size) throws DataFormatException {
        return unsafe.allocateMemory(size);
    }

}
