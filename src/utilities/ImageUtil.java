package utilities;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import model.ImprovedImageModel;
import model.ImprovedImageProcessorModel;
import model.pixel.Pixel;
import model.pixel.RGBPixel;


/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 */
public class ImageUtil {

  /**
   * Read an image file in the PPM format and print the colors.
   *
   * @param filename the path of the file.
   */
  public static ImprovedImageModel readPPM(String filename) throws IllegalStateException {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("File " + filename + " not found!");
    }

    if (!filename.endsWith(".ppm")) {
      throw new IllegalStateException("File format is not PPM!");
    }

    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      //System.out.println("Invalid PPM file: plain RAW file should begin with P3");
      throw new IllegalStateException("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    //System.out.println("Width of image: " + width);
    int height = sc.nextInt();
    //System.out.println("Height of image: " + height);
    int maxValue = sc.nextInt();
    //System.out.println("Maximum value of a color in this file (usually 255): " + maxValue);

    Pixel[][] pixels = new Pixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        pixels[i][j] = new RGBPixel(r, g, b, maxValue);
      }
    }
    return new ImprovedImageModel(pixels, width, height, maxValue);
  }

  /**
   * Read an image file in any other format and print the colors.
   * @param filename is the filename of the file
   * @return a Model with the correct specifications.
   */
  public static ImprovedImageProcessorModel readOtherFiles(String filename) {
    File f = new File(filename);

    try {
      BufferedImage image = ImageIO.read(f);
      int width = image.getWidth();
      int height = image.getHeight();
      int maxValue = 255;
      Pixel[][] pixels = new Pixel[height][width];

      for (int i = 0; i < height; i++) {
        for (int z = 0; z < width; z++) {
          int r = new Color(image.getRGB(z, i)).getRed();
          int g = new Color(image.getRGB(z, i)).getGreen();
          int b = new Color(image.getRGB(z, i)).getBlue();
          pixels[i][z] = new RGBPixel(r, g, b, maxValue);
        }
      }
      return new ImprovedImageModel(pixels, width, height, maxValue);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to load image!");
    }
  }

  /**
   * Saves an image with the given name to the specified path which should include the
   * name of the file.
   *
   * @param path represents the new reference namne of the image.
   */
  public static void saveImage(String path, ImprovedImageProcessorModel m) {
    if (path.endsWith("ppm")) {
      ImageUtil.savePPM(path, m);
    }
    String type = path.substring(path.length() - 3);
    try {
      BufferedImage output = ImageUtil.convertToBuffered(m);
      File f = new File(path);

      ImageIO.write(output, type, f);
    } catch (IOException e) {
      throw new IllegalStateException("Unable to save file to: " + path);
    }
  }

  private static void savePPM(String path, ImprovedImageProcessorModel m) {
    try {
      BufferedWriter bw = new BufferedWriter(
              new FileWriter(path));
      bw.write(m.toFile());
      bw.close();
    } catch (IOException e) {
      throw new IllegalStateException("Could not save image to " + path);
    }
  }

  /**
   * Loads the image from the given path and stores it in the given map with the given filename.
   * @param path is the path of the file in the file system
   * @param filename is the name that will be saved in the map
   * @param map is the storage of models
   * @return true if it worked and false otherwise
   */
  public static boolean load(String path, String filename, ImageRunTimeStorage map) {
    if (!path.endsWith("ppm")) {
      try {
        ImprovedImageProcessorModel m = ImageUtil.readOtherFiles(path);
        map.addToRunTimeStorage(filename, m);
        return true;
      } catch (NullPointerException e) {
        return false;
      } catch (IllegalStateException e) {
        return false;
      }
    }
    else {
      try {
        ImprovedImageProcessorModel m = ImageUtil.readPPM(path);
        map.addToRunTimeStorage(filename, m);
        System.out.println("Load has been called!");
        System.out.println(map.getCurrentModel());
        return true;
      } catch (IllegalStateException e) {
        return false;
      }
    }
  }

  /**
   * Transforms a given model into a BufferedImage.
   * @param model is the model from which the image is made
   * @return the BufferedImage made from the model
   */
  public static BufferedImage convertToBuffered(ImprovedImageProcessorModel model) {
    BufferedImage output = new BufferedImage(
            model.getWidth(),
            model.getHeight(),
            BufferedImage.TYPE_INT_RGB);

    for (int i = 0; i < model.getHeight(); i++) {
      for (int j = 0; j < model.getWidth(); j++) {
        int r = model.getPixel(i, j).getColorChannels()[0];
        int g = model.getPixel(i, j).getColorChannels()[1];
        int b = model.getPixel(i, j).getColorChannels()[2];

        int color = (r << 16) + (g << 8) + b;
        output.setRGB(j, i, color);
      }
    }
    return output;
  }
}

