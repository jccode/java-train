package com.github.jccode.javatrain.reactor_demo.classic1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

    private static final int PORT = 9000;
    private static final int MAX_INPUT = 4 * 1024;

    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(PORT);
            while (Thread.interrupted()) {
                new Thread(new Handler(ss.accept())).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class Handler implements Runnable {

        final Socket socket;

        public Handler(Socket s) {
            socket = s;
        }

        @Override
        public void run() {
            try {
                byte[] input = new byte[MAX_INPUT];
                socket.getInputStream().read(input);
                byte[] output = process(input);
                socket.getOutputStream().write(output);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private byte[] process(byte[] input) {
            // process input
            return new byte[0];
        }
    }
}
