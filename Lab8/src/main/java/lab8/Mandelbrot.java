package lab8;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.swing.JFrame;

public class Mandelbrot extends JFrame {
    private static final int MAX_ITER = 570;
    private static final double ZOOM = 150;

    private BufferedImage I;
    private double zx, zy, cX, cY, tmp;

    public Mandelbrot(int maxIter, double zoom) {
        super("Mandelbrot Set");
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

        ExecutorService pool = Executors.newWorkStealingPool();
        LinkedList<Future<Integer>> futures = new LinkedList<>();

        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                ForkMandelbrot mandelbrot = new ForkMandelbrot(I, maxIter, zoom, x, y);
                Future<Integer> future = pool.submit(mandelbrot);
                futures.add(future);
            }
        }

        for (Future<Integer> f : futures) {
            try {
                f.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Mandelbrot(int maxIter, double zoom, boolean row) {
        super("Mandelbrot Set");
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

        ExecutorService pool = Executors.newWorkStealingPool();
        LinkedList<Future<Integer>> futures = new LinkedList<>();

        int xWidth = getWidth();
        for (int y = 0; y < getHeight(); y++) {
            ForkMandelbrotRow mandelbrot = new ForkMandelbrotRow(I, maxIter, zoom, xWidth, y);
            Future<Integer> future = pool.submit(mandelbrot);
            futures.add(future);
        }

        for (Future<Integer> f : futures) {
            try {
                f.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }

    public static void main(String[] args) {
        try {
            File file = new File("mandelbrot.txt");
            file.createNewFile();

            FileWriter fileWriter = new FileWriter("mandelbrot.txt");

            for (int i = 10; i < MAX_ITER; i += 10) {
                long start = System.nanoTime();
                new Mandelbrot(i, ZOOM);
                long end = System.nanoTime();
                double elapsedInMilis = (end - start) / 1000000.0;

                System.out.println("Elapsed time: " + elapsedInMilis + " ms");

                long start2 = System.nanoTime();
                new Mandelbrot(i, ZOOM, true);
                long end2 = System.nanoTime();
                double elapsedInMilis2 = (end2 - start2) / 1000000.0;

                fileWriter.write(i + " " + Math.round((elapsedInMilis) * 100) / 100.0f + " " + Math.round((elapsedInMilis2) * 100) / 100.0f + "\r\n");
                System.out.println("Elapsed time 2: " + elapsedInMilis2 + " ms");
            }

            fileWriter.close();
        } catch (Exception e) {
        }
    }
}
