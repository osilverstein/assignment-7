package controller.commands;

import model.ImprovedImageProcessorModel;
import model.component.RedComponent;
import utilities.ImageRunTimeStorage;

/**
 * The RedComponentCommand is an extension of the AbstractCommand in the Command Design that
 * enables the user to make the image grayscaled based on the red component.
 */
public class RedComponentCommand extends AbstractCommand {

  public RedComponentCommand(String firstInput, String secondInput, ImageRunTimeStorage map) {
    super(firstInput, secondInput, map);
  }

  @Override
  public String use(ImprovedImageProcessorModel m) {
    m.component(new RedComponent());
    return this.firstInput + " set to red gray scale. Now called: " + this.secondInput;
  }

}
