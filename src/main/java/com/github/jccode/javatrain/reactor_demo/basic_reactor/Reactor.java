package com.github.jccode.javatrain.reactor_demo.basic_reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class Reactor implements Runnable {

    private final Selector selector;
    private final ServerSocketChannel serverSocketChannel;
    private final static int MAXIN = 4 * 1024;
    private final static int MAXOUT = 4 * 1024;

    public Reactor(int port) throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        SelectionKey sk = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        sk.attach(new Acceptor());
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                selector.select();
                Set<SelectionKey> selected = selector.selectedKeys();
                for (SelectionKey key : selected) {
                    dispatch(key);
                }
                selected.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void dispatch(SelectionKey key) {
        Runnable acceptor = (Runnable)key.attachment();
        while (acceptor != null) {
            acceptor.run();
        }
    }

    class Acceptor implements Runnable {

        @Override
        public void run() {
            try {
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel != null) {
                    new Handler(selector, socketChannel);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    final static class Handler implements Runnable {
        final SocketChannel socket;
        final SelectionKey sk;
        ByteBuffer input = ByteBuffer.allocate(MAXIN);
        ByteBuffer output = ByteBuffer.allocate(MAXOUT);
        static final int READING = 0, SENDING = 1;
        int state = READING;

        public Handler(Selector selector, SocketChannel socketChannel) throws IOException {
            socket = socketChannel;
            socketChannel.configureBlocking(false);
            sk = socketChannel.register(selector, 0);
        }

        @Override
        public void run() {

        }
    }
}

