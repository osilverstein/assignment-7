package commands;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import controller.commands.Command;
import model.ImprovedImageProcessorModel;
import utilities.ImageRunTimeStorage;
import utilities.ImageUtil;

import static org.junit.Assert.assertEquals;

/**
 * The commands.AbstractCommandTest method allows for abstract testing of the Command Design.
 */
public abstract class AbstractCommandTest {
  ImprovedImageProcessorModel m;
  ImprovedImageProcessorModel mCopy;

  @Before
  public void setUp() {
    m = ImageUtil.readPPM("src/images/Koala.ppm");
    mCopy = m.getCopy();
  }

  abstract Command createCommand(String name, String newName, ImageRunTimeStorage map);

  abstract int[] getInts(int row, int col);

  abstract String returnString();

  // Constructor throws error if the name is null:
  @Test (expected = IllegalArgumentException.class)
  public void testCommandConstructorNameNull() {
    this.createCommand(null, "", new ImageRunTimeStorage(new HashMap<>()));
  }

  // Constructor throws error if the newName is null:
  @Test (expected = IllegalArgumentException.class)
  public void testCommandConstructorNewNameNull() {
    this.createCommand("", null, new ImageRunTimeStorage(new HashMap<>()));
  }

  // Constructor throws error if the map is null:
  @Test (expected = IllegalArgumentException.class)
  public void testCommandConstructorMapNull() {
    this.createCommand("", "", null);
  }

  // Check to see if error is thrown if a copy cannot be created:
  @Test (expected = IllegalStateException.class)
  public void testCommandCopyFail() {
    ImageRunTimeStorage storage = new ImageRunTimeStorage(new HashMap<>());
    ImprovedImageProcessorModel m = ImageUtil.readPPM("src/images/Koala.ppm");

    storage.addToRunTimeStorage("koala", m);
    this.createCommand("koala123", "koala-updated", storage).getModelCopy();
  }

  // Test Blue Component Command (updates all pixels to a blue gray scale):
  @Test
  public void testAbstractComponentCommand() {
    ImageRunTimeStorage storage = new ImageRunTimeStorage(new HashMap<>());
    ImprovedImageProcessorModel m = ImageUtil.readPPM("src/images/Koala.ppm");

    String output = this.createCommand("", "", storage).use(m);

    int errors = 0;

    for (int row = 0; row < m.getHeight(); row++) {
      for (int col = 0; col < m.getWidth(); col++) {
        //try {
        assertEquals(m.getPixel(row, col).getColorChannels()[0], this.getInts(row, col)[0]);
        assertEquals(m.getPixel(row, col).getColorChannels()[1], this.getInts(row, col)[1]);
        assertEquals(m.getPixel(row, col).getColorChannels()[2], this.getInts(row, col)[2]);
        /*} catch (AssertionError e) {
          errors++;
        }*/
      }
    }
    System.out.println("Errors: " + String.valueOf(errors));
    assertEquals(output, this.returnString());
  }

  // Test Blue Component Command to create a copy of the model in reference.
  @Test
  public void testBlueComponentCommandGetCopy() {
    ImageRunTimeStorage storage = new ImageRunTimeStorage(new HashMap<>());
    ImprovedImageProcessorModel m = ImageUtil.readPPM("src/images/Koala.ppm");

    storage.addToRunTimeStorage("koala", m);
    ImprovedImageProcessorModel model =
            this.createCommand("koala", "koala-updated", storage).getModelCopy();

    assertEquals(model, m);

    // will be the same for right now, copy has not been updated yet.
    assertEquals(storage.grabFromRunTimeStorage("koala-updated"), m);
  }
}
