package model.filter;

import model.Matrix;
import model.pixel.Pixel;

/**
 * An AbstractFilter class to lift the commonalities between the filters.
 */
public abstract class AbstractFilter implements Filter {
  int width;
  int height;
  Matrix kernel;

  /**
   * Constructor for an abstract filter.
   * @param width represents the width of the kernel
   * @param height represents the height of the kernel
   * @throws IllegalArgumentException if either are even or less than or equal to 0
   */
  public AbstractFilter(int width, int height) throws IllegalArgumentException {
    if (width % 2 == 0 || height % 2 == 0 || width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Incorrect width or height");
    }
    this.width = width;
    this.height = height;
  }

  @Override
  public Pixel evaluateFilter(Pixel[][] pixels, int row, int col) {
    double[] channels = new double[3]; //might not be 3

    for (int i = 0 ; i < this.width ; i++) {
      for (int z = 0 ; z < this.height ; z++) {
        try {
          Pixel p = pixels[row - ((this.width - 1) / 2) + i][col - ((this.height - 1) / 2) + z];
          channels[0] += p.getColorChannels()[0] * this.kernel.getSlotAt(i, z);
          channels[1] += p.getColorChannels()[1] * this.kernel.getSlotAt(i, z);
          channels[2] += p.getColorChannels()[2] * this.kernel.getSlotAt(i, z);
        } catch (IndexOutOfBoundsException e) {
          continue;
        }
      }
    }

    return pixels[0][0].createPixelFromChannels(channels);
  }
}
