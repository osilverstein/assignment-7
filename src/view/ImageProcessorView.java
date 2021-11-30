package view;

import java.io.IOException;

/**
 * The ImageProcessorView is a key part of our MVC design. It represents the view. It's one method
 * is to render a message.
 */
public interface ImageProcessorView {

  /**
   * Render a specific message to the provided data destination.
   * @param message the message to be transmitted
   * @throws IOException if transmission of the board to the provided data destination fails
   */
  void sendOutPutMessage(String message);
}
