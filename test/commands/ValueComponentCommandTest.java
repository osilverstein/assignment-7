package commands;

import org.junit.Before;

import java.util.HashMap;

import commands.AbstractCommandTest;
import controller.commands.Command;
import controller.commands.ValueComponentCommand;
import model.ImageProcessorModel;
import utilities.ImageRunTimeStorage;
import utilities.ImageUtil;


/**
 * commands.ValueComponentCommandTest tests the command that turns an image into a grayscaled image
 * based on the value calculation.
 */
public class ValueComponentCommandTest extends AbstractCommandTest {

  ImageProcessorModel m;
  ImageProcessorModel mCopy;
  ImageRunTimeStorage storage;

  @Before
  public void setUp() {
    m = ImageUtil.readPPM("src/images/Koala.ppm");
    mCopy = m.getCopy();
    storage = new ImageRunTimeStorage(new HashMap<>());
  }

  @Override
  Command createCommand(String name, String newName, ImageRunTimeStorage map) {
    return new ValueComponentCommand(name, newName, map);
  }

  @Override
  int[] getInts(int row, int col) {
    int maxSoFar = 0;
    for (int channel : mCopy.getPixel(row, col).getColorChannels()) {
      if (channel > maxSoFar) {
        maxSoFar = channel;
      }
    }
    return new int[]{maxSoFar, maxSoFar, maxSoFar};
  }

  @Override
  String returnString() {
    return " set to value gray scale. Now called: ";
  }
}