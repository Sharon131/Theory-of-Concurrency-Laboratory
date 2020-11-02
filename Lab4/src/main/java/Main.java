import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.util.concurrent.Semaphore;

public class Main {

    private static float program(int min_rand, int max_rand, int producers_no, int consumers_no, int buffer_size) {
        Semaphore prod_sem = new Semaphore(producers_no);
        Semaphore cons_sem = new Semaphore(consumers_no);

        Buffer buff = new Buffer(buffer_size);
        Producer [] producers = new Producer[producers_no];
        Consumer [] consumers = new Consumer[consumers_no];

        long start = System.nanoTime();

        for (int i=0;i<producers_no;i++)
        {
            producers[i] = new Producer(buff, min_rand, max_rand, 10, prod_sem);
            producers[i].start();
        }

        for (int i=0;i<consumers_no;i++) {
            consumers[i] = new Consumer(buff, min_rand, max_rand, 10, cons_sem);
            consumers[i].start();
        }


        try {
            boolean is_finished = false;
            while (!is_finished) {
                if (!prod_sem.tryAcquire()) {
                    for (int i = 0; i < producers_no; i++) {
                        producers[i].join();
                    }
                    for (int i = 0; i < consumers_no; i++) {
                        if (consumers[i].isAlive()) {
                            consumers[i].interrupt();
                        }
                    }
                    is_finished = true;
                } else {
                    prod_sem.release();
                }

                if (!cons_sem.tryAcquire()) {
                    for (int i = 0; i < consumers_no; i++) {
                        consumers[i].join();
                    }
                    for (int i = 0; i < producers_no; i++) {
                        if (producers[i].isAlive()) {
                            producers[i].interrupt();
                        }
                    }
                    is_finished = true;
                } else {
                    cons_sem.release();
                }
            }

        } catch(Exception e) {}

        long end = System.nanoTime();

        float elapsed_ms = (end-start) / 1000000;

        return elapsed_ms;
    }

    public static void main(String[] args) {
        int start_i = 10;
        int end_i = 100;

        double[] xData = new double[end_i - start_i];
        double[] yData = new double[end_i - start_i];

        for (int i=start_i;i<end_i;i++) {
            float elapsed_ms = program(49, 50, 10, i, 100);
            xData[i-start_i] = i;
            yData[i-start_i] = elapsed_ms;
            System.out.println("Elapsed time: " + elapsed_ms + " ms");
        }

        try {
            XYChart chart = QuickChart.getChart("Program execution time from number of producers", "Number of producers", "Program execution time [ms]", "time", xData, yData);
            new SwingWrapper(chart).displayChart();
            BitmapEncoder.saveBitmap(chart, "./prod_no_gen", BitmapEncoder.BitmapFormat.PNG);
        } catch (Exception e) {}
    }
}
