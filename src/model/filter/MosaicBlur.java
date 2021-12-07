package model.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import model.pixel.Pixel;
import model.pixel.RGBPixel;

/**
 * MosaicBlur is a filter that represents a blur of the mosaic type.
 */

public class MosaicBlur implements Filter {
  int numNodes;
  int[][] clusterMap;
  ArrayList<int[]> nodeCoords;
  HashMap<int[], int[]> pixelToNodeHashMap;

  /**
   * Constructor for an mosaic geometric filter.
   * 
   * @param numNodes   the number of nodes in the mosaic.
   * @param clusterMap the cluster map.
   * @param nodeCoords the coordinates of the nodes.
   * @throws IllegalArgumentException if either are even or less than or equal to
   *                                  0
   */
  public MosaicBlur(int numNodes) throws IllegalArgumentException {
    this.numNodes = numNodes;
    this.nodeCoords = new ArrayList<int[]>();
    this.pixelToNodeHashMap = new HashMap<int[], int[]>();
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
    // check if clusterMap has been initialized
    if (this.clusterMap != null) {
      return this.clusterMap;
    }
    Random rand = new Random(seed);
    // how many #s of pixels a node should be added (numNodes)
    int nodeFreq = (pixels.length * pixels[0].length) / numNodes;
    // how much space (nodeSpace) is between those nodes
    int nodeSpace = (pixels.length * pixels[0].length) / nodeFreq;
    // create a cluster map
    this.clusterMap = new int[pixels.length][pixels[0].length];
    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[0].length; j++) {
        int pixelCountSoFar = ((i + 1) * pixels[0].length) + j + 1;
        if (pixelCountSoFar % nodeFreq == 0) {
          int[] nodeCoord = new int[2];
          int additionFactor = rand.nextInt(nodeSpace);
          // if j + additionFactor is out of bounds, move to the next i value and add the
          // remainder of additionFactor to j
          if (j + additionFactor < pixels[0].length) {
            nodeCoord[0] = j + additionFactor;
            nodeCoord[1] = i;
          }
          // if there is a next row and the remainder is smaller than that row
          else if (i + 1 < pixels.length && j + additionFactor - pixels[0].length < pixels[0].length) {
            nodeCoord[0] = j + additionFactor - pixels[0].length;
            nodeCoord[1] = i + 1;
          } else {
            nodeCoord[0] = j;
            nodeCoord[1] = i;
          }
          try {
            this.nodeCoords.add(nodeCoord);
            this.clusterMap[nodeCoord[0]][nodeCoord[1]] = 1;
          } catch (ArrayIndexOutOfBoundsException e) {
            try {
              this.clusterMap[j][i] = 1;
              this.nodeCoords.add(new int[] { j, i });
            } catch (ArrayIndexOutOfBoundsException e2) {
              System.out.println("Skipping.");
            }
          }
        }
      }
    }
    return this.clusterMap;
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
    if (pixelToNodeHashMap.containsKey(new int[] { x, y })) {
      return pixelToNodeHashMap.get(new int[] { x, y });
    }
    int[] closestNode = new int[2];
    int minDistance = Integer.MAX_VALUE;
    for (int i = 0; i < nodeCoords.size(); i++) {
      int[] nodeCoord = nodeCoords.get(i);
      int distance = (int) Math.sqrt(Math.pow(nodeCoord[0] - x, 2) + Math.pow(nodeCoord[1] - y, 2));
      if (distance < minDistance) {
        minDistance = distance;
        closestNode = nodeCoord;
        this.pixelToNodeHashMap.put(new int[] { x, y }, closestNode);
      }
    }
    return closestNode;
  }

  /**
   * Finds the average color of a cluster.
   * 
   * @param pixels     the pixels denoted.
   * @param clusterMap the map of pixel index to a predictable randomized boolean.
   * @param x          the x coordinate of the pixel.
   * @param y          the y coordinate of the pixel.
   * @return the average color of a cluster as a Pixel.
   */
  private Pixel findAverageColor(Pixel[][] pixels, int[][] clusterMap, int x, int y) {
    int[] closestNode = findClosestNode(pixels, clusterMap, x, y);
    int xCoord = closestNode[0];
    int yCoord = closestNode[1];
    int red = 0;
    int green = 0;
    int blue = 0;
    int numPixels = 0;
    for (int i = xCoord - 1; i <= xCoord + 1; i++) {
      for (int j = yCoord - 1; j <= yCoord + 1; j++) {
        if (i >= 0 && i < pixels.length && j >= 0 && j < pixels[0].length) {
          if (clusterMap[i][j] == 1) {
            red += pixels[i][j].getColorChannels()[0];
            green += pixels[i][j].getColorChannels()[1];
            blue += pixels[i][j].getColorChannels()[2];
            numPixels++;
          }
        }
      }
    }
    if (numPixels == 0) {
      numPixels = 1;
    }
    return new RGBPixel(red / numPixels, green / numPixels, blue / numPixels, 255);
  }

  /**
   * Applies the mosaic blur filter to a pixel array.
   * 
   * @param pixels the pixels denoted.
   * @param row    the row of the pixel array.
   * @param col    the column of the pixel array.
   * @return Pixel the blurred pixel.
   */
  @Override
  public Pixel evaluateFilter(Pixel[][] pixels, int row, int col) {
    return findAverageColor(pixels, generateClusterMap(pixels, 42), row, col);
  }

}
