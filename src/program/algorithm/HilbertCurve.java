//taken from https://www.cs.cmu.edu/~adamchik/15-121/lectures/Recursions/demo/Hilbert.java
package program.algorithm;

import program.system.AlgorithmSetting;
import program.system.Curve;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class HilbertCurve extends Curve {
        private static double orientation;
        private static double x = 0.0, y = 0.0;
        private static float scaleFactor = 1f;
        private static Graphics2D g;


        public static BufferedImage render(BufferedImage image, HashMap<String, AlgorithmSetting> settings) {
            scaleFactor = (float) settings.get("scaleFactor").getValue();
            x = (int) settings.get("startX").getValue();
            y = (int) settings.get("startY").getValue();
            int iterations = (int) settings.get("iterations").getValue();

            g = image.createGraphics();
            g.setColor(Color.ORANGE);

            hilbert0(iterations);

            g.dispose();

            return image;
        }

        public HilbertCurve() {
            super();

            settings.put("startX", new AlgorithmSetting<Integer>(200, 1000, 0));
            settings.put("startY", new AlgorithmSetting<Integer>(200, 1000, 0));
            settings.put("scaleFactor", new AlgorithmSetting<Float>(50f, 100f, 0.1f));
            settings.put("iterations", new AlgorithmSetting<Integer>(10, 50, 1));
        }


        private static void rotate(double angle) {
        orientation += angle;
        }

        // move the turtle forward (includes drawing)
        private static void forward(double d) {
        double x0 = x, y0 = y;
        x += d * Math.cos(Math.toRadians(orientation)) * scaleFactor;
        y += d * Math.sin(Math.toRadians(orientation)) * scaleFactor;


        g.draw(new Line2D.Double(x0,  y0,  x,  y));
        }

        // Hilbert curve
        private static void hilbert0(int n) {
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
        private static void hilbert1(int n) {
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
    }
