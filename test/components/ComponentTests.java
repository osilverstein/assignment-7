package components;

import org.junit.Test;

import model.Matrix;
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

import static org.junit.Assert.assertEquals;

/**
 * Test class for the Component interface.
 */
public class ComponentTests {
  @Test
  public void testEvaluateBlue() {
    Pixel before = new RGBPixel(75, 150, 225, 255);
    Pixel expected = new RGBPixel(225, 225, 225, 255);
    Pixel after = new BlueComponent().evaluate(before);
    assertEquals(expected, after);
  }

  @Test
  public void testMatrixBlue() {
    assertEquals(new Matrix(3, 3).setSlots(0, 0, 1, 0, 0, 1, 0, 0, 1),
            new BlueComponent().getGrayScaleMatrix());
  }

  @Test
  public void testEvaluateBrighten() {
    Pixel before = new RGBPixel(75, 150, 225, 255);
    Pixel expected = new RGBPixel(100, 175, 250, 255);
    Pixel after = new BrightenComponent(25).evaluate(before);
    assertEquals(expected, after);
  }

  @Test
  public void testEvaluateCappedBrighten() {
    Pixel before = new RGBPixel(75, 150, 225, 255);
    Pixel expected = new RGBPixel(225, 255, 255, 255);
    Pixel after = new BrightenComponent(150).evaluate(before);
    assertEquals(expected, after);
  }

  @Test
  public void testEvaluateLoweredBrighten() {
    Pixel before = new RGBPixel(75, 150, 225, 255);
    Pixel expected = new RGBPixel(0, 0, 75, 255);
    Pixel after = new BrightenComponent(-150).evaluate(before);
    assertEquals(expected, after);
  }

  @Test
  public void testMatrixBrighten() {
    assertEquals(null, new BrightenComponent(50).getGrayScaleMatrix());
  }

  @Test
  public void testEvaluateGreen() {
    Pixel before = new RGBPixel(75, 150, 225, 255);
    Pixel expected = new RGBPixel(150, 150, 150, 255);
    Pixel after = new GreenComponent().evaluate(before);
    assertEquals(expected, after);
  }

  @Test
  public void testMatrixGreen() {
    assertEquals(new Matrix(3, 3).setSlots(0, 1, 0, 0, 1, 0, 0, 1, 0),
            new GreenComponent().getGrayScaleMatrix());
  }

  @Test
  public void testEvaluateHorizontal() {
    int[] values = new HorizontalFlip().evaluate(0, 0, 100, 100);
    assertEquals(0, values[0]);
    assertEquals(99, values[1]);
  }

  @Test
  public void testEvaluateIntensity() {
    Pixel before = new RGBPixel(75, 150, 225, 255);
    Pixel expected = new RGBPixel(150, 150, 150, 255);
    Pixel after = new IntensityComponent().evaluate(before);
    assertEquals(expected, after);
  }

  @Test
  public void testMatrixIntensity() {
    assertEquals(null,
            new IntensityComponent().getGrayScaleMatrix());
  }

  @Test
  public void testEvaluateLuma() {
    Pixel before = new RGBPixel(75, 150, 225, 255);
    Pixel expected = new RGBPixel(139, 139, 139, 255);
    Pixel after = new LumaComponent().evaluate(before);
    assertEquals(expected, after);
  }

  @Test
  public void testMatrixLuma() {
    assertEquals(new Matrix(3, 3).setSlots(
                    0.21260, 0.71520, 0.07220,
                    0.21260, 0.71520, 0.07220,
                    0.21260, 0.71520, 0.07220),
            new LumaComponent().getGrayScaleMatrix());
  }

  @Test
  public void testEvaluateRed() {
    Pixel before = new RGBPixel(75, 150, 225, 255);
    Pixel expected = new RGBPixel(75, 75, 75, 255);
    Pixel after = new RedComponent().evaluate(before);
    assertEquals(expected, after);
  }

  @Test
  public void testMatrixRed() {
    assertEquals(new Matrix(3, 3).setSlots(1, 0, 0, 1, 0, 0, 1, 0, 0),
            new RedComponent().getGrayScaleMatrix());
  }

  @Test
  public void testEvaluateValue() {
    Pixel before = new RGBPixel(75, 150, 225, 255);
    Pixel expected = new RGBPixel(225, 225, 225, 255);
    Pixel after = new ValueComponent().evaluate(before);
    assertEquals(expected, after);
  }

  @Test
  public void testMatrixValue() {
    assertEquals(null, new ValueComponent().getGrayScaleMatrix());
  }

  @Test
  public void testEvaluateVertical() {
    int[] values = new VerticalFlip().evaluate(0, 0, 100, 100);
    assertEquals(99, values[0]);
    assertEquals(0, values[1]);
  }
}
