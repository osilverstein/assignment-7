package model.component;

import model.Matrix;

/**
 * GreenGrayScaleComponent to represent the functionality that turns a pixel into a grayscale
 * based on the green channel.
 */
public class GreenComponent extends AbstractComponent
        implements Component {

  @Override
  public Matrix getGrayScaleMatrix() {
    return new Matrix(3, 3).setSlots(0, 1, 0, 0, 1, 0, 0, 1, 0);
  }
}