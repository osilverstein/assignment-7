package controller.commands;


import model.ImprovedImageProcessorModel;
import model.component.BlueComponent;
import utilities.ImageRunTimeStorage;

/**
 * The BlueComponentCommand is an extension of the AbstractCommand in the Command Design that
 * enables the user to make the image grayscaled based on the blue component.
 */
public class BlueComponentCommand extends AbstractCommand {

  public BlueComponentCommand(String originalName, String newName, ImageRunTimeStorage map) {
    super(originalName, newName, map);
  }

  @Override
  public String use(ImprovedImageProcessorModel m) {
    m.component(new BlueComponent());
    return this.firstInput + " set to blue gray scale. Now called: " + this.secondInput;
  }
}