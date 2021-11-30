package model.component;

import model.Matrix;

/**
 * SepiaComponent to represent the functionality that turns a pixel into its sepia equivalent.
 */
public class SepiaComponent extends AbstractComponent implements Component {

  @Override
  public Matrix getGrayScaleMatrix() {
    return new Matrix(3, 3).setSlots(
            0.393, 0.769, 0.189,
            0.349, 0.686, 0.168,
            0.272, 0.534, 0.131);
  }
}
