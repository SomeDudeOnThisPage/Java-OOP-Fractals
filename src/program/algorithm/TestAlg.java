package program.algorithm;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class TestAlg {
  public static GraphicsContext generate(GraphicsContext g, int offx, int offy) {
    g.setFill(Color.GREEN);
    g.fillOval(offx,offy,20,20);
    return g;
  }
}
