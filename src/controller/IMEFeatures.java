package controller;

/**
 * Interface of the features the GUI offers.
 */
public interface IMEFeatures {
  /**
   * Enacts a component's modification on the current model.
   * @param type is a string describing the type of component
   */
  void runComponent(String type);

  /**
   * Enacts a brighten / darken modification on the current model.
   * @param increment is an integer representing how bright or dark the image will be made
   */
  void runBrighten(int increment);

  /**
   * Enacts a filter's modification on the current model.
   * @param type is a string describing the type of filter
   */
  void runFilter(String type);

  /**
   * Saves the current model to the given path.
   * @param path is the path of the file to be saved to
   */
  void saveImage(String path);

  /**
   * Laods the image from the given path to be the current model.
   * @param path is the path of the file to be loaded
   */
  void loadImage(String path);

  /**
   * Enacts a flip on the current model.
   * @param type is a string describing the type of flip
   */
  void runFlip(String type);

  /**
   * Undoes the most recent model modification, can work in series.
   */
  void undoChange();
}
