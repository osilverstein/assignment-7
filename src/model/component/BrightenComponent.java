package model.component;

import model.pixel.Pixel;

/**
 * BrightenComponent to represent the functionality that increments all pixel channels by a
 * given amount.
 */
public class BrightenComponent extends AbstractComponent implements Component {
  int amount;

  /**
   * Constructor to create a BrightenComponent with a given amount.
   * @param amount represents the amount to be brightened by
   */
  public BrightenComponent(int amount) {
    this.amount = amount;
  }

  @Override
  public Pixel evaluate(Pixel p) {
    return p.createPixelFromChannels(p.getColorChannels()[0] + this.amount,
            p.getColorChannels()[1] + this.amount,
            p.getColorChannels()[2] + this.amount);
  }
}
