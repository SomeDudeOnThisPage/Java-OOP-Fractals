package program.algorithm;

import program.Program;
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

    private static final int DEFAULT_ITER = 3;
    private static final int MIN_ITER = 1;
    private static final int MAX_ITER = 8;

    /**
     * Main method for drawing the curve
     * @param image The image object to be drawn on
     * @param settings A list of specified settings
     * @param mode The coloring mode
     * @param colors An array of 1 or 2 colors
     * @param strokeWidth The stroke width for the drawing
     *
     * @see GraphicsSetting.Type
     */
    static long render(BufferedImage image, HashMap<String, AlgorithmSetting> settings, GraphicsSetting.Type mode, Color[] colors, double strokeWidth) {

        //initialize all values from the settings menu
        double scaleFactor = settings.get("scaleFactor").getValue().doubleValue();
        double x = settings.get("startX").getValue().intValue();
        double y = settings.get("startY").getValue().intValue();
        int iterations = settings.get("iterations").getValue().intValue();
        double rotation = settings.get("rotation").getValue().doubleValue();

        //declare a turning angle for the turtle
        final int ANGLE = 60;

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

        List<GosperDirections> turns = getSequence(iterations);

        //the drawing part, split into three different parts depending on the coloring mode
        switch (mode) {

            //solid color mode
            case SOLID:
                for (GosperDirections d : turns) {
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
                for (GosperDirections d: turns) {
                    if (d == GosperDirections.F)
                        steps++;
                }

                int counter = 1;
                //iterate over the direction list to draw
                for (GosperDirections d : turns) {
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
                for (GosperDirections d : turns) {
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

        return turns.size();
    }

    GosperCurve() {
        super();

        //set bounds and default values for the menu options
        settings.put("startX", new AlgorithmSetting<>("X Start Coordinate",350, 1000, 0, AlgorithmSetting.Type.SPINNER));
        settings.put("startY", new AlgorithmSetting<>("Y Start Coordinate", 450, 1000, 0, AlgorithmSetting.Type.SPINNER));
        settings.put("scaleFactor", new AlgorithmSetting<>("Scale Factor", 8d, 100d, 0.1d, AlgorithmSetting.Type.SLIDER));
        settings.put("rotation", new AlgorithmSetting<>("Rotation", 0d, 360d, 0d, AlgorithmSetting.Type.SPINNER));

        if (Program.IGNORE_LIMITS)
        {
            settings.put("iterations", new AlgorithmSetting<>("Number of Iterations", DEFAULT_ITER, 50, MIN_ITER, AlgorithmSetting.Type.SLIDER));
        }
        else
        {
            settings.put("iterations", new AlgorithmSetting<>("Number of Iterations", DEFAULT_ITER, MAX_ITER, MIN_ITER, AlgorithmSetting.Type.SLIDER));
        }
    }

    //the four different action cases
    private enum GosperDirections {
        F,
        A,
        B,
        L,
        R
    }

    //define the generation rules
    private static final List<GosperDirections> caseA = new ArrayList<>(Arrays.asList(
            GosperDirections.A,
            GosperDirections.R,
            GosperDirections.B,
            GosperDirections.F,
            GosperDirections.R,
            GosperDirections.R,
            GosperDirections.B,
            GosperDirections.F,
            GosperDirections.L,
            GosperDirections.F,
            GosperDirections.A,
            GosperDirections.L,
            GosperDirections.L,
            GosperDirections.F,
            GosperDirections.A,
            GosperDirections.F,
            GosperDirections.A,
            GosperDirections.L,
            GosperDirections.B,
            GosperDirections.F,
            GosperDirections.R
    ));

    private static final List<GosperDirections> caseB = new ArrayList<>(Arrays.asList(
            GosperDirections.L,
            GosperDirections.F,
            GosperDirections.A,
            GosperDirections.R,
            GosperDirections.B,
            GosperDirections.F,
            GosperDirections.B,
            GosperDirections.F,
            GosperDirections.R,
            GosperDirections.R,
            GosperDirections.B,
            GosperDirections.F,
            GosperDirections.R,
            GosperDirections.F,
            GosperDirections.A,
            GosperDirections.L,
            GosperDirections.L,
            GosperDirections.F,
            GosperDirections.A,
            GosperDirections.L,
            GosperDirections.B
    ));

    /**
     * Compute the turn sequence of a curve for the turtle to draw
     * @param iterations The number of iterations it should run
     * @return A sequence of enum values that can be used to move the turtle
     */
    private static List<GosperDirections> getSequence(int iterations) {
        //begin the turn sequence
        List<GosperDirections> turnSequence = new ArrayList<>();
        //add an initial value
        turnSequence.add(GosperDirections.F);
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
