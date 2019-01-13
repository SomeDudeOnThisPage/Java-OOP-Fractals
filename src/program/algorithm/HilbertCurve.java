//taken from https://www.cs.cmu.edu/~adamchik/15-121/lectures/Recursions/demo/Hilbert.java
package program.algorithm;


import program.system.Fractal;
import program.ui.elements.AlgorithmSetting;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class HilbertCurve extends Fractal {

    public static BufferedImage render(BufferedImage image, HashMap<String, AlgorithmSetting> settings) {

        //initialize all values from the settings menu
        double scaleFactor = (double) settings.get("scaleFactor").getValue();
        double x = (int) settings.get("startX").getValue();
        double y = (int) settings.get("startY").getValue();
        int iterations = (int) settings.get("iterations").getValue();

        //create the graphics2d object from the buffered image
        Graphics2D g = image.createGraphics();

        //set a fixed color for now
        g.setColor(Color.ORANGE);

        //initialize a turtle for drawing the curve
        Turtle t = new Turtle(x, y, scaleFactor, g);

        //start the recursive function
        hilbert0(iterations, t);

        //dispose the graphics object
        g.dispose();

        return image;
    }

    public HilbertCurve() {
        super();

        //set bounds and default values for the menu options
        settings.put("startX", new AlgorithmSetting<Integer>("X Start Coordinate",200, 1000, 0, AlgorithmSetting.Type.SPINNER));
        settings.put("startY", new AlgorithmSetting<Integer>("Y Start Coordinate", 200, 1000, 0, AlgorithmSetting.Type.SPINNER));
        settings.put("scaleFactor", new AlgorithmSetting<Double>("Scale Factor", 50d, 100d, 0.1d, AlgorithmSetting.Type.SLIDER));
        settings.put("iterations", new AlgorithmSetting<Integer>("Number of Iterations", 10, 50, 1, AlgorithmSetting.Type.SLIDER));
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