import org.junit.Before;
import org.junit.Test;

import model.ImageModel;
import model.ImageProcessorModel;
import model.component.BlueComponent;
import model.component.BrightenComponent;
import model.component.GreenComponent;
import model.component.IntensityComponent;
import model.component.LumaComponent;
import model.component.RedComponent;
import model.component.ValueComponent;
import model.flip.HorizontalFlip;
import model.flip.VerticalFlip;
import model.pixel.Pixel;
import model.pixel.RGBPixel;
import utilities.ImageUtil;

import static org.junit.Assert.assertEquals;

/**
 * A test class to test the intricacies of the ImageModel.
 */
public class ImageModelTest {
  ImageProcessorModel m1;
  ImageProcessorModel m2;

  @Before
  public void setUp() {
    m1 = ImageUtil.readPPM("src/images/Koala.ppm");
    m2 = m1.getCopy();
  }

  // TEST CONSTRUCTOR:

  // Constructor throws error if pixels are null.
  @Test(expected = IllegalArgumentException.class)
  public void testNullPixels() {
    new ImageModel(null, 100, 200, 255);
  }

  // Constructor throws error if width is negative.
  @Test(expected = IllegalArgumentException.class)
  public void testNegativeWidth() {
    new ImageModel(new Pixel[100][100], -100, 100, 255);
  }

  // Constructor throws error if height is negative.
  @Test(expected = IllegalArgumentException.class)
  public void testNegativeHeight() {
    new ImageModel(new Pixel[100][100], 100, -100, 255);
  }

  // Constructor throws error if max value is negative.
  @Test(expected = IllegalArgumentException.class)
  public void testNegativeMaxValue() {
    new ImageModel(new Pixel[100][100], 100, 100, -255);
  }

  // Constructor builds itself properly.
  @Test
  public void testConstructor() {
    ImageProcessorModel model =
            new ImageModel(new Pixel[100][100], 100, 100, 255);

    assertEquals(model.getWidth(), 100);
    assertEquals(model.getHeight(), 100);
    assertEquals(model.getMaxValue(), 255);
  }
  /*
    // TESTS SAVEIMAGE(STRING PATH)

    // Method throws error if the provided path does not exist.
    @Test(expected = IllegalStateException.class)
    public void testCannotWriteFile() {
      m1.saveImage("src/fake/KoalaNew.ppm");
    }

    // Test to see if image is saved properly.
    @Test
    public void testSaveImage() {
      m1.saveImage("src/images/TestSave.ppm");
      ImageProcessorModel model = ImageUtil.readPPM("src/images/TestSave.ppm");

      for (int row = 0; row < m1.getHeight(); row++) {
        for (int col = 0; col < m1.getWidth(); col++) {
          assertEquals(m2.getPixel(row, col), (model.getPixel(row, col)));
        }
      }
    }
  */
  // TEST COMPONENT

  // Testing red gray scale:
  @Test
  public void testRed() {
    m1.component(new RedComponent());

    for (int row = 0; row < m1.getHeight(); row++) {
      for (int col = 0; col < m1.getWidth(); col++) {
        assertEquals(m1.getPixel(row, col).getColorChannels()[0],
                m2.getPixel(row, col).getColorChannels()[0]);
        assertEquals(m1.getPixel(row, col).getColorChannels()[1],
                m2.getPixel(row, col).getColorChannels()[0]);
        assertEquals(m1.getPixel(row, col).getColorChannels()[2],
                m2.getPixel(row, col).getColorChannels()[0]);
      }
    }
  }

  // Testing blue gray scale:
  @Test
  public void testGreen() {
    m1.component(new GreenComponent());

    for (int row = 0; row < m1.getHeight(); row++) {
      for (int col = 0; col < m1.getWidth(); col++) {
        assertEquals(m1.getPixel(row, col).getColorChannels()[0],
                m2.getPixel(row, col).getColorChannels()[1]);
        assertEquals(m1.getPixel(row, col).getColorChannels()[1],
                m2.getPixel(row, col).getColorChannels()[1]);
        assertEquals(m1.getPixel(row, col).getColorChannels()[2],
                m2.getPixel(row, col).getColorChannels()[1]);
      }
    }
  }

  // Testing blue gray scale:
  @Test
  public void testBlue() {
    m1.component(new BlueComponent());

    for (int row = 0; row < m1.getHeight(); row++) {
      for (int col = 0; col < m1.getWidth(); col++) {
        assertEquals(m1.getPixel(row, col).getColorChannels()[0],
                m2.getPixel(row, col).getColorChannels()[2]);
        assertEquals(m1.getPixel(row, col).getColorChannels()[1],
                m2.getPixel(row, col).getColorChannels()[2]);
        assertEquals(m1.getPixel(row, col).getColorChannels()[2],
                m2.getPixel(row, col).getColorChannels()[2]);
      }
    }
  }

  // Testing value component:
  @Test
  public void testValueComponent() {
    m1.component(new ValueComponent());

    for (int row = 0; row < m1.getHeight(); row++) {
      for (int col = 0; col < m1.getWidth(); col++) {
        int maxSoFar = 0;
        for (int channel : m2.getPixel(row, col).getColorChannels()) {
          if (channel > maxSoFar) {
            maxSoFar = channel;
          }
        }
        assertEquals(m1.getPixel(row, col).getColorChannels()[0], maxSoFar);
        assertEquals(m1.getPixel(row, col).getColorChannels()[1], maxSoFar);
        assertEquals(m1.getPixel(row, col).getColorChannels()[2], maxSoFar);
      }
    }
  }

  // Testing luma component:
  @Test
  public void testLumaComponent() {
    m1.component(new LumaComponent());

    for (int row = 0; row < m1.getHeight(); row++) {
      for (int col = 0; col < m1.getWidth(); col++) {
        int[] channels = m2.getPixel(row, col).getColorChannels();
        int luma = (int) (0.2126 * channels[0] + 0.7152 * channels[1] + 0.0722 * channels[2]);

        assertEquals(m1.getPixel(row, col).getColorChannels()[0], luma);
        assertEquals(m1.getPixel(row, col).getColorChannels()[1], luma);
        assertEquals(m1.getPixel(row, col).getColorChannels()[2], luma);
      }
    }
  }

  // Testing intensity component:
  @Test
  public void testIntensityComponent() {
    m1.component(new IntensityComponent());

    for (int row = 0; row < m1.getHeight(); row++) {
      for (int col = 0; col < m1.getWidth(); col++) {
        int accum = 0;
        for (int channel : m2.getPixel(row, col).getColorChannels()) {
          accum += channel;
        }
        accum = accum / 3;

        assertEquals(m1.getPixel(row, col).getColorChannels()[0], accum);
        assertEquals(m1.getPixel(row, col).getColorChannels()[1], accum);
        assertEquals(m1.getPixel(row, col).getColorChannels()[2], accum);
      }
    }
  }

  // Test brighten component (positive brightness):
  @Test
  public void testBrightenComponentPositive() {
    m1.component(new BrightenComponent(100));

    for (int row = 0; row < m1.getHeight(); row++) {
      for (int col = 0; col < m1.getWidth(); col++) {
        Pixel p = m2.getPixel(row, col);
        int red = this.cappedSum(100,
                p.getColorChannels()[0],
                p.getMaxValue());
        int green = this.cappedSum(100,
                p.getColorChannels()[1],
                p.getMaxValue());
        int blue = this.cappedSum(100,
                p.getColorChannels()[2],
                p.getMaxValue());

        assertEquals(m1.getPixel(row, col).getColorChannels()[0], red);
        assertEquals(m1.getPixel(row, col).getColorChannels()[1], green);
        assertEquals(m1.getPixel(row, col).getColorChannels()[2], blue);
      }
    }
  }

  // Test brighten component (brightness = 0):
  @Test
  public void testBrightenComponentZero() {
    m1.component(new BrightenComponent(0));

    for (int row = 0; row < m1.getHeight(); row++) {
      for (int col = 0; col < m1.getWidth(); col++) {
        assertEquals(m1.getPixel(row, col).getColorChannels()[0],
                m2.getPixel(row, col).getColorChannels()[0]);
        assertEquals(m1.getPixel(row, col).getColorChannels()[1],
                m2.getPixel(row, col).getColorChannels()[1]);
        assertEquals(m1.getPixel(row, col).getColorChannels()[2],
                m2.getPixel(row, col).getColorChannels()[2]);
      }
    }
  }

  // Test brighten component (negative brightness):
  @Test
  public void testBrightenComponentNegative() {
    m1.component(new BrightenComponent(-100));

    for (int row = 0; row < m1.getHeight(); row++) {
      for (int col = 0; col < m1.getWidth(); col++) {
        Pixel p = m2.getPixel(row, col);
        int red = this.cappedSum(-100,
                p.getColorChannels()[0],
                p.getMaxValue());
        int green = this.cappedSum(-100,
                p.getColorChannels()[1],
                p.getMaxValue());
        int blue = this.cappedSum(-100,
                p.getColorChannels()[2],
                p.getMaxValue());

        assertEquals(m1.getPixel(row, col).getColorChannels()[0], red);
        assertEquals(m1.getPixel(row, col).getColorChannels()[1], green);
        assertEquals(m1.getPixel(row, col).getColorChannels()[2], blue);
      }
    }
  }

  private int cappedSum(int increment, int value, int maxValue) {
    if (value + increment > maxValue) {
      return maxValue;
    } else if (value + increment < 0) {
      return 0;
    }
    return value + increment;
  }

  // Test that brighten caps pixels at 255.
  @Test
  public void testBrightenComponentCap() {
    m1.component(new BrightenComponent(255));

    for (int row = 0; row < m1.getHeight(); row++) {
      for (int col = 0; col < m1.getWidth(); col++) {
        assertEquals(m1.getPixel(row, col).getColorChannels()[0], 255);
        assertEquals(m1.getPixel(row, col).getColorChannels()[1], 255);
        assertEquals(m1.getPixel(row, col).getColorChannels()[2], 255);
      }
    }
  }

  // Test that brighten minimizes pixels at 0.
  @Test
  public void testBrightenComponentMinimum() {
    m1.component(new BrightenComponent(-255));

    for (int row = 0; row < m1.getHeight(); row++) {
      for (int col = 0; col < m1.getWidth(); col++) {
        assertEquals(m1.getPixel(row, col).getColorChannels()[0], 0);
        assertEquals(m1.getPixel(row, col).getColorChannels()[1], 0);
        assertEquals(m1.getPixel(row, col).getColorChannels()[2], 0);
      }
    }
  }

  // TEST FLIP(FLIP FLIP):

  // Test a horizontal flip:
  @Test
  public void testHorizontalFlip() {
    m1.flip(new HorizontalFlip());

    for (int row = 0; row < m1.getHeight(); row++) {
      for (int col = 0; col < m1.getWidth(); col++) {
        Pixel p = m2.getPixel(row, m1.getWidth() - 1 - col);
        assertEquals(m1.getPixel(row, col), p);
      }
    }
  }

  // Test a vertical flip:
  @Test
  public void testVerticalFlip() {
    m1.flip(new VerticalFlip());

    for (int row = 0; row < m1.getHeight(); row++) {
      for (int col = 0; col < m1.getWidth(); col++) {
        Pixel p = m2.getPixel(m1.getHeight() - 1 - row, col);
        assertEquals(m1.getPixel(row, col), p);
      }
    }
  }

  // Test a horizontal than vertical flip:
  @Test
  public void testHorizontalVerticalFlip() {
    m1.flip(new HorizontalFlip());
    m1.flip(new VerticalFlip());

    for (int row = 0; row < m1.getHeight(); row++) {
      for (int col = 0; col < m1.getWidth(); col++) {
        Pixel p = m2.getPixel(m1.getHeight() - 1 - row, m1.getWidth() - 1 - col);
        assertEquals(m1.getPixel(row, col), p);
      }
    }
  }

  // TEST GETPIXEL(INT ROW, INT COL):

  // Test error thrown if row is less than 0.
  @Test(expected = IllegalArgumentException.class)
  public void testGetPixelRowLessThanZero() {
    m1.getPixel(-1, 200);
  }

  // Test error thrown if row is greater than or equals to height.
  @Test(expected = IllegalArgumentException.class)
  public void testGetPixelRowGreaterThanHeight() {
    m1.getPixel(800, 200);
  }

  // Test error thrown if col is less than 0.
  @Test(expected = IllegalArgumentException.class)
  public void testGetPixelColLessThanZero() {
    m1.getPixel(200, -1);
  }

  // Test error thrown if col is greater than width.
  @Test(expected = IllegalArgumentException.class)
  public void testGetPixelColGreaterThanWidth() {
    m1.getPixel(200, 1025);
  }

  // Test get pixel.
  @Test
  public void testGetPixel() {
    assertEquals(m1.getPixel(0, 0),
            new RGBPixel(230, 225, 222, 255));
    assertEquals(m1.getPixel(0, 1),
            new RGBPixel(229, 225, 222, 255));
    assertEquals(m1.getPixel(100, 100),
            new RGBPixel(81, 170, 110, 255));
  }

  // TEST GETCOPY():

  // Test that a copy of a PPM model is created.
  @Test
  public void testGetCopy() {
    ImageProcessorModel model1 = m1.getCopy();
    assertEquals(model1, m1);
  }

  // TEST TOFILE():
  @Test
  public void testToFile() {
    String file = m1.toFile();
    String[] fileArray = file.split("\n");

    assertEquals(fileArray[0], "P3"); // file call
    assertEquals(fileArray[1], m1.getWidth() + " " + m1.getHeight()); // width and height
    assertEquals(fileArray[2], "255"); // max value

    int counter = 3;

    for (int row = 0; row < m1.getHeight(); row++) {
      for (int col = 0; col < m1.getWidth(); col++) {
        assertEquals(fileArray[counter],
                String.valueOf(m1.getPixel(row, col).getColorChannels()[0]));
        counter++;
        assertEquals(fileArray[counter],
                String.valueOf(m1.getPixel(row, col).getColorChannels()[1]));
        counter++;
        assertEquals(fileArray[counter],
                String.valueOf(m1.getPixel(row, col).getColorChannels()[2]));
        counter++;
      }
    }
  }

  // TEST GETMETHODS:

  @Test
  public void testGetWidth() {
    assertEquals(m1.getWidth(), m1.getWidth());
  }

  @Test
  public void testGetHeight() {
    assertEquals(m1.getHeight(), m1.getHeight());
  }

  @Test
  public void testGetMaxValue() {
    assertEquals(m1.getMaxValue(), 255);
  }

  // TEST EQUALS METHOD:

  @Test
  public void testEquals() {

    // Checks to see if equals passes.
    assertEquals(m1, m2);

    // Show why they are equal.
    assertEquals(m1.getWidth(), m2.getWidth());
    assertEquals(m1.getHeight(), m2.getHeight());
    assertEquals(m1.getMaxValue(), m2.getMaxValue());

    for (int row = 0; row < m1.getHeight(); row++) {
      for (int col = 0; col < m1.getWidth(); col++) {
        assertEquals(m1.getPixel(row, col), m2.getPixel(row, col));
      }
    }
  }
}