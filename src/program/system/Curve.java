package program.system;

import java.awt.image.BufferedImage;

/**
 * Abstract class serving as a template for various algorithms
 * <p>
 *   This class contains the shared variables between all algorithms
 * </p>
 * @author Robin Buhlmann
 * @version 0.1
 */
public abstract class Curve
{
  public static final int TESTALG = 0;

  /**
   * The size of the canvas that holds this curve.
   * The canvas is squared so no x/y are needed.
   */
  private int csize;

  /**
   * The scale for the algorithm.
   */
  private int scale;

  /**
   * Constructor for the curve class
   * @param csize The size of the canvas
   * @param scale The scale of the curve
   */
  public Curve(int csize, int scale)
  {
    this.csize = csize;
    this.scale = scale;
  }
}
