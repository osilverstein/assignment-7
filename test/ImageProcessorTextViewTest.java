import org.junit.Test;

import view.ImageProcessorTextView;
import view.ImageProcessorView;

import static org.junit.Assert.assertTrue;

/**
 * ImageProcessorTextViewTest tests the text view of the ImageProcessorView. To make sure that
 * the messages render correctly and fail properly.
 */
public class ImageProcessorTextViewTest {

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorFails() {
    new ImageProcessorTextView(null);
  }

  @Test(expected = IllegalStateException.class)
  public void testRenderMessageFails() {
    Appendable s = new BadAppendable();
    ImageProcessorView v = new ImageProcessorTextView(s);
    v.sendOutPutMessage("hello");
  }

  @Test
  public void testConstructorAndRenderMessageWorks() {
    Appendable s = new StringBuilder();
    ImageProcessorView v = new ImageProcessorTextView(s);
    v.sendOutPutMessage("hello");
    assertTrue(s.toString().equals("hello"));
  }
}
