//just a helper class for drawing out of a static context

package program.algorithm;

import java.awt.*;
import java.awt.geom.Line2D;

class Turtle {
    private double orientation;
    private double x, y;
    private double scaleFactor;
    private Graphics2D g;

    //rotate the turtle
    void rotate(double angle) {
        orientation += angle;
    }
    // move the turtle forward (includes drawing)
    void forward(double d) {
        double x0 = x, y0 = y;
        x += d * Math.cos(Math.toRadians(orientation)) * scaleFactor;
        y += d * Math.sin(Math.toRadians(orientation)) * scaleFactor;


        g.draw(new Line2D.Double(x0,  y0,  x,  y));
    }

    Turtle(double startX, double startY, double scaleFactor, Graphics2D g) {
        this.x = startX;
        this.y = startY;
        this.scaleFactor = scaleFactor;
        this.g = g;
    }
}
