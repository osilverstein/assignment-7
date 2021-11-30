package controller.commands;

import model.ImprovedImageProcessorModel;
import model.component.LumaComponent;
import utilities.ImageRunTimeStorage;


/**
 * The LumaComponentCommand is an extension of the AbstractCommand in the Command Design that
 * enables the user to make the image grayscaled based on the luma calculation.
 */
public class LumaComponentCommand extends AbstractCommand {

  public LumaComponentCommand(String firstInput, String secondInput, ImageRunTimeStorage map) {
    super(firstInput, secondInput, map);
  }

  @Override
  public String use(ImprovedImageProcessorModel m) {
    m.component(new LumaComponent());
    return this.firstInput + " set to luma gray scale. Now called: " + this.secondInput;
  }

}
