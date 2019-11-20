package xingoo.nio.e01_simple_demo;

import java.io.IOException;
import java.net.ServerSocket;

public class OldServer {
    public static void main(String[] args) throws IOException {
        System.out.println("Server begin");
        ServerSocket server = new ServerSocket(8080);
        server.accept();
        System.out.println("Server end");
    }
}
