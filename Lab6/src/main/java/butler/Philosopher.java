package butler;

import java.util.Random;
import symetric_philosophers.Fork;

public class Philosopher extends Thread {
    private final Random gen = new Random();
    private Butler _butler;
    private Fork _left;
    private Fork _right;
    private String _logsColor;
    private int _ID;

    public static final String ANSI_RESET = "\u001B[0m";

    public Philosopher(Fork left, Fork right, Butler butler, String logsColor, int ID)
    {
        _left = left;
        _right = right;
        _butler = butler;
        _logsColor = logsColor;
        _ID = ID;
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
            while (!_butler.canIEat(_ID))
            {
                try {
                    sleep(gen.nextInt(10));
                } catch(Exception e) {;}
            }
            _left.pick_up();
            _right.pick_up();
            // eat
            System.out.println(_logsColor + "Philosopher " + Thread.currentThread() +
                    ": I am starting to eat." + ANSI_RESET);
            try {
                sleep(gen.nextInt(50));
            } catch (Exception e) {;}

            // koniec jedzenia
            _left.put_down();
            _right.put_down();
            _butler.returnForks(_ID);
            System.out.println(_logsColor + "Philosopher " + Thread.currentThread() +
                    ": I have eaten " + (i+1) + " time(s)." + ANSI_RESET);
        }
    }
}
