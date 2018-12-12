package program.algorithm;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

public class TestAlg {
/*
  public int x = 0;
  public int y = 0;
  public int oldx = 0;
  public int oldy = 0;

  public int sc = 1;

  public int mousex = 100;
  public int mousey = 100;

  private static void moveHilbert(int j, int h, Graphics g) {
    oldy = y;
    oldx = x;
    if (j == 1) {
      y = y - h;
    } else if (j == 2) {
      x = x + h;
    } else if (j == 3) {
      y = y + h;
    } else if (j == 4) {
      x = x - h;
    }

    Graphics2D g2d = (Graphics2D) g;

    Line2D line = new Line2D.Double(oldx + mousex,oldy + mousey,x + mousex,y + mousey);

    AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(sc), mousex, mousey);

    g2d.draw(line);
  }

  private static void hilbert(int r, int d, int l, int u, int i, int h, Graphics g)  {
    if (i > 0) {
      i--;
      hilbert(d,r,u,l,i,h,g);
      moveHilbert(r,h,g);
      hilbert(r,d,l,u,i,h,g);
      moveHilbert(d,h,g);
      hilbert(r,d,l,u,i,h,g);
      moveHilbert(l,h,g);
      hilbert(u,l,d,r,i,h,g);
    }
  }

  //public Graphics2D static generate(TestAlg alg) {
  //  hilbert(2,3,4,1,10,30,g);
  //  return g;
  //}
  */
}
