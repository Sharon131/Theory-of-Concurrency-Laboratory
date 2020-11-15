package symetric_philosophers;  // symetric_philosophers.Fil5mon.java

public class Fil5mon {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    static private int philosophers_no = 5;
    static private Philosopher [] philosophers = new Philosopher[philosophers_no];
    static private String [] logsColors = {ANSI_RED, ANSI_GREEN, ANSI_YELLOW, ANSI_BLUE, ANSI_PURPLE};
    static private Fork [] forks = new Fork[philosophers_no];
    static private EatersCounter counter = new EatersCounter(philosophers_no);

    public static void main(String[] args)
    {
        long start = System.nanoTime();

        for (int i=0;i<philosophers_no;i++)
        {
            forks[i] = new Fork();
        }

        for (int i=0;i<philosophers_no;i++)
        {
            philosophers[i] = new Philosopher(forks[i], forks[(i+1)%philosophers_no], counter, logsColors[i]);
            philosophers[i].start();
        }

        for (int i=0;i<philosophers_no;i++)
        {
           try
           {
               philosophers[i].join();
           } catch (Exception e) {;}
        }

        long end = System.nanoTime();

        float elapsed = (end-start)/1000000.0f;

        System.out.println("Elapsed time: " + elapsed + " ms");
    }
}
