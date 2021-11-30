import org.junit.Test;

import model.Kernel;
import model.Matrix;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class to test the Kernel Class.
 */
public class KernelTest {

  @Test
  public void constructorWorks() {
    Matrix m = new Kernel(3, 3);
    assertEquals(0, m.getSlotAt(0, 0), 0.01);
    assertEquals(0, m.getSlotAt(1, 0), 0.01);
    assertEquals(0, m.getSlotAt(2, 0), 0.01);
    assertEquals(0, m.getSlotAt(0, 1), 0.01);
    assertEquals(0, m.getSlotAt(0, 2), 0.01);
    assertEquals(0, m.getSlotAt(1, 1), 0.01);
    assertEquals(0, m.getSlotAt(1, 2), 0.01);
    assertEquals(0, m.getSlotAt(2, 1), 0.01);
    assertEquals(0, m.getSlotAt(2, 2), 0.01);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorFails() {
    Matrix m = new Kernel(3, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorFails2() {
    Matrix m = new Kernel(2, 2);
  }

  @Test
  public void setSlotsWorks() {
    Matrix m = new Matrix(1, 3);
    m.setSlots(3.14, 1.59, 2.65);
    assertEquals(3.14, m.getSlotAt(0, 0), 0.01);
    assertEquals(1.59, m.getSlotAt(0, 1), 0.01);
    assertEquals(2.65, m.getSlotAt(0, 2), 0.01);
  }

  @Test(expected = IllegalArgumentException.class)
  public void setSlotsFails() {
    Matrix m = new Matrix(1, 3);
    m.setSlots(3.14, 1.59, 2.65, 3.58, 9.7932384626433832795);
  }

  @Test
  public void setSlotAtWorks() {
    Matrix m = new Matrix(1, 3);
    m.setSlotAt(0, 1, 420);
    assertEquals(0, m.getSlotAt(0, 0), 0.01);
    assertEquals(420, m.getSlotAt(0, 1), 0.01);
    assertEquals(0, m.getSlotAt(0, 2), 0.01);
  }

  @Test(expected = IllegalArgumentException.class)
  public void setSlotAtFails() {
    Matrix m = new Matrix(3, 3);
    m.setSlotAt(1, 4, 69);
  }

  @Test
  public void getSlotAtWorks() {
    Matrix m = new Matrix(1, 3);
    m.setSlots(3.14, 1.59, 2.65);
    assertEquals(3.14, m.getSlotAt(0,0), 0.01);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getSlotAtFails() {
    Matrix m = new Matrix(1, 3);
    m.setSlots(3.14, 1.59, 2.65);
    assertEquals(3.14, m.getSlotAt(3,0), 0.01);
  }



  @Test
  public void testMultiplication() {
    Matrix blue = new Kernel(3, 3).setSlots(0, 0, 1, 0, 0, 1, 0, 0, 1);
    Matrix rgb = new Kernel(1, 3).setSlots(75, 150, 225);
    Matrix expected = new Kernel(1, 3).setSlots(225, 225, 225);

    assertTrue(expected.equals(blue.multiplyBy(rgb)));
  }

  @Test
  public void testToInts() {
    Matrix m = new Kernel(1, 3);
    m.setSlots(3.14, 1.59, 2.65);
    assertEquals(3.14, m.toInts()[0], 0.01);
    assertEquals(1.59, m.toInts()[1], 0.01);
    assertEquals(2.65, m.toInts()[2], 0.01);
  }

  @Test
  public void testToString() {
    Matrix m = new Kernel(1, 3);
    m.setSlots(3.14, 1.59, 2.65);
    assertEquals("1 by 3\n" +
            " 3.14\n 1.59\n 2.65\n", m.toString());
  }
}
