package program.system;

import program.algorithm.TestAlg;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class ImageLayer extends Canvas {
  public String name;

  public boolean visible;

  // Generate again
  public void redraw() {
    System.out.println("hi");
    GraphicsContext g = this.getGraphicsContext2D();
    g = TestAlg.generate(g,100,100);
  }

  public ImageLayer(String name, int x, int y) {
    super(x,y);
    this.name = name;
    this.visible = false;
  }
}
