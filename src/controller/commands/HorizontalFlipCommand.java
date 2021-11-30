package controller.commands;

import model.ImprovedImageProcessorModel;
import model.flip.HorizontalFlip;
import utilities.ImageRunTimeStorage;

/**
 * The HorizontalFlipCommand is an extension of the AbstractCommand in the Command Design that
 * enables the user to flip the image horizontally.
 */
public class HorizontalFlipCommand extends AbstractCommand {

  public HorizontalFlipCommand(String firstInput, String secondInput, ImageRunTimeStorage map) {
    super(firstInput, secondInput, map);
  }

  @Override
  public String use(ImprovedImageProcessorModel m) {
    m.flip(new HorizontalFlip());
    return this.firstInput + " has been flipped horizontally! Now called: " + this.secondInput;
  }
}
