package xingoo.multithread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
    public static void main(String[] args) {
        ExecutorService es = Executors.newCachedThreadPool();
        es.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("sub-thread");
            }
        });
        System.out.println("main-thread");
    }
}
