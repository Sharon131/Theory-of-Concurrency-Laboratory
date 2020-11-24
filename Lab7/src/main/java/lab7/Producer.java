package lab7;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Producer extends Thread {
    private Random gen;
    private Proxy buffer;
    private Integer id;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public Producer(Integer id, Proxy buffer) {
        gen = new Random();
        this.buffer = buffer;
        this.id = id;
    }

    public void run() {
        for (int i=0;i<10;i++) {
            Integer to_put = gen.nextInt(100);
            CompletableFuture<Integer> putResult = buffer.put(to_put);
            try {
                putResult.get(100, TimeUnit.SECONDS);
                System.out.println(ANSI_BLUE + System.nanoTime() + " lab7.Producer " + id + " produced value " + to_put + ANSI_RESET);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                System.out.println(ANSI_BLUE + "lab7.Producer " + id + " couldn't produce value " + to_put + ANSI_RESET);
            }

            try {
                Thread.sleep(100 + gen.nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
