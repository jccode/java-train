package com.github.jccode.javatrain.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class NioDemo1 {

    static class ReadDemo {
        void read() throws URISyntaxException, IOException {
            URI resource = getClass().getClassLoader().getResource("application.yml").toURI();
            // String resource = "/Users/chenjunchang/temp/1.txt";
            FileInputStream fileInputStream = new FileInputStream(new File(resource));
            FileChannel fc = fileInputStream.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            fc.read(buffer);

            // output the content of the file
            System.out.println(new String(buffer.array(), StandardCharsets.UTF_8));


        }
    }

    public static void main(String[] args) throws Exception {
        new ReadDemo().read();
    }
}
