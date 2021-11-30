package model;

import model.filter.Filter;
import model.pixel.Pixel;

/**
 * This represents an implementation for the model of an image processor.
 * It contains all the methods needed for altering an image (filter, flip, component).
 */
public class ImprovedImageModel extends ImageModel implements ImprovedImageProcessorModel {
  /**
   * A constructor for the creation of a PPMModel.
   *
   * @param pixels   is an 2D array of Pixel to represent the image
   * @param width    is the amount of pixels horizontally in the 2D array
   * @param height   is the amount of pixels vertically in the 2D array
   * @param maxValue is the maximum value of any color channel
   * @throws IllegalArgumentException if pixels is null or
   *                                  if the width or height are less than or equal to 0 or
   *                                  if the maxValue is less than 0
   */
  public ImprovedImageModel(Pixel[][] pixels, int width, int height, int maxValue)
          throws IllegalArgumentException {
    super(pixels, width, height, maxValue);
  }

  @Override
  public void filter(Filter filter) {
    Pixel[][] pixelsTEMP = new Pixel[this.height][this.width];
    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        pixelsTEMP[row][col] = this.getPixel(row, col);
      }
    }
    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        this.pixels[row][col] = filter.evaluateFilter(pixelsTEMP, row, col);
      }
    }
  }
}
