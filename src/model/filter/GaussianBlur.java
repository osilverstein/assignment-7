package model.filter;

import model.Kernel;

/**
 * GuassianBlur is a filter that represents a blur of the gaussian type using a 3 by 3 kernel.
 */
public class GaussianBlur extends AbstractFilter implements Filter {

  /**
   * A constructor that sets up the kernel for a gaussian blur.
   */
  public GaussianBlur() {
    super(3, 3);
    this.kernel = new Kernel(3,3).setSlots(
            0.0625, 0.125, 0.0625,
            0.125, 0.25, 0.125,
            0.0625, 0.125, 0.0625);
  }

}
