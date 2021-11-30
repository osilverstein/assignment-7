package controller.commands;

import model.ImprovedImageProcessorModel;
import model.filter.GaussianBlur;
import utilities.ImageRunTimeStorage;

/**
 * The BrightenCommand is an extension of the AbstractCommand in the Command Design that
 *  * enables the user to blur the image using a gaussian blur kernel.
 */
public class BlurCommand extends AbstractCommand {
  /**
   * Constructor to be supered by an extensions of this abstract class AbstractCommand.
   *
   * @param firstInput  is the old filename in the storage
   * @param secondInput is the new filename in the storage
   * @param map         is the ImageRunTimeStorage of the models and their String names
   * @throws IllegalArgumentException if the {@code map} is null
   */
  public BlurCommand(String firstInput, String secondInput, ImageRunTimeStorage map)
          throws IllegalArgumentException {
    super(firstInput, secondInput, map);
  }

  @Override
  public String use(ImprovedImageProcessorModel m) {
    m.filter(new GaussianBlur());
    return this.firstInput + " gaussian blurred. Now called: " + this.secondInput;
  }
}
