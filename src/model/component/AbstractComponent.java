package model.component;

import model.Matrix;
import model.pixel.Pixel;

/**
 * Abstract class to represent a GrayScaleComponent, which sets all the color channels in a pixel
 * to a single value, making the image gray.
 */
public abstract class AbstractComponent implements Component {

  /**
   * Evaluates this component on a given Pixel.
   * @param p is the pixel that this component is evaluated upon
   */
  @Override
  public Pixel evaluate(Pixel p) {
    Matrix channels = new Matrix(1, 3).setSlots(
            p.getColorChannels()[0],
            p.getColorChannels()[1],
            p.getColorChannels()[2]);
    return p.createPixelFromChannels(this.getGrayScaleMatrix().multiplyBy(channels).toInts());
  }

  @Override
  public Matrix getGrayScaleMatrix() {
    return null;
  }
}
