package readers_writers.readers_first;

import java.io.File;
import java.io.FileWriter;
import java.util.concurrent.Semaphore;

public class Main {
    static private int min_writers_no = 1;
    static private int max_writers_no = 10;
    static private int min_readers_no = 10;
    static private int max_readers_no = 100;

    static private Semaphore readers_sem = new Semaphore(1);
    static private Semaphore writers_sem = new Semaphore(1);

    static private SynchData synchData = new SynchData();

    static float iteration(int writers_no, int readers_no) {
        Writer [] writers = new Writer[writers_no];
        Reader [] readers = new Reader[readers_no];

        long start = System.nanoTime();

        for (int i=0;i<writers_no;i++) {
            try {
                Thread.sleep(1);
            } catch (Exception e) {}
            writers[i] = new Writer(writers_sem, synchData);
            writers[i].start();
        }

        for(int i=0;i<readers_no;i++) {
            try {
                Thread.sleep(1);
            } catch (Exception e) {}
            readers[i] = new Reader(readers_sem, writers_sem, synchData);
            readers[i].start();
        }

        try {
            for (int i = 0; i < writers_no; i++) {
                writers[i].join();
            }
            for (int i = 0; i < readers_no; i++) {
                readers[i].join();
            }
        } catch (Exception e) {}

        long end = System.nanoTime();

        float elapsed_time = ((end-start) / 10000)/100.0f;

        System.out.println("Elapsed time in ms: " + elapsed_time);

        return elapsed_time;
    }

    public static void main(String[] args) {

        try {
            File file = new File("readers_first.txt");
            file.createNewFile();

            FileWriter fileWriter = new FileWriter("readers_first.txt");

            for (int w=min_writers_no;w<=max_writers_no;w++)
            {
                for (int r=min_readers_no;r<=max_readers_no;r++)
                {
                    float elapsed = iteration(w, r);
                    fileWriter.write(w + " " + r + " " + elapsed + "\r\n");
                }
                fileWriter.write("\r\n");
            }

            fileWriter.close();
        } catch (Exception e) {}
    }
}
