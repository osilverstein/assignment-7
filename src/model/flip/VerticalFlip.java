package model.flip;

/**
 * A class to represent a vertical flip of an image.
 */
public class VerticalFlip implements Flip {

  /**
   * Returns the correct position of a given pixel after a vertical flip.
   * @param row represents the row of the pixel
   * @param col represents the column of the pixel
   * @param width represents the width of the image
   * @param height represents the height of the image
   * @return an int array that contains the row and col of a pixel after the vertical flip
   */
  @Override
  public int[] evaluate(int row, int col, int width, int height) {
    return new int[]{height - 1 - row, col};
  }
}
