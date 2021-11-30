package controller.commands;

import model.ImprovedImageProcessorModel;

/**
 * The Command interface is the core of the Command Design. It is implemented by all commands
 * (except load) and allows extensibility.
 */
public interface Command {

  String use(ImprovedImageProcessorModel m);

  ImprovedImageProcessorModel getModelCopy();
}
