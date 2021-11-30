package view;


import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.BorderLayout;
import java.awt.Color;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.ImprovedImageProcessorModel;
import model.pixel.Pixel;

import utilities.ImageRunTimeStorage;
import utilities.ImageUtil;

/**
 * An implementation of ImageProcessorViewGUI that utilizes JFrame.
 * This is the view component of the MVC model for the GUI version.
 */
public class ImageProcessorJView extends JFrame implements ImageProcessorViewGUI {

  private ImageRunTimeStorage storage;
  private PlotPanel plotPanel;
  private JSlider slider;
  private JButton loadButton;
  private JButton saveButton;

  private JButton flipHorizontal;
  private JButton flipVertical;

  private JButton applyFilter;
  private JButton applyComponent;
  private JButton applyBrighten;

  private JButton undo;

  private JLabel image;
  private JLabel textViewPath;

  private JComboBox<String> filterDropdown;
  private JComboBox<String> componentDropdown;

  /**
   * Constructor that creates the view structure with its panels, buttons, etc.
   *
   * @param imageRunTimeStorage is the storage of all models.
   */
  public ImageProcessorJView(ImageRunTimeStorage imageRunTimeStorage) {
    this.storage = imageRunTimeStorage;
    int width = 1440;
    int height = 900;
    this.setPreferredSize(new Dimension(width, height));
    this.setLocation(120, 75);
    this.setLayout(new BorderLayout());
    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    // Creating the image field.

    image = new JLabel();
    image.setText("Please load an image by selecting the import button");
    image.setHorizontalAlignment(SwingConstants.CENTER);

    JScrollPane scrollPan = new JScrollPane(image);

    JPanel imagePanel = new JPanel();
    imagePanel.setLayout(new BorderLayout());
    imagePanel.setPreferredSize(new Dimension((int) (width * 0.75), (int) (height * 0.7)));
    imagePanel.setBackground(new Color(250, 217, 193));
    imagePanel.add(scrollPan, BorderLayout.CENTER);

    this.add(imagePanel, BorderLayout.EAST);

    // Creating the histogram panel.

    JPanel plotContainer = new JPanel();

    plotPanel = new PlotPanel();
    plotPanel.setLayout(new BorderLayout());
    plotPanel.setPreferredSize(new Dimension((int) (width * 0.8), (int) (height * 0.25)));

    JLabel key = new JLabel("<html>Line Plot Key: <font color='#ff0000'>Red Component </font>" +
            "<font color='#00ff00'>Green Component </font> " +
            "<font color='#0000ff'>Blue Component </font> " +
            "<font color='#000000'>Intensity Component</font> </html>");

    plotContainer.add(key, BorderLayout.PAGE_START);
    plotContainer.add(plotPanel, BorderLayout.CENTER);

    this.add(plotContainer, BorderLayout.SOUTH);

    // Creating the button panel.

    JPanel buttonPanel = new JPanel();

    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
    buttonPanel.setPreferredSize(new Dimension((int) (width * 0.25), (height)));
    this.add(buttonPanel, BorderLayout.WEST);

    // Creating the brighten panel.

    JPanel brightenPanel = new JPanel();
    brightenPanel.setBorder(BorderFactory.createTitledBorder("Brighten / Darken"));

    slider = new JSlider(JSlider.HORIZONTAL, -255, 255, 0);
    slider.setMajorTickSpacing(102);
    slider.setMinorTickSpacing(51);
    slider.setPaintLabels(true);
    slider.setPaintTicks(true);
    brightenPanel.add(slider);

    applyBrighten = new JButton("Apply");
    applyBrighten.setActionCommand("Brighten");

    brightenPanel.add(applyBrighten);

    buttonPanel.add(brightenPanel);

    // Creating the component panel.

    JPanel componentPanel = new JPanel();
    componentPanel.setBorder(BorderFactory.createTitledBorder("Apply Component"));

    String[] componentsToChoose = {"Red", "Green", "Blue", "Value", "Intensity", "Luma", "Sepia"};

    componentDropdown = new JComboBox<>(componentsToChoose);
    componentDropdown.setBounds(80, 50, 120, 20);
    componentPanel.add(componentDropdown);

    applyComponent = new JButton("Apply");
    applyComponent.setActionCommand("Apply Component");
    applyComponent.setVerticalAlignment(SwingConstants.CENTER);
    componentPanel.add(applyComponent);

    buttonPanel.add(componentPanel);

    // Creating the filter panel.

    JPanel filterPanel = new JPanel();
    filterPanel.setBorder(BorderFactory.createTitledBorder("Filter"));

    String[] filtersToChoose = {"Sharpen", "Blur", "Mosaic"};

    filterDropdown = new JComboBox<>(filtersToChoose);
    filterDropdown.setBounds(80, 50, 140, 20);
    filterPanel.add(filterDropdown);

    applyFilter = new JButton("Apply");
    applyFilter.setActionCommand("Apply Filter");
    applyFilter.setVerticalAlignment(SwingConstants.CENTER);
    filterPanel.add(applyFilter);

    buttonPanel.add(filterPanel);

    // Creating the flip panel.

    JPanel flipPanel = new JPanel();
    flipPanel.setBorder(BorderFactory.createTitledBorder("Apply Flip"));

    flipHorizontal = new JButton("Horizontal Flip");
    flipHorizontal.setActionCommand("Flip Horizontal");
    flipHorizontal.setVerticalAlignment(SwingConstants.CENTER);
    flipPanel.add(flipHorizontal);

    flipVertical = new JButton("Vertical Flip");
    flipVertical.setActionCommand("Flip Vertical");
    flipVertical.setVerticalAlignment(SwingConstants.CENTER);
    flipPanel.add(flipVertical);

    buttonPanel.add(flipPanel);

    // Creating the import/export panel.

    JPanel portPanel = new JPanel();
    portPanel.setBorder(BorderFactory.createTitledBorder("Load & Save"));

    loadButton = new JButton("Import Image");
    loadButton.setActionCommand("import");
    loadButton.setVerticalAlignment(SwingConstants.CENTER);
    portPanel.add(loadButton);

    saveButton = new JButton("Export Image");
    saveButton.setActionCommand("export");
    saveButton.setVerticalAlignment(SwingConstants.CENTER);
    portPanel.add(saveButton);

    buttonPanel.add(portPanel);

    // Creating the undo panel.

    JPanel undoPanel = new JPanel();
    undoPanel.setBorder(BorderFactory.createTitledBorder("Undo Image Change"));

    undo = new JButton("Undo Change");
    undo.setActionCommand("Undo");
    undo.setVerticalAlignment(SwingConstants.CENTER);
    undoPanel.add(undo);

    buttonPanel.add(undoPanel);

    // Creating the output panel.

    JPanel outputPanel = new JPanel();
    outputPanel.setBorder(BorderFactory.createTitledBorder("Output"));

    textViewPath = new JLabel("Successfully loaded the Image Processor!");
    outputPanel.add(textViewPath);

    buttonPanel.add(outputPanel);


    this.pack();
    this.setVisible(true);
  }

  public void sendOutPutMessage(String message) {
    this.textViewPath.setText(message);
  }

  private void doPlot() {
    ImprovedImageProcessorModel m =
            this.storage.grabFromRunTimeStorage(this.storage.getCurrentModel());
    Pixel[][] pixels = new Pixel[m.getHeight()][m.getWidth()];
    for (int row = 0; row < m.getHeight(); row++) {
      for (int col = 0; col < m.getWidth(); col++) {
        pixels[row][col] = m.getPixel(row, col);
      }
    }
    this.plotPanel.setPixels(pixels);
  }

  /**
   * Retrieves the selected value for the brighten scroll bar.
   * @return the integer value selected.
   */
  public int retrieveBrightenScrollInput() {
    int value = this.slider.getValue();
    this.slider.setValue(0);
    return value;
  }

  /**
   * Imports an image with a file chooser.
   * @return the path of the file.
   */
  public String importImage() {
    final JFileChooser fchooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "JPG, JPEG, PNG, PPM, or BPM Images", "jpg", "ppm", "jpeg", "png", "bpm");
    fchooser.setFileFilter(filter);
    int value = fchooser.showOpenDialog(ImageProcessorJView.this);
    if (value == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      return f.getAbsolutePath();
    }
    throw new IllegalStateException("Failed to load file!");
  }

  /**
   * Exports the image with a file chooser.
   * @return the path of the file.
   */
  public String exportImage() {
    final JFileChooser fchooser = new JFileChooser(".");
    int value = fchooser.showSaveDialog(ImageProcessorJView.this);

    if (value == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      return f.getAbsolutePath();
    }
    throw new IllegalStateException("Failed to save file!");
  }

  /**
   * Retrieves the value of the dropdown respective to the given dropdown.
   * @param whichOne specifies which dropdown is being requested
   * @return the value of which Component or Filter is currently selected
   */
  public String getDropdownValue(String whichOne) {
    if (whichOne.equals("Component")) {
      return componentDropdown.getItemAt(componentDropdown.getSelectedIndex());
    } else if (whichOne.equals("Filter")) {
      return filterDropdown.getItemAt(filterDropdown.getSelectedIndex());
    }
    return null;
  }

  /**
   * Sets the action listeners for all the buttons.
   * @param actionEvent is the actionEvent from the controller
   */
  public void setCommandButtonListener(ActionListener actionEvent) {
    this.loadButton.addActionListener(actionEvent);
    this.saveButton.addActionListener(actionEvent);
    this.flipHorizontal.addActionListener(actionEvent);
    this.flipVertical.addActionListener(actionEvent);
    this.applyComponent.addActionListener(actionEvent);
    this.applyFilter.addActionListener(actionEvent);
    this.applyBrighten.addActionListener(actionEvent);
    this.undo.addActionListener(actionEvent);
  }

  /**
   * Set the image to the model with the given path.
   * @param absolutePath the path of the model in storage
   */
  public void updateView(String absolutePath) {
    ImprovedImageProcessorModel m =
            this.storage.grabFromRunTimeStorage(absolutePath);
    BufferedImage bI = null;
    try {
      bI = ImageUtil.convertToBuffered(m);
    } catch (NullPointerException e) {
      throw new IllegalStateException("Must load file of valid type");
    }

    this.image.setText("");
    this.image.setIcon(new ImageIcon(bI));
  }

  public void refresh() {
    this.repaint();
    this.doPlot();
  }
}
