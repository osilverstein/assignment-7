package model;

import model.component.Component;
import model.flip.Flip;
import model.pixel.Pixel;

/**
 * Represents an Image Processor that deals with various image types.
 */
public interface ImageProcessorModel {

  /**
   * Flips the image by an axis given by the Flip.
   * @param flip determines the axis over which the image is flipped
   */
  void flip(Flip flip);

  /**
   * Brightens the image by the given amount.
   * @param component represents the component to alter this pixel by.
   */
  void component(Component component);

  /**
   * Getter method for a pixel.
   * @param row the row of the pixel
   * @param col the column of the pixel
   * @return the pixel at the given row and column
   */
  Pixel getPixel(int row, int col) throws IllegalStateException;

  ImprovedImageProcessorModel getCopy();

  String toFile();

  int getWidth();

  int getHeight();

  int getMaxValue();
}
