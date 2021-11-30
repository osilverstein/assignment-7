package model;

/**
 * A Kernel class is an extension of the Matrix class with the restriction of being odd in
 * both width and height.
 */
public class Kernel extends Matrix {

  /**
   * A constructor to impose the odd restrictions of a Kernel.
   * @param width represents the width of the kernel
   * @param height represents the height of the kernel
   * @throws IllegalArgumentException if the width or height are less than zero or odd
   */
  public Kernel(int width, int height) throws IllegalArgumentException {
    super(width, height);
    if (width % 2 == 0 || height % 2 == 0) {
      throw new IllegalArgumentException("Kernel dimension must be odd and positive!");
    }
  }
}
