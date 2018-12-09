//taken from https://www.cs.cmu.edu/~adamchik/15-121/lectures/Recursions/demo/Hilbert.java
package fractals.curves;

import java.awt.*;

public class HilbertCurve extends DrawableCurve {

    // emulate a turtle by remembering variables because it was the simplest approach to drawing the curve
    private double x = 0.0, y = 0.0;
    private double orientation = 0.0;
    private float scaleFactor = 1;
    private Graphics g;

    // rotate the turtle
    private void rotate(double angle) {
        orientation += angle;
    }

    // move the turtle forward (includes drawing)
    private void forward(double d) {
        double x0 = x, y0 = y;
        x += d * Math.cos(Math.toRadians(orientation)) * scaleFactor;
        y += d * Math.sin(Math.toRadians(orientation)) * scaleFactor;

        //switch to Graphics2D to allow doubles as coordinates
        //Shape line = new Line2D.Double(x0, y0, x, y);
        //g.draw(line);

        g.drawLine((int) x0, (int) y0, (int) x, (int) y);
    }

    // Hilbert curve
    private void hilbert0(int n) {
        if (n == 0) return;
        rotate(90);
        hilbert1(n-1);
        forward(1.0);
        rotate(-90);
        hilbert0(n-1);
        forward(1.0);
        hilbert0(n-1);
        rotate(-90);
        forward(1.0);
        hilbert1(n-1);
        rotate(90);
    }

    // evruc trebliH
    private void hilbert1(int n) {
        if (n == 0) return;
        rotate(-90);
        hilbert0(n-1);
        forward(1.0);
        rotate(90);
        hilbert1(n-1);
        forward(1.0);
        hilbert1(n-1);
        rotate(90);
        forward(1.0);
        hilbert0(n-1);
        rotate(-90);
    }

    //our drawing method specified by the abstract class
    public Graphics generate(Graphics g, float scaleFactor, int startX, int startY, int iterations) {
        this.x = startX;
        this.y = startY;
        this.scaleFactor = scaleFactor;
        this.g = g;

        hilbert0(iterations);

        return g;
    }
}