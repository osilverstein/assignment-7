package controller.commands;

import model.ImprovedImageProcessorModel;
import model.component.BrightenComponent;
import utilities.ImageRunTimeStorage;

/**
 * The BrightenCommand is an extension of the AbstractCommand in the Command Design that
 * enables the user to increment the image's color channels by a given amount.
 */
public class BrightenCommand extends AbstractCommand {
  private int amount;

  public BrightenCommand(
          int amount, String firstInput, String secondInput, ImageRunTimeStorage map) {
    super(firstInput, secondInput, map);
    this.amount = amount;
  }

  @Override
  public String use(ImprovedImageProcessorModel m) {
    m.component(new BrightenComponent(this.amount));
    return this.firstInput + " successfully brightened by "
            + this.amount + ". Now called: " + this.secondInput;
  }
}
