package xingoo.nio;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class NetworkInterfaceDemo {
    public static void main(String[] args) throws SocketException {
        Enumeration<NetworkInterface> networkInterface = NetworkInterface.getNetworkInterfaces();
        while(networkInterface.hasMoreElements()){
            NetworkInterface ethnetInterface =  networkInterface.nextElement();
            // 名字
            System.out.println(ethnetInterface.getName());
            System.out.println(ethnetInterface.getDisplayName());
            // 索引
            System.out.println(ethnetInterface.getIndex());
            // 是否可用
            System.out.println(ethnetInterface.isUp());
            // 是否回环地址
            System.out.println(ethnetInterface.isLoopback());
            // 是否虚拟地址
            System.out.println(ethnetInterface.isVirtual());
            // MTU最大传输单元的大小
            System.out.println(ethnetInterface.getMTU());

            // 硬件地址
            byte[] address = ethnetInterface.getHardwareAddress();
            //System.out.println(new String(ethnetInterface.getHardwareAddress()));

            Enumeration<InetAddress> addr = ethnetInterface.getInetAddresses();
            System.out.println("*****");
            while(addr.hasMoreElements()){
                InetAddress ia = addr.nextElement();
                System.out.println(ia.getCanonicalHostName());
                System.out.println(ia.getHostAddress());
                System.out.println(ia.getHostName());
            }
            System.out.println("*****");
            System.out.println(ethnetInterface.getInetAddresses());

            System.out.println("-----");
        }
    }
}
