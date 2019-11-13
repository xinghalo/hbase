package xingoo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Client {

    public static void main(String[] args) throws IOException, InterruptedException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(7778));

        int count = 0;
        while(true){
            ByteBuffer out = ByteBuffer.allocate(1024);
            ByteBuffer in = ByteBuffer.allocate(1024);

            // 写数据
            String message = "来自client的第"+ count++ +"次请求";
            out.put(message.getBytes());
            out.flip();
            socketChannel.write(out);
            out.compact();

            // 读数据
            int length = socketChannel.read(in);
            if(length > 0){
                in.flip();
                System.out.println(new String(in.array(), StandardCharsets.UTF_8));
                in.compact();
            }

            Thread.sleep(500);
        }
    }
}
