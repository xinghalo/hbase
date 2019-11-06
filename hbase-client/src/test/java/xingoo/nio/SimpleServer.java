package xingoo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class SimpleServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 是否阻塞
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(7778));
        while(true){
            // 如果为阻塞模式，则accpet会一直阻塞到有新的连接到达
            SocketChannel channel = serverSocketChannel.accept();
            if(channel!=null){
                //TODO
            }
            Thread.sleep(1000);
        }
    }
}
