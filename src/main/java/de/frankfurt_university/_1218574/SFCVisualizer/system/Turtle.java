/**
 * A simple Turtle<br>
 * <p>
 *  A turtle helper class to draw with memory from a static context
 * </p>
 * @author Leonard Pudwitz
 * @version 1.0
 * <br>
 * @see de.frankfurt_university._1218574.SFCVisualizer.system.Fractal
 *
 * @see de.frankfurt_university._1218574.SFCVisualizer.algorithm.HilbertCurve
 * @see de.frankfurt_university._1218574.SFCVisualizer.algorithm.KochCurve
 * @see de.frankfurt_university._1218574.SFCVisualizer.algorithm.GosperCurve
 */
package de.frankfurt_university._1218574.SFCVisualizer.system;

import java.awt.*;
import java.awt.geom.Line2D;

public class Turtle {
    private double orientation;
    private double x, y;
    private double scaleFactor;
    private Graphics2D g;

    //rotate the turtle
    public void rotate(double angle) {
        orientation += angle;
    }

    // move the turtle forward (includes drawing)
    public void forward(double d) {
        double x0 = x, y0 = y;
        x += d * Math.cos(Math.toRadians(orientation)) * scaleFactor;
        y += d * Math.sin(Math.toRadians(orientation)) * scaleFactor;


        g.draw(new Line2D.Double(x0,  y0,  x,  y));
    }

    //the default constructor
    public Turtle(double startX, double startY, double scaleFactor, Graphics2D g) {
        this.x = startX;
        this.y = startY;
        this.scaleFactor = scaleFactor;
        this.g = g;
    }
}
