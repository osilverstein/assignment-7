package model.filter;

import model.Kernel;

/**
 * Sharpen is a filter that represents a sharpening using a 5 by 5 kernel.
 */
public class Sharpen extends AbstractFilter implements Filter {

  /**
   * A constructor that sets up the kernel for a sharpening.
   */
  public Sharpen() {
    super(5, 5);
    this.kernel = new Kernel(this.width,this.height).setSlots(
            -0.125, -0.125, -0.125, -0.125, -0.125,
            -0.125, 0.25, 0.25, 0.25, -0.125,
            -0.125, 0.25, 1.00, 0.25, -0.125,
            -0.125, 0.25, 0.25, 0.25, -0.125,
            -0.125, -0.125, -0.125, -0.125, -0.125);
  }
}
