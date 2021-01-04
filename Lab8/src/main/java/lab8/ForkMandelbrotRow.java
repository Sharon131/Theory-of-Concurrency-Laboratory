package lab8;

import java.awt.image.BufferedImage;
import java.util.concurrent.Callable;

public class ForkMandelbrotRow implements Callable {
    private BufferedImage image;
    private int maxIterationsNo;
    private double zoom;
    private int xWidth;
    private int y;

    private double zx, zy, cX, cY, tmp;

    public ForkMandelbrotRow(BufferedImage I, int maxIters, double zoom, int xWidth, int y)
    {
        this.image = I;
        this.maxIterationsNo = maxIters;
        this.zoom = zoom;
        this.xWidth = xWidth;
        this.y = y;
    }

    @Override
    public Object call() throws Exception
    {
        for (int x=0;x<xWidth;x++)
        {
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
        }
        return 0;
    }
}
