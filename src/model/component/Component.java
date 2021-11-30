package model.component;

import model.Matrix;
import model.pixel.Pixel;

/**
 * The Component interface allows for extensible manipulation of a Pixel.
 */
public interface Component {
  /**
   * Does functionality upon the given pixel.
   * @param p represents the pixel this component evaluates upon
   * @return the value that the component evaluates
   */
  Pixel evaluate(Pixel p);

  Matrix getGrayScaleMatrix();
}
