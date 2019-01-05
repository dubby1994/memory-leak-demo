package cn.dubby.memory.leak.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.management.BufferPoolMXBean;
import java.lang.management.ManagementFactory;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.LinkedList;
import java.util.List;

/**
 * 使用FileChannel.map来分配native memory
 */
public class NativeMemoryLeakDemo2 {

    private static final Logger logger = LoggerFactory.getLogger(NativeMemoryLeakDemo2.class);

    public static void main(String[] args) throws InterruptedException, IOException {
        List<BufferPoolMXBean> mxBeanList = ManagementFactory.getPlatformMXBeans(BufferPoolMXBean.class);
        List<MappedByteBuffer> list = new LinkedList<>();
        String fileName = "test.txt";

        while (true) {
            MappedByteBuffer mappedByteBuffer = allocateMappedByteBuffer(fileName);
            list.add(mappedByteBuffer);
            logger.info("size:{}", list.size());
            Thread.sleep(100);

            mxBeanList.forEach(mxBean -> {
                logger.info("name:{}, total:{}, used:{}", mxBean.getName(), mxBean.getTotalCapacity(), mxBean.getMemoryUsed());
            });
        }
    }

    private static MappedByteBuffer allocateMappedByteBuffer(String fileName) throws IOException {
        //"READ_WRITE" must be one of "r", "rw", "rws", or "rwd"
        RandomAccessFile file = new RandomAccessFile(fileName, "rws");
        return file.getChannel().map(FileChannel.MapMode.PRIVATE, 0, file.length());
    }

}
