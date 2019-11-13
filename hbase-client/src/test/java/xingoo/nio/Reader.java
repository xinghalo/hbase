package xingoo.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

class Reader extends Thread {

    private final Selector selector;

    Reader(String name) throws IOException {
        super(name);
        this.selector = Selector.open();
    }

    public synchronized SelectionKey register(SocketChannel channel) throws ClosedChannelException {
        return channel.register(selector, SelectionKey.OP_READ);
    }

    @Override
    public void run() {
        System.out.println(getName() + " handle");
        int count = 0;
        try{
            while(true){
                selector.select();
                Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    iter.remove();
                    if (key.isValid()) {
                        if (key.isReadable()) {
                            ByteBuffer in  = ByteBuffer.allocate(1024);
                            ByteBuffer out = ByteBuffer.allocate(1024);

                            SocketChannel sc = (SocketChannel) key.channel();
                            int length = sc.read(in);
                            if(length > 0){
                                in.flip();
                                System.out.println(new String(in.array(), StandardCharsets.UTF_8));
                            }

                            Thread.sleep(3000);

                            String resp = getName() + " 来自服务器的第"+ count++ +"次应答";
                            out.put(resp.getBytes());
                            out.flip();
                            sc.write(out);
                            out.compact();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
