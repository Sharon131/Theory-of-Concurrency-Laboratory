package lab8;

import java.awt.image.BufferedImage;
import java.util.concurrent.Callable;

public class ForkMandelbrot implements Callable {
    private BufferedImage image;
    private int maxIterationsNo;
    private double zoom;
    private int x;
    private int y;

    private double zx, zy, cX, cY, tmp;

    public ForkMandelbrot(BufferedImage I, int maxIters, double zoom, int x, int y) {
        this.image = I;
        this.maxIterationsNo = maxIters;
        this.zoom = zoom;
        this.x = x;
        this.y = y;
    }

    @Override
    public Object call() throws Exception {
        zx = zy = 0;
        cX = (x - 400) / zoom;
        cY = (y - 300) / zoom;
        int iter = maxIterationsNo;
        while (zx * zx + zy * zy < 4 && iter > 0) {
            tmp = zx * zx - zy * zy + cX;
            zy = 2.0 * zx * zy + cY;
            zx = tmp;
            iter--;
        }
        image.setRGB(x, y, iter | (iter << 8));

        return 0;
    }
}
