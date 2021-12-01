import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.JFrame;

import model.ImprovedImageProcessorModel;
import model.pixel.Pixel;
import utilities.ImageRunTimeStorage;
import view.ImageProcessorViewGUI;

/**
 * Represents a Mock of an ImageProcessorViewGUI.
 */
public class MockView extends JFrame implements ImageProcessorViewGUI {
  final private StringBuilder log;
  private ImageRunTimeStorage storage;
  private String importPath;
  private String savePath;

  /**
   * Constructs a Mock of an ImageProcessorModelViewGUI.
   * @param log represents the log in which a record of all method calls are stored.
   * @param storage represents the path of the image used for testing.
   */
  public MockView(StringBuilder log, ImageRunTimeStorage storage) {
    this.log = Objects.requireNonNull(log);
    this.storage = storage;
    this.importPath = "src/images/Koala.png";
    this.savePath = "src/images/New.png";
  }

  /**
   * Sends a message to the dialog box to keep the user informed of successful and unsuccessful
   * changes made to and image.
   *
   * @param message the message to be transmitted
   */
  @Override
  public void sendOutPutMessage(String message) {
    this.log.append("calling sendOutPutMessage " + message + "\n");
  }

  /**
   * Retrieves the scroll bar input from the button panel.
   *
   * @return an int that represents the scroll bar value in the button panel.
   */
  @Override
  public int retrieveBrightenScrollInput() {
    this.log.append("calling retrieveBrightenScrollInput\n");
    return 0;
  }

  /**
   * Imports an image that will be used in the Image Processor.
   *
   * @return a string that represents the name of the image that will be used as its reference
   *         in the ImageRunTimeStorage.
   */
  @Override
  public String importImage() {
    this.log.append("calling importImage\n");
    return importPath;
  }

  /**
   * Exports an image a selected location.
   *
   * @return the path at which the image has been exported to.
   */
  @Override
  public String exportImage() {
    this.log.append("calling exportImage\n");
    return savePath;
  }

  /**
   * Grabs the selected icon in the drop-down value.
   *
   * @param whichOne indicates which drop-down member to retrieve data from.
   * @return a String that represents the selected drop-down value.
   */
  @Override
  public String getDropdownValue(String whichOne) {
    this.log.append("calling getDropdownValue with " + whichOne + "\n");
    if (whichOne.equals("Component")) {
      return "value";
    } else if (whichOne.equals("Filter")) {
      return "sharpen";
    }
    return null;
  }

  /**
   * Sets the ActionListener of all buttons in the view to the provided {@Code actionEvent}.
   * @param actionEvent represents the ActionListener that will be provided to all the buttons.
   */
  @Override
  public void setCommandButtonListener(ActionListener actionEvent) {
    this.log.append("calling setCommandButtonListener with " + actionEvent.toString() + "\n");
  }

  /**
   * Updates the view to illustrate changes made to an image by the user.
   * @param absolutePath represents the image that is being drawn in the image pannel.
   */
  @Override
  public void updateView(String absolutePath) {
    this.log.append("calling updateView with " + absolutePath + "\n");
  }

  private void doPlot() {
    ImprovedImageProcessorModel m =
            this.storage.grabFromRunTimeStorage(this.storage.getCurrentModel());
    Pixel[][] pixels = new Pixel[m.getHeight()][m.getWidth()];
    for (int row = 0; row < m.getHeight(); row++) {
      for (int col = 0; col < m.getWidth(); col++) {
        pixels[row][col] = m.getPixel(row, col);
      }
    }
    new MockPlot(this.log).setPixels(pixels);
  }

  /**
   * Refreshes the JFrame and repaints everything.
   */
  @Override
  public void refresh() {
    this.log.append("calling refresh and drawing new histogram!\n");
    this.doPlot();
  }

  @Override
  public String getTextFieldValue(String whichOne) {
    this.log.append("calling getTextFieldValue with " + whichOne + "\n");
    return "";
  }
}
