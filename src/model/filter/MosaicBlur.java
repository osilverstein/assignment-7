
package model.filter;

import java.util.ArrayList;
import java.util.Random;

import model.Matrix;
import model.pixel.Pixel;


/**
 * MosaicBlur is a filter that represents a blur of the mosaic type.
 */

/**
 * An image can be "broken down" into stained glass pieces, by choosing a set of
 * points in the image (called seeds). Each pixel in the image is then paired to
 * the seed that is closest to it (by distance). This creates a cluster of
 * pixels for each seed. Then the color of each pixel in the image is replaced
 * with the average color of its cluster.
 */

public class MosaicBlur implements Filter {
  int numNodes;
  int[][] clusterMap;
  ArrayList<int[]> nodeCoords;

  /**
   * Constructor for an mosaic geometric filter.
   * 
   * @param numNodes the number of nodes in the mosaic.
   * @param clusterMap the cluster map.
   * @param nodeCoords the coordinates of the nodes.
   * @throws IllegalArgumentException if either are even or less than or equal to
   *                                  0
   */
  public MosaicBlur(int numNodes) throws IllegalArgumentException {
    this.numNodes = numNodes;
    this.nodeCoords = new ArrayList<int[]>();
  }

  /**
   * Generates a map of pixel index to a predictable randomized boolean on whether
   * they are a cluster node.
   * 
   * @param pixels the pixels denoted.
   * @param seed   the seed to use for random.
   * @return a map of pixel index to a predictable randomized bit on whether
   *         they are a cluster node.
   */
  private int[][] generateClusterMap(Pixel[][] pixels, int seed) {
    //check if clusterMap has been initialized
    if (this.clusterMap != null) {
      return this.clusterMap;
    }
    Random rand = new Random(seed);
    int iterationsWithoutNode = 0;
    int averageInterationsWithoutNode = (int) ((pixels.length * pixels[0].length) / (numNodes * 1.2)); //1.2 is a magic number; see below
    //each index of clustMap is the same index as a pixel with the value being a 1 or 0 if it is a cluster node
    int[][] clusterMap = new int[pixels.length][pixels[0].length];
    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[0].length; j++) {
        double probabilityOfNode = iterationsWithoutNode / averageInterationsWithoutNode;
        if (rand.nextDouble() + 0.9 < probabilityOfNode) { //TODO: 0.9 magic number to fix logic error; see below
          clusterMap[i][j] = 1;
          iterationsWithoutNode = 0;
          nodeCoords.add(new int[] {i, j});
        } else {
          clusterMap[i][j] = 0;
          iterationsWithoutNode++;
        }
      }
    }
    this.clusterMap = clusterMap;
    return clusterMap;
  }

  /**
   * Finds the closest cluster node to a pixel.
   * 
   * @param pixels     the pixels denoted.
   * @param clusterMap the map of pixel index to a predictable randomized boolean.
   * @param x          the x coordinate of the pixel.
   * @param y          the y coordinate of the pixel.
   * @return the closest cluster node to a pixel as an int[].
   */
  private int[] findClosestNode(Pixel[][] pixels, int[][] clusterMap, int x, int y) {
    int[] closestNode = new int[2];
    int minDistance = Integer.MAX_VALUE;
    for (int i = 0; i < nodeCoords.size(); i++) {
      int[] nodeCoord = nodeCoords.get(i);
      int distance = (int) Math.sqrt(Math.pow(nodeCoord[0] - x, 2) + Math.pow(nodeCoord[1] - y, 2));
      if (distance < minDistance) {
        minDistance = distance;
        closestNode = nodeCoord;
      }
    }
    return closestNode;
  }

  @Override
  public Pixel evaluateFilter(Pixel[][] pixels, int row, int col) {
    int[] closestClusterNode = findClosestNode(pixels, generateClusterMap(pixels, 420),
        row, col);
    //returns pixel at a coordinate
    return pixels[closestClusterNode[0]][closestClusterNode[1]];
  }

}
