/**
 * A simple Turtle<br>
 * <p>
 *  A turtle helper class to draw with memory from a static context
 * </p>
 * @author Leonard Pudwitz
 * @version 1.0
 * <br>
 * @see program.system.Fractal
 *
 * @see program.algorithm.HilbertCurve
 * @see program.algorithm.KochCurve
 * @see program.algorithm.GosperCurve
 * @see program.algorithm.DragonCurve
 * @see program.algorithm.KochSnowflakeCurve
 * @see program.algorithm.PeanoCurve
 * @see program.algorithm.SierpinskiSquareCurve
 *
 * @see java.awt.Graphics2D
 */
package program.system;

import java.awt.*;
import java.awt.geom.Line2D;

public class Turtle {
    private double orientation;
    private double x, y;
    private double scaleFactor;
    private Graphics2D g;

    /**
     * Rotate the turtle by a certain angle
     * @param angle The angle to be rotated with
     */
    public void rotate(double angle) {
        orientation += angle;
    }

    /**
     * Move the turtle to a new location and draws a line along the way
     * @param d The distance it should move
     */
    public void forward(double d) {
        double x0 = x, y0 = y;
        x += d * Math.cos(Math.toRadians(orientation)) * scaleFactor;
        y += d * Math.sin(Math.toRadians(orientation)) * scaleFactor;


        g.draw(new Line2D.Double(x0,  y0,  x,  y));
    }

    /**
     * The constructor for the turtle class
     * @param startX The x coordinate of the start position
     * @param startY The y coordinate of the start position
     * @param scaleFactor A scale factor each forward step should be multiplied with
     * @param g A Graphics2D object that the turtle can draw on
     */
    public Turtle(double startX, double startY, double scaleFactor, Graphics2D g) {
        this.x = startX;
        this.y = startY;
        this.scaleFactor = scaleFactor;
        this.g = g;
    }
}
