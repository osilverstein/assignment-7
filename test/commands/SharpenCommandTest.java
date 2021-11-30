package commands;

import org.junit.Test;

import java.util.HashMap;

import controller.commands.Command;
import controller.commands.SharpenCommand;
import model.ImprovedImageProcessorModel;
import model.Kernel;
import model.Matrix;
import model.pixel.Pixel;
import utilities.ImageRunTimeStorage;
import utilities.ImageUtil;

import static org.junit.Assert.assertEquals;

/**
 * Test class to test the sharpen command.
 */
public class SharpenCommandTest extends AbstractCommandTest {
  @Override
  Command createCommand(String name, String newName, ImageRunTimeStorage map) {
    return new SharpenCommand(name, newName, map);
  }

  @Override
  int[] getInts(int row, int col) {
    return new int[0];
  }

  // Test Blue Component Command (updates all pixels to a blue gray scale):
  @Test
  public void testAbstractComponentCommand() {
    ImageRunTimeStorage storage = new ImageRunTimeStorage(new HashMap<>());
    ImprovedImageProcessorModel m = ImageUtil.readPPM("src/images/Koala.ppm");

    String output = this.createCommand("", "", storage).use(m);

    int errors = 0;

    Matrix kernel = new Kernel(5, 5).setSlots(
            -0.125, -0.125, -0.125, -0.125, -0.125,
            -0.125, 0.25, 0.25, 0.25, -0.125,
            -0.125, 0.25, 1.00, 0.25, -0.125,
            -0.125, 0.25, 0.25, 0.25, -0.125,
            -0.125, -0.125, -0.125, -0.125, -0.125);

    for (int row = 0; row < m.getHeight(); row++) {
      for (int col = 0; col < m.getWidth(); col++) {

        double[] channels = new double[3];

        for (int i = 0 ; i < 5; i++) {
          for (int z = 0 ; z < 5; z++) {
            try {
              Pixel p = mCopy.getPixel(row - ((5 - 1) / 2) + i, col - ((5 - 1) / 2) + z);
              channels[0] += p.getColorChannels()[0] * kernel.getSlotAt(i, z);
              channels[1] += p.getColorChannels()[1] * kernel.getSlotAt(i, z);
              channels[2] += p.getColorChannels()[2] * kernel.getSlotAt(i, z);
            } catch (IllegalArgumentException e) {
              continue;
            } catch (IndexOutOfBoundsException e) {
              continue;
            }

          }
        }

        assertEquals(m.getPixel(row, col).getColorChannels()[0], this.conform((int) channels[0],
                m.getPixel(row, col).getMaxValue()));
        assertEquals(m.getPixel(row, col).getColorChannels()[1], this.conform((int) channels[1],
                m.getPixel(row, col).getMaxValue()));
        assertEquals(m.getPixel(row, col).getColorChannels()[2], this.conform((int) channels[2],
                m.getPixel(row, col).getMaxValue()));

      }
    }
    System.out.println("Errors: " + String.valueOf(errors));
    assertEquals(output, this.returnString());
  }

  private int conform(int value, int maxValue) {
    if (value > maxValue) {
      return maxValue;
    } else if (value < 0) {
      return 0;
    }
    return value;
  }

  @Override
  String returnString() {
    return " sharpened. Now called: ";
  }
}
