package lab7;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Consumer extends Thread {
    private Proxy buffer;
    private Integer id;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    public Consumer(Integer id, Proxy buffer) {
        this.id = id;
        this.buffer = buffer;
    }

    public void run() {
        for (int i=0;i<6;i++) {
            try {
                Integer value = buffer.get().get(100, TimeUnit.SECONDS);
                System.out.println(ANSI_YELLOW + System.nanoTime() + " lab7.Consumer " + id + " consumed value " + value + ANSI_RESET);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
//                System.out.println("ANSI_YELLOW + lab7.Consumer " + id + " waiting to consume value + ANSI_RESET");
            }

            try {
                Thread.sleep(100 + new Random().nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
