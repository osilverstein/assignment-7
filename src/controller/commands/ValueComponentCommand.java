package controller.commands;

import model.ImprovedImageProcessorModel;
import model.component.ValueComponent;
import utilities.ImageRunTimeStorage;

/**
 * The ValueComponentCommand is an extension of the AbstractCommand in the Command Design that
 * enables the user to make the image grayscaled based on the value calculation.
 */
public class ValueComponentCommand extends AbstractCommand {

  public ValueComponentCommand(String firstInput, String secondInput, ImageRunTimeStorage map) {
    super(firstInput, secondInput, map);
  }

  @Override
  public String use(ImprovedImageProcessorModel m) {
    m.component(new ValueComponent());
    return this.firstInput + " set to value gray scale. Now called: " + this.secondInput;
  }
}
