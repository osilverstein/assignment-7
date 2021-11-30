package model.pixel;

import model.component.Component;

/**
 * Represents a Pixel in an image.
 */
public interface Pixel {

  /**
   * Find the various color channels that make up the color of a pixel.
   *
   * @return the color channels that make up a pixel in the form of an int array.
   */
  int[] getColorChannels();

  /**
   * Updates all the channels of a Pixel based upon the grayscale component provided.
   *
   * @param c represents a grayscale component that will be used to determine what to update the
   *          RGB values to.
   */
  Pixel setComponent(Component c);

  /**
   * A method to generate a copy of this pixel.
   * @return an identical pixel to this pixel
   */
  Pixel getCopy();

  /**
   * Creates a pixels from the given channels.
   * @param channels is an array of doubles with the channel values for a new pixel.
   * @return a Pixel that is made from the given channels.
   */
  Pixel createPixelFromChannels(double... channels);

  /**
   * Getter method to return the maxValue of the pixel.
   * @return the max value of the pixel.
   */
  int getMaxValue();
}
