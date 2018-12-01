package fractals;

import javax.swing.*;
import java.awt.*;

public class FractalContainer extends JPanel implements Runnable {

  private Fractal fractal;

  // TODO: Create a Fractal object in this class, and call its' "paint" or "generate" (Name TBD) method
  //       Pass a Graphics-Object to this method to alter
  //       Redraw this panel afterwards (use Swingworker to not explode everything??) with the returned Graphics-object

  // Implementation needs overhaul.
  @Override
  public void paint(Graphics g) {
    super.paint(g); // Make sure to draw super
    // Multiple fractals per container maybe?
    this.fractal.generate(g);
  }

  /*
   *  Name: void initGUIProperties <Dimension>
   *  Desc: Method to set the default desired GUI Properties of this GUI object
   */
  private void initGUIProperties(Dimension size) {
    this.setSize(size);
    this.setPreferredSize(size);
    this.setBackground(Color.WHITE);
    this.setLayout(new BorderLayout());
  }

  public void run() {
    // Testing...
    JButton button = new JButton("Helo am test buton bls preß :)");
    button.setPreferredSize(new Dimension(100,20));
    this.add(button, BorderLayout.SOUTH);
  }

  protected FractalContainer(Dimension size) {
    initGUIProperties(size);

    this.fractal = new Fractal();
    this.repaint();

    Thread t = new Thread(this);
    t.start();
  }

}
