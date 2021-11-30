import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Scanner;

import controller.GUIController;
import controller.ImageProcessorController;
import controller.UpdatedController;
import utilities.ImageRunTimeStorage;
import view.ImageProcessorJView;
import view.ImageProcessorTextView;
import view.ImageProcessorView;
import view.ImageProcessorViewGUI;

/**
 * Main Class that hosts the main method.
 */
public class ImageProcessor {
  /**
   * Main method which creates the model, view, and controller accordingly to the given args.
   * @param args determine the parameters of the game, could be -file txt, -text, or empty
   */
  public static void main(String[] args) {
    Appendable out = System.out;
    Readable in = new InputStreamReader(System.in);
    ImageProcessorView view = new ImageProcessorTextView(out);
    ImageRunTimeStorage storage = new ImageRunTimeStorage(new HashMap<>());
    ImageProcessorController c1 = new UpdatedController(storage, view, in);

    try {
      String command = args[0];

      switch (command) {
        case ("-file"):
          Scanner sc;

          try {
            String fileLoc = args[1];
            sc = new Scanner(new FileInputStream(fileLoc));

            StringBuilder builder = new StringBuilder();
            while (sc.hasNextLine()) {
              String s = sc.nextLine();
              builder.append(s);
              builder.append("\n");
            }
            builder.append("q");

            Readable inTxt = new InputStreamReader(
                    new ByteArrayInputStream(builder.toString().getBytes()));

            c1 = new UpdatedController(storage, view, inTxt);
            c1.useImageProcessor();
          } catch (FileNotFoundException e) {
            view.sendOutPutMessage("File location not found! Retry!");
          } catch (IndexOutOfBoundsException e) {
            view.sendOutPutMessage("Need to include script!");
          }
          break;
        case ("-text"):
          c1.useImageProcessor();
          break;
        default :
          view.sendOutPutMessage("Invalid Command!");
      }
    }
    catch (IndexOutOfBoundsException e) {
      ImageProcessorViewGUI viewGUI = new ImageProcessorJView(storage);
      c1 = new GUIController(storage, viewGUI);
      c1.useImageProcessor();
    }
  }
}
