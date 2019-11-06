package xingoo.multithread;

public class AnoyInternalExample {
    public static void main(String[] args) {
        System.out.println("main-thread");

        new Thread(){
            @Override
            public void run() {
                System.out.println("sub-thread-1");
            }
        }.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("sub-thread-2");
            }
        }).start();

        // new Thread(()->System.out.println("sub-thread-3")).start();
    }
}
