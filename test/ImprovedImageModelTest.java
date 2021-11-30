import org.junit.Before;
import org.junit.Test;

import model.ImprovedImageProcessorModel;
import model.Kernel;
import model.Matrix;
import model.filter.Filter;
import model.filter.GaussianBlur;
import model.filter.Sharpen;
import model.pixel.Pixel;
import utilities.ImageUtil;

import static org.junit.Assert.assertEquals;

/**
 * To represent the tests and examples of an ImprovedImageModel.
 */
public class ImprovedImageModelTest {

  ImprovedImageProcessorModel m1;
  ImprovedImageProcessorModel m2;
  ImprovedImageProcessorModel mCopy;
  Filter gaussian;
  Filter sharpen;

  @Before
  public void setUp() {
    m1 = ImageUtil.readPPM("src/images/Koala.ppm");
    m2 = ImageUtil.readOtherFiles("src/images/Koala.png");
    mCopy = m1.getCopy();
    gaussian = new GaussianBlur();
    sharpen = new Sharpen();
  }

  // Test a gaussian blur filter.
  @Test
  public void testGaussianBlurFilter() {
    m1.filter(gaussian);

    for (int row = 0; row < m1.getHeight(); row++) {
      for (int col = 0; col < m1.getWidth(); col++) {

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

        assertEquals((int) red, m1.getPixel(row, col).getColorChannels()[0]);
        assertEquals((int) green, m1.getPixel(row, col).getColorChannels()[1]);
        assertEquals((int) blue, m1.getPixel(row, col).getColorChannels()[2]);
      }
    }
  }

  // Test sharpen filter
  @Test
  public void testSharpenFilter() {
    m1.filter(sharpen);
    int errors = 0;

    Matrix kernel = new Kernel(5, 5).setSlots(
            -0.125, -0.125, -0.125, -0.125, -0.125,
            -0.125, 0.25, 0.25, 0.25, -0.125,
            -0.125, 0.25, 1.00, 0.25, -0.125,
            -0.125, 0.25, 0.25, 0.25, -0.125,
            -0.125, -0.125, -0.125, -0.125, -0.125);

    for (int row = 0; row < m1.getHeight(); row++) {
      for (int col = 0; col < m1.getWidth(); col++) {

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
        assertEquals(m1.getPixel(row, col).getColorChannels()[0], this.conform((int) channels[0],
                m1.getPixel(row, col).getMaxValue()));
        assertEquals(m1.getPixel(row, col).getColorChannels()[1], this.conform((int) channels[1],
                m1.getPixel(row, col).getMaxValue()));
        assertEquals(m1.getPixel(row, col).getColorChannels()[2], this.conform((int) channels[2],
                m1.getPixel(row, col).getMaxValue()));

      }
    }
    System.out.println("Errors: " + String.valueOf(errors));
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