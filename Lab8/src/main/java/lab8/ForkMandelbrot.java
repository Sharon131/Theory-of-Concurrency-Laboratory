package lab8;

import java.awt.image.BufferedImage;
import java.util.concurrent.RecursiveAction;

public class ForkMandelbrot extends RecursiveAction {
    private BufferedImage image;
    private int maxIterationsNo;
    private double zoom;
    private int xMin;
    private int xMax;
    private int yMin;
    private int yMax;

    private double zx, zy, cX, cY, tmp;

    public ForkMandelbrot(BufferedImage I, int maxIters, double zoom, int xMin, int xMax, int yMin, int yMax)
    {
        this.image = I;
        this.maxIterationsNo = maxIters;
        this.zoom = zoom;
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
    }

    private void computeDirectly()
    {
        zx = zy = 0;
        cX = (xMin - 400) / zoom;
        cY = (yMin - 300) / zoom;
        int iter = maxIterationsNo;
        while (zx * zx + zy * zy < 4 && iter > 0) {
            tmp = zx * zx - zy * zy + cX;
            zy = 2.0 * zx * zy + cY;
            zx = tmp;
            iter--;
        }
        image.setRGB(xMin, yMin, iter | (iter << 8));
    }

    @Override
    protected void compute() {
        if (this.xMin == this.xMax && this.yMin == this.yMax)
        {
            computeDirectly();
            return;
        }

        int xMiddle1 = (xMin + xMax) / 2;
        int yMiddle1 = (yMin + yMax) / 2;
        int xMiddle2 = (xMin + xMax) / 2;
        int yMiddle2 = (yMin + yMax) / 2;

        if (xMin != xMax)
        {
            xMiddle2 += 1;
        }
        if (yMin != yMax)
        {
            yMiddle2 += 1;
        }

        invokeAll(new ForkMandelbrot(image, maxIterationsNo, zoom, xMin, xMiddle1, yMin, yMiddle1),
                    new ForkMandelbrot(image, maxIterationsNo, zoom, xMiddle2, xMax, yMiddle2, yMax));
    }
}
