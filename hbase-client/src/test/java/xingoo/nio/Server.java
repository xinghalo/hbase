package xingoo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class Server {

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.socket().bind(new InetSocketAddress(7778));

        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        int count = 0;

        while(true) {
            int num = selector.select();
            if(num == 0) {
                continue;
            }

            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            while(iter.hasNext()) {
                SelectionKey key = iter.next();
                iter.remove();

                //处理
                if (key.isValid() && key.isAcceptable()) {
                    ServerSocketChannel ssChannel = (ServerSocketChannel) key.channel();
                    SocketChannel sc = ssChannel.accept();
                    sc.configureBlocking(false);
                    sc.register(key.selector(), SelectionKey.OP_READ);
                }
                if (key.isValid() && key.isReadable()){
                    SocketChannel sc = (SocketChannel) key.channel();

                    ByteBuffer in  = ByteBuffer.allocate(1024);
                    ByteBuffer out = ByteBuffer.allocate(1024);

                    int length = sc.read(in);
                    if(length > 0){
                        in.flip();
                        System.out.println(new String(in.array(), StandardCharsets.UTF_8));

                        Thread.sleep(3000); // 模拟复杂处理流程

                        String resp = "来自服务器的第"+ count++ +"次应答";
                        out.put(resp.getBytes());
                        out.flip();
                        sc.write(out);
                        out.compact();
                    }else{
                        key.cancel();
                        sc.close();
                    }
                }
            }
        }
    }
}
