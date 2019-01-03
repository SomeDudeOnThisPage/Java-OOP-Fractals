package program.algorithm;

import program.system.Fractal;
import program.ui.elements.AlgorithmSetting;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class DragonCurve extends Fractal {

    public static BufferedImage render(BufferedImage image, HashMap<String, AlgorithmSetting> settings) {

        //retrieve settings
        float scaleFactor = (float) settings.get("scaleFactor").getValue();
        int iterations = (int) settings.get("iterations").getValue();
        int startX = (int) settings.get("startX").getValue();
        int startY = (int) settings.get("startY").getValue();

        //generate a Graphics2D object from the given BufferedImage
        Graphics2D g = image.createGraphics();

        //color beep boop to be changed
        g.setColor(Color.RED);

        //turn anti aliasing off because it washes out the straight lines
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

        //generate the turn sequence
        List<Integer>turns = getSequence(iterations);

        //calculate the starting angle so that the thing will be horizontal
        double startingAngle = -iterations * (Math.PI / 4);

        //calculate the side length
        double side = (500 / Math.pow(2, iterations / 2.)) * scaleFactor;


        //now to the drawing

        double currentAngle = startingAngle;

        //the starting coordinates
        int x1 = startX, y1 = startY;

        //do some trigonometry magic to find the next point
        int x2 = x1 + (int) (Math.cos(currentAngle) * side);
        int y2 = y1 + (int) (Math.sin(currentAngle) * side);

        //draw the first line by hand
        g.draw(new Line2D.Double(x1, y1, x2, y2));

        //now onto the next point(s)
        x1 = x2;
        y1 = y2;

        //step through the calculated turn list
        for (Integer turn : turns) {
            //calculate the new angle by multiplying it with pi or -pi
            currentAngle += turn * (Math.PI / 2);

            //calculate x2, y2 like before
            x2 = x1 + (int) (Math.cos(currentAngle) * side);
            y2 = y1 + (int) (Math.sin(currentAngle) * side);

            //and draw the line
            g.draw(new Line2D.Double(x1, y1, x2, y2));

            //put the second point into the first again and onto the next turn
            x1 = x2;
            y1 = y2;
        }

        g.dispose();

        return image;
    }

    //generate the turn sequence with the approach of a folded strip of paper
    private static List<Integer> getSequence(int iterations) {
        //the sequence to return in the end
        List<Integer> turnSequence = new ArrayList<>();

        //for every iteration..
        for (int i = 0; i < iterations; i++) {
            //we copy the list
            List<Integer> copy = new ArrayList<>(turnSequence);

            //reverse it
            Collections.reverse(copy);

            //add one to the sequence (1 pi)
            turnSequence.add(1);

            //and add all complements of the copy to the end as well
            for (Integer turn : copy) {
                turnSequence.add(-turn);
            }
        }

        //finally we return the completed sequence
        return turnSequence;
    }

    public DragonCurve() {
        super();

        //initialize settings
        settings.put("scaleFactor", new AlgorithmSetting<Float>("Scale Factor", 1f, 100f, 0.1f, AlgorithmSetting.Type.SLIDER));
        settings.put("iterations", new AlgorithmSetting<Integer>("Number of Iterations" ,10, 50, 1, AlgorithmSetting.Type.SLIDER));
        settings.put("startX", new AlgorithmSetting<Integer>("X Start Coordinate", 150, 1000, 0, AlgorithmSetting.Type.SPINNER));
        settings.put("startY", new AlgorithmSetting<Integer>("Y Start Coordinate", 400, 1000, 0, AlgorithmSetting.Type.SPINNER));
    }
}
