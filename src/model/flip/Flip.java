package model.flip;

/**
 * The Flip interface allows for extensibility for any image to be flipped in different ways.
 */
public interface Flip {
  /**
   * Returns the correct position of a given pixel after a flip.
   * @param row represents the row of the pixel
   * @param col represents the column of the pixel
   * @param width represents the width of the image
   * @param height represents the height of the image
   * @return an int array that contains the row and col of a pixel after a flip
   */
  int[] evaluate(int row, int col, int width, int height);
}
