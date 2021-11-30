package controller.commands;

import model.ImprovedImageProcessorModel;
import model.flip.VerticalFlip;
import utilities.ImageRunTimeStorage;

/**
 * The VerticalFlipCommand is an extension of the AbstractCommand in the Command Design that
 * enables the user to flip the image vertically.
 */
public class VerticalFlipCommand extends AbstractCommand {

  public VerticalFlipCommand(String firstInput, String secondInput, ImageRunTimeStorage map) {
    super(firstInput, secondInput, map);
  }

  @Override
  public String use(ImprovedImageProcessorModel m) {
    m.flip(new VerticalFlip());
    return this.firstInput + " has been flipped vertically! Now called: " + this.secondInput;
  }
}
