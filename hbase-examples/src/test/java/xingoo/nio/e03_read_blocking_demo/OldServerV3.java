package xingoo.nio.e03_read_blocking_demo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class OldServerV3 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        // 阻塞等待连接
        Socket socket = serverSocket.accept();
        System.out.println("等待");

        byte[] byteArray = new byte[1024];
        // 阻塞等待发送
        int length = socket.getInputStream().read(byteArray);
        System.out.println("结束");
        socket.close();
        serverSocket.close();
    }
}
