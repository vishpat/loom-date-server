package com.vishpat.learnings.fibers;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;

class DateRequestHandler implements Runnable {

    private SocketChannel socketChannel;

    DateRequestHandler(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public void run() {
        String dateStr = String.format("%s\n", new Date().toString());
        ByteBuffer byteBuffer = ByteBuffer.allocate(128);
        byteBuffer.put(dateStr.getBytes());
        byteBuffer.flip();
        try {
            while (byteBuffer.hasRemaining()) {
                socketChannel.write(byteBuffer);
            }
            socketChannel.close();
        } catch (IOException ex) {
            System.out.printf("Problem while writing to the client %s", ex.getMessage());
        }
    }
}


@Service
public class FiberDateServer {
    static {
        System.out.println("Starting fibered date server....");
        FiberScope scope = FiberScope.open();
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            serverSocketChannel.socket().bind(new InetSocketAddress(59059));
            while (true) {
                SocketChannel socketChannel = serverSocketChannel.accept();
                scope.schedule(new DateRequestHandler(socketChannel));
            }
        } catch (Exception ex) {
            System.out.printf("Unable to open server socket %s", ex.getMessage());
        }
    }
}
