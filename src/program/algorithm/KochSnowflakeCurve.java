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
 * Algorithm for drawing a Koch Snowflake<br>
 * <p>
 *  A static class for drawing the Koch Snowflake with the given parameters from the GUI
 * </p>
 * @author Leonard Pudwitz
 * @version 1.0
 * <br>
 * @see program.system.Fractal
 *
 * @see program.system.Turtle
 */
class KochSnowflakeCurve extends Fractal {

    static BufferedImage render(BufferedImage image, HashMap<String, AlgorithmSetting> settings, GraphicsSetting.Type mode, Color[] colors, double strokeWidth) {

        //initialize all values from the settings menu
        double scaleFactor = (double) settings.get("scaleFactor").getValue();
        double x = (int) settings.get("startX").getValue();
        double y = (int) settings.get("startY").getValue();
        int iterations = (int) settings.get("iterations").getValue();
        double rotation = (double) settings.get("rotation").getValue();
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

        List<KochDirections> turns = getSequence(iterations);

        //for this algorithm the drawing procedure is different because the sequence only describes a third of the snowflake - one side
        for (int i = 0; i < 3; i++) {

            //the drawing part, split into three different parts depending on the coloring mode
            switch (mode) {

                //solid color mode
                case SOLID:
                    for (KochDirections d : turns) {
                        switch (d) {
                            case F:
                                t.forward(1);
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
                    for (KochDirections d : turns) {
                        if (d == KochDirections.F)
                            steps++;
                    }

                    int counter = 0;
                    //iterate over the direction list to draw
                    for (KochDirections d : turns) {
                        switch (d) {
                            case F:

                                //do a linear interpolation between the two colors
                                float red, green, blue, alpha;
                                red = colors[0].getRed() * ((float) counter / steps) + colors[1].getRed() * (1 - ((float) counter / steps));
                                green = colors[0].getGreen() * ((float) counter / steps) + colors[1].getGreen() * (1 - ((float) counter / steps));
                                blue = colors[0].getBlue() * ((float) counter / steps) + colors[1].getBlue() * (1 - ((float) counter / steps));
                                alpha = colors[0].getAlpha() * ((float) counter / steps) + colors[1].getAlpha() * (1 - ((float) counter / steps));

                                //System.out.println(counter + "/" + steps + ": " + red + " " + green + " " + blue);

                                g.setColor(new Color(red / 255, green / 255, blue / 255, alpha / 255));
                                t.forward(1);
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
                    for (KochDirections d : turns) {
                        switch (d) {
                            case F:
                                g.setColor(flag ? colors[0] : colors[1]);
                                flag = !flag;
                                t.forward(1);
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
            t.rotate(-120);
        }
        g.dispose();

        return image;
    }

    KochSnowflakeCurve(){
        super();

        settings.put("startX", new AlgorithmSetting<>("X Start Coordinate",200, 1000, 0, AlgorithmSetting.Type.SPINNER));
        settings.put("startY", new AlgorithmSetting<>("Y Start Coordinate", 250, 1000, 0, AlgorithmSetting.Type.SPINNER));
        settings.put("scaleFactor", new AlgorithmSetting<>("Scale Factor", 10d, 500d, 0.1d, AlgorithmSetting.Type.SLIDER));
        settings.put("iterations", new AlgorithmSetting<>("Number of Iterations", 8, 50, 1, AlgorithmSetting.Type.SLIDER));
        settings.put("rotation", new AlgorithmSetting<>("Rotation", 0d, 360d, 0d, AlgorithmSetting.Type.SPINNER));
    }
    private enum KochDirections {
        L,
        R,
        F
    }

    //define the generation rules
    static final java.util.List<KochDirections> caseF = new ArrayList<>(Arrays.asList(
            KochDirections.F,
            KochDirections.R,
            KochDirections.F,
            KochDirections.L,
            KochDirections.L,
            KochDirections.F,
            KochDirections.R,
            KochDirections.F
    ));



    private static java.util.List<KochDirections> getSequence(int iterations) {

        //begin the turn sequence
        java.util.List<KochDirections> turnSequence = new ArrayList<>();

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
