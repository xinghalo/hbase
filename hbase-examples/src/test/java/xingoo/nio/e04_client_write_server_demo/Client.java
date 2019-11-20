package xingoo.nio.e04_client_write_server_demo;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8080);

        OutputStream out = socket.getOutputStream();
        out.write("我是外星人".getBytes());
        out.write("你好\n".getBytes());
        out.write("哈哈".getBytes());

        out.close();
        socket.close();
    }
}
