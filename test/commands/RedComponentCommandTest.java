package commands;

import org.junit.Before;

import commands.AbstractCommandTest;
import controller.commands.Command;
import controller.commands.RedComponentCommand;
import model.ImageProcessorModel;
import utilities.ImageRunTimeStorage;
import utilities.ImageUtil;


/**
 * commands.RedComponentCommandTest tests the command that turns an image into a grayscaled image
 * based on the red channel.
 */
public class RedComponentCommandTest extends AbstractCommandTest {

  ImageProcessorModel m;
  ImageProcessorModel mCopy;

  @Before
  public void setUp() {
    m = ImageUtil.readPPM("src/images/Koala.ppm");
    mCopy = m.getCopy();
  }

  @Override
  Command createCommand(String name, String newName, ImageRunTimeStorage map) {
    return new RedComponentCommand(name, newName, map);
  }

  @Override
  int[] getInts(int row, int col) {
    int r = mCopy.getPixel(row, col).getColorChannels()[0];
    return new int[]{r, r, r};
  }

  @Override
  String returnString() {
    return " set to red gray scale. Now called: ";
  }
}