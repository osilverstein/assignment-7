package commands;

import org.junit.Before;

import controller.commands.Command;
import controller.commands.SepiaComponentCommand;
import model.ImageProcessorModel;
import utilities.ImageRunTimeStorage;
import utilities.ImageUtil;

/**
 * Test class to test the sepia command.
 */
public class SepiaCommandTest extends AbstractCommandTest {

  ImageProcessorModel m;
  ImageProcessorModel mCopy;

  @Before
  public void setUp() {
    m = ImageUtil.readPPM("src/images/Koala.ppm");
    mCopy = m.getCopy();
  }

  @Override
  Command createCommand(String name, String newName, ImageRunTimeStorage map) {
    return new SepiaComponentCommand(name, newName, map);
  }

  @Override
  int[] getInts(int row, int col) {
    int r = mCopy.getPixel(row, col).getColorChannels()[0];
    int g = mCopy.getPixel(row, col).getColorChannels()[1];
    int b = mCopy.getPixel(row, col).getColorChannels()[2];
    int m = mCopy.getPixel(row, col).getMaxValue();
    double newRed = 0.393 * r + 0.769 * g + 0.189 * b;
    double newGreen = 0.349 * r + 0.686 * g + 0.168 * b;
    double newBlue = 0.272 * r + 0.534 * g + 0.131 * b;

    return new int[]{this.conform((int) newRed, m),
            this.conform((int) newGreen, m),
            this.conform((int) newBlue, m)};
  }

  @Override
  String returnString() {
    return " set to sepia gray scale. Now called: ";
  }

  private int conform(int value, int maxValue) {
    if (value > maxValue) {
      return maxValue;
    } else if (value < 0) {
      return 0;
    }
    return value;
  }
}

