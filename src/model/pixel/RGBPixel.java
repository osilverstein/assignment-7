package model.pixel;

import java.util.Objects;

import model.component.Component;

/**
 * Represents a pixel with RGB channels.
 */
public class RGBPixel implements Pixel {
  private int red;
  private int green;
  private int blue;
  private int maxValue;

  /**
   * Constructs a pixel with RGB channels {@code red}, {@code green}, and {@code blue}.
   *
   * @param red represents the red channel of an RGB pixel.
   * @param green represents the green channel of an RGB pixel.
   * @param blue represents the blue channel of an RGB pixel.
   * @param maxValue represents the maximum value of any channel of an RGB pixel.
   * @throws IllegalArgumentException if any values are less than 0 or
   *                                  if {@code maxValue} is less than any other value
   */
  public RGBPixel(int red, int green, int blue, int maxValue) throws IllegalArgumentException {
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.maxValue = maxValue;

    if (this.red < 0 || this.green < 0 || this.blue < 0 || maxValue < 0) {
      throw new IllegalArgumentException("Values must be greater than or equal to 0");
    }
    if (this.red > maxValue || this.green > maxValue || this.blue > maxValue) {
      throw new IllegalArgumentException("Values must be less than or equal to maxValue");
    }
  }

  /**
   * A string representation of the Pixel.
   * @return a string representation of the Pixel
   */
  @Override
  public String toString() {
    return this.red + "\n" + this.green + "\n" + this.blue;
  }


  /**
   * Find the various color channels that make up the color of a pixel.
   *
   * @return the color channels that make up a pixel in the form of an int array.
   */
  @Override
  public int[] getColorChannels() {
    return new int[]{this.red, this.green, this.blue};
  }

  /**
   * Updates all the channels of a Pixel based upon the increment component provided.
   *
   * @param c represents an increment component that will be used to determine what to update the
   *          RGB values by.
   */

  public Pixel setComponent(Component c) {
    return c.evaluate(this);
  }

  /**
   * A method to generate a copy of this pixel.
   * @return an identical pixel to this pixel
   */
  @Override
  public Pixel getCopy() {
    return new RGBPixel(this.red, this.green, this.blue, this.maxValue);
  }

  @Override
  public Pixel createPixelFromChannels(double... channels) {
    return new RGBPixel(
            this.cappedSum(0, (int) channels[0]),
            this.cappedSum(0, (int) channels[1]),
            this.cappedSum(0, (int) channels[2]),
            this.maxValue);
  }

  private int cappedSum(int increment, int value) {
    if (value + increment > this.maxValue) {
      return this.maxValue;
    } else if (value + increment < 0) {
      return 0;
    }
    return value + increment;
  }

  @Override
  public int getMaxValue() {
    return this.maxValue;
  }

  /**
   * Two Pixels are equal of all the value channels and maxValue are equal.
   * @param o an object
   * @return whether this is equal to {@code o}
   */
  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof RGBPixel)) {
      return false;
    }
    RGBPixel other = (RGBPixel) o;
    return (this.red == other.red
            && this.green == other.green
            && this.blue == other.blue
            && this.maxValue == other.maxValue);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.red, this.green, this.blue, this.maxValue);
  }
}

