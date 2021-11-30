package view;

import java.awt.event.ActionListener;

/**
 * Interface for the GUI view which has extensibility over the ImageProcessorView.
 */
public interface ImageProcessorViewGUI extends ImageProcessorView {

  int retrieveBrightenScrollInput();

  String importImage();

  String exportImage();

  String getDropdownValue(String whichOne);

  void setCommandButtonListener(ActionListener actionEvent);

  void updateView(String absolutePath);

  void refresh();
}
