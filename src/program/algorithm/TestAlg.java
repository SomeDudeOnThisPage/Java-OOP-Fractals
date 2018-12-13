package program.algorithm;

import program.Program;
import program.system.Curve;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

public class TestAlg extends Curve
{
  public static BufferedImage render(BufferedImage image)
  {
    // Inner class is a godsent here
    class Algorithm
    {
      private double x,y;
      private double oldX, oldY;
      private Graphics2D g;

      private void hilbert(int r, int d, int l, int u, int iterations, double size)
      {
        if (iterations > 0)
        {
          iterations--;
          hilbert(d,r,u,l,iterations,size);
          moveHilbert(r,size,g);
          hilbert(r,d,l,u,iterations,size);
          moveHilbert(d,size,g);
          hilbert(r,d,l,u,iterations,size);
          moveHilbert(l,size,g);
          hilbert(u,l,d,r,iterations,size);
        }
      }

      private void moveHilbert(int j, double h, Graphics2D g)
      {
        oldY = y;
        oldX = x;
        if (j == 1)
        {
          y = y - h;
        }
        else if (j == 2)
        {
          x = x + h;
        }
        else if (j == 3)
        {
          y = y + h;
        }
        else if (j == 4)
        {
          x = x - h;
        }
        g.draw(new Line2D.Double(oldX, oldY, x, y));
      }

      public Algorithm(Graphics2D g)
      {
        // Define start
        // Testing
        final int ITER_DEPTH = 6;

        // (4^n - 1) / 2^n -> Overall size of the curve in dim2
        double eSize = ( Math.pow(4,ITER_DEPTH) - 1 ) / Math.pow(2,ITER_DEPTH);
        double iSize = 1000.0 / eSize;
        // TODO: instead of fixed 1000.0 -> pass canvas size object

        Program.debug("Rendering hilbert curve with iteration depth = " + ITER_DEPTH + " and eucledian size = " + eSize + "...");

        x = iSize / 2;
        y = iSize / 2;

        g.setColor(Color.BLUE);
        this.g = g;

        hilbert(3,2,1,4,ITER_DEPTH,iSize);
      }
    }

    new Algorithm((Graphics2D) image.getGraphics());

    return image;
  }

  public TestAlg(int csize, int scale)
  {
    super(csize, scale);
  }
}
