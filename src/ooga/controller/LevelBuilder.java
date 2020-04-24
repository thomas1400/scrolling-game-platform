package ooga.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.MalformedInputException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import ooga.exceptions.ExceptionFeedback;
import ooga.controller.levels.BasicLevel;
import ooga.controller.levels.CompleteLevel;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityBuilder;
import ooga.model.entity.EntityList;

public final class LevelBuilder {

  private static final String HEADER_TAG = "#HEADER";
  private static final String ENTITIES_TAG = "#ENTITIES";
  private static final String LEVEL_TAG = "#LEVEL";
  private static final String TAG_DELIMITER = "#";
  private static final String KEY_VAL_SEPARATOR = ":";
  private static final String LEVEL_OBJ_SEPARATOR = " ";
  private static final String MAIN_ENTITY_SYMBOL = "X";
  private static final String EMPTY_SPACE_SYMBOL = ".";
  private static final int KEY_INDEX = 0;
  private static final int VALUE_INDEX = 1;
  private static final double PADDING = 0.01;
  private static final String LEVEL_HEIGHT_SPECIFIER = "levelHeight";
  private static final String LEVEL_WIDTH_SPECIFIER = "levelWidth";

  /**
   * Returns a BasicLevel based off of a file containing pertinent information
   * @param levelNumber the level number previously parsed, used to create the basic level
   * @param levelFile the file to be parsed to create the level header map
   * @return a new BasicLevel with the correct LevelHeader map
   * location
   */
  public static BasicLevel buildBasicLevel(int levelNumber, File levelFile) {
    Map<String,String> headerInfo = getMapFromFile(levelFile, HEADER_TAG);
    return new BasicLevel(levelNumber, levelFile, headerInfo);
  }

  /**
   * Parses the level file from a basic level, and builds/positions the appropriate entities and
   * other level attributes, placing them in a CompleteLevel to be used in the LevelLoop
   * @param basicLevel needed to get the basic features of the complete level
   * @param gameWindowHeight used to size the entities properly
   * @param gameWindowWidth used to size the entities properly
   * @return a CompleteLevel with the appropriate Entity
   */
  public static CompleteLevel buildCompleteLevel(BasicLevel basicLevel, double gameWindowHeight,
      double gameWindowWidth) {

    File levelFile = basicLevel.getLevelFile();
    int levelHeight = Integer.parseInt(basicLevel.getHeaderInfo().get(LEVEL_HEIGHT_SPECIFIER));
    int levelWidth = Integer.parseInt(basicLevel.getHeaderInfo().get(LEVEL_WIDTH_SPECIFIER));

    EntityList levelEntities = buildEntities(levelFile, levelHeight, levelWidth,
        basicLevel.getGameType(), gameWindowHeight, gameWindowWidth);

    return new CompleteLevel(basicLevel, levelEntities);
  }

  private static Map<String, String> getMapFromFile(File levelFile, String sectionTag) {

    Map<String, String> sectionMap = new HashMap<>();

    Scanner sc = getScanner(levelFile);

    moveToSection(sectionTag, sc);
    addDataToMap(sectionMap, sc);

    return sectionMap;
  }

  private static Scanner getScanner(File levelFile) {
    Scanner sc = null;
    try {
      sc = new Scanner(levelFile);
    } catch (FileNotFoundException e) {
      ExceptionFeedback.throwBreakingException(e, "The level file " + levelFile.getName() + " "
          + "cannot be found. Level Unable to be built. Please check to see if the file exists "
          + "and is in the correct location");
    }
    return sc;
  }

  private static void moveToSection(String sectionTag, Scanner sc) {
    String nextLine = sc.nextLine();
    while (!nextLine.contains(sectionTag)){
      nextLine = sc.nextLine();
    }
  }

  private static void addDataToMap(Map<String, String> sectionMap, Scanner sc) {
    String nextLine = sc.nextLine();
    while (!nextLine.contains(TAG_DELIMITER)){
      String[] sectionLine = nextLine.split(KEY_VAL_SEPARATOR);
      if (sectionLine.length == 2){
        sectionMap.put(sectionLine[KEY_INDEX], sectionLine[VALUE_INDEX]);
      } else {
        ExceptionFeedback.throwBreakingException(
            new MalformedInputException(0), "Invalid Level File. Invalid info in section");
      }
      nextLine = sc.nextLine();
    }
  }

  private static EntityList buildEntities(File levelFile, int levelHeight, int levelWidth,
      String gameType, double gameWindowHeight, double gameWindowWidth) {

    EntityList myEntities = new EntityList();
    Map<String,String> entityInfo = getMapFromFile(levelFile, ENTITIES_TAG);

    Scanner sc = getScanner(levelFile);
    moveToSection(LEVEL_TAG, sc);

    double scaleFactor = getScaleFactor(levelHeight, levelWidth, gameWindowHeight, gameWindowWidth);
    createAllEntities(levelHeight, levelWidth, gameType, myEntities, entityInfo, sc, scaleFactor);

    return myEntities;
  }

  private static void createAllEntities(int levelHeight, int levelWidth, String gameType,
      EntityList myEntities, Map<String, String> entityInfo, Scanner sc, double scaleFactor) {
    for (int j = 0; j < levelHeight; j++){
      String[] levelLine = sc.nextLine().split(LEVEL_OBJ_SEPARATOR);
      for (int i = 0; i < levelWidth; i++){
        String entityCode = levelLine[i];
        if (!entityCode.equals(EMPTY_SPACE_SYMBOL)){
          initializeEntity(gameType, myEntities, entityInfo, scaleFactor, j, i, entityCode);
        }
      }
    }
  }

  private static void initializeEntity(String gameType, EntityList myEntities,
      Map<String, String> entityInfo, double scaleFactor, int j, int i, String entityCode) {
    String entityName = entityInfo.get(entityCode);
    Entity myEntity = EntityBuilder.getEntity(entityName, gameType);
    setEntitySize(myEntity, scaleFactor);
    setEntityCoordinates(j, i, myEntity, scaleFactor);
    addNewEntityToEntitiesList(myEntities, entityCode, myEntity);
  }

  private static double getScaleFactor(int levelHeight, int levelWidth, double gameWindowHeight,
      double gameWindowWidth) {
    return Math.max(gameWindowHeight/levelHeight, gameWindowWidth/levelWidth);
  }

  private static void setEntitySize(Entity myEntity, double scaleFactor) {
    double scalingFactor = myEntity.getBoundsInLocal().getWidth()/scaleFactor;
    myEntity.setFitWidth(myEntity.getBoundsInLocal().getWidth()/scalingFactor - PADDING);
    myEntity.setFitHeight(myEntity.getBoundsInLocal().getHeight()/scalingFactor - PADDING);
  }

  private static void addNewEntityToEntitiesList(EntityList myEntities, String symbol, Entity myEntity) {
    if (symbol.equals(MAIN_ENTITY_SYMBOL)) {
      myEntities.setMainEntity(myEntity);
    }
    myEntities.addEntity(myEntity);
  }

  private static void setEntityCoordinates(int j, int i, Entity myEntity,
      double scaleFactor) {
    myEntity.setX(getRelativeX(i, scaleFactor));
    myEntity.setY(getRelativeY(myEntity.getBoundsInLocal().getHeight(), j, scaleFactor));
  }

  private static double getRelativeY(double entityHeight, int j, double scaleFactor) {
    return (j*scaleFactor) - entityHeight + scaleFactor + PADDING/2;
  }

  private static double getRelativeX(int i, double scaleFactor) {
    return i*scaleFactor + PADDING/2;
  }

}
