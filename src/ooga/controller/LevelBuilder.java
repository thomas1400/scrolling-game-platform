package ooga.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import ooga.exceptions.ExceptionFeedback;
import ooga.model.data.Level;
import ooga.model.entity.EntityList;

public final class LevelBuilder {

  private static final String LEVEL_FILE_EXTENSION = ".level";
  public static final String USERS_PATH_NAME = "resources/levels";

  public static final String HEADER_TAG = "#HEADER";
  public static final String ENTITIES_TAG = "#ENTITIES";
  public static final String LEVEL_TAG = "#LEVEL";
  private static final String TAG_DELIMITER = "#";
  public static final String KEY_VAL_SEPARATOR = ":";
  public static final String LEVEL_OBJ_SEPARATOR = " ";
  private static final String MAIN_ENTITY_SYMBOL = "X";
  public static final String EMPTY_SPACE_SYMBOL = ".";
  public static final String WIDTH_SPECIFIER = "levelWidth";
  public static final String HEIGHT_SPECIFIER = "levelWidth";
  public static final int KEY_INDEX = 0;
  public static final int VALUE_INDEX = 1;

  public static final double PIXEL_BLOCK_RATIO = 30.0;
  public static final int HEIGHT_ADJUST = 1;

  public static Level buildLevel(String levelName) throws ExceptionFeedback, FileNotFoundException {
    File levelFile = getLevelFile(levelName);

    Map<String,String> headerInfo = getMapFromFile(levelFile, HEADER_TAG);
    Map<String,String> entityInfo = getMapFromFile(levelFile, ENTITIES_TAG);

    int levelHeight = Integer.parseInt(headerInfo.get(HEIGHT_SPECIFIER));
    int levelWidth = Integer.parseInt(headerInfo.get(WIDTH_SPECIFIER));

    EntityList levelEntities = buildEntities(levelFile, entityInfo, levelHeight, levelWidth);

    Level level = new Level(levelName, levelEntities);

    return null;
  }

  private static File getLevelFile(String levelName) throws ExceptionFeedback {
    FilenameFilter filter = (f, name) -> name.endsWith(LEVEL_FILE_EXTENSION);
    File folder = new File(USERS_PATH_NAME);
    File[] listOfFiles = folder.listFiles(filter);

    assert listOfFiles != null;
    for (File levelFile : listOfFiles){
      if (levelFile.getName().equals(levelName)) {
        //TODO: remove print statement
        System.out.println(levelFile.getName() + " file was found. Proceeding to Parse Level");
        return levelFile;
      }
    }
    throw new ExceptionFeedback();
  }

  private static Map<String, String> getMapFromFile(File levelFile, String sectionTag)
      throws FileNotFoundException, ExceptionFeedback {

    Map<String, String> sectionMap = new HashMap<>();

    Scanner sc = new Scanner(levelFile);
    String nextLine = sc.nextLine();
    nextLine = moveToSection(sectionTag, sc, nextLine);

    addDataToMap(sectionMap, sc, nextLine);

    return sectionMap;
  }

  private static String moveToSection(String sectionTag, Scanner sc, String nextLine) {
    while (!nextLine.contains(sectionTag)){
      nextLine = sc.nextLine();
    }
    return nextLine;
  }

  private static void addDataToMap(Map<String, String> sectionMap, Scanner sc, String nextLine)
      throws ExceptionFeedback {
    while (!nextLine.contains(TAG_DELIMITER)){
      String[] sectionLine = nextLine.split(KEY_VAL_SEPARATOR);
      if (sectionLine.length == 2){
        sectionMap.put(sectionLine[KEY_INDEX], sectionLine[VALUE_INDEX]);
      } else {
        //TODO: remove print statement
        System.out.println("Invalid Level File. Invalid info in section");
        throw new ExceptionFeedback();
      }
      nextLine = sc.nextLine();
    }
  }

  private static EntityList buildEntities(File levelFile, Map<String, String> entityInfo,
      int height, int width)
      throws FileNotFoundException {
    EntityList myEntities = new EntityList();

    Scanner sc = new Scanner(levelFile);
    moveToSection(LEVEL_TAG, sc, sc.nextLine());

    for (int j = 0; j < height; j++){
      String[] levelLine = sc.nextLine().split(LEVEL_OBJ_SEPARATOR);
      for (int i = 0; i < width; i++){
        String symbol = levelLine[i];
        if (!symbol.equals(EMPTY_SPACE_SYMBOL)){
          String entityFile = entityInfo.get(symbol);
          //EntityBuilder myEntityBuilder = new EntityBuilder(entityFile);
          //Entity myEntity = myEntityBuilder.getEntity();
          //myEntity.setX(getRelativeX(i));
          //myEntity.setY(getRelativeY(j,height));
          if (symbol.equals(MAIN_ENTITY_SYMBOL)){
            //myEntities.addMainEntity(myEntity);
          } else{
            //myEntities.addEntity(myEntity);
          }
        }
      }
    }

    return myEntities;
  }

  private static double getRelativeY(int j, int height) {
    return (height-HEIGHT_ADJUST-j)*PIXEL_BLOCK_RATIO;
  }

  private static double getRelativeX(int i) {
    return i*PIXEL_BLOCK_RATIO;
  }

}
