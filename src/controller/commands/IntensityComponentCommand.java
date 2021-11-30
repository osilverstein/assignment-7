package controller.commands;

import model.ImprovedImageProcessorModel;
import model.component.IntensityComponent;
import utilities.ImageRunTimeStorage;


/**
 * The IntensityComponentCommand is an extension of the AbstractCommand in the Command Design that
 * enables the user to make the image grayscaled based on the intensity calculation.
 */
public class IntensityComponentCommand extends AbstractCommand {

  public IntensityComponentCommand(String firstInput, String secondInput, ImageRunTimeStorage map) {
    super(firstInput, secondInput, map);
  }

  @Override
  public String use(ImprovedImageProcessorModel m) {
    m.component(new IntensityComponent());
    return this.firstInput + " set to intensity gray scale. Now called: " + this.secondInput;
  }

}
