package model;

import model.filter.Filter;

/**
 * This represents an interface for the model of an image processor.
 * It contains all the methods needed for altering an image (filter, flip, component).
 */
public interface ImprovedImageProcessorModel extends ImageProcessorModel {
  /**
   * Brightens the image by the given amount.
   * @param filter represents the filter to alter this pixel by.
   */
  void filter(Filter filter);
}
