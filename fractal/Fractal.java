package fractal;

import javax.swing.JComponent;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Fractal extends JComponent {

	private static final long serialVersionUID = 1L;
	private int scale;
	private int currentStep;
	private int x;
	private int y;
	
	@Override
   protected void paintComponent(Graphics g) {
			super.paintComponent(g);

      Graphics2D graph2 = (Graphics2D) g;
        
      graph2.setColor(Color.BLACK);
      
      // DACH
      graph2.drawLine(scale, 0, 0, scale);
      graph2.drawLine(scale, 0, 2*scale, scale);
      graph2.drawLine(0, scale, 2*scale, scale);
        
      // HAUS
      graph2.drawLine(0, scale, 0, 3*scale);
      graph2.drawLine(2*scale, scale, 2*scale, 3*scale);
      graph2.drawLine(0, 3*scale, 2*scale, 3*scale);
        
      // MITTELBALKENDINGER
      graph2.drawLine(0, scale, 2*scale, 3*scale);
      graph2.drawLine(2*scale, scale, 0, 3*scale);
        
      graph2.drawString("Ich drawe more lines als ein kokainjunkie am bahnhof", 10, 350);
      graph2.drawString("Pass auf oder ich komm und klopp dich zusammen, dann machste nie mehr ferien auf dem reiterhof", 10, 375);
		}
	
	public Fractal(int scale) {
		this.currentStep = 0;
		this.scale = scale;
	}
	
}
