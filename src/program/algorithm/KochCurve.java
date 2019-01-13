package program.algorithm;

import program.system.Fractal;
import program.system.Turtle;
import program.ui.elements.AlgorithmSetting;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * Algorithm for drawing a Koch Snowflake<br>
 * <p>
 *  A static class for drawing the Koch Snowflake with the given parameters from the GUI
 * </p>
 * @author Leonard Pudwitz
 * @version 1.0
 * <br>
 * @see program.system.Fractal
 *
 * @see program.system.Turtle
 */
class KochCurve extends Fractal {

    static BufferedImage render(BufferedImage image, HashMap<String, AlgorithmSetting> settings) {

        //initialize all values from the settings menu
        double scaleFactor = (double) settings.get("scaleFactor").getValue();
        double x = (int) settings.get("startX").getValue();
        double y = (int) settings.get("startY").getValue();
        int iterations = (int) settings.get("iterations").getValue();

        //create the graphics2d object from the buffered image
        Graphics2D g = image.createGraphics();

        //set a fixed color for now
        g.setColor(Color.GREEN);

        //initialize a turtle for drawing the curve
        Turtle t = new Turtle(x, y, scaleFactor, g);

        //draw first part of snowflake
        koch(iterations, (1.0 / Math.pow(3.0, iterations)), t);

        //turn 120 degrees
        t.rotate(-120);

        //draw second part
        koch(iterations, (1.0 / Math.pow(3.0, iterations)), t);
        t.rotate(-120);

        //and third part
        koch(iterations, (1.0 / Math.pow(3.0, iterations)), t);

        return image;
    }

    KochCurve(){
        super();

        settings.put("startX", new AlgorithmSetting<>("X Start Coordinate",200, 1000, 0, AlgorithmSetting.Type.SPINNER));
        settings.put("startY", new AlgorithmSetting<>("Y Start Coordinate", 250, 1000, 0, AlgorithmSetting.Type.SPINNER));
        settings.put("scaleFactor", new AlgorithmSetting<>("Scale Factor", 250d, 500d, 0.1d, AlgorithmSetting.Type.SLIDER));
        settings.put("iterations", new AlgorithmSetting<>("Number of Iterations", 10, 50, 1, AlgorithmSetting.Type.SLIDER));
    }

    //draw a koch CURVE
    private static void koch(int n, double step, Turtle t) {


        if (n == 0) {
            t.forward(step);
            return;
        }
        koch(n-1, step, t);
        t.rotate(60.0);
        koch(n-1, step, t);
        t.rotate(-120.0);
        koch(n-1, step, t);
        t.rotate(60.0);
        koch(n-1, step, t);
    }
}
