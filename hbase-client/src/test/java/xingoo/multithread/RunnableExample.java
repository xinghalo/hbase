package xingoo.multithread;

/**
 * 在调用thread.run时，间接调用runnable.run
 */
public class RunnableExample implements Runnable{
    @Override
    public void run() {
        System.out.println("sub-thread");
    }

    public static void main(String[] args) {
        RunnableExample task = new RunnableExample();
        Thread t = new Thread(task);
        t.start();
        System.out.println("main-thread");
    }
}
