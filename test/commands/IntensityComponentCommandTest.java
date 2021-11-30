package commands;

import org.junit.Before;

import controller.commands.Command;
import controller.commands.IntensityComponentCommand;
import model.ImageProcessorModel;
import utilities.ImageRunTimeStorage;
import utilities.ImageUtil;


/**
 * commands.IntensityComponentCommandTest tests the command that turns an image into a
 * grayscaled image based on the intensity calculation.
 */
public class IntensityComponentCommandTest extends AbstractCommandTest {

  ImageProcessorModel m;
  ImageProcessorModel mCopy;

  @Before
  public void setUp() {
    m = ImageUtil.readPPM("src/images/Koala.ppm");
    mCopy = m.getCopy();
  }

  @Override
  Command createCommand(String name, String newName, ImageRunTimeStorage map) {
    return new IntensityComponentCommand(name, newName, map);
  }

  @Override
  int[] getInts(int row, int col) {
    int accum = 0;
    for (int channel : mCopy.getPixel(row, col).getColorChannels()) {
      accum += channel;
    }
    return new int[]{accum / 3, accum / 3, accum / 3};
  }

  @Override
  String returnString() {
    return " set to intensity gray scale. Now called: ";
  }
}