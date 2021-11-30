package controller;

/**
 * Interface ImageProcessorController to represent the Controller, which has a useImageProcessor
 * method which will start the input process.
 */
public interface ImageProcessorController {

  /**
   * Processes commands appropriately utilizing the command design where appropriate. A few of the
   * commands include brightening the image, flipping the image, setting the image to a grayscale
   * based on a color channel or calculation using the color channels. Additionally, loading and
   * saving from and to files respectively.
   */
  void useImageProcessor();
}
