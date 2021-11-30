package commands;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import controller.commands.SaveCommand;
import model.ImprovedImageProcessorModel;
import utilities.ImageRunTimeStorage;
import utilities.ImageUtil;

import static org.junit.Assert.assertEquals;

/**
 * commands.SaveCommandTest tests the command that saves images to file,
 * both that it works and fails properly.
 */
public class SaveCommandTest {

  ImprovedImageProcessorModel m;
  ImprovedImageProcessorModel mCopy;
  ImageRunTimeStorage storage;

  @Before
  public void setUp() {
    m = ImageUtil.readPPM("src/images/Koala.ppm");
    mCopy = m.getCopy();
    storage = new ImageRunTimeStorage(new HashMap<>());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testConstructorPathNull() {
    new SaveCommand(null, "", storage);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testConstructorNameNull() {
    new SaveCommand("", null, storage);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testConstructorMapNull() {
    new SaveCommand("", "", null);
  }

  @Test (expected = IllegalStateException.class)
  public void testSaveCommandGetCopyFail() {
    storage.addToRunTimeStorage("koala", m);
    new SaveCommand(
            "src/images/KoalaNew.ppm", "koala123", storage).getModelCopy();
  }

  @Test
  public void testSaveCommandGetCopy() {
    storage.addToRunTimeStorage("koala", m);
    ImprovedImageProcessorModel model = new SaveCommand(
            "src/images/KoalaNew.ppm", "koala", storage).getModelCopy();

    assertEquals(model, m);
  }

  @Test
  public void testSaveCommandUse() {
    storage.addToRunTimeStorage("koala", m);

    String output = new SaveCommand(
            "src/images/KoalaTest.ppm", "koala", storage).use(m);

    ImprovedImageProcessorModel model = ImageUtil.readPPM("src/images/KoalaTest.ppm");

    for (int row = 0; row < m.getHeight(); row++) {
      for (int col = 0; col < m.getWidth(); col++) {
        assertEquals(mCopy.getPixel(row, col), (model.getPixel(row, col)));
      }
    }
    assertEquals(output, "koala has been saved successfully to: src/images/KoalaTest.ppm");
  }

  @Test
  public void testSaveCommandUseForOtherFileType() {
    storage.addToRunTimeStorage("koala", m);

    String output = new SaveCommand(
            "src/images/KoalaTest.png", "koala", storage).use(m);

    ImprovedImageProcessorModel model =
            ImageUtil.readOtherFiles("src/images/KoalaTest.png");

    for (int row = 0; row < m.getHeight(); row++) {
      for (int col = 0; col < m.getWidth(); col++) {
        assertEquals(mCopy.getPixel(row, col), (model.getPixel(row, col)));
      }
    }
    assertEquals(output, "koala has been saved successfully to: src/images/KoalaTest.png");
  }
}