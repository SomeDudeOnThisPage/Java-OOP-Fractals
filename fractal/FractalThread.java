package fractal;

import fractal.graphics.*;

public class FractalThread extends Thread {
	private FractalContainer container;
	
	@Override
	public void run() {
		container = new FractalContainer();
	}
}
