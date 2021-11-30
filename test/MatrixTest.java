import org.junit.Test;

import model.Matrix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class to test the Matrix Class.
 */
public class MatrixTest {

  @Test
  public void constructorWorks() {
    Matrix m = new Matrix(2, 2);
    assertEquals(0, m.getSlotAt(0, 0), 0.01);
    assertEquals(0, m.getSlotAt(1, 0), 0.01);
    assertEquals(0, m.getSlotAt(0, 1), 0.01);
    assertEquals(0, m.getSlotAt(1, 1), 0.01);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorFails() {
    Matrix m = new Matrix(2, -1);
  }

  @Test
  public void setSlotsWorks() {
    Matrix m = new Matrix(2, 2);
    m.setSlots(3.14, 1.59, 2.65, 3.58);
    assertEquals(3.14, m.getSlotAt(0, 0), 0.01);
    assertEquals(1.59, m.getSlotAt(1, 0), 0.01);
    assertEquals(2.65, m.getSlotAt(0, 1), 0.01);
    assertEquals(3.58, m.getSlotAt(1, 1), 0.01);
  }

  @Test(expected = IllegalArgumentException.class)
  public void setSlotsFails() {
    Matrix m = new Matrix(2, 2);
    m.setSlots(3.14, 1.59, 2.65, 3.58, 9.7932384626433832795);
  }

  @Test
  public void setSlotAtWorks() {
    Matrix m = new Matrix(2, 2);
    m.setSlotAt(1, 1, 420);
    assertEquals(0, m.getSlotAt(0, 0), 0.01);
    assertEquals(0, m.getSlotAt(1, 0), 0.01);
    assertEquals(0, m.getSlotAt(0, 1), 0.01);
    assertEquals(420, m.getSlotAt(1, 1), 0.01);
  }

  @Test(expected = IllegalArgumentException.class)
  public void setSlotAtFails() {
    Matrix m = new Matrix(2, 2);
    m.setSlotAt(1, 3, 69);
  }

  @Test
  public void getSlotAtWorks() {
    Matrix m = new Matrix(2, 2);
    m.setSlots(3.14, 1.59, 2.65, 3.58);
    assertEquals(3.14, m.getSlotAt(0,0), 0.01);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getSlotAtFails() {
    Matrix m = new Matrix(2, 2);
    m.setSlots(3.14, 1.59, 2.65, 3.58);
    assertEquals(3.14, m.getSlotAt(3,0), 0.01);
  }



  @Test
  public void testMultiplication() {
    Matrix blue = new Matrix(3, 3).setSlots(0, 0, 1, 0, 0, 1, 0, 0, 1);
    Matrix rgb = new Matrix(1, 3).setSlots(75, 150, 225);
    Matrix expected = new Matrix(1, 3).setSlots(225, 225, 225);

    assertTrue(expected.equals(blue.multiplyBy(rgb)));
  }

  @Test
  public void testToInts() {
    Matrix m = new Matrix(1, 2);
    m.setSlots(3.14, 1.59);
    assertEquals(3.14, m.toInts()[0], 0.01);
    assertEquals(1.59, m.toInts()[1], 0.01);
  }

  @Test
  public void testToString() {
    Matrix m = new Matrix(2, 2);
    m.setSlots(3.14, 1.59, 2.65, 3.58);
    assertEquals("2 by 2\n" +
            " 3.14 1.59\n" +
            " 2.65 3.58\n", m.toString());
  }
}
