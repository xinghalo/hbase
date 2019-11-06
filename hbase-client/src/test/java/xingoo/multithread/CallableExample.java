package xingoo.multithread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 通过futuretask包装返回结果
 */
public class CallableExample implements Callable<String> {

    @Override
    public String call() throws Exception {
        return "sub-thread";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CallableExample c = new CallableExample();
        FutureTask<String> future = new FutureTask<>(c);
        Thread t = new Thread(future);
        t.start();
        System.out.println("main-thread");
        String result = future.get();
        System.out.println(result);
    }
}
