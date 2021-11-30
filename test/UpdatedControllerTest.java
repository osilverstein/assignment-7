import org.junit.Test;

import java.io.StringReader;
import java.util.HashMap;

import controller.ImageProcessorController;
import controller.UpdatedController;
import model.ImprovedImageProcessorModel;
import model.Kernel;
import model.Matrix;
import model.pixel.Pixel;
import model.pixel.RGBPixel;
import utilities.ImageRunTimeStorage;
import utilities.ImageUtil;
import view.ImageProcessorTextView;
import view.ImageProcessorView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * A test class to ensure the controller functions and fails properly.
 * A summary of the tests below:
 * - Constructor fails any way possible DONE
 * - Constructor works DONE
 * - Every command works as intended DONE
 * - Every command doesn't work DONE
 * - if you don't load first DONE
 * - Unknown commands DONE
 * - quitting DONE
 * - overriding horizontal-flip koala koala DONE
 */
public class UpdatedControllerTest {

  @Test(expected = IllegalArgumentException.class)
  public void controllerConstructorFails1() {
    ImageRunTimeStorage map = null;
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("");
    ImageProcessorController c = new UpdatedController(map, view, in);
  }

  @Test(expected = IllegalArgumentException.class)
  public void controllerConstructorFails2() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = null;
    Readable in = new StringReader("");
    ImageProcessorController c = new UpdatedController(map, view, in);
  }

  @Test(expected = IllegalArgumentException.class)
  public void controllerConstructorFails3() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = null;
    ImageProcessorController c = new UpdatedController(map, view, in);
  }

  @Test
  public void controllerConstructorWorksAnd() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("");
    ImageProcessorController c = new UpdatedController(map, view, in);
    assertFalse(c == null);
  }

  @Test
  public void testUseImageProcessorUnknown1() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("koala src/images/Koala.ppm koala");
    ImageProcessorController c = new UpdatedController(map, view, in);
    c.useImageProcessor();
    assertEquals("Unknown Command\n" +
            "Unknown Command\n" +
            "Unknown Command\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorUnknown2() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " koala src/images/Koala.ppm koala");
    ImageProcessorController c = new UpdatedController(map, view, in);
    c.useImageProcessor();
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
            "Unknown Command\n" +
            "Unknown Command\n" +
            "Unknown Command\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorQuitting1() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("quit src/images/Koala.ppm koala");
    ImageProcessorController c = new UpdatedController(map, view, in);
    c.useImageProcessor();
    assertEquals("Quitting\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorQuitting2() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " q src/images/Koala.ppm koala");
    ImageProcessorController c = new UpdatedController(map, view, in);
    c.useImageProcessor();
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
            "Quitting\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorLoad() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala");
    ImageProcessorController c = new UpdatedController(map, view, in);
    assertTrue(map.grabFromRunTimeStorage("koala") == null);
    c.useImageProcessor();
    assertFalse(map.grabFromRunTimeStorage("koala") == null);
  }

  @Test
  public void testUseImageProcessorLoadPNG() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.png koala");
    ImageProcessorController c = new UpdatedController(map, view, in);
    assertTrue(map.grabFromRunTimeStorage("koala") == null);
    c.useImageProcessor();
    assertFalse(map.grabFromRunTimeStorage("koala") == null);
  }

  @Test
  public void testUseImageProcessorLoadCorrectImage() {
    ImprovedImageProcessorModel m = ImageUtil.readPPM("src/images/Koala.ppm");

    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala");
    ImageProcessorController c = new UpdatedController(map, view, in);
    c.useImageProcessor();

    ImprovedImageProcessorModel model = map.grabFromRunTimeStorage("koala");
    for (int row = 0; row < m.getHeight(); row++) {
      for (int col = 0; col < m.getWidth(); col++) {

        assertEquals(m.getPixel(row,col), model.getPixel(row, col));
      }
    }
  }

  @Test
  public void testUseImageProcessorLoadCorrectImagePNG() {
    ImprovedImageProcessorModel m = ImageUtil.readOtherFiles("src/images/Koala.png");

    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.png koala");
    ImageProcessorController c = new UpdatedController(map, view, in);
    c.useImageProcessor();

    ImprovedImageProcessorModel model = map.grabFromRunTimeStorage("koala");
    for (int row = 0; row < m.getHeight(); row++) {
      for (int col = 0; col < m.getWidth(); col++) {

        assertEquals(m.getPixel(row,col), model.getPixel(row, col));
      }
    }
  }

  @Test
  public void testUseImageProcessorLoadTwice() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " load src/images/KoalaOverride.ppm koala2");
    ImageProcessorController c = new UpdatedController(map, view, in);
    assertTrue(map.grabFromRunTimeStorage("koala") == null);
    assertTrue(map.grabFromRunTimeStorage("koala2") == null);
    c.useImageProcessor();
    assertFalse(map.grabFromRunTimeStorage("koala") == null);
    assertFalse(map.grabFromRunTimeStorage("koala2") == null);
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
            "Successfully loaded image: koala2 from src/images/KoalaOverride.ppm\n",
            destination.toString());
  }

  @Test
  public void testUseImageProcessorLoadTwicePNG() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.png koala" +
            " load src/images/Koala.png koala2");
    ImageProcessorController c = new UpdatedController(map, view, in);
    assertTrue(map.grabFromRunTimeStorage("koala") == null);
    assertTrue(map.grabFromRunTimeStorage("koala2") == null);
    c.useImageProcessor();
    assertFalse(map.grabFromRunTimeStorage("koala") == null);
    assertFalse(map.grabFromRunTimeStorage("koala2") == null);
    assertEquals("Successfully loaded image: koala from src/images/Koala.png\n" +
                    "Successfully loaded image: koala2 from src/images/Koala.png\n",
            destination.toString());
  }

  @Test
  public void testUseImageProcessorLoadOverride() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " save src/images/KoalaIdentical.ppm koala" +
            " load src/images/KoalaIdentical.ppm koala");
    ImageProcessorController c = new UpdatedController(map, view, in);
    assertTrue(map.grabFromRunTimeStorage("koala") == null);
    c.useImageProcessor();
    assertFalse(map.grabFromRunTimeStorage("koala") == null);
  }

  @Test
  public void testUseImageProcessorLoadOverridePNG() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.png koala" +
            " save src/images/KoalaIdentical.png koala" +
            " load src/images/KoalaIdentical.png koala");
    ImageProcessorController c = new UpdatedController(map, view, in);
    assertTrue(map.grabFromRunTimeStorage("koala") == null);
    c.useImageProcessor();
    assertFalse(map.grabFromRunTimeStorage("koala") == null);
  }

  @Test(expected = IllegalStateException.class)
  public void testUseImageProcessorLoadFails1() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm ");
    ImageProcessorController c = new UpdatedController(map, view, in);
    c.useImageProcessor();
  }

  @Test(expected = IllegalStateException.class)
  public void testUseImageProcessorLoadFails1PNG() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.png ");
    ImageProcessorController c = new UpdatedController(map, view, in);
    c.useImageProcessor();
  }

  @Test
  public void testUseImageProcessorLoadFails2() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Noala.ppm koala");
    ImageProcessorController c = new UpdatedController(map, view, in);
    c.useImageProcessor();
    assertEquals("File src/images/Noala.ppm not found!\n",
            destination.toString());
  }

  @Test
  public void testUseImageProcessorLoadFails2PNG() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Noala.png koala");
    ImageProcessorController c = new UpdatedController(map, view, in);
    c.useImageProcessor();
    assertEquals("Failed to load image!\n",
            destination.toString());
  }

  @Test
  public void testUseImageProcessorLoadFails3() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppn koala");
    ImageProcessorController c = new UpdatedController(map, view, in);
    c.useImageProcessor();
    assertEquals("Failed to load image!\n",
            destination.toString());
  }

  @Test
  public void testUseImageProcessorRed() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " red-component koala koala-red");
    ImageProcessorController c = new UpdatedController(map, view, in);
    assertTrue(map.grabFromRunTimeStorage("koala-red") == null);
    c.useImageProcessor();
    assertFalse(map.grabFromRunTimeStorage("koala-red") == null);
    ImprovedImageProcessorModel koala = map.grabFromRunTimeStorage("koala");
    ImprovedImageProcessorModel koala_red = map.grabFromRunTimeStorage("koala-red");
    for (int row = 0; row < koala.getHeight(); row++) {
      for (int col = 0; col < koala.getWidth(); col++) {
        assertEquals(koala_red.getPixel(row, col).getColorChannels()[0],
                koala.getPixel(row, col).getColorChannels()[0]);
        assertEquals(koala_red.getPixel(row, col).getColorChannels()[1],
                koala.getPixel(row, col).getColorChannels()[0]);
        assertEquals(koala_red.getPixel(row, col).getColorChannels()[2],
                koala.getPixel(row, col).getColorChannels()[0]);
      }
    }
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
            "koala set to red gray scale. Now called: koala-red\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorRedOverride() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " red-component koala koala");
    ImageProcessorController c = new UpdatedController(map, view, in);
    assertTrue(map.grabFromRunTimeStorage("koala") == null);
    c.useImageProcessor();
    assertFalse(map.grabFromRunTimeStorage("koala") == null);
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
            "koala set to red gray scale. Now called: koala\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorRedFails1() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " red-component noala koala-red");
    ImageProcessorController c = new UpdatedController(map, view, in);
    c.useImageProcessor();
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
            "Cannot find model in storage!\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorRedFails2() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("red-component noala koala-red");
    ImageProcessorController c = new UpdatedController(map, view, in);
    c.useImageProcessor();
    assertEquals("Must load an image first!\n" +
            "Unknown Command\n" +
            "Unknown Command\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorGreen() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " green-component koala koala-green");
    ImageProcessorController c = new UpdatedController(map, view, in);
    assertTrue(map.grabFromRunTimeStorage("koala-green") == null);
    c.useImageProcessor();
    assertFalse(map.grabFromRunTimeStorage("koala-green") == null);
    ImprovedImageProcessorModel koala = map.grabFromRunTimeStorage("koala");
    ImprovedImageProcessorModel koala_green = map.grabFromRunTimeStorage("koala-green");
    for (int row = 0; row < koala.getHeight(); row++) {
      for (int col = 0; col < koala.getWidth(); col++) {
        assertEquals(koala_green.getPixel(row, col).getColorChannels()[0],
                koala.getPixel(row, col).getColorChannels()[1]);
        assertEquals(koala_green.getPixel(row, col).getColorChannels()[1],
                koala.getPixel(row, col).getColorChannels()[1]);
        assertEquals(koala_green.getPixel(row, col).getColorChannels()[2],
                koala.getPixel(row, col).getColorChannels()[1]);
      }
    }
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
            "koala set to green gray scale. Now called: koala-green\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorGreenOverride() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " green-component koala koala");
    ImageProcessorController c = new UpdatedController(map, view, in);
    assertTrue(map.grabFromRunTimeStorage("koala") == null);
    c.useImageProcessor();
    assertFalse(map.grabFromRunTimeStorage("koala") == null);
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
            "koala set to green gray scale. Now called: koala\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorGreenFails1() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " green-component noala koala-green");
    ImageProcessorController c = new UpdatedController(map, view, in);
    c.useImageProcessor();
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
            "Cannot find model in storage!\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorGreenFails2() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("green-component noala koala-green");
    ImageProcessorController c = new UpdatedController(map, view, in);
    c.useImageProcessor();
    assertEquals("Must load an image first!\n" +
            "Unknown Command\n" +
            "Unknown Command\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorBlue() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " blue-component koala koala-blue");
    ImageProcessorController c = new UpdatedController(map, view, in);
    assertTrue(map.grabFromRunTimeStorage("koala-blue") == null);
    c.useImageProcessor();
    assertFalse(map.grabFromRunTimeStorage("koala-blue") == null);
    ImprovedImageProcessorModel koala = map.grabFromRunTimeStorage("koala");
    ImprovedImageProcessorModel koala_blue = map.grabFromRunTimeStorage("koala-blue");
    for (int row = 0; row < koala.getHeight(); row++) {
      for (int col = 0; col < koala.getWidth(); col++) {
        assertEquals(koala_blue.getPixel(row, col).getColorChannels()[0],
                koala.getPixel(row, col).getColorChannels()[2]);
        assertEquals(koala_blue.getPixel(row, col).getColorChannels()[1],
                koala.getPixel(row, col).getColorChannels()[2]);
        assertEquals(koala_blue.getPixel(row, col).getColorChannels()[2],
                koala.getPixel(row, col).getColorChannels()[2]);
      }
    }
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
            "koala set to blue gray scale. Now called: koala-blue\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorBlueOverride() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " blue-component koala koala");
    ImageProcessorController c = new UpdatedController(map, view, in);
    assertTrue(map.grabFromRunTimeStorage("koala") == null);
    c.useImageProcessor();
    assertFalse(map.grabFromRunTimeStorage("koala") == null);
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
            "koala set to blue gray scale. Now called: koala\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorBlueFails1() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " blue-component noala koala-blue");
    ImageProcessorController c = new UpdatedController(map, view, in);
    c.useImageProcessor();
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
            "Cannot find model in storage!\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorBlueFails2() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("blue-component noala koala-blue");
    ImageProcessorController c = new UpdatedController(map, view, in);
    c.useImageProcessor();
    assertEquals("Must load an image first!\n" +
            "Unknown Command\n" +
            "Unknown Command\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorLuma() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " luma-component koala koala-luma");
    ImageProcessorController c = new UpdatedController(map, view, in);
    assertTrue(map.grabFromRunTimeStorage("koala-luma") == null);
    c.useImageProcessor();
    assertFalse(map.grabFromRunTimeStorage("koala-luma") == null);
    ImprovedImageProcessorModel koala = map.grabFromRunTimeStorage("koala");
    ImprovedImageProcessorModel koala_luma = map.grabFromRunTimeStorage("koala-luma");
    for (int row = 0; row < koala.getHeight(); row++) {
      for (int col = 0; col < koala.getWidth(); col++) {
        int[] channels = koala.getPixel(row, col).getColorChannels();
        int luma = (int) (0.2126 * channels[0] + 0.7152 * channels[1] + 0.0722 * channels[2]);
        assertEquals(koala_luma.getPixel(row, col).getColorChannels()[0],
                luma);
        assertEquals(koala_luma.getPixel(row, col).getColorChannels()[1],
                luma);
        assertEquals(koala_luma.getPixel(row, col).getColorChannels()[2],
                luma);
      }
    }
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
            "koala set to luma gray scale. Now called: koala-luma\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorLumaOverride() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " luma-component koala koala");
    ImageProcessorController c = new UpdatedController(map, view, in);
    assertTrue(map.grabFromRunTimeStorage("koala") == null);
    c.useImageProcessor();
    assertFalse(map.grabFromRunTimeStorage("koala") == null);
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
            "koala set to luma gray scale. Now called: koala\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorLumaFails1() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " luma-component noala koala-luma");
    ImageProcessorController c = new UpdatedController(map, view, in);
    c.useImageProcessor();
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
            "Cannot find model in storage!\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorLumaFails2() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("luma-component noala koala-luma");
    ImageProcessorController c = new UpdatedController(map, view, in);
    c.useImageProcessor();
    assertEquals("Must load an image first!\n" +
            "Unknown Command\n" +
            "Unknown Command\n", destination.toString());
  }




  @Test
  public void testUseImageProcessorSepia() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " sepia-component koala koala-sepia");
    ImageProcessorController c = new UpdatedController(map, view, in);
    assertTrue(map.grabFromRunTimeStorage("koala-sepia") == null);
    c.useImageProcessor();
    assertFalse(map.grabFromRunTimeStorage("koala-sepia") == null);
    ImprovedImageProcessorModel koala = map.grabFromRunTimeStorage("koala");
    ImprovedImageProcessorModel koala_sepia = map.grabFromRunTimeStorage("koala-sepia");
    for (int row = 0; row < koala.getHeight(); row++) {
      for (int col = 0; col < koala.getWidth(); col++) {
        int r = koala.getPixel(row, col).getColorChannels()[0];
        int g = koala.getPixel(row, col).getColorChannels()[1];
        int b = koala.getPixel(row, col).getColorChannels()[2];
        int m = koala.getPixel(row, col).getMaxValue();
        double newRed = 0.393 * r + 0.769 * g + 0.189 * b;
        double newGreen = 0.349 * r + 0.686 * g + 0.168 * b;
        double newBlue = 0.272 * r + 0.534 * g + 0.131 * b;

        Pixel p = new RGBPixel(this.conform((int) newRed, m),
                this.conform((int) newGreen, m),
                this.conform((int) newBlue, m), m);
        assertEquals(koala_sepia.getPixel(row, col), p);
      }
    }
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
            "koala set to sepia gray scale. Now called: koala-sepia\n", destination.toString());
  }

  private int conform(int value, int maxValue) {
    if (value > maxValue) {
      return maxValue;
    } else if (value < 0) {
      return 0;
    }
    return value;
  }

  @Test
  public void testUseImageProcessorSepiaOverride() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " sepia-component koala koala");
    ImageProcessorController c = new UpdatedController(map, view, in);
    assertTrue(map.grabFromRunTimeStorage("koala") == null);
    c.useImageProcessor();
    assertFalse(map.grabFromRunTimeStorage("koala") == null);
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
            "koala set to sepia gray scale. Now called: koala\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorSepiaFails1() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " sepia-component noala koala-luma");
    ImageProcessorController c = new UpdatedController(map, view, in);
    c.useImageProcessor();
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
            "Cannot find model in storage!\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorSepiaFails2() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("sepia-component noala koala-luma");
    ImageProcessorController c = new UpdatedController(map, view, in);
    c.useImageProcessor();
    assertEquals("Must load an image first!\n" +
            "Unknown Command\n" +
            "Unknown Command\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorBlur() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " gaussian-blur koala koala-blur");
    ImageProcessorController c = new UpdatedController(map, view, in);
    assertTrue(map.grabFromRunTimeStorage("koala-blur") == null);
    c.useImageProcessor();
    assertFalse(map.grabFromRunTimeStorage("koala-blur") == null);
    ImprovedImageProcessorModel koala = map.grabFromRunTimeStorage("koala");
    ImprovedImageProcessorModel koala_blur = map.grabFromRunTimeStorage("koala-blur");

    Matrix kernel = new Kernel(3,3).setSlots(
            0.0625, 0.125, 0.0625,
            0.125, 0.25, 0.125,
            0.0625, 0.125, 0.0625);

    for (int row = 0; row < koala.getHeight(); row++) {
      for (int col = 0; col < koala.getWidth(); col++) {
        double[] channels = new double[3];

        for (int i = 0 ; i < 3; i++) {
          for (int z = 0 ; z < 3; z++) {
            try {
              Pixel p = koala.getPixel(row - ((3 - 1) / 2) + i, col - ((3 - 1) / 2) + z);
              channels[0] += p.getColorChannels()[0] * kernel.getSlotAt(i, z);
              channels[1] += p.getColorChannels()[1] * kernel.getSlotAt(i, z);
              channels[2] += p.getColorChannels()[2] * kernel.getSlotAt(i, z);
            } catch (IllegalArgumentException e) {
              continue;
            } catch (IndexOutOfBoundsException e) {
              continue;
            }

          }
        }

        Pixel p = koala.getPixel(0,0).createPixelFromChannels(channels);

        assertEquals(koala_blur.getPixel(row, col), p);
      }
    }
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
            "koala gaussian blurred. Now called: koala-blur\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorBlurOverride() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " gaussian-blur koala koala");
    ImageProcessorController c = new UpdatedController(map, view, in);
    assertTrue(map.grabFromRunTimeStorage("koala") == null);
    c.useImageProcessor();
    assertFalse(map.grabFromRunTimeStorage("koala") == null);
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
            "koala gaussian blurred. Now called: koala\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorBlurFails1() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " gaussian-blur noala koala-blur");
    ImageProcessorController c = new UpdatedController(map, view, in);
    c.useImageProcessor();
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
            "Cannot find model in storage!\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorBlurFails2() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("gaussian-blur noala koala-luma");
    ImageProcessorController c = new UpdatedController(map, view, in);
    c.useImageProcessor();
    assertEquals("Must load an image first!\n" +
            "Unknown Command\n" +
            "Unknown Command\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorSharpen() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " sharpen koala koala-sharp");
    ImageProcessorController c = new UpdatedController(map, view, in);
    assertTrue(map.grabFromRunTimeStorage("koala-sharp") == null);
    c.useImageProcessor();
    assertFalse(map.grabFromRunTimeStorage("koala-sharp") == null);
    ImprovedImageProcessorModel koala = map.grabFromRunTimeStorage("koala");
    ImprovedImageProcessorModel koala_sharp = map.grabFromRunTimeStorage("koala-sharp");

    Matrix kernel = new Kernel(5, 5).setSlots(
            -0.125, -0.125, -0.125, -0.125, -0.125,
            -0.125, 0.25, 0.25, 0.25, -0.125,
            -0.125, 0.25, 1.00, 0.25, -0.125,
            -0.125, 0.25, 0.25, 0.25, -0.125,
            -0.125, -0.125, -0.125, -0.125, -0.125);

    for (int row = 0; row < koala.getHeight(); row++) {
      for (int col = 0; col < koala.getWidth(); col++) {
        double[] channels = new double[3];

        for (int i = 0 ; i < 5; i++) {
          for (int z = 0 ; z < 5; z++) {
            try {
              Pixel p = koala.getPixel(row - ((5 - 1) / 2) + i, col - ((5 - 1) / 2) + z);
              channels[0] += p.getColorChannels()[0] * kernel.getSlotAt(i, z);
              channels[1] += p.getColorChannels()[1] * kernel.getSlotAt(i, z);
              channels[2] += p.getColorChannels()[2] * kernel.getSlotAt(i, z);
            } catch (IllegalArgumentException e) {
              continue;
            } catch (IndexOutOfBoundsException e) {
              continue;
            }

          }
        }

        Pixel p = koala.getPixel(0,0).createPixelFromChannels(channels);

        assertEquals(koala_sharp.getPixel(row, col), p);
      }
    }
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
            "koala sharpened. Now called: koala-sharp\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorSharpenOverride() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " sharpen koala koala");
    ImageProcessorController c = new UpdatedController(map, view, in);
    assertTrue(map.grabFromRunTimeStorage("koala") == null);
    c.useImageProcessor();
    assertFalse(map.grabFromRunTimeStorage("koala") == null);
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
            "koala sharpened. Now called: koala\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorSharpenFails1() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " sharpen noala koala-sharp");
    ImageProcessorController c = new UpdatedController(map, view, in);
    c.useImageProcessor();
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
            "Cannot find model in storage!\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorSharpenFails2() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("sharpen noala koala-luma");
    ImageProcessorController c = new UpdatedController(map, view, in);
    c.useImageProcessor();
    assertEquals("Must load an image first!\n" +
            "Unknown Command\n" +
            "Unknown Command\n", destination.toString());
  }



  @Test
  public void testUseImageProcessorIntensity() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " intensity-component koala koala-intensity");
    ImageProcessorController c = new UpdatedController(map, view, in);
    assertTrue(map.grabFromRunTimeStorage("koala-intensity") == null);
    c.useImageProcessor();
    assertFalse(map.grabFromRunTimeStorage("koala-intensity") == null);
    ImprovedImageProcessorModel koala = map.grabFromRunTimeStorage("koala");
    ImprovedImageProcessorModel koala_intensity = map.grabFromRunTimeStorage("koala-intensity");
    for (int row = 0; row < koala.getHeight(); row++) {
      for (int col = 0; col < koala.getWidth(); col++) {
        int[] channels = koala.getPixel(row, col).getColorChannels();
        int intensity = (int) ((channels[0] + channels[1] + channels[2]) / 3);
        assertEquals(koala_intensity.getPixel(row, col).getColorChannels()[0],
                intensity);
        assertEquals(koala_intensity.getPixel(row, col).getColorChannels()[1],
                intensity);
        assertEquals(koala_intensity.getPixel(row, col).getColorChannels()[2],
                intensity);
      }
    }
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
            "koala set to intensity gray scale. Now called: koala-intensity\n",
            destination.toString());
  }

  @Test
  public void testUseImageProcessorIntensityOverride() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " intensity-component koala koala");
    ImageProcessorController c = new UpdatedController(map, view, in);
    assertTrue(map.grabFromRunTimeStorage("koala") == null);
    c.useImageProcessor();
    assertFalse(map.grabFromRunTimeStorage("koala") == null);
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
            "koala set to intensity gray scale. Now called: koala\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorIntensityFails1() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " intensity-component noala koala-intensity");
    ImageProcessorController c = new UpdatedController(map, view, in);
    c.useImageProcessor();
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
            "Cannot find model in storage!\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorIntensityFails2() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("intensity-component noala koala-intensity");
    ImageProcessorController c = new UpdatedController(map, view, in);
    c.useImageProcessor();
    assertEquals("Must load an image first!\n" +
            "Unknown Command\n" +
            "Unknown Command\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorValue() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " value-component koala koala-value");
    ImageProcessorController c = new UpdatedController(map, view, in);
    assertTrue(map.grabFromRunTimeStorage("koala-value") == null);
    c.useImageProcessor();
    assertFalse(map.grabFromRunTimeStorage("koala-value") == null);
    ImprovedImageProcessorModel koala = map.grabFromRunTimeStorage("koala");
    ImprovedImageProcessorModel koala_value = map.grabFromRunTimeStorage("koala-value");
    for (int row = 0; row < koala.getHeight(); row++) {
      for (int col = 0; col < koala.getWidth(); col++) {
        int[] channels = koala.getPixel(row, col).getColorChannels();
        int value = channels[0];
        if (channels[1] > value) {
          value = channels[1];
        }
        if (channels[2] > value) {
          value = channels[2];
        }
        assertEquals(koala_value.getPixel(row, col).getColorChannels()[0],
                value);
        assertEquals(koala_value.getPixel(row, col).getColorChannels()[1],
                value);
        assertEquals(koala_value.getPixel(row, col).getColorChannels()[2],
                value);
      }
    }
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
                    "koala set to value gray scale. Now called: koala-value\n",
            destination.toString());
  }

  @Test
  public void testUseImageProcessorValueOverride() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " value-component koala koala");
    ImageProcessorController c = new UpdatedController(map, view, in);
    assertTrue(map.grabFromRunTimeStorage("koala") == null);
    c.useImageProcessor();
    assertFalse(map.grabFromRunTimeStorage("koala") == null);
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
            "koala set to value gray scale. Now called: koala\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorValueFails1() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " value-component noala koala-value");
    ImageProcessorController c = new UpdatedController(map, view, in);
    c.useImageProcessor();
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
            "Cannot find model in storage!\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorValueFails2() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("value-component noala koala-value");
    ImageProcessorController c = new UpdatedController(map, view, in);
    c.useImageProcessor();
    assertEquals("Must load an image first!\n" +
            "Unknown Command\n" +
            "Unknown Command\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorHorizontal() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " horizontal-flip koala koala-horizontal");
    ImageProcessorController c = new UpdatedController(map, view, in);
    assertTrue(map.grabFromRunTimeStorage("koala-horizontal") == null);
    c.useImageProcessor();
    assertFalse(map.grabFromRunTimeStorage("koala-horizontal") == null);
    ImprovedImageProcessorModel koala = map.grabFromRunTimeStorage("koala");
    ImprovedImageProcessorModel koala_horizontal =
            map.grabFromRunTimeStorage("koala-horizontal");
    for (int row = 0; row < koala.getHeight(); row++) {
      for (int col = 0; col < koala.getWidth(); col++) {
        assertEquals(koala_horizontal.getPixel(row, col).getColorChannels()[0],
                koala.getPixel(row, koala.getWidth() - 1 - col).getColorChannels()[0]);
        assertEquals(koala_horizontal.getPixel(row, col).getColorChannels()[1],
                koala.getPixel(row, koala.getWidth() - 1 - col).getColorChannels()[1]);
        assertEquals(koala_horizontal.getPixel(row, col).getColorChannels()[2],
                koala.getPixel(row, koala.getWidth() - 1 - col).getColorChannels()[2]);
      }
    }
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
                    "koala has been flipped horizontally! Now called: koala-horizontal\n",
            destination.toString());
  }

  @Test
  public void testUseImageProcessorHorizontalOverride() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " horizontal-flip koala koala");
    ImageProcessorController c = new UpdatedController(map, view, in);
    assertTrue(map.grabFromRunTimeStorage("koala") == null);
    c.useImageProcessor();
    assertFalse(map.grabFromRunTimeStorage("koala") == null);
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
            "koala has been flipped horizontally! Now called: koala\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorHorizontalFails1() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " horizontal-flip noala koala-horizontal");
    ImageProcessorController c = new UpdatedController(map, view, in);
    c.useImageProcessor();
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
            "Cannot find model in storage!\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorHorizontalFails2() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("horizontal-flip noala koala-horizontal");
    ImageProcessorController c = new UpdatedController(map, view, in);
    c.useImageProcessor();
    assertEquals("Must load an image first!\n" +
            "Unknown Command\n" +
            "Unknown Command\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorVertical() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " vertical-flip koala koala-vertical");
    ImageProcessorController c = new UpdatedController(map, view, in);
    assertTrue(map.grabFromRunTimeStorage("koala-vertical") == null);
    c.useImageProcessor();
    assertFalse(map.grabFromRunTimeStorage("koala-vertical") == null);
    ImprovedImageProcessorModel koala = map.grabFromRunTimeStorage("koala");
    ImprovedImageProcessorModel koala_vertical =
            map.grabFromRunTimeStorage("koala-vertical");
    for (int row = 0; row < koala.getHeight(); row++) {
      for (int col = 0; col < koala.getWidth(); col++) {
        assertEquals(koala_vertical.getPixel(row, col).getColorChannels()[0],
                koala.getPixel(koala.getHeight() - 1 - row, col).getColorChannels()[0]);
        assertEquals(koala_vertical.getPixel(row, col).getColorChannels()[1],
                koala.getPixel(koala.getHeight() - 1 - row, col).getColorChannels()[1]);
        assertEquals(koala_vertical.getPixel(row, col).getColorChannels()[2],
                koala.getPixel(koala.getHeight() - 1 - row, col).getColorChannels()[2]);
      }
    }
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
                    "koala has been flipped vertically! Now called: koala-vertical\n",
            destination.toString());
  }

  @Test
  public void testUseImageProcessorVerticalOverride() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " vertical-flip koala koala");
    ImageProcessorController c = new UpdatedController(map, view, in);
    assertTrue(map.grabFromRunTimeStorage("koala") == null);
    c.useImageProcessor();
    assertFalse(map.grabFromRunTimeStorage("koala") == null);
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
            "koala has been flipped vertically! Now called: koala\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorVerticalFails1() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " vertical-flip noala koala-vertical");
    ImageProcessorController c = new UpdatedController(map, view, in);
    c.useImageProcessor();
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
            "Cannot find model in storage!\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorVerticalFails2() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("vertical-flip noala koala-vertical");
    ImageProcessorController c = new UpdatedController(map, view, in);
    c.useImageProcessor();
    assertEquals("Must load an image first!\n" +
            "Unknown Command\n" +
            "Unknown Command\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorBrighten() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " brighten 50 koala koala-brighter");
    ImageProcessorController c = new UpdatedController(map, view, in);
    assertTrue(map.grabFromRunTimeStorage("koala-brighter") == null);
    c.useImageProcessor();
    assertFalse(map.grabFromRunTimeStorage("koala-brighter") == null);
    ImprovedImageProcessorModel koala = map.grabFromRunTimeStorage("koala");
    ImprovedImageProcessorModel koala_brighter =
            map.grabFromRunTimeStorage("koala-brighter");
    for (int row = 0; row < koala.getHeight(); row++) {
      for (int col = 0; col < koala.getWidth(); col++) {
        int[] channels = koala.getPixel(row, col).getColorChannels();
        int value = channels[0];
        if (channels[1] > value) {
          value = channels[1];
        }
        if (channels[2] > value) {
          value = channels[2];
        }
        assertEquals(koala_brighter.getPixel(row, col).getColorChannels()[0],
                this.capSum(koala.getPixel(row, col).getColorChannels()[0], 50));
        assertEquals(koala_brighter.getPixel(row, col).getColorChannels()[1],
                this.capSum(koala.getPixel(row, col).getColorChannels()[1], 50));
        assertEquals(koala_brighter.getPixel(row, col).getColorChannels()[2],
                this.capSum(koala.getPixel(row, col).getColorChannels()[2], 50));
      }
    }
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
                    "koala successfully brightened by 50. Now called: koala-brighter\n",
            destination.toString());
  }

  @Test
  public void testUseImageProcessorBrightenOverride() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " brighten 100 koala koala");
    ImageProcessorController c = new UpdatedController(map, view, in);
    assertTrue(map.grabFromRunTimeStorage("koala") == null);
    c.useImageProcessor();
    assertFalse(map.grabFromRunTimeStorage("koala") == null);
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
            "koala successfully brightened by 100. Now called: koala\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorBrightenFails1() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " brighten 50 noala koala-vertical");
    ImageProcessorController c = new UpdatedController(map, view, in);
    c.useImageProcessor();
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
            "Cannot find model in storage!\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorBrightenFails2() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("brighten 50 noala koala-vertical");
    ImageProcessorController c = new UpdatedController(map, view, in);
    c.useImageProcessor();
    assertEquals("Must load an image first!\n" +
            "Unknown Command\n" +
            "Unknown Command\n" +
            "Unknown Command\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorBrightenFails3() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " brighten fifty koala koala-brighter");
    ImageProcessorController c = new UpdatedController(map, view, in);
    c.useImageProcessor();
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
            "Brighten requires an integer, please try again\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorBrightenFails4() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " brighten 50 koala");
    ImageProcessorController c = new UpdatedController(map, view, in);
    c.useImageProcessor();
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
            "Ran out of inputs.\n", destination.toString());
  }

  @Test
  public void testUseImageProcessorDarken() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " brighten -50 koala koala-brighter");
    ImageProcessorController c = new UpdatedController(map, view, in);
    assertTrue(map.grabFromRunTimeStorage("koala-brighter") == null);
    c.useImageProcessor();
    assertFalse(map.grabFromRunTimeStorage("koala-brighter") == null);
    ImprovedImageProcessorModel koala = map.grabFromRunTimeStorage("koala");
    ImprovedImageProcessorModel koala_brighter = map.grabFromRunTimeStorage("koala-brighter");
    for (int row = 0; row < koala.getHeight(); row++) {
      for (int col = 0; col < koala.getWidth(); col++) {
        int[] channels = koala.getPixel(row, col).getColorChannels();
        int value = channels[0];
        if (channels[1] > value) {
          value = channels[1];
        }
        if (channels[2] > value) {
          value = channels[2];
        }
        assertEquals(koala_brighter.getPixel(row, col).getColorChannels()[0],
                this.capSum(koala.getPixel(row, col).getColorChannels()[0], -50));
        assertEquals(koala_brighter.getPixel(row, col).getColorChannels()[1],
                this.capSum(koala.getPixel(row, col).getColorChannels()[1], -50));
        assertEquals(koala_brighter.getPixel(row, col).getColorChannels()[2],
                this.capSum(koala.getPixel(row, col).getColorChannels()[2], -50));
      }
    }
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
                    "koala successfully brightened by -50. Now called: koala-brighter\n",
            destination.toString());
  }

  @Test
  public void testUseImageProcessorSave() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " brighten 50 koala koala-brighter" +
            " save src/images/KoalaBrighter.ppm koala-brighter" +
            " load src/images/KoalaBrighter.ppm koala-brighter-reloaded");
    ImageProcessorController c = new UpdatedController(map, view, in);
    assertTrue(map.grabFromRunTimeStorage("koala-brighter-reloaded") == null);
    c.useImageProcessor();
    assertFalse(map.grabFromRunTimeStorage("koala-brighter-reloaded") == null);
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
                    "koala successfully brightened by 50. Now called: koala-brighter\n" +
                    "koala-brighter has been saved successfully to: " +
                    "src/images/KoalaBrighter.ppm\n" +
            "Successfully loaded image: koala-brighter-reloaded " +
                    "from src/images/KoalaBrighter.ppm\n",
            destination.toString());
  }

  @Test
  public void testUseImageProcessorSaveOverride() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/KoalaOverride.ppm koala" +
            " brighten 50 koala koala-brighter" +
            " save src/images/KoalaOverride.ppm koala-brighter" +
            " load src/images/KoalaOverride.ppm koala-brighter-reloaded");
    ImageProcessorController c = new UpdatedController(map, view, in);
    assertTrue(map.grabFromRunTimeStorage("koala-brighter-reloaded") == null);
    c.useImageProcessor();
    assertFalse(map.grabFromRunTimeStorage("koala-brighter-reloaded") == null);
    assertEquals("Successfully loaded image: koala from src/images/KoalaOverride.ppm\n" +
                    "koala successfully brightened by 50. Now called: koala-brighter\n" +
                    "koala-brighter has been saved successfully to: " +
                    "src/images/KoalaOverride.ppm\n" +
                    "Successfully loaded image: koala-brighter-reloaded " +
                    "from src/images/KoalaOverride.ppm\n",
            destination.toString());

  }

  @Test
  public void testUseImageProcessorSaveFails1() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " save src/images/KoalaBrighter.ppm noala-brighter");
    ImageProcessorController c = new UpdatedController(map, view, in);
    c.useImageProcessor();
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
                    "Cannot find model in storage\n",
            destination.toString());
  }

  @Test
  public void testUseImageProcessorSaveFails3() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " save src/fake/KoalaBrighter.ppm koala");
    ImageProcessorController c = new UpdatedController(map, view, in);
    c.useImageProcessor();
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
                    "Failed to save koala to: src/fake/KoalaBrighter.ppm\n",
            destination.toString());
  }

  @Test
  public void testUseImageProcessorSaveFails2() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " brighten 50 koala koala-brighter" +
            " save src/images/InvalidKoala.ppn koala-brighter" +
            " load src/images/InvalidKoala.ppn koala-brighter-reloaded");
    ImageProcessorController c = new UpdatedController(map, view, in);
    c.useImageProcessor();
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
                    "koala successfully brightened by 50. Now called: koala-brighter\n" +
                    "koala-brighter has been saved successfully to: " +
                    "src/images/InvalidKoala.ppn\n" +
                    "Failed to load image!\n",
            destination.toString());

  }

  @Test
  public void testUseImageProcessorSavePPMToPNG() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.ppm koala" +
            " brighten 50 koala koala-brighter" +
            " save src/images/KoalaBrighter.png koala-brighter" +
            " load src/images/KoalaBrighter.png koala-brighter-reloaded");
    ImageProcessorController c = new UpdatedController(map, view, in);
    assertTrue(map.grabFromRunTimeStorage("koala-brighter-reloaded") == null);
    c.useImageProcessor();
    assertFalse(map.grabFromRunTimeStorage("koala-brighter-reloaded") == null);
    assertEquals("Successfully loaded image: koala from src/images/Koala.ppm\n" +
                    "koala successfully brightened by 50. Now called: koala-brighter\n" +
                    "koala-brighter has been saved successfully to: " +
                    "src/images/KoalaBrighter.png\n" +
                    "Successfully loaded image: koala-brighter-reloaded " +
                    "from src/images/KoalaBrighter.png\n",
            destination.toString());
  }

  @Test
  public void testUseImageProcessorSavePNGToPPM() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.png koala" +
            " brighten 50 koala koala-brighter" +
            " save src/images/KoalaBrighter.ppm koala-brighter" +
            " load src/images/KoalaBrighter.ppm koala-brighter-reloaded");
    ImageProcessorController c = new UpdatedController(map, view, in);
    assertTrue(map.grabFromRunTimeStorage("koala-brighter-reloaded") == null);
    c.useImageProcessor();
    assertFalse(map.grabFromRunTimeStorage("koala-brighter-reloaded") == null);
    assertEquals("Successfully loaded image: koala from src/images/Koala.png\n" +
                    "koala successfully brightened by 50. Now called: koala-brighter\n" +
                    "koala-brighter has been saved successfully to: " +
                    "src/images/KoalaBrighter.ppm\n" +
                    "Successfully loaded image: koala-brighter-reloaded " +
                    "from src/images/KoalaBrighter.ppm\n",
            destination.toString());
  }

  @Test
  public void testUseImageProcessorSavePNGtoPNG() {
    ImageRunTimeStorage map = new ImageRunTimeStorage(
            new HashMap<String, ImprovedImageProcessorModel>());
    Appendable destination = new StringBuilder();
    ImageProcessorView view = new ImageProcessorTextView(destination);
    Readable in = new StringReader("load src/images/Koala.png koala" +
            " brighten 50 koala koala-brighter" +
            " save src/images/KoalaBrighter.png koala-brighter" +
            " load src/images/KoalaBrighter.png koala-brighter-reloaded");
    ImageProcessorController c = new UpdatedController(map, view, in);
    assertTrue(map.grabFromRunTimeStorage("koala-brighter-reloaded") == null);
    c.useImageProcessor();
    assertFalse(map.grabFromRunTimeStorage("koala-brighter-reloaded") == null);
    assertEquals("Successfully loaded image: koala from src/images/Koala.png\n" +
                    "koala successfully brightened by 50. Now called: koala-brighter\n" +
                    "koala-brighter has been saved successfully to: " +
                    "src/images/KoalaBrighter.png\n" +
                    "Successfully loaded image: koala-brighter-reloaded " +
                    "from src/images/KoalaBrighter.png\n",
            destination.toString());
  }

  private int capSum(int colorChannel, int increment) {
    if (colorChannel + increment > 255) {
      return 255;
    } else if (colorChannel + increment < 0) {
      return 0;
    }
    return colorChannel + increment;
  }
}
