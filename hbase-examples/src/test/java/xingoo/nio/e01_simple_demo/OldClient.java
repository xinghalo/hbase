package xingoo.nio.e01_simple_demo;

import java.io.IOException;
import java.net.Socket;

public class OldClient {
    public static void main(String[] args) throws IOException {
        System.out.println("client begin");
        Socket client = new Socket("localhost", 8080);
        System.out.println("client end");
    }
}