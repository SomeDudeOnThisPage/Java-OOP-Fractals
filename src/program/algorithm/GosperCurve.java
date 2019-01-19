package program.algorithm;

import program.system.Fractal;
import program.system.Turtle;
import program.ui.elements.AlgorithmSetting;
import program.ui.elements.ColorSetting;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
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
        settings.put("rotation", new AlgorithmSetting<>("Rotation", 0d, 360d, 0d, AlgorithmSetting.Type.SPINNER));
    }

    //the four different action cases
    private enum GosperDirections {
        A,
        B,
        L,
        R
    }

    private static List<GosperDirections> getSequence(int iterations) {
        //initialize two preset lists
        List<GosperDirections> caseA = new ArrayList<>(Arrays.asList(
                GosperDirections.L,
                GosperDirections.B,
                GosperDirections.L,
                GosperDirections.L,
                GosperDirections.B,
                GosperDirections.R,
                GosperDirections.A,
                GosperDirections.R,
                GosperDirections.R,
                GosperDirections.A,
                GosperDirections.A,
                GosperDirections.R,
                GosperDirections.B,
                GosperDirections.L));

        List<GosperDirections> caseB = new ArrayList<>(Arrays.asList(
               GosperDirections.R,
               GosperDirections.A,
               GosperDirections.L,
               GosperDirections.B,
               GosperDirections.B,
               GosperDirections.L,
               GosperDirections.L,
               GosperDirections.B,
               GosperDirections.L,
               GosperDirections.A,
               GosperDirections.R,
               GosperDirections.R,
               GosperDirections.A,
               GosperDirections.R,
               GosperDirections.B
        ));


        //begin the turn sequence
        List<GosperDirections> turnSequence = new ArrayList<>();
        //add an initial value
        turnSequence.add(GosperDirections.A);

        for (int i = 0; i < iterations; i++) {
            //copy the list so we don't modify the same object we're iterating over
            List<GosperDirections> copy = new ArrayList<>(turnSequence);

            //initialize the new list
            turnSequence = new ArrayList<>();

            //append new values depending on the cases
            for (GosperDirections d : copy) {
                switch(d) {
                    case A:
                        turnSequence.addAll(caseA);
                        break;
                    case B:
                        turnSequence.addAll(caseB);
                        break;
                    default:
                        turnSequence.add(d);
                }
            }
        }

        return turnSequence;
    }
}
