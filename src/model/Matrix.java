package model;

import java.util.Objects;

/**
 * The Matrix class allows for complex calculations relating to Linear Algebra.
 */
public class Matrix {
  double[][] matrix;
  int cols;
  int rows;

  /**
   * The constructor for a Matrix, imposes the restriction that the amount of columns and rows
   * must be at least 1.
   * @param cols is the amount of columns
   * @param rows is the amount of rows
   * @throws IllegalArgumentException if the amount of columns or rows is less than 1
   */
  public Matrix(int cols, int rows) throws IllegalArgumentException {
    if (cols <= 0 || rows <= 0) {
      throw new IllegalArgumentException("Rows and Columns must be at least 1");
    }
    this.cols = cols;
    this.rows = rows;
    this.matrix = new double[cols][rows];
  }


  /**
   * sets the cells by row in the matrix.
   * @param values is the array of values to fill the matrix
   * @return the Matrix created from setting the values.
   * @throws IllegalArgumentException if the amount of values doesn't fit exactly in the matrix
   */
  public Matrix setSlots(double ...values) throws IllegalArgumentException {
    if (values.length != this.cols * this.rows) {
      throw new IllegalArgumentException("Incorrect amount of values");
    }
    for (int j = 0; j < this.rows; j++) {
      for (int i = 0; i < this.cols; i++) {
        this.setSlotAt(i, j, values[j * this.cols + i]);
      }
    }
    return this;
  }

  /**
   * Set the value of a specific slot in a matrix.
   * @param col is the column of the specific slot
   * @param row is the row of the specific slot
   * @param value is the value to be put in the slot at the column and row
   * @throws IllegalArgumentException if the given column and row are less than 0 or greater than
   *                                  the given amount of columns and rows respectively.
   */
  public void setSlotAt(int col, int row, double value) throws IllegalArgumentException {
    if (col < 0 || col >= this.cols || row < 0 || row >= this.rows) {
      throw new IllegalArgumentException("invalid row or column for this matrix");
    }
    this.matrix[col][row] = value;
  }

  /**
   * Get the value of a specific slot in a matrix.
   * @param col is the column of the specific slot
   * @param row is the row of the specific slot
   * @throws IllegalArgumentException if the given column and row are less than 0 or greater than
   *                                  the given amount of columns and rows respectively.
   */
  public double getSlotAt(int col, int row) {
    if (col < 0 || col >= this.cols || row < 0 || row >= this.rows) {
      throw new IllegalArgumentException("invalid row or column for this matrix");
    }
    return this.matrix[col][row];
  }

  /**
   * Multiply this matrix by another matrix.
   * @param other is the other matrix for this matrix to be multiplied by
   * @return the product matrix
   * @throws IllegalArgumentException if the matrices are invalid to be multiplied by each other.
   */
  public Matrix multiplyBy(Matrix other) throws IllegalArgumentException {
    if (other.rows != this.cols) {
      throw new IllegalArgumentException("cannot multiply these matrices");
    }
    Matrix result = new Matrix(other.cols, this.rows);

    for (int col = 0; col < result.cols; col++) {
      for (int row = 0; row < result.rows; row++) {
        result.setSlotAt(col, row, multiplyMatricesCell(other, row, col, this.cols));
      }
    }

    return result;
  }

  private double multiplyMatricesCell(Matrix other, int r, int c, int m) {
    double value = 0;

    for (int i = 0; i < m; i++) {
      //System.out.println(other.getSlotAt(c, i) * this.getSlotAt(i, r));
      value += other.getSlotAt(c, i) * this.getSlotAt(i, r);
    }

    return value;
  }


  /**
   * Converts a matrix to an array of doubles.
   * @return an array of doubles listing the first column of a matrix.
   */
  public double[] toInts() {
    double[] channels = new double[this.rows];

    for (int j = 0; j < this.rows; j++) {
      channels[j] = this.getSlotAt(0, j);
    }

    return channels;

  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof Matrix)) {
      return false;
    }
    Matrix other = (Matrix) o;

    if (this.rows != other.rows
            || this.cols != other.cols) {
      return false;
    }

    for (int x = 0; x < this.cols; x++) {
      for (int y = 0; y < this.rows; y++) {
        if (Math.abs(this.getSlotAt(x, y) - other.getSlotAt(x, y)) >= 0.01) {
          return false;
        }
      }
    }

    return true;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.rows, this.cols);
  }

  @Override
  public String toString() {
    String output = "" + this.cols + " by " + this.rows + "\n";
    for (int x = 0; x < rows; x++) {
      for (int y = 0; y < cols; y++) {
        output += " " + this.getSlotAt(y, x);
      }
      output += "\n";
    }
    return output;
  }
}
