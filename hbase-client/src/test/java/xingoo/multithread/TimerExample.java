package xingoo.multithread;

import java.util.Timer;
import java.util.TimerTask;

public class TimerExample {
    public static void main(String[] args) {
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("sub-thread");
            }
        }, 200, 100);
        System.out.println("main-thread");
    }
}
