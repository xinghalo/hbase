package xingoo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {

    public static void main(String[] args) throws IOException, InterruptedException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(7778));


        while(true){
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put("hello".getBytes());
            buffer.flip();

            socketChannel.write(buffer);

            Thread.sleep(2000);
        }
    }
}
