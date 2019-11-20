package xingoo.nio.e04_client_write_server_demo;

import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        Socket socket = serverSocket.accept();

        InputStream inputStream = socket.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String getString = "";
        while(StringUtils.isNotBlank(getString = bufferedReader.readLine())){
            System.out.println(getString);
        }

        inputStream.close();
        socket.close();
        serverSocket.close();
    }
}
