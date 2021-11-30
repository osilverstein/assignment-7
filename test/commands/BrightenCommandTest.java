package commands;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import controller.commands.BrightenCommand;
import model.ImageProcessorModel;
import model.ImprovedImageProcessorModel;
import model.pixel.Pixel;
import utilities.ImageRunTimeStorage;
import utilities.ImageUtil;

import static org.junit.Assert.assertEquals;


/**
 * commands.BrightenCommandTest tests the command that increments the color channels of an image by
 * a given amount.
 */
public class BrightenCommandTest {

  ImprovedImageProcessorModel m;
  ImprovedImageProcessorModel mCopy;
  ImageRunTimeStorage storage;

  @Before
  public void setUp() {
    m = ImageUtil.readPPM("src/images/Koala.ppm");
    mCopy = m.getCopy();
    storage = new ImageRunTimeStorage(new HashMap<>());
  }

  // Error thrown in constructor when name is null.
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNameNull() {
    new BrightenCommand(10, null, "", storage);
  }

  // Error thrown in constructor when newName is null.
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNewNameNull() {
    new BrightenCommand(10, "", null, storage);
  }

  // Error thrown in constructor when map is null.
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorMapNull() {
    new BrightenCommand(10, "", "", null);
  }

  // Error thrown if model cannot be found for getCopy:
  @Test(expected = IllegalStateException.class)
  public void testBrightenCommandGetCopyFail() {
    storage.addToRunTimeStorage("koala", m);
    new BrightenCommand(
            10, "koala123", "koala-updated", storage).getModelCopy();
  }

  // Test Brighten Command Positive, Negative, and Zero
  // (increments pixel channels by the provided positive increment):
  @Test
  public void testBrightenCommandPositive() {
    int[] array = new int[]{50, -50, 0};

    for (int i : array) {
      this.setUp();
      String output = new BrightenCommand(i, "", "", storage).use(m);

      for (int row = 0; row < m.getHeight(); row++) {
        for (int col = 0; col < m.getWidth(); col++) {
          Pixel p = mCopy.getPixel(row, col);
          int red = this.cappedSum(i,
                  p.getColorChannels()[0],
                  p.getMaxValue());
          int green = this.cappedSum(i,
                  p.getColorChannels()[1],
                  p.getMaxValue());
          int blue = this.cappedSum(i,
                  p.getColorChannels()[2],
                  p.getMaxValue());

          assertEquals(m.getPixel(row, col).getColorChannels()[0], red);
          assertEquals(m.getPixel(row, col).getColorChannels()[1], green);
          assertEquals(m.getPixel(row, col).getColorChannels()[2], blue);
        }
      }
      assertEquals(output, " successfully brightened by "
              + i + ". Now called: ");
    }
  }

  // Test Brighten Command Maximum (increments pixel channels until they reach the
  // provided max value):
  @Test
  public void testBrightenCommandMaxValue() {

    String output = new BrightenCommand(255, "", "", storage).use(m);

    for (int row = 0; row < m.getHeight(); row++) {
      for (int col = 0; col < m.getWidth(); col++) {
        assertEquals(m.getPixel(row, col).getColorChannels()[0], 255);
        assertEquals(m.getPixel(row, col).getColorChannels()[1], 255);
        assertEquals(m.getPixel(row, col).getColorChannels()[2], 255);
      }
    }
    assertEquals(output, " successfully brightened by "
            + 255 + ". Now called: ");
  }

  // Test Brighten Command Minimum (increments pixel channels until they reach the minimum value):
  @Test
  public void testBrightenCommandMinimum() {
    String output = new BrightenCommand(-255, "", "", storage).use(m);

    for (int row = 0; row < m.getHeight(); row++) {
      for (int col = 0; col < m.getWidth(); col++) {
        assertEquals(m.getPixel(row, col).getColorChannels()[0], 0);
        assertEquals(m.getPixel(row, col).getColorChannels()[1], 0);
        assertEquals(m.getPixel(row, col).getColorChannels()[2], 0);
      }
    }
    assertEquals(output, " successfully brightened by "
            + -255 + ". Now called: ");
  }

  @Test
  public void testBrightenCommandGetCopy() {
    storage.addToRunTimeStorage("koala", m);
    ImageProcessorModel model = new BrightenCommand(
            10, "koala", "koala-updated", storage).getModelCopy();

    assertEquals(model, m);

    // will be the same for right now, copy has not been updated yet.
    assertEquals(storage.grabFromRunTimeStorage("koala-updated"), m);
  }

  private int cappedSum(int increment, int value, int maxValue) {
    if (value + increment > maxValue) {
      return maxValue;
    } else if (value + increment < 0) {
      return 0;
    }
    return value + increment;
  }
}