package model.component;

import model.pixel.Pixel;

/**
 * ValueGrayScaleComponent to represent the functionality that turns a pixel into a grayscale
 * based on the value calculation (the channel with the greatest value).
 */
public class ValueComponent extends AbstractComponent
        implements Component {

  @Override
  public Pixel evaluate(Pixel p) {
    int maxSoFar = 0;
    for (int channel : p.getColorChannels()) {
      if (channel > maxSoFar) {
        maxSoFar = channel;
      }
    }
    return p.createPixelFromChannels(maxSoFar, maxSoFar, maxSoFar);
  }
}
