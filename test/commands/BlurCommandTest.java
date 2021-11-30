package commands;

import org.junit.Before;
import org.junit.Test;


import controller.commands.BlurCommand;
import controller.commands.Command;
import model.Kernel;
import model.Matrix;
import model.filter.Filter;
import model.filter.GaussianBlur;
import model.pixel.Pixel;
import utilities.ImageRunTimeStorage;

import static org.junit.Assert.assertEquals;

/**
 * Test class for the BlurCommand class.
 */
public class BlurCommandTest extends AbstractCommandTest {
  Filter f;
  Pixel p;
  Matrix k;
  Pixel[][] pixels;

  @Before
  public void setUp() {
    super.setUp();
    f = new GaussianBlur();
    p = m.getPixel(0, 0);
    k = new Kernel(3, 3).setSlots(
            0.0625, 0.125, 0.0625,
            0.125, 0.25, 0.125,
            0.0625, 0.125, 0.0625);
    pixels = new Pixel[mCopy.getHeight()][mCopy.getWidth()];
    for (int row = 0; row < mCopy.getHeight(); row++) {
      for (int col = 0; col < mCopy.getWidth(); col++) {
        pixels[row][col] = mCopy.getPixel(row, col);
      }
    }
  }

  @Override
  Command createCommand(String name, String newName, ImageRunTimeStorage map) {
    return new BlurCommand(name, newName, map);
  }

  @Override
  int[] getInts(int row, int col) {
    return new int[]{0};
  }

  @Override
  @Test
  public void testAbstractComponentCommand() {
    //ImageRunTimeStorage storage = new ImageRunTimeStorage(new HashMap<>());
    //ImageProcessorModel m = ImageUtil.readPPM("src/images/Koala.ppm");

    //String output = this.createCommand("", "", storage).use(m);
    m.filter(new GaussianBlur());

    int errors = 0;

    for (int row = 0; row < m.getHeight(); row++) {
      for (int col = 0; col < m.getWidth(); col++) {

        double negOneNegOneTallyRed = 0;
        double negOneNegOneTallyGreen = 0;
        double negOneNegOneTallyBlue = 0;
        double negOneZeroTallyRed = 0;
        double negOneZeroTallyGreen = 0;
        double negOneZeroTallyBlue = 0;
        double negOneOneTallyRed = 0;
        double negOneOneTallyGreen = 0;
        double negOneOneTallyBlue = 0;
        double zeroNegOneTallyRed = 0;
        double zeroNegOneTallyGreen = 0;
        double zeroNegOneTallyBlue = 0;
        double zeroOneTallyRed = 0;
        double zeroOneTallyGreen = 0;
        double zeroOneTallyBlue = 0;
        double oneNegOneTallyRed = 0;
        double oneNegOneTallyGreen = 0;
        double oneNegOneTallyBlue = 0;
        double oneZeroTallyRed = 0;
        double oneZeroTallyGreen = 0;
        double oneZeroTallyBlue = 0;
        double oneOneTallyRed = 0;
        double oneOneTallyGreen = 0;
        double oneOneTallyBlue = 0;


        try {
          negOneNegOneTallyRed = (double) (mCopy.getPixel(row - 1,
                  col - 1).getColorChannels()[0] * 0.0625);
          negOneNegOneTallyGreen = (double) (mCopy.getPixel(row - 1,
                  col - 1).getColorChannels()[1] * 0.0625);
          negOneNegOneTallyBlue = (double) (mCopy.getPixel(row - 1,
                  col - 1).getColorChannels()[2] * 0.0625);
        } catch (IllegalArgumentException e) {
          String s = "yes";
        }


        try {
          negOneZeroTallyRed = (double) (mCopy.getPixel(row - 1,
                  col).getColorChannels()[0] * 0.125);
          negOneZeroTallyGreen = (double) (mCopy.getPixel(row - 1,
                  col).getColorChannels()[1] * 0.125);
          negOneZeroTallyBlue = (double) (mCopy.getPixel(row - 1,
                  col).getColorChannels()[2] * 0.125);
        } catch (IllegalArgumentException e) {
          String s = "yes";
        }


        try {
          negOneOneTallyRed = (double) (mCopy.getPixel(row - 1,
                  col + 1).getColorChannels()[0] * 0.0625);
          negOneOneTallyGreen = (double) (mCopy.getPixel(row - 1,
                  col + 1).getColorChannels()[1] * 0.0625);
          negOneOneTallyBlue = (double) (mCopy.getPixel(row - 1,
                  col + 1).getColorChannels()[2] * 0.0625);
        } catch (IllegalArgumentException e) {
          String s = "yes";
        }


        try {
          zeroNegOneTallyRed = (double) (mCopy.getPixel(row,
                  col - 1).getColorChannels()[0] * 0.125);
          zeroNegOneTallyGreen = (double) (mCopy.getPixel(row,
                  col - 1).getColorChannels()[1] * 0.125);
          zeroNegOneTallyBlue = (double) (mCopy.getPixel(row,
                  col - 1).getColorChannels()[2] * 0.125);
        } catch (IllegalArgumentException e) {
          String s = "yes";
        }


        double zeroZeroTallyRed = (double) (mCopy.getPixel(row,
                col).getColorChannels()[0] * 0.25);
        double zeroZeroTallyGreen = (double) (mCopy.getPixel(row,
                col).getColorChannels()[1] * 0.25);
        double zeroZeroTallyBlue = (double) (mCopy.getPixel(row,
                col).getColorChannels()[2] * 0.25);

        try {
          zeroOneTallyRed = (double) (mCopy.getPixel(row,
                  col + 1).getColorChannels()[0] * 0.125);
          zeroOneTallyGreen = (double) (mCopy.getPixel(row,
                  col + 1).getColorChannels()[1] * 0.125);
          zeroOneTallyBlue = (double) (mCopy.getPixel(row,
                  col + 1).getColorChannels()[2] * 0.125);
        } catch (IllegalArgumentException e) {
          String s = "yes";
        }


        try {
          oneNegOneTallyRed = (double) (mCopy.getPixel(row + 1,
                  col - 1).getColorChannels()[0] * 0.0625);
          oneNegOneTallyGreen = (double) (mCopy.getPixel(row + 1,
                  col - 1).getColorChannels()[1] * 0.0625);
          oneNegOneTallyBlue = (double) (mCopy.getPixel(row + 1,
                  col - 1).getColorChannels()[2] * 0.0625);
        } catch (IllegalArgumentException e) {
          String s = "yes";
        }


        try {
          oneZeroTallyRed = (double) (mCopy.getPixel(row + 1,
                  col).getColorChannels()[0] * 0.125);
          oneZeroTallyGreen = (double) (mCopy.getPixel(row + 1,
                  col).getColorChannels()[1] * 0.125);
          oneZeroTallyBlue = (double) (mCopy.getPixel(row + 1,
                  col).getColorChannels()[2] * 0.125);
        } catch (IllegalArgumentException e) {
          String s = "yes";
        }


        try {
          oneOneTallyRed = (double) (mCopy.getPixel(row + 1,
                  col + 1).getColorChannels()[0] * 0.0625);
          oneOneTallyGreen = (double) (mCopy.getPixel(row + 1,
                  col + 1).getColorChannels()[1] * 0.0625);
          oneOneTallyBlue = (double) (mCopy.getPixel(row + 1,
                  col + 1).getColorChannels()[2] * 0.0625);
        } catch (IllegalArgumentException e) {
          String s = "yes";
        }

        double red = negOneNegOneTallyRed
                + negOneZeroTallyRed
                + negOneOneTallyRed
                + zeroNegOneTallyRed
                + zeroZeroTallyRed
                + zeroOneTallyRed
                + oneNegOneTallyRed
                + oneZeroTallyRed
                + oneOneTallyRed;
        double green = negOneNegOneTallyGreen
                + negOneZeroTallyGreen
                + negOneOneTallyGreen
                + zeroNegOneTallyGreen
                + zeroZeroTallyGreen
                + zeroOneTallyGreen
                + oneNegOneTallyGreen
                + oneZeroTallyGreen
                + oneOneTallyGreen;
        double blue = negOneNegOneTallyBlue
                + negOneZeroTallyBlue
                + negOneOneTallyBlue
                + zeroNegOneTallyBlue
                + zeroZeroTallyBlue
                + zeroOneTallyBlue
                + oneNegOneTallyBlue
                + oneZeroTallyBlue
                + oneOneTallyBlue;

        assertEquals((int) red, m.getPixel(row, col).getColorChannels()[0]);
        assertEquals((int) green, m.getPixel(row, col).getColorChannels()[1]);
        assertEquals((int) blue, m.getPixel(row, col).getColorChannels()[2]);

      }
    }
  }


  @Override
  String returnString() {
    return " gaussian blurred. Now called: ";
  }
}
