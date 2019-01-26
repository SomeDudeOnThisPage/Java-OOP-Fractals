package program.algorithm;

import program.system.Fractal;
import program.system.Turtle;
import program.ui.elements.AlgorithmSetting;
import program.ui.elements.GraphicsSetting;

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

    static BufferedImage render(BufferedImage image, HashMap<String, AlgorithmSetting> settings, GraphicsSetting.Type mode, Color[] colors, double strokeWidth) {

        //initialize all values from the settings menu
        double scaleFactor = (double) settings.get("scaleFactor").getValue();
        double x = (int) settings.get("startX").getValue();
        double y = (int) settings.get("startY").getValue();
        int iterations = (int) settings.get("iterations").getValue();
        double rotation = (double) settings.get("rotation").getValue();
        //declare a turning angle for the turtle
        final int ANGLE = 90;

        //create the graphics2d object from the buffered image
        Graphics2D g = image.createGraphics();

        //set stroke width
        g.setStroke(new BasicStroke((float) strokeWidth));

        //no matter what coloring mode we use, we always start with the first color
        g.setColor(colors[0]);

        //initialize a turtle for drawing the curve
        Turtle t = new Turtle(x, y, scaleFactor, g);

        //turn the turtle by the rotation amount specified by the user
        t.rotate(rotation);

        List<PeanoDirections> turns = getSequence(iterations);

        //the drawing part, split into three different parts depending on the coloring mode
        switch (mode) {

            //solid color mode
            case SOLID:
                for (PeanoDirections d : turns) {
                    switch (d) {
                        case F:
                            t.forward(1 );
                            break;
                        case R:
                            t.rotate(ANGLE);
                            break;
                        case L:
                            t.rotate(-ANGLE);
                            break;
                        default:
                            break;
                    }
                }
                break;

            //gradient mode
            case GRADIENT_LINEAR:

                //calculate how many interpolations we have to do
                int steps = 0;
                for (PeanoDirections d: turns) {
                    if (d == PeanoDirections.F)
                        steps++;
                }

                int counter = 1;
                //iterate over the direction list to draw
                for (PeanoDirections d : turns) {
                    switch (d) {
                        case F:
                            //do a linear interpolation between the two colors
                            float red, green, blue, alpha;
                            red = colors[0].getRed() * ((float) counter / steps) + colors[1].getRed() * (1 - ((float) counter / steps));
                            green = colors[0].getGreen() * ((float) counter / steps) + colors[1].getGreen() * (1 - ((float) counter / steps));
                            blue = colors[0].getBlue() * ((float) counter / steps) + colors[1].getBlue() * (1 - ((float) counter / steps));

                            g.setColor(new Color(red / 255, green / 255, blue / 255));
                            t.forward(1 );
                            counter++;
                            break;
                        case R:
                            t.rotate(ANGLE);
                            break;
                        case L:
                            t.rotate(-ANGLE);
                            break;
                        default:
                            break;
                    }
                }
                break;

            //alternating mode
            case ALTERNATING:
                boolean flag = false;
                for (PeanoDirections d : turns) {
                    switch (d) {
                        case F:
                            g.setColor(flag ? colors[0] : colors[1]);
                            flag = !flag;
                            t.forward(1 );
                            break;
                        case R:
                            t.rotate(ANGLE);
                            break;
                        case L:
                            t.rotate(-ANGLE);
                            break;
                        default:
                            break;
                    }
                }
                break;
        }

        g.dispose();

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
    //define the generation rules
    private static final List<PeanoDirections> caseA = new ArrayList<>(Arrays.asList(
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

    private static final List<PeanoDirections> caseB = new ArrayList<>(Arrays.asList(
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

    private static List<PeanoDirections> getSequence(int iterations) {

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
