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
 * Algorithm for drawing a Koch Island<br>
 * <p>
 *  A static class for drawing the Koch Island with the given parameters from the GUI
 * </p>
 * @author Leonard Pudwitz
 * @version 1.0
 * <br>
 * @see program.system.Fractal
 *
 * @see program.system.Turtle
 */

public class KochIslandCurve extends Fractal {

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
        g.setColor(Color.pink);

        //initialize a turtle for drawing the curve
        Turtle t = new Turtle(x, y, scaleFactor, g);

        //turn the turtle by the rotation amount specified by the user
        t.rotate(rotation);

        List<KochDirections> turns = getSequence(iterations);

        for (KochDirections d : turns) {
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

    KochIslandCurve() {
        super();

        //set bounds and default values for the menu options
        settings.put("startX", new AlgorithmSetting<>("X Start Coordinate",200, 1000, 0, AlgorithmSetting.Type.SPINNER));
        settings.put("startY", new AlgorithmSetting<>("Y Start Coordinate", 200, 1000, 0, AlgorithmSetting.Type.SPINNER));
        settings.put("scaleFactor", new AlgorithmSetting<>("Scale Factor", 4d, 100d, 0.1d, AlgorithmSetting.Type.SLIDER));
        settings.put("iterations", new AlgorithmSetting<>("Number of Iterations", 4, 50, 1, AlgorithmSetting.Type.SLIDER));
        settings.put("rotation", new AlgorithmSetting<>("Rotation", 0d, 360d, 0d, AlgorithmSetting.Type.SPINNER));
    }

    //the different cases for the turtle
    private enum KochDirections {
        F,
        L,
        R
    }

    private static List<KochDirections> getSequence(int iterations) {
        //initialize the preset list according to the L system grammar representation

        List<KochDirections> caseF = new ArrayList<>(Arrays.asList(
                KochDirections.F,
                KochDirections.L,
                KochDirections.F,
                KochDirections.R,
                KochDirections.F,
                KochDirections.R,
                KochDirections.F,
                KochDirections.F,
                KochDirections.F,
                KochDirections.L,
                KochDirections.F,
                KochDirections.L,
                KochDirections.F,
                KochDirections.R,
                KochDirections.F
        ));

        //begin the turn sequence
        List<KochDirections> turnSequence = new ArrayList<>();

        //add an initial value
        turnSequence.add(KochDirections.F);

        for (int i = 0; i < iterations; i++) {
            //copy the list so we don't modify the same object we're iterating over
            List<KochDirections> copy = new ArrayList<>(turnSequence);

            //initialize the new list
            turnSequence = new ArrayList<>();

            //append new values depending on the cases
            for (KochDirections d : copy) {
                switch(d) {
                    case F:
                        turnSequence.addAll(caseF);
                        break;
                    default:
                        turnSequence.add(d);
                }
            }
        }

        return turnSequence;

    }
}
