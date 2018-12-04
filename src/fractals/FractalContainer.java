package fractals;

import fractals.curves.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class FractalContainer extends JPanel implements Runnable {

  //have a dynamic list of fractal curves to generate from
  private List<DrawableCurve> curves;

  // TODO: Create a Fractal object in this class, and call its' "paint" or "generate" (Name TBD) method
  //       Pass a Graphics-Object to this method to alter
  //       Redraw this panel afterwards (use Swingworker to not explode everything??) with the returned Graphics-object
  //        Create UI Elements for scale, x/y position, iteration count

  // Implementation needs overhaul.
  // ur mom needs overhaul lol
  @Override
  public void paint(Graphics g) {
    super.paint(g); // Make sure to draw super

    //iterate over the curve list, generate every one for now
    for (DrawableCurve c : curves) {
      c.generate(g, 0.5f, 50, 350, 5);
    }
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

    //initialize the fractal list
    curves = new ArrayList<>();

    //add a dragon curve for example
    //this.curves.add(new DragonCurve());
    this.curves.add(new DragonCurve());

    this.repaint();

    Thread t = new Thread(this);
    t.start();
  }

}
