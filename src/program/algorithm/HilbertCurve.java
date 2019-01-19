//taken from https://www.cs.cmu.edu/~adamchik/15-121/lectures/Recursions/demo/Hilbert.java
package program.algorithm;


import program.system.Fractal;
import program.system.Turtle;
import program.ui.elements.AlgorithmSetting;
import program.ui.elements.ColorSetting;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
/**
 * Algorithm for drawing a Hilbert Curve<br>
 * <p>
 *  A static class for drawing the Hilbert Curve with the given parameters from the GUI
 * </p>
 * @author Leonard Pudwitz
 * @version 1.0
 * <br>
 * @see program.system.Fractal
 *
 * @see program.system.Turtle
 */
class HilbertCurve extends Fractal {

    static BufferedImage render(BufferedImage image, HashMap<String, AlgorithmSetting> settings, ColorSetting.Type mode, Color[] colors) {

        //initialize all values from the settings menu
        double scaleFactor = (double) settings.get("scaleFactor").getValue();
        double x = (int) settings.get("startX").getValue();
        double y = (int) settings.get("startY").getValue();
        int iterations = (int) settings.get("iterations").getValue();
        double rotation = (double) settings.get("rotation").getValue();

        //create the graphics2d object from the buffered image
        Graphics2D g = image.createGraphics();

        //set a fixed color for now
        g.setColor(Color.ORANGE);

        //initialize a turtle for drawing the curve
        Turtle t = new Turtle(x, y, scaleFactor, g);

        //turn the turtle by the rotation amount specified by the user
        t.rotate(rotation);

        //start the recursive function
        hilbert0(iterations, t);

        //dispose the graphics object
        g.dispose();

        return image;
    }

    HilbertCurve() {
        super();

        //set bounds and default values for the menu options
        settings.put("startX", new AlgorithmSetting<>("X Start Coordinate",200, 1000, 0, AlgorithmSetting.Type.SPINNER));
        settings.put("startY", new AlgorithmSetting<>("Y Start Coordinate", 200, 1000, 0, AlgorithmSetting.Type.SPINNER));
        settings.put("scaleFactor", new AlgorithmSetting<>("Scale Factor", 50d, 100d, 0.1d, AlgorithmSetting.Type.SLIDER));
        settings.put("iterations", new AlgorithmSetting<>("Number of Iterations", 10, 50, 1, AlgorithmSetting.Type.SLIDER));
        settings.put("rotation", new AlgorithmSetting<>("Rotation", 0d, 360d, 0d, AlgorithmSetting.Type.SPINNER));
    }

    // Hilbert curve
    private static void hilbert0(int n, Turtle t) {
        if (n == 0) return;
        t.rotate(90);
        hilbert1(n-1, t);
        t.forward(1.0);
        t.rotate(-90);
        hilbert0(n-1, t);
        t.forward(1.0);
        hilbert0(n-1, t);
        t.rotate(-90);
        t.forward(1.0);
        hilbert1(n-1, t);
        t.rotate(90);
    }

    // evruc trebliH
    private static void hilbert1(int n, Turtle t) {
        if (n == 0) return;
        t.rotate(-90);
        hilbert0(n-1, t);
        t.forward(1.0);
        t.rotate(90);
        hilbert1(n-1, t);
        t.forward(1.0);
        hilbert1(n-1, t);
        t.rotate(90);
        t.forward(1.0);
        hilbert0(n-1, t);
        t.rotate(-90);
    }
}