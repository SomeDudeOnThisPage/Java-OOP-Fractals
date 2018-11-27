package fractal;

import fractal.graphics.*;

	// TODO: Important: Fractal-Classes themselves need a paint() and a step() function, paint() should draw the whole depth of the fractal, whilst
	//			 						step() should draw a fractal to a lesser depth, and when called needs to resume drawing one level deeper
	// TODO: Implement Runnable
public class Main {

	public static void main(String[] args) {
		FractalThread fc1 = new FractalThread();
		fc1.start();
	}

}
