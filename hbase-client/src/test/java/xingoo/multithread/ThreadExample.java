package xingoo.multithread;

/**
 * 直接继承Thread，重写run方法
 */
public class ThreadExample extends Thread {
    @Override
    public void run() {
        System.out.println("sub-thread");
    }

    public static void main(String[] args) {
        ThreadExample t = new ThreadExample();
        t.start();
        System.out.println("main-thread");
    }
}
