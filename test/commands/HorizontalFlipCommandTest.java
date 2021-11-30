package commands;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import controller.commands.HorizontalFlipCommand;
import model.ImprovedImageProcessorModel;
import model.pixel.Pixel;
import utilities.ImageRunTimeStorage;
import utilities.ImageUtil;

import static org.junit.Assert.assertEquals;

/**
 * commands.HorizontalFlipCommandTest tests the command that flips an image horizontally.
 */
public class HorizontalFlipCommandTest {

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
    new HorizontalFlipCommand(null, "", storage);
  }

  // Error thrown in constructor when newName is null.
  @Test (expected = IllegalArgumentException.class)
  public void testConstructorNewNameNull() {
    new HorizontalFlipCommand("", null, storage);
  }

  // Error thrown in constructor when map is null.
  @Test (expected = IllegalArgumentException.class)
  public void testConstructorMapNull() {
    new HorizontalFlipCommand("", "", null);
  }

  // Error thrown if model cannot be found for getCopy:
  @Test (expected = IllegalStateException.class)
  public void testHorizontalFlipCommandGetCopyFail() {
    storage.addToRunTimeStorage("koala", m);
    new HorizontalFlipCommand(
            "koala123", "koala-updated", storage).getModelCopy();
  }

  // Test Horizontal Flip Command (updates the pixels to their horizontal flip locations):
  @Test
  public void testHorizontalFlipCommand() {
    String output = new HorizontalFlipCommand("", "", storage).use(m);

    for (int row = 0; row < m.getHeight(); row++) {
      for (int col = 0; col < m.getWidth(); col++) {
        Pixel p = mCopy.getPixel(row, m.getWidth() - 1 - col);
        assertEquals(m.getPixel(row, col), p);
      }
    }
    assertEquals(output, " has been flipped horizontally! Now called: ");
  }

  // Test Blue Component Command to create a copy of the model in reference.
  @Test
  public void testHorizontalFlipCommandGetCopy() {
    storage.addToRunTimeStorage("koala", m);
    ImprovedImageProcessorModel model = new HorizontalFlipCommand(
            "koala", "koala-updated", storage).getModelCopy();

    assertEquals(model, m);

    // will be the same for right now, copy has not been updated yet.
    assertEquals(storage.grabFromRunTimeStorage("koala-updated"), m);
  }
}