package commands;

import controller.commands.BlueComponentCommand;
import controller.commands.Command;
import utilities.ImageRunTimeStorage;

/**
 * commands.BlueComponentCommandTest tests the command that turns an image into a grayscaled image
 * based on the blue channel.
 */
public class BlueComponentCommandTest extends AbstractCommandTest {

  @Override
  Command createCommand(String name, String newName , ImageRunTimeStorage map) {
    return new BlueComponentCommand(name, newName, map);
  }

  @Override
  int[] getInts(int row, int col) {
    int b = mCopy.getPixel(row, col).getColorChannels()[2];
    return new int[]{b, b, b};
  }

  @Override
  String returnString() {
    return " set to blue gray scale. Now called: ";
  }
}