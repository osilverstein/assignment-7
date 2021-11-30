package model.component;

import model.Matrix;

/**
 * RedGrayScaleComponent to represent the functionality that turns a pixel into a grayscale
 * based on the blue channel.
 */
public class RedComponent extends AbstractComponent
        implements Component {

  @Override
  public Matrix getGrayScaleMatrix() {
    return new Matrix(3, 3).setSlots(1, 0, 0, 1, 0, 0, 1, 0, 0);
  }
}
