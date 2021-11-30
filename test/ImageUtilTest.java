import org.junit.Before;
import org.junit.Test;

import model.ImprovedImageProcessorModel;
import utilities.ImageUtil;

import static org.junit.Assert.assertEquals;


/**
 * ImageUtilTest tests if the ImageUtilTest throws IllegalStateExceptions properly.
 */
public class ImageUtilTest {
  ImprovedImageProcessorModel m1;
  ImprovedImageProcessorModel m2;
  ImprovedImageProcessorModel m3;

  @Before
  public void setUp() {
    m1 = ImageUtil.readPPM("src/images/Koala.ppm");
    m2 = m1.getCopy();
    m3 = ImageUtil.readOtherFiles("src/images/Koala.png");
  }

  // Can save a PPM to a PPM
  @Test
  public void testSavePPM() {
    ImageUtil.saveImage("src/images/TestSave.ppm", m1);
    ImprovedImageProcessorModel model = ImageUtil.readPPM("src/images/TestSave.ppm");

    for (int row = 0 ; row < m1.getHeight() ; row++) {
      for (int col = 0 ; col < m1.getWidth() ; col++) {
        assertEquals(m2.getPixel(row, col), (model.getPixel(row, col)));
      }
    }
  }

  // Can save a PNG from a PPM
  @Test
  public void testSavePNGFromPPM() {
    ImageUtil.saveImage("src/images/TestSave.png", m1);
    ImprovedImageProcessorModel model = ImageUtil.readOtherFiles("src/images/TestSave.png");

    for (int row = 0 ; row < m1.getHeight() ; row++) {
      for (int col = 0 ; col < m1.getWidth() ; col++) {
        assertEquals(m2.getPixel(row, col), (model.getPixel(row, col)));
      }
    }
  }

  // Can save a PPM from a PNG
  @Test
  public void testSavePPMFromPNG() {
    ImageUtil.saveImage("src/images/TestSave.ppm", m3);
    ImprovedImageProcessorModel model = ImageUtil.readPPM("src/images/TestSave.ppm");

    for (int row = 0 ; row < m1.getHeight() ; row++) {
      for (int col = 0 ; col < m1.getWidth() ; col++) {
        assertEquals(m2.getPixel(row, col), (model.getPixel(row, col)));
      }
    }
  }

  @Test(expected = IllegalStateException.class)
  public void testSavePPMFails() {
    ImageUtil.saveImage("src/image/ASDFHSI.ppm", m1);
  }

  @Test(expected = IllegalStateException.class)
  public void testSavePNGFails() {
    ImageUtil.saveImage("src/image/Koala.ppm", m1);
  }

  // Can save a PNG from a PNG
  @Test
  public void testSaveAllOtherFiles() {
    ImageUtil.saveImage("src/images/TestSave.png", m1);
    ImprovedImageProcessorModel model = ImageUtil.readOtherFiles("src/images/TestSave.png");

    for (int row = 0 ; row < m1.getHeight() ; row++) {
      for (int col = 0 ; col < m1.getWidth() ; col++) {
        assertEquals(m2.getPixel(row, col), (model.getPixel(row, col)));
      }
    }
  }

  // Error thrown if filename cannot be found:
  @Test(expected = IllegalStateException.class)
  public void testReadPPMFileNotFound() {
    ImageUtil.readPPM("src/images/Testing.ppm");
  }

  @Test
  public void testReadPPMWorks() {
    ImprovedImageProcessorModel m = ImageUtil.readPPM("src/images/Koala.ppm");
    int totalPixels = 0;
    for (int row = 0; row < m.getHeight(); row++) {
      for (int col = 0; col < m.getWidth(); col++) {
        try {
          m.getPixel(row, col);
          totalPixels++;
        } catch (IllegalStateException e) {
          continue;
        }
      }
    }
    assertEquals(200, m.getHeight());
    assertEquals(200, m.getWidth());
    assertEquals(255, m.getMaxValue());
    assertEquals(totalPixels, m.getHeight() * m.getWidth());
  }

  @Test(expected = NullPointerException.class)
  public void testReadPPMWithOtherFilesWorks() {
    ImprovedImageProcessorModel m = ImageUtil.readOtherFiles("src/images/Koala.ppm");
  }

  @Test
  public void testReadOtherFilesWorks() {
    ImprovedImageProcessorModel m = ImageUtil.readOtherFiles("src/images/Koala.png");
    int totalPixels = 0;
    for (int row = 0; row < m.getHeight(); row++) {
      for (int col = 0; col < m.getWidth(); col++) {
        try {
          m.getPixel(row, col);
          totalPixels++;
        } catch (IllegalStateException e) {
          continue;
        }
      }
    }
    assertEquals(200, m.getHeight());
    assertEquals(200, m.getWidth());
    assertEquals(255, m.getMaxValue());
    assertEquals(totalPixels, m.getHeight() * m.getWidth());
  }
}