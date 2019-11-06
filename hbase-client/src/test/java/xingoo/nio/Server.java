package xingoo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Server {
    private static final int BUF_SIZE = 1024;

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.socket().bind(new InetSocketAddress(7778));

        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        while(true) {
            selector.select();
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            while(iter.hasNext()) {
                SelectionKey key = iter.next();
                iter.remove();

                //处理
                if (key.isValid() && key.isAcceptable()) {
                    System.out.println("accept");
                    ServerSocketChannel ssChannel = (ServerSocketChannel) key.channel();
                    SocketChannel sc = ssChannel.accept();
                    sc.configureBlocking(false);
                    sc.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocateDirect(BUF_SIZE));
                }
                if (key.isValid() && key.isReadable()){
                    System.out.println("read");
                    SocketChannel sc = (SocketChannel) key.channel();
                    try {
                        ByteBuffer buf = (ByteBuffer) key.attachment();

                        ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                        int bytesRead = sc.read(buf);
                        if (bytesRead > 0) {
                            buf.flip();
                            byte[] bytes = new byte[bytesRead];
                            buf.get(bytes, 0, bytesRead);
                            String str = new String(bytes);
                            System.out.println(str);
                            buf.clear();

                            writeBuffer.put(bytes);
                            writeBuffer.flip();
                            while (writeBuffer.hasRemaining()) {
                                sc.write(writeBuffer);
                            }
                            writeBuffer.compact();

                        } else {
                            System.out.println("关闭的连接");
                            key.cancel();
                            sc.close();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        key.cancel();
                        sc.close();
                    }

                }
                if(key.isValid() && key.isWritable()){
                    ByteBuffer buf = (ByteBuffer) key.attachment();
                    buf.put("客户端返回".getBytes());
                    buf.flip();
                    SocketChannel sc = (SocketChannel) key.channel();
                    while (buf.hasRemaining()) {
                        sc.write(buf);
                    }
                    buf.compact();
                }
            }
        }
    }
}
