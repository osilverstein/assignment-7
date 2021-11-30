import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import model.ImprovedImageModel;
import model.ImprovedImageProcessorModel;
import model.pixel.Pixel;
import utilities.ImageRunTimeStorage;

import static org.junit.Assert.assertEquals;

/**
 * ImageRunTimeStorageTest tests that the storage functionality of the models is proper.
 */
public class ImageRunTimeStorageTest {

  Map<String, ImprovedImageProcessorModel> map;
  ImageRunTimeStorage storage;

  @Before
  public void setUp() {
    map = new HashMap<>();
    storage = new ImageRunTimeStorage(map);
  }

  // Error thrown if the runtime storage is null:
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNull() {
    new ImageRunTimeStorage(null);
  }

  // Test addToRunTimeStorage:
  @Test
  public void testAddToRunTimeStorage() {
    assertEquals(map.size(), 0);

    storage.addToRunTimeStorage(
            "koala", new ImprovedImageModel(new Pixel[5][5], 5, 5, 255));

    assertEquals(map.size(), 1);
  }

  // Test grabFromRunTimeStorage:
  @Test
  public void testGrabFromRunTimeStorage() {
    ImprovedImageProcessorModel m = new ImprovedImageModel(new Pixel[5][5], 5, 5, 255);

    storage.addToRunTimeStorage("koala", m);

    assertEquals(storage.grabFromRunTimeStorage("koala"), m);
    assertEquals(storage.grabFromRunTimeStorage("koala123"), null);
  }
}