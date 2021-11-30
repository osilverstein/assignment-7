package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A controller interface to include the public method actionPerformed for the GUI controller.
 */
public interface IGUIController extends ImageProcessorController, ActionListener {
  /**
   * Passes the ActionEvent from the view to the controller.
   * @param e represents some action the program runs
   */
  void actionPerformed(ActionEvent e);

}
