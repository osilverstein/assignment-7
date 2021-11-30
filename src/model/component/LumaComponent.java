package model.component;

import model.Matrix;

/**
 * LumaGrayScaleComponent to represent the functionality that turns a pixel into a grayscale
 * based on the luma calculation.
 */
public class LumaComponent extends AbstractComponent
        implements Component {

  @Override
  public Matrix getGrayScaleMatrix() {
    return new Matrix(3, 3).setSlots(
            0.21260, 0.71520, 0.07220,
            0.21260, 0.71520, 0.07220,
            0.21260, 0.71520, 0.07220);
  }

}
