package controller.commands;

import model.ImprovedImageProcessorModel;
import model.filter.MosaicBlur;
import utilities.ImageRunTimeStorage;

/**
 * The MosaicCommand is an extension of the AbstractCommand in the Command
 * Design that
 * enables the user to mosaicafy the image using a 5 by 5 kernel.
 */
public class MosaicCommand extends AbstractCommand {
  /**
   * Constructor to be supered by an extensions of this abstract class
   * AbstractCommand.
   *
   * @param firstInput  is the old filename in the storage
   * @param secondInput is the new filename in the storage
   * @param map         is the ImageRunTimeStorage of the models and their String
   *                    names
   * @throws IllegalArgumentException if the {@code map} is null
   */
  public MosaicCommand(String firstInput, String secondInput,
                       ImageRunTimeStorage map) {
    super(firstInput, secondInput, map);
}
@Override
public String use(ImprovedImageProcessorModel m) {
  m.filter(new MosaicBlur());
  return this.firstInput + " mosaicafied. Now called: " + this.secondInput;
}

}


