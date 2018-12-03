package fractals;


import java.awt.Graphics;
import java.util.*;

public class DragonCurve {

    public Graphics generate(Graphics g) {

        int iterations = 15;

        //generate the turn sequence
        List<Integer>turns = getSequence(iterations);

       //calculate the starting angle so that the thing will be horizontal
       double startingAngle = -iterations * (Math.PI / 4);

       //calculate the side length
       double side = 600 / Math.pow(2, iterations / 2.);


       //now to the drawing

        double currentAngle = startingAngle;

        //the starting coordinates
        int x1 = 200, y1 = 550;

        //do some trigonometry magic to find the next point
        int x2 = x1 + (int) (Math.cos(currentAngle) * side);
        int y2 = y1 + (int) (Math.sin(currentAngle) * side);

        //draw the first line by hand
        g.drawLine(x1, y1, x2, y2);

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
            g.drawLine(x1, y1, x2, y2);

            //put the second point into the first again and onto the next turn
            x1 = x2;
            y1 = y2;
        }

        return g;
    }

    //generate the turn sequence with the approach of a folded strip of paper
    private List<Integer> getSequence(int iterations) {
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

}
