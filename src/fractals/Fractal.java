package fractals;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class Fractal {

  // Just generate a sierpinsky-triangle right now. Algorithm ripped from the interwebs and slightly adjusted
  public Graphics generate(Graphics g) {

    Graphics2D g2 = (Graphics2D)g;

    Point a = between(new Point(0, 0), new Point(940, 0));
    Point b = new Point(940, 940);
    Point c = new Point(0, 940);

    drawTriangle(g2, a, b, c);

    return (Graphics) g;
  }

  private void drawTriangle(Graphics2D g, Point a, Point b, Point c) {
    g.drawLine(a.x, a.y, b.x, b.y);
    g.drawLine(b.x, b.y, c.x, c.y);
    g.drawLine(c.x, c.y, a.x, a.y);

    if(a.distance(b.x, b.y) < 2.0) {
      return;
    }

    Point ab = between(a, b);
    Point bc = between(b, c);
    Point ca = between(c, a);

    drawTriangle(g, a, ab, ca);
    drawTriangle(g, ab, b, bc);
    drawTriangle(g, c, ca, bc);
  }

  private static Point between(Point a, Point b) {
    return new Point((a.x + b.x) / 2, (a.y + b.y) / 2);
  }

}
