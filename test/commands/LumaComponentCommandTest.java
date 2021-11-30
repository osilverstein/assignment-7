package commands;

import org.junit.Before;

import commands.AbstractCommandTest;
import controller.commands.Command;
import controller.commands.LumaComponentCommand;
import model.ImageProcessorModel;
import utilities.ImageRunTimeStorage;
import utilities.ImageUtil;


/**
 * commands.LumaComponentCommandTest tests the command that turns an image into a grayscaled image
 * based on the luma calculation.
 */
public class LumaComponentCommandTest extends AbstractCommandTest {

  ImageProcessorModel m;
  ImageProcessorModel mCopy;

  @Before
  public void setUp() {
    m = ImageUtil.readPPM("src/images/Koala.ppm");
    mCopy = m.getCopy();
  }

  @Override
  Command createCommand(String name, String newName, ImageRunTimeStorage map) {
    return new LumaComponentCommand(name, newName, map);
  }

  @Override
  int[] getInts(int row, int col) {
    int[] channels = mCopy.getPixel(row, col).getColorChannels();
    int luma = (int) (0.2126 * channels[0] + 0.7152 * channels[1] + 0.0722 * channels[2]);
    return new int[]{luma, luma, luma};
  }

  @Override
  String returnString() {
    return " set to luma gray scale. Now called: ";
  }
}