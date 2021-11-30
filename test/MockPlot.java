import java.util.Arrays;

import model.pixel.Pixel;
import view.PlotPanel;

/**
 * This class is a Mock version of our PlotPanel for testing purposes.
 */
public class MockPlot extends PlotPanel {
  StringBuilder log;

  public MockPlot(StringBuilder log) {
    super();
    this.log = log;
  }

  @Override
  protected void setPixels(Pixel[][] pixels) {
    this.loaded = true;
    this.reds = new int[256];
    this.greens = new int[256];
    this.blues = new int[256];
    this.intensities = new int[256];

    //Count the values per channel value per channel
    for (Pixel[] pixel_row : pixels) {
      for (Pixel pixel : pixel_row) {
        int[] channels = pixel.getColorChannels();
        this.reds[channels[0]] += 1;
        this.greens[channels[1]] += 1;
        this.blues[channels[2]] += 1;
        int intensity = (channels[0] + channels[1] + channels[2]) / 3;
        this.intensities[intensity] += 1;
      }
    }

    this.maxXValue = pixels[0][0].getMaxValue();
    this.maxYValue = Arrays.stream(new int[]{Arrays.stream(this.reds).max().getAsInt(),
            Arrays.stream(this.greens).max().getAsInt(),
            Arrays.stream(this.blues).max().getAsInt(),
            Arrays.stream(this.intensities).max().getAsInt()}).max().getAsInt();

    log.append("Max RGB Value For Histogram: " + maxXValue + "\n"
            + "Max Total Count For Histogram: " + maxYValue + "\n");
  }
}
