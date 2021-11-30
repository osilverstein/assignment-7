package controller.commands;

import model.ImprovedImageProcessorModel;
import model.component.GreenComponent;
import utilities.ImageRunTimeStorage;

/**
 * The GreenComponentCommand is an extension of the AbstractCommand in the Command Design that
 * enables the user to make the image grayscaled based on the green component.
 */
public class GreenComponentCommand extends AbstractCommand {

  public GreenComponentCommand(String firstInput, String secondInput, ImageRunTimeStorage map) {
    super(firstInput, secondInput, map);
  }

  @Override
  public String use(ImprovedImageProcessorModel m) {
    m.component(new GreenComponent());
    return this.firstInput + " set to green gray scale. Now called: " + this.secondInput;
  }

}
