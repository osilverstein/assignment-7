package commands;

import org.junit.Before;

import commands.AbstractCommandTest;
import controller.commands.Command;
import controller.commands.GreenComponentCommand;
import model.ImageProcessorModel;
import utilities.ImageRunTimeStorage;
import utilities.ImageUtil;

/**
 * commands.GreenComponentCommandTest tests the command that turns an image into a grayscaled image
 * based on the green channel.
 */
public class GreenComponentCommandTest extends AbstractCommandTest {

  ImageProcessorModel m;
  ImageProcessorModel mCopy;

  @Before
  public void setUp() {
    m = ImageUtil.readPPM("src/images/Koala.ppm");
    mCopy = m.getCopy();
  }

  @Override
  Command createCommand(String name, String newName, ImageRunTimeStorage map) {
    return new GreenComponentCommand(name, newName, map);
  }

  @Override
  int[] getInts(int row, int col) {
    int g = mCopy.getPixel(row, col).getColorChannels()[1];
    return new int[]{g, g, g};
  }

  @Override
  String returnString() {
    return " set to green gray scale. Now called: ";
  }
}