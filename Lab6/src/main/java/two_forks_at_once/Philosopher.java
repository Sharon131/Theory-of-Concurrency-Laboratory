package two_forks_at_once;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Philosopher extends Thread {
    private final Random gen = new Random();
    private Semaphore _mainSemaphore;
    private Fork _left;
    private Fork _right;
    private String _logsColor;

    public static final String ANSI_RESET = "\u001B[0m";

    public Philosopher(Fork left, Fork right, Semaphore mainSemaphore, String logsColor)
    {
        _left = left;
        _right = right;
        _mainSemaphore = mainSemaphore;
        _logsColor = logsColor;
    }

    public void run()
    {
        for (int i=0;i<10;i++) {
            //think
            try {
                sleep(gen.nextInt(100));
            } catch (Exception e) {;}

            System.out.println(_logsColor + "Philosopher " + Thread.currentThread() +
                    ": Waiting for forks." + ANSI_RESET);
            try {
                _mainSemaphore.acquire();
                while (_left.isPickedUp() || _right.isPickedUp())
                {
                    _mainSemaphore.release();
                    sleep(gen.nextInt(3));
                    _mainSemaphore.acquire();
                }
                _left.pick_up();
                _right.pick_up();
                _mainSemaphore.release();
            } catch (Exception e) {;}
            // eat
            System.out.println(_logsColor + "Philosopher " + Thread.currentThread() +
                                ": I am starting to eat." + ANSI_RESET);
            try {
                sleep(gen.nextInt(50));
            } catch (Exception e) {;}

            // koniec jedzenia
            try {
                _mainSemaphore.acquire();
                _left.put_down();
                _right.put_down();
                _mainSemaphore.release();
            } catch (Exception e) {;}
            System.out.println(_logsColor + "Philosopher " + Thread.currentThread() +
                    ": I have eaten " + (i+1) + " time(s)." + ANSI_RESET);
        }
    }
}
