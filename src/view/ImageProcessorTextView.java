package view;

import java.io.IOException;

/**
 * A text view of the image processor.
 */
public class ImageProcessorTextView implements ImageProcessorView {
  private Appendable destination;

  /**
   * Constructor for the TextView.
   * @param destination represents the appendable
   * @throws IllegalArgumentException if the destination is null
   */
  public ImageProcessorTextView(Appendable destination) throws IllegalArgumentException {
    if (destination == null) {
      throw new IllegalArgumentException("Destination cannot be null!");
    }

    this.destination = destination;
  }

  /**
   * Render a specific message to the provided data destination.
   *
   * @param message the message to be transmitted
   * @throws IOException if transmission of the board to the provided data destination fails
   */
  @Override
  public void sendOutPutMessage(String message) {
    try {
      this.destination.append(message);
    }
    catch (IOException e) {
      throw new IllegalStateException("Unable to transmit message!");
    }
  }
}
