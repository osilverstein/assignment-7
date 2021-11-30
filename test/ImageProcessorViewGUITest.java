import org.junit.Test;

import java.awt.event.ActionEvent;
import java.util.HashMap;

import javax.swing.JButton;

import controller.GUIController;
import controller.IGUIController;
import model.ImprovedImageProcessorModel;
import model.component.BrightenComponent;
import model.component.ValueComponent;
import model.filter.Sharpen;
import model.flip.HorizontalFlip;
import model.flip.VerticalFlip;
import utilities.ImageRunTimeStorage;
import utilities.ImageUtil;
import view.ImageProcessorViewGUI;

import static org.junit.Assert.assertEquals;

/**
 * To represent the examples and tests of a ImageProcessor GUI View.
 */
public class ImageProcessorViewGUITest {
  StringBuilder s;
  ImageRunTimeStorage storage;
  ImageProcessorViewGUI view;
  HashMap<String, ImprovedImageProcessorModel> map;
  ImprovedImageProcessorModel model;
  IGUIController c;
  String loadMessage;


  /**
   * Constructs a new ImageProcessorViewGUI test.
   */
  public ImageProcessorViewGUITest() {
    s = new StringBuilder();
    map = new HashMap<String, ImprovedImageProcessorModel>();
    model = ImageUtil.readOtherFiles("src/images/Koala.png");
    storage = new ImageRunTimeStorage(map);
    view = new MockView(s, storage);
    c = new GUIController(storage, view);
    this.loadMessage = "calling importImage\n" +
            "calling updateView with src/images/Koala.png\n" +
            "calling refresh and drawing new histogram!\n" +
            "Max RGB Value For Histogram: 255\n" +
            "Max Total Count For Histogram: 1327\n" +
            "calling sendOutPutMessage Successfully loaded!\n";
  }

  @Test
  public void testLoad() {
    c.actionPerformed(new ActionEvent(new JButton(), 3627894, "import"));
    assertEquals(loadMessage, s.toString());

    // Properly adding the image to the storage.
    assertEquals(storage.sizeOfStorage(), 1);
    assertEquals(storage.getCurrentModel(), "src/images/Koala.png");
  }

  @Test
  public void testSaveBeforeLoad() {
    c.actionPerformed(new ActionEvent(new JButton(), 12342, "export"));
    assertEquals("calling exportImage\n" +
            "calling sendOutPutMessage Must load before saving\n", s.toString());

    // No feature testing is necessary as the above message illustrates that the error was caught.
  }

  @Test
  public void testSaveAfterLoad() {
    c.actionPerformed(new ActionEvent(new JButton(), 3627894, "import"));
    c.actionPerformed(new ActionEvent(new JButton(), 12342, "export"));
    assertEquals(loadMessage + "calling exportImage\n" +
            "calling sendOutPutMessage Successfully saved!\n", s.toString());

    // What we are currently seeing (current model):
    ImprovedImageProcessorModel model = storage.grabFromRunTimeStorage(storage.getCurrentModel());

    // We are saving to "src/images/New.png" per our mock model:
    ImprovedImageProcessorModel modelSaved =
            ImageUtil.readOtherFiles("src/images/New.png");

    for (int row = 0 ; row < model.getHeight() ; row++) {
      for (int col = 0 ; col < model.getWidth() ; col++) {
        assertEquals(modelSaved.getPixel(row, col), (model.getPixel(row, col)));
      }
    }
  }

  @Test
  public void testSaveAfterLoadAndPhotoAlteration() {
    c.actionPerformed(new ActionEvent(new JButton(), 3627894, "import"));

    // Check to see that one image has been added to the storage:
    assertEquals(storage.sizeOfStorage(), 1);

    // Check to see what we are currently seeing:
    assertEquals(storage.getCurrentModel(), "src/images/Koala.png");

    c.actionPerformed(new ActionEvent(new JButton(), 523485, "Apply Component"));
    c.actionPerformed(new ActionEvent(new JButton(), 12342, "export"));
    assertEquals("calling importImage\n" +
            "calling updateView with src/images/Koala.png\n" +
            "calling refresh and drawing new histogram!\n" +
            "Max RGB Value For Histogram: 255\n" +
            "Max Total Count For Histogram: 1327\n" +
            "calling sendOutPutMessage Successfully loaded!\n" +
            "calling getDropdownValue with Component\n" +
            "calling updateView with src/images/Koala.png-value\n" +
            "calling refresh and drawing new histogram!\n" +
            "Max RGB Value For Histogram: 255\n" +
            "Max Total Count For Histogram: 1140\n" +
            "calling sendOutPutMessage Successfully applied value component!\n" +
            "calling exportImage\n" +
            "calling sendOutPutMessage Successfully saved!\n", s.toString());

    // Confirm that the image has been edited (storage is now of size 2):
    assertEquals(storage.sizeOfStorage(), 2);

    // Check to see that what we are currently seeing has been updated:
    assertEquals(storage.getCurrentModel(), "src/images/Koala.png-value");

    // What we are currently seeing (current model):
    ImprovedImageProcessorModel model = storage.grabFromRunTimeStorage(storage.getCurrentModel());

    // We are saving to "src/images/New.png" per our mock model:
    ImprovedImageProcessorModel modelSaved =
            ImageUtil.readOtherFiles("src/images/New.png");

    for (int row = 0 ; row < model.getHeight() ; row++) {
      for (int col = 0 ; col < model.getWidth() ; col++) {
        assertEquals(modelSaved.getPixel(row, col), (model.getPixel(row, col)));
      }
    }
  }

  @Test
  public void testFlipHorizontalBeforeLoad() {
    c.actionPerformed(new ActionEvent(new JButton(), 754235, "Flip Horizontal"));
    assertEquals("calling sendOutPutMessage Must load before flipping\n", s.toString());

    // Confirm that nothing has been added to storage:
    assertEquals(storage.sizeOfStorage(), 0);
  }

  @Test
  public void testFlipHorizontalAfterLoad() {
    c.actionPerformed(new ActionEvent(new JButton(), 3627894, "import"));

    // Confirm that one image has been added to storage after load is called:
    assertEquals(storage.sizeOfStorage(), 1);

    // What is the current model we are viewing:
    assertEquals(storage.getCurrentModel(), "src/images/Koala.png");

    c.actionPerformed(new ActionEvent(new JButton(), 754235, "Flip Horizontal"));

    // Confirm that a second image has been added to the storage after the flip is called.
    assertEquals(storage.sizeOfStorage(), 2);

    // Confirm that what we are currently viewing has been updated:
    assertEquals(storage.getCurrentModel(), "src/images/Koala.png-horizontal");

    assertEquals(loadMessage + "calling updateView with src/images/Koala.png-horizontal\n" +
            "calling refresh and drawing new histogram!\n" +
            "Max RGB Value For Histogram: 255\n" +
            "Max Total Count For Histogram: 1327\n" +
            "calling sendOutPutMessage Successfully flipped horizontally!\n", s.toString());

    // Check to see that the image has been flipped:
    ImprovedImageProcessorModel m1 = ImageUtil.readOtherFiles("src/images/Koala.png");
    m1.flip(new HorizontalFlip());

    ImprovedImageProcessorModel m2 = storage.grabFromRunTimeStorage(storage.getCurrentModel());

    for (int row = 0 ; row < m1.getHeight() ; row++) {
      for (int col = 0 ; col < m1.getWidth() ; col++) {
        assertEquals(m2.getPixel(row, col), (m1.getPixel(row, col)));
      }
    }
  }

  @Test
  public void testFlipVerticalBeforeLoad() {
    c.actionPerformed(new ActionEvent(new JButton(), 754235, "Flip Vertical"));
    assertEquals("calling sendOutPutMessage Must load before flipping\n", s.toString());

    // Confirm that nothing has been added to storage:
    assertEquals(storage.sizeOfStorage(), 0);
  }

  @Test
  public void testFlipVerticalAfterLoad() {
    c.actionPerformed(new ActionEvent(new JButton(), 3627894, "import"));

    // Confirm that one image has been added to storage after load is called:
    assertEquals(storage.sizeOfStorage(), 1);

    // What is the current model we are viewing:
    assertEquals(storage.getCurrentModel(), "src/images/Koala.png");

    c.actionPerformed(new ActionEvent(new JButton(), 754235, "Flip Vertical"));

    // Confirm that a second image has been added to the storage after the flip is called.
    assertEquals(storage.sizeOfStorage(), 2);

    // Confirm that what we are currently viewing has been updated:
    assertEquals(storage.getCurrentModel(), "src/images/Koala.png-vertical");

    assertEquals(loadMessage + "calling updateView with src/images/Koala.png-vertical\n" +
            "calling refresh and drawing new histogram!\n" +
            "Max RGB Value For Histogram: 255\n" +
            "Max Total Count For Histogram: 1327\n" +
            "calling sendOutPutMessage Successfully flipped vertically!\n", s.toString());

    // Check to see that the image has been flipped:
    ImprovedImageProcessorModel m1 = ImageUtil.readOtherFiles("src/images/Koala.png");
    m1.flip(new VerticalFlip());

    ImprovedImageProcessorModel m2 = storage.grabFromRunTimeStorage(storage.getCurrentModel());

    for (int row = 0 ; row < m1.getHeight() ; row++) {
      for (int col = 0 ; col < m1.getWidth() ; col++) {
        assertEquals(m2.getPixel(row, col), (m1.getPixel(row, col)));
      }
    }
  }


  @Test

  public void testApplyComponentBeforeLoad() {
    c.actionPerformed(new ActionEvent(new JButton(), 2356, "Apply Component"));
    assertEquals("calling getDropdownValue with Component\n" +
            "calling sendOutPutMessage Must load before applying component\n", s.toString());

    // Confirm that nothing has been added to storage:
    assertEquals(storage.sizeOfStorage(), 0);
  }

  @Test
  public void testApplyComponentAfterLoad() {
    c.actionPerformed(new ActionEvent(new JButton(), 3627894, "import"));

    // Confirm that the image has been added to the storage.
    assertEquals(storage.sizeOfStorage(), 1);

    // What is the current model we are viewing:
    assertEquals(storage.getCurrentModel(), "src/images/Koala.png");

    c.actionPerformed(new ActionEvent(new JButton(), 2356, "Apply Component"));

    // Confirm that a second image has been added to the storage after the flip is called.
    assertEquals(storage.sizeOfStorage(), 2);

    // Confirm that what we are currently viewing has been updated:
    assertEquals(storage.getCurrentModel(), "src/images/Koala.png-value");

    assertEquals(loadMessage + "calling getDropdownValue with Component\n" +
            "calling updateView with src/images/Koala.png-value\n" +
            "calling refresh and drawing new histogram!\n" +
            "Max RGB Value For Histogram: 255\n" +
            "Max Total Count For Histogram: 1140\n" +
            "calling sendOutPutMessage Successfully applied value component!\n", s.toString());

    // Check to see that the component has been applied to the image (we use "value" in the mock):
    ImprovedImageProcessorModel m1 = ImageUtil.readOtherFiles("src/images/Koala.png");
    m1.component(new ValueComponent());

    ImprovedImageProcessorModel m2 = storage.grabFromRunTimeStorage(storage.getCurrentModel());

    for (int row = 0 ; row < m1.getHeight() ; row++) {
      for (int col = 0 ; col < m1.getWidth() ; col++) {
        assertEquals(m2.getPixel(row, col), (m1.getPixel(row, col)));
      }
    }
  }

  @Test
  public void testApplyFilterBeforeLoad() {
    c.actionPerformed(new ActionEvent(new JButton(), 64326, "Apply Filter"));
    assertEquals("calling getDropdownValue with Filter\n" +
            "calling sendOutPutMessage Must load before applying filter\n", s.toString());
  }

  @Test
  public void testApplyFilterAfterLoad() {
    c.actionPerformed(new ActionEvent(new JButton(), 3627894, "import"));

    // Confirm that the image has been added to the storage.
    assertEquals(storage.sizeOfStorage(), 1);

    // What is the current model we are viewing:
    assertEquals(storage.getCurrentModel(), "src/images/Koala.png");

    c.actionPerformed(new ActionEvent(new JButton(), 64326, "Apply Filter"));

    // Confirm that a second image has been added to the storage after the flip is called.
    assertEquals(storage.sizeOfStorage(), 2);

    // Confirm that what we are currently viewing has been updated:
    assertEquals(storage.getCurrentModel(), "src/images/Koala.png-sharpen");

    assertEquals(loadMessage + "calling getDropdownValue with Filter\n" +
            "calling updateView with src/images/Koala.png-sharpen\n" +
            "calling refresh and drawing new histogram!\n" +
            "Max RGB Value For Histogram: 255\n" +
            "Max Total Count For Histogram: 1325\n" +
            "calling sendOutPutMessage Successfully applied sharpen filter!\n", s.toString());

    // Check to see that the component has been applied to the image (we use "value" in the mock):
    ImprovedImageProcessorModel m1 = ImageUtil.readOtherFiles("src/images/Koala.png");
    m1.filter(new Sharpen());

    ImprovedImageProcessorModel m2 = storage.grabFromRunTimeStorage(storage.getCurrentModel());

    for (int row = 0 ; row < m1.getHeight() ; row++) {
      for (int col = 0 ; col < m1.getWidth() ; col++) {
        assertEquals(m2.getPixel(row, col), (m1.getPixel(row, col)));
      }
    }
  }

  @Test
  public void testBrightenBeforeLoad() {
    c.actionPerformed(new ActionEvent(new JButton(), 64326, "Brighten"));
    assertEquals("calling retrieveBrightenScrollInput\n" +
            "calling sendOutPutMessage Must load before brightening\n", s.toString());

    // Confirm that nothing has been added to storage:
    assertEquals(storage.sizeOfStorage(), 0);
  }

  @Test
  public void testBrightenAfterLoad() {
    c.actionPerformed(new ActionEvent(new JButton(), 3627894, "import"));

    // Confirm that one image has been added to storage after load is called:
    assertEquals(storage.sizeOfStorage(), 1);

    // What is the current model we are viewing:
    assertEquals(storage.getCurrentModel(), "src/images/Koala.png");

    c.actionPerformed(new ActionEvent(new JButton(), 64326, "Brighten"));

    // Confirm that a second image has been added to the storage after the flip is called.
    assertEquals(storage.sizeOfStorage(), 2);

    // Confirm that what we are currently viewing has been updated:
    assertEquals(storage.getCurrentModel(), "src/images/Koala.png-brighten");

    assertEquals(loadMessage + "calling retrieveBrightenScrollInput\n" +
            "calling updateView with src/images/Koala.png-brighten\n" +
            "calling refresh and drawing new histogram!\n" +
            "Max RGB Value For Histogram: 255\n" +
            "Max Total Count For Histogram: 1327\n" +
            "calling sendOutPutMessage Successfully brightened!\n", s.toString());

    // Check to see that the image has been flipped:
    ImprovedImageProcessorModel m1 = ImageUtil.readOtherFiles("src/images/Koala.png");
    m1.component(new BrightenComponent(0));

    ImprovedImageProcessorModel m2 = storage.grabFromRunTimeStorage(storage.getCurrentModel());

    for (int row = 0 ; row < m1.getHeight() ; row++) {
      for (int col = 0 ; col < m1.getWidth() ; col++) {
        assertEquals(m2.getPixel(row, col), (m1.getPixel(row, col)));
      }
    }
  }

  @Test
  public void testUndoBeforeLoad() {
    c.actionPerformed(new ActionEvent(new JButton(), 64326, "Undo"));
    assertEquals("calling sendOutPutMessage Cannot undo!\n", s.toString());

    // Confirm that nothing has been added to storage:
    assertEquals(storage.sizeOfStorage(), 0);
  }

  @Test
  public void testUndoAfterLoad() {
    c.actionPerformed(new ActionEvent(new JButton(), 64326, "import"));
    c.actionPerformed(new ActionEvent(new JButton(), 64326, "Undo"));
    assertEquals(loadMessage + "calling sendOutPutMessage Cannot undo!\n", s.toString());

    // Confirm that there is still just 1 model in storage
    assertEquals(storage.sizeOfStorage(), 1);
  }

  @Test
  public void testUndoBrighten() {
    c.actionPerformed(new ActionEvent(new JButton(), 3627894, "import"));

    // Confirm that one image has been added to storage after load is called:
    assertEquals(storage.sizeOfStorage(), 1);

    // What is the current model we are viewing:
    assertEquals(storage.getCurrentModel(), "src/images/Koala.png");

    c.actionPerformed(new ActionEvent(new JButton(), 64326, "Brighten"));

    // Confirm that a second image has been added to the storage after the flip is called.
    assertEquals(storage.sizeOfStorage(), 2);

    // Confirm that what we are currently viewing has been updated:
    assertEquals(storage.getCurrentModel(), "src/images/Koala.png-brighten");

    c.actionPerformed(new ActionEvent(new JButton(), 64326, "Undo"));
    assertEquals(loadMessage + "calling retrieveBrightenScrollInput\n" +
            "calling updateView with src/images/Koala.png-brighten\n" +
            "calling refresh and drawing new histogram!\n" +
            "Max RGB Value For Histogram: 255\n" +
            "Max Total Count For Histogram: 1327\n" +
            "calling sendOutPutMessage Successfully brightened!\n" +
            "calling updateView with src/images/Koala.png\n" +
            "calling refresh and drawing new histogram!\n" +
            "Max RGB Value For Histogram: 255\n" +
            "Max Total Count For Histogram: 1327\n" +
            "calling sendOutPutMessage Successfully undid change!\n", s.toString());

    // Confirm that one image has been removed from storage after undo is called:
    assertEquals(storage.sizeOfStorage(), 1);
  }
}
