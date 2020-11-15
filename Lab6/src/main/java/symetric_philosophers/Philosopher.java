package symetric_philosophers;

import java.util.Random;

public class Philosopher extends Thread {
    private final Random gen = new Random();
    private EatersCounter _eatersCounter;
    private Fork _left;
    private Fork _right;
    private String _logsColor;

    public static final String ANSI_RESET = "\u001B[0m";

    public Philosopher(Fork left, Fork right, EatersCounter eatersCounter, String logsColor)
    {
        _left = left;
        _right = right;
        _eatersCounter = eatersCounter;
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
            while (!_eatersCounter.canIEat());
            _eatersCounter.increase();
            _left.pick_up();
            _right.pick_up();
            // eat
            System.out.println(_logsColor + "Philosopher " + Thread.currentThread() +
                                ": I am starting to eat." + ANSI_RESET);
            try {
                sleep(gen.nextInt(50));
            } catch (Exception e) {;}

            System.out.println(_logsColor + "Philosopher " + Thread.currentThread() +
                    ": I have eaten " + (i+1) + " time(s)." + ANSI_RESET);
            // koniec jedzenia
            _left.put_down();
            _right.put_down();
            _eatersCounter.decrease();
        }
    }
}
