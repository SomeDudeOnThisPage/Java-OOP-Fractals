package fractals;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

  public Main() {
    this.setSize(1000,1000);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.getContentPane().setLayout(new BorderLayout());

    FractalContainer fc1 = new FractalContainer(new Dimension(500,500));
    this.add(fc1, BorderLayout.CENTER);

    this.setVisible(true);
  }

  public static void main(String[] args) {
    try {
      // Default looks shit so ima use dis. 
      UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
    } catch (Exception e) {

    }

    new Main();
  }
}
