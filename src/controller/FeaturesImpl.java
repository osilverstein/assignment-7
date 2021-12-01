package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.ImprovedImageProcessorModel;
import model.component.BlueComponent;
import model.component.BrightenComponent;
import model.component.Component;
import model.component.GreenComponent;
import model.component.IntensityComponent;
import model.component.LumaComponent;
import model.component.RedComponent;
import model.component.SepiaComponent;
import model.component.ValueComponent;
import model.filter.Filter;
import model.filter.GaussianBlur;
import model.filter.MosaicBlur;
import model.filter.Sharpen;
import model.flip.Flip;
import model.flip.HorizontalFlip;
import model.flip.VerticalFlip;
import utilities.ImageRunTimeStorage;
import utilities.ImageUtil;

/**
 * An implementation of our features interface to assist communication between
 * the GUI view and
 * the GUI controller. It features methods for each action:
 * runComponent, runFilter, runFlip, runBrighten, undo, save, and load.
 */
public class FeaturesImpl implements IMEFeatures {
  ImageRunTimeStorage storage;
  ArrayList<String> storedImages;

  /**
   * Constructor that sets the storage from the controller, it is necessary for
   * all actions.
   * 
   * @param storage is the runtime storage itself.
   */
  public FeaturesImpl(ImageRunTimeStorage storage) {
    this.storage = storage;
    this.storedImages = new ArrayList<>();
  }

  private ImprovedImageProcessorModel getCopy(String errorMessage) {
    try {
      ImprovedImageProcessorModel original = storage.grabFromRunTimeStorage(storage.getCurrentModel());
      return original.getCopy();
    } catch (NullPointerException e) {
      throw new IllegalStateException(errorMessage);
    }
  }

  private void addToStorage(String nameMessage, ImprovedImageProcessorModel m) {
    storedImages.add(storage.getCurrentModel() + nameMessage);
    storage.addToRunTimeStorage(storage.getCurrentModel() + nameMessage, m);
  }

  @Override
  public void runComponent(String type) throws IllegalStateException {
    Map<String, Component> components = new HashMap<String, Component>();
    components.put("red", new RedComponent());
    components.put("green", new GreenComponent());
    components.put("blue", new BlueComponent());
    components.put("value", new ValueComponent());
    components.put("intensity", new IntensityComponent());
    components.put("luma", new LumaComponent());
    components.put("sepia", new SepiaComponent());

    ImprovedImageProcessorModel copy = this.getCopy("Must load before applying component");
    copy.component(components.get(type.toLowerCase()));
    this.addToStorage("-" + type, copy);
  }

  @Override
  public void runFilter(String type) {
    Map<String, Filter> filters = new HashMap<String, Filter>();
    filters.put("blur", new GaussianBlur());
    filters.put("sharpen", new Sharpen());
    filters.put("mosaic", new MosaicBlur(3));

    ImprovedImageProcessorModel copy = this.getCopy("Must load before applying filter");
    if (!type.contains("Mosaic")) {
      copy.filter(filters.get(type.toLowerCase()));
      this.addToStorage("-" + type, copy);
    } else { //if mosaic
      //type is the type + " " + the size of the mosaic tiles
      String[] split = type.split(" ");
      int size = Integer.parseInt(split[1]);
      filters.put("mosaic", new MosaicBlur(size));
      copy.filter(filters.get(split[0].toLowerCase()));
      this.addToStorage("-" + split[0], copy);
    }
  }

  @Override
  public void runFlip(String type) throws IllegalStateException {
    Map<String, Flip> flips = new HashMap<String, Flip>();
    flips.put("horizontal", new HorizontalFlip());
    flips.put("vertical", new VerticalFlip());

    ImprovedImageProcessorModel copy = this.getCopy("Must load before flipping");
    copy.flip(flips.get(type.toLowerCase()));
    this.addToStorage("-" + type, copy);
  }

  @Override
  public void runBrighten(int increment) {
    ImprovedImageProcessorModel copy = this.getCopy("Must load before brightening");
    copy.component(new BrightenComponent(increment));
    this.addToStorage("-brighten", copy);
  }

  @Override
  public void undoChange() {
    if (this.storage.sizeOfStorage() > 1) {
      String current = this.storage.getCurrentModel();
      this.storage.removeImage(current);
      this.storedImages.remove(this.storedImages.size() - 1);
      this.storage.setCurrentName(this.storedImages.get(this.storedImages.size() - 1));
    } else {
      throw new IllegalStateException("Cannot undo!");
    }
  }

  @Override
  public void saveImage(String path) throws IllegalStateException {
    try {
      ImageUtil.saveImage(path, storage.grabFromRunTimeStorage(storage.getCurrentModel()));
    } catch (NullPointerException error) {
      throw new IllegalStateException("Must load before saving");
    }
  }

  @Override
  public void loadImage(String path) {
    storedImages.add(path);
    ImageUtil.load(path, path, this.storage);
  }
}
