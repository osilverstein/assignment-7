package controller.commands;

import model.ImprovedImageProcessorModel;
import utilities.ImageRunTimeStorage;
import utilities.ImageUtil;


/**
 * The SaveCommand is an extension of the AbstractCommand in the Command Design that
 * enables the user to save the image to a given file.
 */
public class SaveCommand extends AbstractCommand {

  public SaveCommand(String firstInput, String secondInput, ImageRunTimeStorage map) {
    super(firstInput, secondInput, map);
  }

  @Override
  public ImprovedImageProcessorModel getModelCopy() {
    ImprovedImageProcessorModel m = this.map.grabFromRunTimeStorage(this.secondInput);
    if (m == null) {
      throw new IllegalStateException("Cannot find model in storage");
    }
    return m;
  }

  @Override
  public String use(ImprovedImageProcessorModel m) {
    try {
      ImageUtil.saveImage(this.firstInput, m);
      return this.secondInput + " has been saved successfully to: " + this.firstInput;
    } catch (IllegalStateException e) {
      return "Failed to save " + this.secondInput + " to: " + this.firstInput;
    }
  }
}
