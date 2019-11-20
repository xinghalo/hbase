package xingoo.nio.e03_read_blocking_demo;

import java.io.IOException;
import java.net.Socket;

public class OldClientV3 {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("localhost", 8080);
        Thread.sleep(5000);
    }
}
