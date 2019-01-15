package program.algorithm;

import program.system.Fractal;
import program.system.Turtle;
import program.ui.elements.AlgorithmSetting;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Algorithm for drawing a Sierpinski Square<br>
 * <p>
 *  A static class for drawing the Sierpinski Square with the given parameters from the GUI
 * </p>
 * @author Leonard Pudwitz
 * @version 1.0
 * <br>
 * @see program.system.Fractal
 *
 * @see program.system.Turtle
 */

public class SierpinskiSquareCurve extends Fractal {

    static BufferedImage render(BufferedImage image, HashMap<String, AlgorithmSetting> settings) {

        //initialize all values from the settings menu
        double scaleFactor = (double) settings.get("scaleFactor").getValue();
        double x = (int) settings.get("startX").getValue();
        double y = (int) settings.get("startY").getValue();
        int iterations = (int) settings.get("iterations").getValue();
        double rotation = (double) settings.get("rotation").getValue();

        //create the graphics2d object from the buffered image
        Graphics2D g = image.createGraphics();

        //set a fixed color for now
        g.setColor(Color.black);

        //initialize a turtle for drawing the curve
        Turtle t = new Turtle(x, y, scaleFactor, g);

        //turn the turtle by the rotation amount specified by the user
        t.rotate(rotation);

        List<SierpinskiDirections> turns = getSequence(iterations);

        for (SierpinskiDirections d : turns) {
            switch (d) {
                case F:
                    t.forward(1 );
                    break;
                case R:
                    t.rotate(45);
                    break;
                case L:
                    t.rotate(-45);
                    break;
                default:
                    break;
            }
        }
        return image;
    }

    SierpinskiSquareCurve() {
        super();

        //set bounds and default values for the menu options
        settings.put("startX", new AlgorithmSetting<>("X Start Coordinate",200, 1000, 0, AlgorithmSetting.Type.SPINNER));
        settings.put("startY", new AlgorithmSetting<>("Y Start Coordinate", 200, 1000, 0, AlgorithmSetting.Type.SPINNER));
        settings.put("scaleFactor", new AlgorithmSetting<>("Scale Factor", 10d, 100d, 0.1d, AlgorithmSetting.Type.SLIDER));
        settings.put("iterations", new AlgorithmSetting<>("Number of Iterations", 4, 50, 1, AlgorithmSetting.Type.SLIDER));
        settings.put("rotation", new AlgorithmSetting<>("Rotation", 0d, 360d, 0d, AlgorithmSetting.Type.SPINNER));
    }

    //the different cases for the turtle
    private enum SierpinskiDirections {
        F,
        A,
        L,
        R
    }

    private static List<SierpinskiDirections> getSequence(int iterations) {
        //initialize the preset list according to the L system grammar representation

        List<SierpinskiDirections> caseA = new ArrayList<>(Arrays.asList(
                SierpinskiDirections.A,
                SierpinskiDirections.F,
                SierpinskiDirections.L,
                SierpinskiDirections.F,
                SierpinskiDirections.F,
                SierpinskiDirections.L,
                SierpinskiDirections.A,
                SierpinskiDirections.F,
                SierpinskiDirections.R,
                SierpinskiDirections.R,
                SierpinskiDirections.F,
                SierpinskiDirections.R,
                SierpinskiDirections.R,
                SierpinskiDirections.A,
                SierpinskiDirections.F,
                SierpinskiDirections.L,
                SierpinskiDirections.F,
                SierpinskiDirections.F,
                SierpinskiDirections.L,
                SierpinskiDirections.A
        ));

        //begin the turn sequence with some initial values
        List<SierpinskiDirections> turnSequence = new ArrayList<>(Arrays.asList(
                SierpinskiDirections.F,
                SierpinskiDirections.R,
                SierpinskiDirections.R,
                SierpinskiDirections.A,
                SierpinskiDirections.F,
                SierpinskiDirections.R,
                SierpinskiDirections.R,
                SierpinskiDirections.F,
                SierpinskiDirections.R,
                SierpinskiDirections.R,
                SierpinskiDirections.A,
                SierpinskiDirections.F
        ));

        for (int i = 0; i < iterations; i++) {
            //copy the list so we don't modify the same object we're iterating over
            List<SierpinskiDirections> copy = new ArrayList<>(turnSequence);

            //initialize the new list
            turnSequence = new ArrayList<>();

            //append new values depending on the cases
            for (SierpinskiDirections d : copy) {
                switch(d) {
                    case A:
                        turnSequence.addAll(caseA);
                        break;
                    default:
                        turnSequence.add(d);
                }
            }
        }

        return turnSequence;

    }
}
