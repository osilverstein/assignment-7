package controller.commands;

import model.ImprovedImageProcessorModel;
import utilities.ImageRunTimeStorage;

/**
 * This abstract command is made to lift commonalities from all the commands. Specifically,
 * the getModelCopy method and a constructor that can be supered.
 */
public abstract class AbstractCommand implements Command {
  protected String firstInput;
  protected String secondInput;
  protected ImageRunTimeStorage map;

  /**
   * Constructor to be supered by an extensions of this abstract class AbstractCommand.
   * @param firstInput is the old filename in the storage
   * @param secondInput is the new filename in the storage
   * @param map is the ImageRunTimeStorage of the models and their String names
   * @throws IllegalArgumentException if the {@code map} is null
   */
  public AbstractCommand(String firstInput, String secondInput, ImageRunTimeStorage map)
          throws IllegalArgumentException {
    if (firstInput == null || secondInput == null || map == null) {
      throw new IllegalArgumentException("Map cannot be null.");
    }

    this.firstInput = firstInput;
    this.secondInput = secondInput;
    this.map = map;
  }

  @Override
  public ImprovedImageProcessorModel getModelCopy() {
    ImprovedImageProcessorModel model;

    try {
      model = this.map.grabFromRunTimeStorage(firstInput).getCopy();
    } catch (NullPointerException e) {
      throw new IllegalStateException("Cannot find model in storage!");
    }

    this.map.addToRunTimeStorage(this.secondInput, model);
    return model;
  }
}
