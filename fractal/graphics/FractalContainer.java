package fractal.graphics;

import fractal.Fractal;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComponent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

// This class is mainly the frame for the fractal canvas
public class FractalContainer extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public FractalContainer() {
		this.setSize(600,500);
		this.setResizable(false);
		this.setTitle("Hallo i bims 1 haus vom 1 santa klaus");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(new FractalGraphics(), BorderLayout.CENTER);
		this.setVisible(true);
	}
	
	// This class holds the actual graphics of a Fractal
	public class FractalGraphics extends JPanel {

		private static final long serialVersionUID = 1L;
		
		public FractalGraphics() {
			this.setLayout(new BorderLayout());
			// DrawTest will be replaced with the actual fractal class obv.
			this.add(new Fractal(100), BorderLayout.CENTER);
			this.setVisible(true);
		}
	}
	
}
