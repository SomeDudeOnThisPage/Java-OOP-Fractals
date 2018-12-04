package fractals.curves;

import java.awt.*;

public abstract class DrawableCurve {
    public abstract Graphics generate(Graphics g, float scaleFactor, int startX, int startY, int iterations);
}
