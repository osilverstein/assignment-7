package commands;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import controller.commands.Command;
import controller.commands.MosaicCommand;
import model.ImageProcessorModel;
import model.ImprovedImageModel;
import model.pixel.Pixel;
import utilities.ImageRunTimeStorage;
import utilities.ImageUtil;

/**
 * commands.MosaicCommandTest tests the command that turns an image into a
 * grayscaled image
 * based on the red channel.
 */
public class MosaicCommandTest extends AbstractCommandTest {

  ImprovedImageModel m;
  ImageProcessorModel mCopy;
  ImageRunTimeStorage storage;

  @Before
  public void setUp() {
    m = ImageUtil.readPPM("/Users/oliversilverstein/Documents/GitHub/assignment-7/src/images/Koala.ppm");
    mCopy = m.getCopy();
    storage = new ImageRunTimeStorage(new HashMap<>());
  }

  // Error thrown in constructor when name is null.
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNameNull() {
    new MosaicCommand(30, null, mCopy.toString(), storage);
  }

  // Error thrown in constructor when name is empty.
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNameEmpty() {
    new MosaicCommand(30, "", mCopy.toString(), storage);
  }

  // Error thrown in constructor when newName is null.
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNewNameNull() {
    new MosaicCommand(30, "test", null, storage);
  }

  // Error thrown in constructor when map is null.
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorMapNull() {
    new MosaicCommand(30, "test", mCopy.toString(), null);
  }

  // Error thrown if model cannot be found for getModelCopy:
  @Test(expected = IllegalStateException.class)
  public void testGetModelCopyNull() {
    storage.addToRunTimeStorage("koala", m);
    new MosaicCommand(30, "test", mCopy.toString(), storage).getModelCopy();
  }

  // Test a mosaic with 20 tiles has 20 unique colors.
  @Test
  public void testMosaic20() {
    Command c = new MosaicCommand(20, m.toString(), mCopy.toString(), storage);
    c.use(m);
    // make a set of all the colors in the image
    Set<Pixel> colors = new HashSet<>();
    for (int i = 0; i < m.getWidth(); i++) {
      for (int j = 0; j < m.getHeight(); j++) {
        colors.add(m.getPixel(i, j));
      }
    }
    assertEquals(20, colors.size(), 1);
  }

  @Override
  Command createCommand(String name, String newName, ImageRunTimeStorage map) {
    return new MosaicCommand(1, name, newName, map);
  }

  @Override
  int[] getInts(int row, int col) {
    int r = mCopy.getPixel(row, col).getColorChannels()[0];
    return new int[] { r, r, r };
  }

  @Override
  String returnString() {
    return " set to red gray scale. Now called: ";
  }
}