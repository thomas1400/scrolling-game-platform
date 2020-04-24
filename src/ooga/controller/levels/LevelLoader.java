package ooga.controller.levels;

import java.io.File;
import java.util.ResourceBundle;
import ooga.controller.LevelBuilder;

public final class LevelLoader {

  public static final String LEVEL_EXTENSION = ".level";
  public static final String LEVEL_NUMBERS = "levelNumbers";
  public static final String DATA_PATH = "data/";
  public static final String GAMEDATA_PATH = "gamedata/";
  public static final String LEVELS = "/levels/";
  public static final String LEVEL_ORDER_RESOURCE_ROOT = "/levels/resources/levelOrder";
  public static final String REGEX = ",";

  public static void loadLevels(String myGameType, BasicLevelList myBasicLevels) {
    ResourceBundle myLevelsBundle = ResourceBundle.getBundle(
        GAMEDATA_PATH + myGameType + LEVEL_ORDER_RESOURCE_ROOT);

    String[] levelNumbers = myLevelsBundle.getString(LEVEL_NUMBERS).split(REGEX);

    addAllAssociatedLevels(myGameType, myLevelsBundle, levelNumbers, myBasicLevels);
  }

  private static void addAllAssociatedLevels(String myGameType, ResourceBundle myLevelsBundle,
      String[] levelNumbers, BasicLevelList myBasicLevels) {
    for (String levelNumberString : levelNumbers) {
      int levelNumber = Integer.parseInt(levelNumberString);
      File levelFile =
          new File(DATA_PATH + GAMEDATA_PATH + myGameType + LEVELS +
              myLevelsBundle.getString(levelNumberString) + LEVEL_EXTENSION);
      myBasicLevels.addBasicLevel(LevelBuilder.buildBasicLevel(levelNumber, levelFile));
    }
  }

}
