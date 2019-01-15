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
 * Algorithm for drawing a Peano Curve<br>
 * <p>
 *  A static class for drawing the Peano Curve with the given parameters from the GUI
 * </p>
 * @author Leonard Pudwitz
 * @version 1.0
 * <br>
 * @see program.system.Fractal
 *
 * @see program.system.Turtle
 */

public class PeanoCurve extends Fractal {

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
        g.setColor(Color.MAGENTA);

        //initialize a turtle for drawing the curve
        Turtle t = new Turtle(x, y, scaleFactor, g);

        //turn the turtle by the rotation amount specified by the user
        t.rotate(rotation);

        List<PeanoDirections> turns = getSequence(iterations);

        for (PeanoDirections d : turns) {
            switch (d) {
                case F:
                    t.forward(1 );
                    break;
                case R:
                    t.rotate(90);
                    break;
                case L:
                    t.rotate(-90);
                    break;
                default:
                    break;
            }
        }
        return image;
    }

    PeanoCurve() {
        super();

        //set bounds and default values for the menu options
        settings.put("startX", new AlgorithmSetting<>("X Start Coordinate",200, 1000, 0, AlgorithmSetting.Type.SPINNER));
        settings.put("startY", new AlgorithmSetting<>("Y Start Coordinate", 200, 1000, 0, AlgorithmSetting.Type.SPINNER));
        settings.put("scaleFactor", new AlgorithmSetting<>("Scale Factor", 4d, 100d, 0.1d, AlgorithmSetting.Type.SLIDER));
        settings.put("iterations", new AlgorithmSetting<>("Number of Iterations", 4, 50, 1, AlgorithmSetting.Type.SLIDER));
        settings.put("rotation", new AlgorithmSetting<>("Rotation", 0d, 360d, 0d, AlgorithmSetting.Type.SPINNER));
    }

    //the different cases for the turtle
    private enum PeanoDirections {
        A,
        B,
        F,
        L,
        R
    }

    private static List<PeanoDirections> getSequence(int iterations) {
        //initialize the preset list according to the L system grammar representation

        List<PeanoDirections> caseA = new ArrayList<>(Arrays.asList(
                PeanoDirections.A,
                PeanoDirections.F,
                PeanoDirections.B,
                PeanoDirections.F,
                PeanoDirections.A,
                PeanoDirections.R,
                PeanoDirections.F,
                PeanoDirections.R,
                PeanoDirections.B,
                PeanoDirections.F,
                PeanoDirections.A,
                PeanoDirections.F,
                PeanoDirections.B,
                PeanoDirections.L,
                PeanoDirections.F,
                PeanoDirections.L,
                PeanoDirections.A,
                PeanoDirections.F,
                PeanoDirections.B,
                PeanoDirections.F,
                PeanoDirections.A));

        List<PeanoDirections> caseB = new ArrayList<>(Arrays.asList(
                PeanoDirections.B,
                PeanoDirections.F,
                PeanoDirections.A,
                PeanoDirections.F,
                PeanoDirections.B,
                PeanoDirections.L,
                PeanoDirections.F,
                PeanoDirections.L,
                PeanoDirections.A,
                PeanoDirections.F,
                PeanoDirections.B,
                PeanoDirections.F,
                PeanoDirections.A,
                PeanoDirections.R,
                PeanoDirections.F,
                PeanoDirections.R,
                PeanoDirections.B,
                PeanoDirections.F,
                PeanoDirections.A,
                PeanoDirections.F,
                PeanoDirections.B));

        //begin the turn sequence
        List<PeanoDirections> turnSequence = new ArrayList<>();
        //add an initial value
        turnSequence.add(PeanoDirections.A);

        for (int i = 0; i < iterations; i++) {
            //copy the list so we don't modify the same object we're iterating over
            List<PeanoDirections> copy = new ArrayList<>(turnSequence);

            //initialize the new list
            turnSequence = new ArrayList<>();

            //append new values depending on the cases
            for (PeanoDirections d : copy) {
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
