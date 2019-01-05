package cn.dubby.memory.leak.demo;

import java.io.FileWriter;
import java.io.IOException;

public class FileGenerator {

    public static void main(String[] args) throws IOException {
        FileWriter fileWriter = new FileWriter("test.txt");

        for (int i = 0; i < 1024; ++i) {
            fileWriter.write("1234567890\n");
        }

        fileWriter.flush();
        fileWriter.close();
    }

}
