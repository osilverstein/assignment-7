package components;

import org.junit.Before;
import org.junit.Test;

import model.ImageProcessorModel;
import model.Kernel;
import model.Matrix;
import model.filter.Filter;
import model.filter.GaussianBlur;
import model.pixel.Pixel;
import utilities.ImageUtil;

import static org.junit.Assert.assertEquals;

/**
 * test class for the GaussianBlur.
 */
public class GaussianBlurTest {

  ImageProcessorModel m;
  Filter f;
  Pixel p;
  Matrix k;
  Pixel[][] pixels;

  @Before
  public void setUp() {
    m = ImageUtil.readPPM("src/images/Koala.ppm");
    f = new GaussianBlur();
    p = m.getPixel(0,0);
    k = new Kernel(3,3).setSlots(
            0.0625, 0.125, 0.0625,
            0.125, 0.25, 0.125,
            0.0625, 0.125, 0.0625);
    pixels = new Pixel[m.getHeight()][m.getWidth()];
    for (int row = 0; row < m.getHeight(); row++) {
      for (int col = 0; col < m.getWidth(); col++) {
        pixels[row][col] = m.getPixel(row, col);
      }
    }
  }

  // Tests blurring a pixel in the corner of the image (ignores grid podoubles of the image).
  @Test
  public void testBlurInCorner() {
    Pixel newPixel = f.evaluateFilter(pixels, 0, 0);
    assertEquals(m.getPixel(0,0).toString(), "230\n225\n222");
    assertEquals(m.getPixel(0,1).toString(), "229\n225\n222");
    assertEquals(m.getPixel(1,0).toString(), "229\n225\n222");
    assertEquals(m.getPixel(1,1).toString(), "229\n224\n221");

    double zeroZeroTallyRed = (double) (m.getPixel(0,0).getColorChannels()[0] * 0.25);
    System.out.println(zeroZeroTallyRed);
    double zeroZeroTallyGreen = (double) (m.getPixel(0,0).getColorChannels()[1] * 0.25);
    double zeroZeroTallyBlue = (double) (m.getPixel(0,0).getColorChannels()[2] * 0.25);

    double zeroOneTallyRed = (double) (m.getPixel(0,1).getColorChannels()[0] * 0.125);
    System.out.println(zeroOneTallyRed);
    double zeroOneTallyGreen = (double) (m.getPixel(0,1).getColorChannels()[1] * 0.125);
    double zeroOneTallyBlue = (double) (m.getPixel(0,1).getColorChannels()[2] * 0.125);

    double oneZeroTallyRed = (double) (m.getPixel(1,0).getColorChannels()[0] * 0.125);
    System.out.println(oneZeroTallyRed);
    double oneZeroTallyGreen = (double) (m.getPixel(1,0).getColorChannels()[1] * 0.125);
    double oneZeroTallyBlue = (double) (m.getPixel(1,0).getColorChannels()[2] * 0.125);

    double oneOneTallyRed = (double) (m.getPixel(1,1).getColorChannels()[0] * 0.0625);
    System.out.println(oneOneTallyRed);
    double oneOneTallyGreen = (double) (m.getPixel(1,1).getColorChannels()[1] * 0.0625);
    double oneOneTallyBlue = (double) (m.getPixel(1,1).getColorChannels()[2] * 0.0625);

    double red = zeroZeroTallyRed + zeroOneTallyRed + oneZeroTallyRed + oneOneTallyRed;
    double green = zeroZeroTallyGreen + zeroOneTallyGreen + oneZeroTallyGreen + oneOneTallyGreen;
    double blue = zeroZeroTallyBlue + zeroOneTallyBlue + oneZeroTallyBlue + oneOneTallyBlue;

    System.out.println(red);
    assertEquals((int) red, newPixel.getColorChannels()[0]);
    assertEquals((int) green, newPixel.getColorChannels()[1]);
    assertEquals((int) blue, newPixel.getColorChannels()[2]);
  }

  // Tests blurring a pixel in the middle of the image.
  @Test
  public void testBlurInMiddle() {
    Pixel newPixel = f.evaluateFilter(pixels, 100, 100);
    assertEquals(m.getPixel(99,99).toString(), "83\n170\n111");
    assertEquals(m.getPixel(99,100).toString(), "93\n173\n116");
    assertEquals(m.getPixel(99,101).toString(), "101\n175\n119");
    assertEquals(m.getPixel(100,99).toString(), "76\n167\n107");
    assertEquals(m.getPixel(100,100).toString(), "81\n170\n110");
    assertEquals(m.getPixel(100,101).toString(), "89\n171\n114");
    assertEquals(m.getPixel(101,99).toString(), "73\n165\n105");
    assertEquals(m.getPixel(101,100).toString(), "76\n166\n107");
    assertEquals(m.getPixel(101,101).toString(), "79\n168\n109");

    double negOneNegOneTallyRed = (double) (m.getPixel(99,99).getColorChannels()[0] * 0.0625);
    double negOneNegOneTallyGreen = (double) (m.getPixel(99,99).getColorChannels()[1] * 0.0625);
    double negOneNegOneTallyBlue = (double) (m.getPixel(99,99).getColorChannels()[2] * 0.0625);

    double negOneZeroTallyRed = (double) (m.getPixel(99,100).getColorChannels()[0] * 0.125);
    double negOneZeroTallyGreen = (double) (m.getPixel(99,100).getColorChannels()[1] * 0.125);
    double negOneZeroTallyBlue = (double) (m.getPixel(99,100).getColorChannels()[2] * 0.125);

    double negOneOneTallyRed = (double) (m.getPixel(99,101).getColorChannels()[0] * 0.0625);
    double negOneOneTallyGreen = (double) (m.getPixel(99,101).getColorChannels()[1] * 0.0625);
    double negOneOneTallyBlue = (double) (m.getPixel(99,101).getColorChannels()[2] * 0.0625);

    double zeroNegOneTallyRed = (double) (m.getPixel(100,99).getColorChannels()[0] * 0.125);
    double zeroNegOneTallyGreen = (double) (m.getPixel(100,99).getColorChannels()[1] * 0.125);
    double zeroNegOneTallyBlue = (double) (m.getPixel(100,99).getColorChannels()[2] * 0.125);

    double zeroZeroTallyRed = (double) (m.getPixel(100,100).getColorChannels()[0] * 0.25);
    double zeroZeroTallyGreen = (double) (m.getPixel(100,100).getColorChannels()[1] * 0.25);
    double zeroZeroTallyBlue = (double) (m.getPixel(100,100).getColorChannels()[2] * 0.25);

    double zeroOneTallyRed = (double) (m.getPixel(100,101).getColorChannels()[0] * 0.125);
    double zeroOneTallyGreen = (double) (m.getPixel(100,101).getColorChannels()[1] * 0.125);
    double zeroOneTallyBlue = (double) (m.getPixel(100,101).getColorChannels()[2] * 0.125);

    double oneNegOneTallyRed = (double) (m.getPixel(101,99).getColorChannels()[0] * 0.0625);
    double oneNegOneTallyGreen = (double) (m.getPixel(101,99).getColorChannels()[1] * 0.0625);
    double oneNegOneTallyBlue = (double) (m.getPixel(101,99).getColorChannels()[2] * 0.0625);

    double oneZeroTallyRed = (double) (m.getPixel(101,100).getColorChannels()[0] * 0.125);
    double oneZeroTallyGreen = (double) (m.getPixel(101,100).getColorChannels()[1] * 0.125);
    double oneZeroTallyBlue = (double) (m.getPixel(101,100).getColorChannels()[2] * 0.125);

    double oneOneTallyRed = (double) (m.getPixel(101,101).getColorChannels()[0] * 0.0625);
    double oneOneTallyGreen = (double) (m.getPixel(101,101).getColorChannels()[1] * 0.0625);
    double oneOneTallyBlue = (double) (m.getPixel(101,101).getColorChannels()[2] * 0.0625);

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

    assertEquals((int) red, newPixel.getColorChannels()[0]);
    assertEquals((int) green, newPixel.getColorChannels()[1]);
    assertEquals((int) blue, newPixel.getColorChannels()[2]);
  }

  // Tests blurring a pixel on the edge of the image (ignores grid podoubles of the image).
  @Test
  public void testBlurOnEdge() {
    Pixel newPixel = f.evaluateFilter(pixels, 0, 1);
    assertEquals(m.getPixel(0,0).toString(), "230\n225\n222");
    assertEquals(m.getPixel(0,1).toString(), "229\n225\n222");
    assertEquals(m.getPixel(0,2).toString(), "229\n225\n222");
    assertEquals(m.getPixel(1,0).toString(), "229\n225\n222");
    assertEquals(m.getPixel(1,1).toString(), "229\n224\n221");
    assertEquals(m.getPixel(1,2).toString(), "229\n224\n221");

    double zeroNegOneTallyRed = (double) (m.getPixel(0,0).getColorChannels()[0] * 0.125);
    double zeroNegOneTallyGreen = (double) (m.getPixel(0,0).getColorChannels()[1] * 0.125);
    double zeroNegOneTallyBlue = (double) (m.getPixel(0,0).getColorChannels()[2] * 0.125);

    double zeroZeroTallyRed = (double) (m.getPixel(0,1).getColorChannels()[0] * 0.25);
    double zeroZeroTallyGreen = (double) (m.getPixel(0,1).getColorChannels()[1] * 0.25);
    double zeroZeroTallyBlue = (double) (m.getPixel(0,1).getColorChannels()[2] * 0.25);

    double zeroOneTallyRed = (double) (m.getPixel(0,2).getColorChannels()[0] * 0.125);
    double zeroOneTallyGreen = (double) (m.getPixel(0,2).getColorChannels()[1] * 0.125);
    double zeroOneTallyBlue = (double) (m.getPixel(0,2).getColorChannels()[2] * 0.125);

    double oneNegOneTallyRed = (double) (m.getPixel(1,0).getColorChannels()[0] * 0.0625);
    double oneNegOneTallyGreen = (double) (m.getPixel(1,0).getColorChannels()[1] * 0.0625);
    double oneNegOneTallyBlue = (double) (m.getPixel(1,0).getColorChannels()[2] * 0.0625);

    double oneZeroTallyRed = (double) (m.getPixel(1,1).getColorChannels()[0] * 0.125);
    double oneZeroTallyGreen = (double) (m.getPixel(1,1).getColorChannels()[1] * 0.125);
    double oneZeroTallyBlue = (double) (m.getPixel(1,1).getColorChannels()[2] * 0.125);

    double oneOneTallyRed = (double) (m.getPixel(1,2).getColorChannels()[0] * 0.0625);
    double oneOneTallyGreen = (double) (m.getPixel(1,2).getColorChannels()[1] * 0.0625);
    double oneOneTallyBlue = (double) (m.getPixel(1,2).getColorChannels()[2] * 0.0625);

    double red = zeroNegOneTallyRed
            + zeroZeroTallyRed
            + zeroOneTallyRed
            + oneNegOneTallyRed
            + oneZeroTallyRed
            + oneOneTallyRed;
    System.out.println(zeroNegOneTallyRed);
    System.out.println(zeroZeroTallyRed);
    System.out.println(zeroOneTallyRed);
    System.out.println(oneNegOneTallyRed);
    System.out.println(oneZeroTallyRed);
    System.out.println(oneOneTallyRed);
    double green = zeroNegOneTallyGreen
            + zeroZeroTallyGreen
            + zeroOneTallyGreen
            + oneNegOneTallyGreen
            + oneZeroTallyGreen
            + oneOneTallyGreen;
    double blue = zeroNegOneTallyBlue
            + zeroZeroTallyBlue
            + zeroOneTallyBlue
            + oneNegOneTallyBlue
            + oneZeroTallyBlue
            + oneOneTallyBlue;

    System.out.println(red + " " + green + " " + blue);
    assertEquals((int) red, newPixel.getColorChannels()[0]);
    assertEquals((int) green, newPixel.getColorChannels()[1]);
    assertEquals((int) blue, newPixel.getColorChannels()[2]);
  }
}