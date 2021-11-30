package utilities;

import java.util.Map;

import model.ImprovedImageProcessorModel;

/**
 * Represents a map containing the runtime storage of a image processor.
 */
public class ImageRunTimeStorage {
  Map<String, ImprovedImageProcessorModel> runTimeStorage;
  String currentName;

  /**
   * Constructs the run time storage of an image processor.
   *
   * @param runTimeStorage represents the storage.
   */
  public ImageRunTimeStorage(Map<String, ImprovedImageProcessorModel> runTimeStorage) {
    if (runTimeStorage == null) {
      throw new IllegalArgumentException("Runtime Storage cannot be null!");
    }
    this.runTimeStorage = runTimeStorage;
  }

  public String getCurrentModel() {
    return this.currentName;
  }

  /**
   * Adds an ImageProcessorModel to the storage using the provided name as key.
   *
   * @param name  represents the reference key for the ImageProcessorModel.
   * @param model represents the ImageProcessorModel to be stored in the map.
   */
  public void addToRunTimeStorage(String name, ImprovedImageProcessorModel model) {
    this.runTimeStorage.put(name, model);
    this.currentName = name;
  }

  public void setCurrentName(String name) {
    this.currentName = name;
  }

  /**
   * Retrieves an ImageProcessorModel from storage based on the provided key reference.
   *
   * @param name represents the key reference of an ImageProcessorModel in the map.
   * @return the ImageProcessorModel associated with the key in the map.
   */
  public ImprovedImageProcessorModel grabFromRunTimeStorage(String name) {
    return this.runTimeStorage.getOrDefault(name, null);
  }

  public int sizeOfStorage() {
    return this.runTimeStorage.size();
  }

  public void removeImage(String image) {
    this.runTimeStorage.remove(image);
  }
}
