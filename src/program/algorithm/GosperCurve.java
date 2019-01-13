package program.algorithm;

import program.system.Fractal;
import program.system.Turtle;
import program.ui.elements.AlgorithmSetting;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * Algorithm for drawing a Gosper Curve<br>
 * <p>
 *  A static class for drawing the Gosper Curve with the given parameters from the GUI
 * </p>
 * @author Leonard Pudwitz
 * @version 1.0
 * <br>
 * @see program.system.Fractal
 *
 * @see program.system.Turtle
 */
public class GosperCurve extends Fractal {

    static BufferedImage render(BufferedImage image, HashMap<String, AlgorithmSetting> settings) {

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

        List<GosperDirections> turns = getSequence(iterations);

        for (GosperDirections d : turns) {
            switch (d) {
                case A:
                case B:
                    t.forward(1 );
                    break;
                case R:
                    t.rotate(60);
                    break;
                case L:
                    t.rotate(-60);
                    break;
            }
        }

        return image;
    }

    GosperCurve() {
        super();

        //set bounds and default values for the menu options
        settings.put("startX", new AlgorithmSetting<>("X Start Coordinate",350, 1000, 0, AlgorithmSetting.Type.SPINNER));
        settings.put("startY", new AlgorithmSetting<>("Y Start Coordinate", 450, 1000, 0, AlgorithmSetting.Type.SPINNER));
        settings.put("scaleFactor", new AlgorithmSetting<>("Scale Factor", 2d, 100d, 0.1d, AlgorithmSetting.Type.SLIDER));
        settings.put("iterations", new AlgorithmSetting<>("Number of Iterations", 4, 50, 1, AlgorithmSetting.Type.SLIDER));
    }

    private enum GosperDirections {
        A,
        B,
        L,
        R
    }

    private static List<GosperDirections> getSequence(int iterations) {
        //initialize two preset lists
        List<GosperDirections> caseA = new ArrayList<>(), caseB = new ArrayList<>();

        //add values to caseA
        caseA.add(GosperDirections.A);
        caseA.add(GosperDirections.L);
        caseA.add(GosperDirections.B);
        caseA.add(GosperDirections.L);
        caseA.add(GosperDirections.L);
        caseA.add(GosperDirections.B);
        caseA.add(GosperDirections.R);
        caseA.add(GosperDirections.A);
        caseA.add(GosperDirections.R);
        caseA.add(GosperDirections.R);
        caseA.add(GosperDirections.A);
        caseA.add(GosperDirections.A);
        caseA.add(GosperDirections.R);
        caseA.add(GosperDirections.B);
        caseA.add(GosperDirections.L);

        //add values to caseB
        caseB.add(GosperDirections.R);
        caseB.add(GosperDirections.A);
        caseB.add(GosperDirections.L);
        caseB.add(GosperDirections.B);
        caseB.add(GosperDirections.B);
        caseB.add(GosperDirections.L);
        caseB.add(GosperDirections.L);
        caseB.add(GosperDirections.B);
        caseB.add(GosperDirections.L);
        caseB.add(GosperDirections.A);
        caseB.add(GosperDirections.R);
        caseB.add(GosperDirections.R);
        caseB.add(GosperDirections.A);
        caseB.add(GosperDirections.R);
        caseB.add(GosperDirections.B);

        List<GosperDirections> turnSequence = new ArrayList<>();
        turnSequence.add(GosperDirections.A);

        for (int i = 0; i < iterations; i++) {
            List<GosperDirections> copy = new ArrayList<>(turnSequence);

            turnSequence = new ArrayList<>();
            for (GosperDirections d : copy) {
                if (d == GosperDirections.A) {
                    turnSequence.addAll(caseA);
                } else if (d == GosperDirections.B) {
                    turnSequence.addAll(caseB);
                } else {
                    turnSequence.add(d);
                }
            }
        }

        return turnSequence;
    }
}
