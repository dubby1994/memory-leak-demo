package cn.dubby.memory.leak.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.BufferPoolMXBean;
import java.lang.management.ManagementFactory;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

/**
 * 使用ByteBuffer.allocateDirect来分配native memory
 */
public class NativeMemoryLeakDemo1 {

    private static final Logger logger = LoggerFactory.getLogger(NativeMemoryLeakDemo1.class);

    public static void main(String[] args) throws InterruptedException {
        List<BufferPoolMXBean> mxBeanList = ManagementFactory.getPlatformMXBeans(BufferPoolMXBean.class);
        List<ByteBuffer> list = new LinkedList<>();
        int size = 1024;

        while (true) {
            ByteBuffer byteBuffer = allocateNativeMemory(size);
            list.add(byteBuffer);
            logger.info("size:{}", list.size());
            Thread.sleep(1000);

            mxBeanList.forEach(mxBean -> {
                logger.info("name:{}, total:{}, used:{}", mxBean.getName(), mxBean.getTotalCapacity(), mxBean.getMemoryUsed());
            });
        }
    }

    private static ByteBuffer allocateNativeMemory(int size) {
        return ByteBuffer.allocateDirect(size);
    }

}
