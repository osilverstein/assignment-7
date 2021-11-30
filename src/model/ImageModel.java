package model;


import java.util.Objects;

import model.component.Component;
import model.flip.Flip;
import model.pixel.Pixel;

/**
 * The class PPMModel is an implementation of the ImprovedImageProcessorModel that can process and
 * manipulate images of the ppm format.
 */
public class ImageModel implements ImageProcessorModel {
  protected Pixel[][] pixels;
  protected int width;
  protected int height;
  protected int maxValue;

  /**
   * A constructor for the creation of a PPMModel.
   * @param pixels is an 2D array of Pixel to represent the image
   * @param width is the amount of pixels horizontally in the 2D array
   * @param height is the amount of pixels vertically in the 2D array
   * @param maxValue is the maximum value of any color channel
   * @throws IllegalArgumentException if pixels is null or
   *                                  if the width or height are less than or equal to 0 or
   *                                  if the maxValue is less than 0
   */
  public ImageModel(Pixel[][] pixels, int width, int height, int maxValue)
          throws IllegalArgumentException {
    this.pixels = pixels;
    this.width = width;
    this.height = height;
    this.maxValue = maxValue;

    if (this.pixels == null || width <= 0 || height <= 0 || maxValue < 0) {
      throw new IllegalArgumentException("Invalid Input(s)!");
    }
  }

  /**
   * This method activates the components on each of the pixels in the image in such a way
   * that enables dynamic dispatch to account for any implementation of Pixel or Component.
   * @param comp represents the Component
   */
  @Override
  public void component(Component comp) {
    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        this.pixels[row][col] = comp.evaluate(this.pixels[row][col]);
      }
    }
  }

  /**
   * Flips the image by an axis given by the Flip.
   *
   * @param flip determines the axis over which the image is flipped
   */
  @Override
  public void flip(Flip flip) {
    Pixel[][] newPixels = new Pixel[this.height][this.width];
    for (int row = 0; row < this.height; row++) {
      for (int col = 0; col < this.width; col++) {
        int[] newPosition = flip.evaluate(row, col, this.width, this.height);
        newPixels[row][col] = pixels[newPosition[0]][newPosition[1]];
      }
    }
    this.pixels = newPixels;
  }

  @Override
  public Pixel getPixel(int row, int col) throws IllegalStateException {
    if (row < 0 || row >= height || col < 0 || col >= width) {
      throw new IllegalArgumentException("Invalid row/col input: " + row + "," + col);
    }
    return this.pixels[row][col].getCopy();
  }

  @Override
  public ImprovedImageProcessorModel getCopy() {
    Pixel[][] newPixels = new Pixel[this.height][this.width];
    for (int row = 0 ; row < this.height ; row ++) {
      for (int col = 0 ; col < this.width ; col ++) {
        newPixels[row][col] = pixels[row][col].getCopy();
      }
    }
    return new ImprovedImageModel(newPixels, this.width, this.height, this.maxValue);
  }

  @Override
  public String toFile() {
    StringBuilder finalOutput = new StringBuilder();
    finalOutput.append("P3\n");
    finalOutput.append(this.width + " " + this.height);
    finalOutput.append("\n" + this.maxValue);
    for (int row = 0 ; row < this.height ; row ++) {
      for (int col = 0 ; col < this.width ; col ++) {
        finalOutput.append("\n");
        finalOutput.append(pixels[row][col].toString());
      }
    }
    finalOutput.append("\n");
    return finalOutput.toString();
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public int getMaxValue() {
    return this.maxValue;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof ImageModel)) {
      return false;
    }
    ImageModel other = (ImageModel) o;
    boolean pixels = true;

    for (int row = 0 ; row < this.height ; row++) {
      for (int col = 0 ; col < this.width ; col ++) {
        if (!this.getPixel(row, col).equals(other.getPixel(row, col))) {
          pixels = false;
        }
      }
    }

    return (this.width == other.width)
            && (this.height == other.height)
            && (this.maxValue == other.maxValue)
            && (pixels);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.pixels, this.width, this.height, this.maxValue);
  }
}
