package model.filter;

import model.pixel.Pixel;

/**
 * Filter interface represents the effects on an image that are based on neighboring pixels.
 */
public interface Filter {

  Pixel evaluateFilter(Pixel[][] pixels, int row, int col);

}
