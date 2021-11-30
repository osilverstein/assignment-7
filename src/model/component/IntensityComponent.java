package model.component;

import model.pixel.Pixel;

/**
 * IntensityGrayScaleComponent to represent the functionality that turns a pixel into a grayscale
 * based on the intensity calculation (the average of the channels).
 */
public class IntensityComponent extends AbstractComponent
        implements Component {

  @Override
  public Pixel evaluate(Pixel p) {
    int accum = 0;
    for (int channel : p.getColorChannels()) {
      accum += channel;
    }
    int average = accum / p.getColorChannels().length;
    return p.createPixelFromChannels(average, average, average);
  }
}