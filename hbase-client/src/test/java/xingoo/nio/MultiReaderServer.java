package xingoo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiReaderServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.socket().bind(new InetSocketAddress(7778));

        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        ExecutorService readPool = Executors.newFixedThreadPool(4);

        List<Reader> readers = new ArrayList<>(4);
        for(int i=0; i< 4;i++){
            Reader reader = new Reader("reader" + i);
            readers.add(reader);
            readPool.execute(reader);
        }

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
                    SocketChannel sc;
                    while( (sc = ssChannel.accept()) != null){
                        sc.configureBlocking(false);
                        Reader currentReader = readers.get((count + 1) % 4);
                        count++;
                        SelectionKey readkey = currentReader.register(sc);
                        //TODO attach connction
                    }
                }
            }
        }
    }
}
