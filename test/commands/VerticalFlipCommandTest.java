package commands;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import controller.commands.VerticalFlipCommand;
import model.ImprovedImageProcessorModel;
import model.pixel.Pixel;
import utilities.ImageRunTimeStorage;
import utilities.ImageUtil;

import static org.junit.Assert.assertEquals;


/**
 * commands.VerticalFlipCommandTest tests the command that flips an image vertically.
 */
public class VerticalFlipCommandTest {

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
  @Test (expected = IllegalArgumentException.class)
  public void testConstructorNameNull() {
    new VerticalFlipCommand(null, "", storage);
  }

  // Error thrown in constructor when newName is null.
  @Test (expected = IllegalArgumentException.class)
  public void testConstructorNewNameNull() {
    new VerticalFlipCommand("", null, storage);
  }

  // Error thrown in constructor when map is null.
  @Test (expected = IllegalArgumentException.class)
  public void testConstructorMapNull() {
    new VerticalFlipCommand("", "", null);
  }

  // Error thrown if model cannot be found for getCopy:
  @Test (expected = IllegalStateException.class)
  public void testVerticalFlipCommandGetCopyFail() {
    storage.addToRunTimeStorage("koala", m);
    new VerticalFlipCommand(
            "koala123", "koala-updated", storage).getModelCopy();
  }

  // Test Vertical Flip Command (updates the pixels to their vertical flip locations):
  @Test
  public void testVerticalFlipCommand() {
    String output = new VerticalFlipCommand("", "", storage).use(m);

    for (int row = 0; row < m.getHeight(); row++) {
      for (int col = 0; col < m.getWidth(); col++) {
        Pixel p = mCopy.getPixel(m.getHeight() - 1 - row, col);
        assertEquals(m.getPixel(row, col), p);
      }
    }
    assertEquals(output, " has been flipped vertically! Now called: ");
  }

  // Test Blue Component Command to create a copy of the model in reference.
  @Test
  public void testVerticalFlipCommandGetCopy() {
    storage.addToRunTimeStorage("koala", m);
    ImprovedImageProcessorModel model = new VerticalFlipCommand(
            "koala", "koala-updated", storage).getModelCopy();

    assertEquals(model, m);

    // will be the same for right now, copy has not been updated yet.
    assertEquals(storage.grabFromRunTimeStorage("koala-updated"), m);
  }


}