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
 * Algorithm for drawing a Dragon Curve<br>
 * <p>
 *  A static class for drawing the Dragon Curve with the given parameters from the GUI
 * </p>
 * @author Leonard Pudwitz
 * @version 1.0
 * <br>
 * @see program.system.Fractal
 */
public class DragonCurve extends Fractal {

    private static final int DEFAULT_ITER = 10;
    private static final int MIN_ITER = 1;
    private static final int MAX_ITER = 25;

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
    public static BufferedImage render(BufferedImage image, HashMap<String, AlgorithmSetting> settings, GraphicsSetting.Type mode, Color[] colors, double strokeWidth) {

        //initialize all values from the settings menu
        double scaleFactor = settings.get("scaleFactor").getValue().doubleValue();
        double x = settings.get("startX").getValue().intValue();
        double y = settings.get("startY").getValue().intValue();
        int iterations = settings.get("iterations").getValue().intValue();
        double rotation = settings.get("rotation").getValue().doubleValue();

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

        List<DragonDirections> turns = getSequence(iterations);

        //the drawing part, split into three different parts depending on the coloring mode
        switch (mode) {

            //solid color mode
            case SOLID:
                for (DragonDirections d : turns) {
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
                for (DragonDirections d: turns) {
                    if (d == DragonDirections.F)
                        steps++;
                }

                int counter = 1;
                //iterate over the direction list to draw
                for (DragonDirections d : turns) {
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
                for (DragonDirections d : turns) {
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

    public DragonCurve() {
        super();

        //initialize settings
        settings.put("scaleFactor", new AlgorithmSetting<>("Scale Factor", 6d, 100d, 0.1d, AlgorithmSetting.Type.SLIDER));
        settings.put("startX", new AlgorithmSetting<>("X Start Coordinate", 150, 1000, 0, AlgorithmSetting.Type.SPINNER));
        settings.put("startY", new AlgorithmSetting<>("Y Start Coordinate", 400, 1000, 0, AlgorithmSetting.Type.SPINNER));
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

    private enum DragonDirections {
        F,
        A,
        B,
        L,
        R
    }

    //define the generation rules
    private static final List<DragonDirections> caseA = new ArrayList<>(Arrays.asList(
            DragonDirections.A,
            DragonDirections.R,
            DragonDirections.B,
            DragonDirections.F,
            DragonDirections.R
    ));

    private static final List<DragonDirections> caseB = new ArrayList<>(Arrays.asList(
            DragonDirections.L,
            DragonDirections.F,
            DragonDirections.A,
            DragonDirections.L,
            DragonDirections.B
    ));

    //generate the turn sequence with the approach of a folded strip of paper
    private static List<DragonDirections> getSequence(int iterations) {

        //begin the turn sequence
        List<DragonDirections> turnSequence = new ArrayList<>();
        //add an initial value
        turnSequence.add(DragonDirections.F);
        turnSequence.add(DragonDirections.A);

        for (int i = 0; i < iterations; i++) {
            //copy the list so we don't modify the same object we're iterating over
            List<DragonDirections> copy = new ArrayList<>(turnSequence);

            //initialize the new list
            turnSequence = new ArrayList<>();

            //append new values depending on the cases
            for (DragonDirections d : copy) {
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
