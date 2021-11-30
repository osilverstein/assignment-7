package model.flip;

/**
 * A class to represent a horizontal flip of an image.
 */
public class HorizontalFlip implements Flip {

  /**
   * Returns the correct position of a given pixel after a horizontal flip.
   * @param row represents the row of the pixel
   * @param col represents the column of the pixel
   * @param width represents the width of the image
   * @param height represents the height of the image
   * @return an int array that contains the row and col of a pixel after the horizontal flip
   */
  @Override
  public int[] evaluate(int row, int col, int width, int height) {
    return new int[]{row, width - 1 - col};
  }
}
