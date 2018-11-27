package fractal;

import javax.swing.JComponent;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Fractal extends JComponent {

	private static final long serialVersionUID = 1L;
	private int scale;
	private int currentStep;
	private int oldx;
	private int oldy;
	private int x;
	private int y;
	
	private void moveHilbert(int j, int h, Graphics g) {
		oldy = y;
		oldx = x;
		if (j == 1) {
			y = y - h;
		} else if (j == 2) {
			x = x + h;
		} else if (j == 3) {
			y = y + h;
		} else if (j == 4) {
			x = x - h;
		}
		g.drawLine(oldx, oldy, x, y);
	}
	
	private void hilbert(int r, int d, int l, int u, int i, int h, Graphics g) {
		if (i > 0) {
			i--;
			hilbert(d,r,u,l,i,h,g);
			moveHilbert(r,h,g);
			hilbert(r,d,l,u,i,h,g);
			moveHilbert(d,h,g);
			hilbert(r,d,l,u,i,h,g);
			moveHilbert(l,h,g);
			hilbert(u,l,d,r,i,h,g); 
		}
	}
	
	@Override
   protected void paintComponent(Graphics g) {
			super.paintComponent(g);

      Graphics2D graph2 = (Graphics2D) g;
        
      graph2.setColor(Color.BLACK);
      oldx = 0;
      oldy = 0;
      this.y = 0;
      this.x = 0;
      int h = 10;
      int r=2;
      int d=3;
      int l=4;
      int u=1; 
      
      int n = 5;
      
      hilbert(r,d,l,u,n,h,g); 
		}
	
	public Fractal(int scale) {
		this.currentStep = 0;
		this.scale = scale;
	}
	
}
