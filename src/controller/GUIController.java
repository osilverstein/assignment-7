package controller;

import java.awt.event.ActionEvent;

import utilities.ImageRunTimeStorage;
import view.ImageProcessorViewGUI;

/**
 * A controller to work with the GUI structure.
 */
public class GUIController implements IGUIController {
  private ImageRunTimeStorage storage;
  private ImageProcessorViewGUI view;
  IMEFeatures features;

  /**
   * A constructor to initialize the storage, view, and features.
   * @param storage is the storage for the controller
   * @param view is the view the features work with.
   */
  public GUIController(ImageRunTimeStorage storage, ImageProcessorViewGUI view) {
    this.storage = storage;
    this.view = view;
    this.features = new FeaturesImpl(this.storage);
  }

  /**
   * Sets all the button listeners.
   */
  public void useImageProcessor() {
    this.view.setCommandButtonListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String action = e.getActionCommand();
    try {
      String successMessage = "Successfully ";
      switch (action) {
        case ("import"):
          String loadPath = view.importImage();
          features.loadImage(loadPath);
          view.updateView(loadPath);
          view.refresh();
          successMessage += "loaded";
          break;
        case ("export"):
          String savePath = view.exportImage();
          features.saveImage(savePath);
          successMessage += "saved";
          break;
        case ("Flip Horizontal"):
          features.runFlip("horizontal");
          view.updateView(this.storage.getCurrentModel());
          view.refresh();
          successMessage += "flipped horizontally";
          break;
        case ("Flip Vertical"):
          features.runFlip("vertical");
          view.updateView(this.storage.getCurrentModel());
          view.refresh();
          successMessage += "flipped vertically";
          break;
        case ("Apply Component"):
          String comp = view.getDropdownValue("Component");
          features.runComponent(comp);
          view.updateView(this.storage.getCurrentModel());
          view.refresh();
          successMessage += "applied " + comp + " component";
          break;
        case ("Apply Filter"):
          String filt = view.getDropdownValue("Filter");
          String param = "";

          
          features.runFilter(filt);
          view.updateView(this.storage.getCurrentModel());
          view.refresh();
          successMessage += "applied " + filt + " filter";
          break;
        case ("Brighten") :
          int increment = view.retrieveBrightenScrollInput();
          features.runBrighten(increment);
          view.updateView(this.storage.getCurrentModel());
          view.refresh();
          successMessage += "brightened";
          break;
        case ("Undo") :
          features.undoChange();
          view.updateView(this.storage.getCurrentModel());
          view.refresh();
          successMessage += "undid change";
          break;
        default:
          successMessage += "did nothing";
      }
      view.sendOutPutMessage(successMessage + "!");
    } catch (IllegalStateException error) {
      view.sendOutPutMessage(error.getMessage());
    }
  }
}
