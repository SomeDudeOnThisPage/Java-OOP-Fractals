package program.system;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.WritableImage;
import program.algorithm.TestAlg;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageLayer extends Canvas {
  public String name;

  public boolean visible;

  // Generate again
  // TODO: Make sure that we do not always redraw when setting visibility for instance
  public void redraw() {
    GraphicsContext g = getGraphicsContext2D();
    g.clearRect(0, 0, this.getWidth(), this.getHeight());

    BufferedImage bi = new BufferedImage((int)this.getWidth(), (int)this.getHeight(), BufferedImage.TYPE_INT_ARGB);

    Graphics2D g2 = bi.createGraphics();
    g2.setColor(Color.BLUE);
    //TestAlg alg = new TestAlg(g2);
    //g2 = alg.generate();
    g2.dispose();

    WritableImage im = SwingFXUtils.toFXImage(bi, null);
    g.drawImage(im,0,0);


  }

  public ImageLayer(String name, int x, int y) {
    super(x,y);
    this.name = name;
    this.visible = true;
  }
}
