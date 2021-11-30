package controller;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Function;

import controller.commands.BlueComponentCommand;
import controller.commands.BlurCommand;
import controller.commands.BrightenCommand;
import controller.commands.Command;
import controller.commands.GreenComponentCommand;
import controller.commands.HorizontalFlipCommand;
import controller.commands.IntensityComponentCommand;
import controller.commands.LumaComponentCommand;
import controller.commands.RedComponentCommand;
import controller.commands.SaveCommand;
import controller.commands.SepiaComponentCommand;
import controller.commands.SharpenCommand;
import controller.commands.ValueComponentCommand;
import controller.commands.VerticalFlipCommand;
import model.ImprovedImageProcessorModel;
import utilities.ImageRunTimeStorage;
import utilities.ImageUtil;
import view.ImageProcessorView;

/**
 * Represents the controller for an ImageProcessor.
 */
public class UpdatedController implements ImageProcessorController {
  private final ImageRunTimeStorage map;
  private final ImageProcessorView view;
  private final Readable in;
  private boolean loaded;

  /**
   * Constructs the controller for an ImageProcessor.
   *
   * @param map  represents the runtime storage for an ImageProcessor.
   * @param view represents the text view for an ImageProcessor.
   * @param in   represents receiver of user input for an ImageProcessor.
   */
  public UpdatedController(ImageRunTimeStorage map, ImageProcessorView view, Readable in) {
    if (map == null || view == null || in == null) {
      throw new IllegalArgumentException("Fields cannot be null!");
    }
    this.map = map;
    this.view = view;
    this.in = in;
    this.loaded = false;
  }

  @Override
  public void useImageProcessor() throws IllegalStateException, InputMismatchException {
    Scanner scan = new Scanner(this.in);
    Map<String, Function<Scanner, Command>> knownCommands;

    knownCommands = new HashMap<>();
    knownCommands.put("red-component",
        s -> {
            try {
              return new RedComponentCommand(s.next(), s.next(), this.map);
            } catch (NoSuchElementException e) {
              throw new IllegalStateException("Ran out of inputs.");
            }
        });
    knownCommands.put("green-component",
        s -> {
            try {
              return new GreenComponentCommand(s.next(), s.next(), this.map);
            } catch (NoSuchElementException e) {
              throw new IllegalStateException("Ran out of inputs.");
            }
        });
    knownCommands.put("blue-component",
        s -> {
            try {
              return new BlueComponentCommand(s.next(), s.next(), this.map);
            } catch (NoSuchElementException e) {
              throw new IllegalStateException("Ran out of inputs.");
            }
        });
    knownCommands.put("luma-component",
        s -> {
            try {
              return new LumaComponentCommand(s.next(), s.next(), this.map);
            } catch (NoSuchElementException e) {
              throw new IllegalStateException("Ran out of inputs.");
            }
        });
    knownCommands.put("intensity-component",
        s -> {
            try {
              return new IntensityComponentCommand(s.next(), s.next(), this.map);
            } catch (NoSuchElementException e) {
              throw new IllegalStateException("Ran out of inputs.");
            }
        });
    knownCommands.put("sepia-component",
        s -> {
            try {
              return new SepiaComponentCommand(s.next(), s.next(), this.map);
            } catch (NoSuchElementException e) {
              throw new IllegalStateException("Ran out of inputs.");
            }
        });
    knownCommands.put("value-component",
        s -> {
            try {
              return new ValueComponentCommand(s.next(), s.next(), this.map);
            } catch (NoSuchElementException e) {
              throw new IllegalStateException("Ran out of inputs.");
            }
        });
    knownCommands.put("gaussian-blur",
        s -> {
            try {
              return new BlurCommand(s.next(), s.next(), this.map);
            } catch (NoSuchElementException e) {
              throw new IllegalStateException("Ran out of inputs.");
            }
        });
    knownCommands.put("sharpen",
        s -> {
            try {
              return new SharpenCommand(s.next(), s.next(), this.map);
            } catch (NoSuchElementException e) {
              throw new IllegalStateException("Ran out of inputs.");
            }
        });
    knownCommands.put("horizontal-flip",
        s -> {
            try {
              return new HorizontalFlipCommand(s.next(), s.next(), this.map);
            } catch (NoSuchElementException e) {
              throw new IllegalStateException("Ran out of inputs.");
            }
        });
    knownCommands.put("vertical-flip",
        s -> {
            try {
              return new VerticalFlipCommand(s.next(), s.next(), this.map);
            } catch (NoSuchElementException e) {
              throw new IllegalStateException("Ran out of inputs.");
            }
        });
    knownCommands.put("brighten",
        s -> {
            try {
              return new BrightenCommand(s.nextInt(), s.next(), s.next(), this.map);
            } catch (InputMismatchException e) {
              s.next();
              s.next();
              s.next();
              throw new IllegalStateException("Brighten requires an integer, please try again");
            } catch (NoSuchElementException e) {
              throw new IllegalStateException("Ran out of inputs.");
            }
        });
    knownCommands.put("save",
        s -> new SaveCommand(s.next(), s.next(), this.map));

    while (scan.hasNext()) {
      Command c;
      String in = scan.next();

      if (in.equalsIgnoreCase("q") || in.equalsIgnoreCase("quit")) {
        this.transmitMessage("Quitting");
        return;
      }

      Function<Scanner, Command> cmd = knownCommands.getOrDefault(in, null);

      if (cmd == null) {
        if (in.equalsIgnoreCase("load")) {
          String path;
          String filename;
          try {
            path = scan.next();
            filename = scan.next();
          } catch (NoSuchElementException e) {
            throw new IllegalStateException("ran out of inputs");
          }
          this.load(path, filename);
        } else {
          this.transmitMessage("Unknown Command");
        }
      } else {
        if (!this.loaded) {
          this.transmitMessage("Must load an image first!");
          continue;
        }

        try {
          c = cmd.apply(scan);
          String output = c.use(c.getModelCopy());
          this.transmitMessage(output);
        } catch (IllegalStateException e) {
          this.transmitMessage(e.getMessage());
        }
      }
    }
  }

  private void load(String path, String filename) {
    if (!path.endsWith("ppm")) {
      try {
        ImprovedImageProcessorModel m = ImageUtil.readOtherFiles(path);
        this.map.addToRunTimeStorage(filename, m);
        this.loaded = true;
        this.transmitMessage("Successfully loaded image: " + filename + " from " + path);
      } catch (NullPointerException e) {
        this.transmitMessage("Invalid file type!");
      } catch (IllegalStateException e) {
        this.transmitMessage(e.getMessage());
      }
    } else {
      try {
        ImprovedImageProcessorModel m = ImageUtil.readPPM(path);
        this.map.addToRunTimeStorage(filename, m);
        this.loaded = true;
        this.transmitMessage("Successfully loaded image: " + filename + " from " + path);
      } catch (IllegalStateException e) {
        this.transmitMessage(e.getMessage());
      }
    }
  }

  /**
   * Transmits a given message to the appendable while catching IOExceptions and transmitting them
   * to the user as IllegalStateExceptions.
   *
   * @param string represents the string that will be transmitted to the appendable.
   */
  private void transmitMessage(String string) throws IllegalStateException {
    view.sendOutPutMessage(string);
    view.sendOutPutMessage("\n");
  }
}
