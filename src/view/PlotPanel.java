package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JPanel;

import model.pixel.Pixel;

/**
 * An extension of the JPanel for the line plots of the channels of the current image.
 */
public class PlotPanel extends JPanel {

  protected int padding = 25;
  protected int labelPadding = 35;
  protected Color gridColor = new Color(200, 200, 200, 200);
  protected static final Stroke GRAPH_STROKE = new BasicStroke(2f);
  protected int pointWidth = 4;
  protected int numberYDivisions = 10;
  protected int[] reds;
  protected int[] greens;
  protected int[] blues;
  protected int[] intensities;
  protected int maxYValue;
  protected boolean loaded;
  protected int maxXValue;

  public PlotPanel() {
    this.loaded = false;
  }

  protected void setPixels(Pixel[][] pixels) {
    this.loaded = true;
    this.reds = new int[256];
    this.greens = new int[256];
    this.blues = new int[256];
    this.intensities = new int[256];

    //Count the values per channel value per channel
    for (Pixel[] pixel_row : pixels) {
      for (Pixel pixel : pixel_row) {
        int[] channels = pixel.getColorChannels();
        this.reds[channels[0]] += 1;
        this.greens[channels[1]] += 1;
        this.blues[channels[2]] += 1;
        int intensity = (channels[0] + channels[1] + channels[2]) / 3;
        this.intensities[intensity] += 1;
      }
    }

    this.maxXValue = pixels[0][0].getMaxValue();
    this.maxYValue = Arrays.stream(new int[]{Arrays.stream(this.reds).max().getAsInt(),
            Arrays.stream(this.greens).max().getAsInt(),
            Arrays.stream(this.blues).max().getAsInt(),
            Arrays.stream(this.intensities).max().getAsInt()}).max().getAsInt();

  }

  @Override
  protected void paintComponent(Graphics g) {
    if (!loaded) {
      return;
    }
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    double xScale = ((double) this.getWidth() - (2 * padding) - labelPadding) / (this.maxXValue);
    double yScale = ((double) this.getHeight() - (2 * padding) - labelPadding) / (this.maxYValue);

    List<int[]> allColors = new ArrayList<>();
    allColors.add(this.reds);
    allColors.add(this.greens);
    allColors.add(this.blues);
    allColors.add(this.intensities);

    //Turn the channel values per channel into points
    ArrayList<ArrayList<Point>> allPoints = new ArrayList<>();
    for (int[] color : allColors) {
      ArrayList<Point> points = new ArrayList<>();
      for (int i = 0; i < 256; i++) {
        int x1 = (int) (i * xScale + padding + labelPadding);
        int y1 = (int) ((this.maxYValue - color[i]) * yScale + padding);
        points.add(new Point(x1, y1));
      }
      allPoints.add(points);
    }

    // draw white background
    g2.setColor(Color.WHITE);
    g2.fillRect(padding + labelPadding, padding,
            getWidth() - (2 * padding) - labelPadding,
            getHeight() - 2 * padding - labelPadding);

    // create tick marks and grid lines for the y axis.
    for (int i = 0; i < numberYDivisions + 1; i++) {
      int tickX0 = padding + labelPadding;
      int tickX1 = pointWidth + padding + labelPadding;
      int tickY = getHeight() -
              ((i * (getHeight() - padding * 2 - labelPadding)) /
                      numberYDivisions + padding + labelPadding);

      String yLabel = (int) ((this.maxYValue * ((i * 1.0) / numberYDivisions))) + "";
      FontMetrics metrics = g2.getFontMetrics();
      int labelWidth = metrics.stringWidth(yLabel);

      g2.setColor(Color.BLACK);
      g2.drawLine(tickX0, tickY, tickX1, tickY);
      g2.drawString(yLabel, tickX0 - labelWidth - 5, tickY + (metrics.getHeight() / 3));

      g2.setColor(gridColor);
      g2.drawLine(padding + labelPadding + 1 + pointWidth,
              tickY, getWidth() - padding, tickY);
    }

    // and for x axis
    for (int i = 0; i < this.reds.length; i++) {
      int tickX = i * (getWidth() - padding * 2 - labelPadding) /
              (this.reds.length - 1) + padding + labelPadding;
      int tickY0 = getHeight() - padding - labelPadding;
      int tickY1 = tickY0 - pointWidth;
      g2.setColor(Color.BLACK);
      g2.drawLine(tickX, tickY0, tickX, tickY1);

      //Only draw the gridlines at intervals
      if ((i % Math.sqrt(this.maxXValue + 1)) == 0 || i == this.maxXValue) {
        g2.setColor(gridColor);
        g2.drawLine(tickX,
                getHeight() - padding - labelPadding - 1 - pointWidth, tickX, padding);

        g2.setColor(Color.BLACK);
        String xLabel = i + "";
        FontMetrics metrics = g2.getFontMetrics();
        int labelWidth = metrics.stringWidth(xLabel);
        g2.drawString(xLabel, tickX - labelWidth / 2,
                tickY0 + metrics.getHeight() + 3);
      }
    }

    // create x and y axes
    g2.drawLine(padding + labelPadding,
            getHeight() - padding - labelPadding, padding + labelPadding, padding);
    g2.drawLine(padding + labelPadding,
            getHeight() - padding - labelPadding,
            getWidth() - padding,
            getHeight() - padding - labelPadding);

    // draw the line plot for each
    this.drawGraphLines(Color.RED, allPoints.get(0), g2);
    this.drawGraphLines(Color.GREEN, allPoints.get(1), g2);
    this.drawGraphLines(Color.BLUE, allPoints.get(2), g2);
    this.drawGraphLines(Color.BLACK, allPoints.get(3), g2);
  }


  private void drawGraphLines(Color color, List<Point> points, Graphics2D graphics) {
    graphics.setColor(color);
    graphics.setStroke(GRAPH_STROKE);
    for (int i = 0; i < points.size() - 1; i++) {
      int x1 = points.get(i).x;
      int y1 = points.get(i).y;
      int x2 = points.get(i + 1).x;
      int y2 = points.get(i + 1).y;
      graphics.drawLine(x1, y1, x2, y2);
    }
  }
}