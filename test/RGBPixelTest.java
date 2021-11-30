import org.junit.Test;

import model.component.BrightenComponent;
import model.component.RedComponent;
import model.component.SepiaComponent;
import model.pixel.Pixel;
import model.pixel.RGBPixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * A test class to make sure the RGBPixel class functions and fails correctly.
 */
public class RGBPixelTest {
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorFails1() {
    Pixel p = new RGBPixel(0, 0, 0, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorFails2() {
    Pixel p = new RGBPixel(0, 0, 255, 1);
  }

  @Test
  public void testConstructorWorks() {
    Pixel white = new RGBPixel(255, 255, 255, 255);
    assertTrue(white.equals(white));
  }

  @Test
  public void testToString() {
    Pixel white = new RGBPixel(255, 255, 255, 255);
    assertEquals("255\n255\n255", white.toString());
  }

  @Test
  public void testGetColorChannels() {
    Pixel white = new RGBPixel(255, 255, 255, 255);
    assertEquals(255, white.getColorChannels()[0]);
    assertEquals(255, white.getColorChannels()[1]);
    assertEquals(255, white.getColorChannels()[2]);
  }

  @Test
  public void testSetGrayscaleComponent() {
    Pixel x = new RGBPixel(75, 150, 225, 255);
    Pixel n = x.setComponent(new RedComponent());
    assertEquals(75, n.getColorChannels()[0]);
    assertEquals(75, n.getColorChannels()[1]);
    assertEquals(75, n.getColorChannels()[2]);
  }

  @Test
  public void testSetIncrementComponent() {
    Pixel x = new RGBPixel(75, 150, 225, 255);
    Pixel n = x.setComponent(new BrightenComponent(15));
    assertEquals(90, n.getColorChannels()[0]);
    assertEquals(165, n.getColorChannels()[1]);
    assertEquals(240, n.getColorChannels()[2]);
  }

  @Test
  public void testSetSepiaComponent() {
    Pixel x = new RGBPixel(75, 150, 225, 255);
    Pixel n = x.setComponent(new SepiaComponent());
    assertEquals(187, n.getColorChannels()[0]);
    assertEquals(166, n.getColorChannels()[1]);
    assertEquals(129, n.getColorChannels()[2]);
  }

  @Test
  public void testGetCopy() {
    Pixel x = new RGBPixel(75, 150, 225, 255);
    assertEquals(x.getCopy().getColorChannels()[0], x.getColorChannels()[0]);
    assertEquals(x.getCopy().getColorChannels()[1], x.getColorChannels()[1]);
    assertEquals(x.getCopy().getColorChannels()[2], x.getColorChannels()[2]);
  }

  @Test
  public void testEquals1() {
    assertFalse(new RGBPixel(75, 150, 225, 255).equals(
            new RGBPixel(75, 150, 225, 225)));
  }

  @Test
  public void testCreatePixelsFromChannels() {
    Pixel x = new RGBPixel(75, 150, 225, 255);
    Pixel n = x.createPixelFromChannels(30, -10, 260);
    assertEquals(new RGBPixel(30, 0, 255, 255), n);
  }

  @Test
  public void testGetMatrix() {
    Pixel x = new RGBPixel(75, 150, 225, 255);
    assertEquals(255, x.getMaxValue());
  }

  @Test
  public void testEquals2() {
    Pixel x = new RGBPixel(75, 150, 225, 255);
    Pixel y = new RGBPixel(75, 150, 225, 255);
    Pixel z = new RGBPixel(75, 150, 225, 255);
    assertTrue(x.equals(x.getCopy()));
    assertTrue(x.equals(y));
    assertTrue(y.equals(x));
    assertTrue(y.equals(z));
    assertTrue(x.equals(z));
  }
}
