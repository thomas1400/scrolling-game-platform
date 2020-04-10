package ooga.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.nio.charset.MalformedInputException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import ooga.exceptions.ExceptionFeedback;
import ooga.model.data.Level;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityBuilder;
import ooga.model.entity.EntityList;

public final class LevelBuilder {

  private static final String LEVEL_FILE_EXTENSION = ".level";
  private static final String USERS_PATH_NAME = "resources/levels";

  private static final String HEADER_TAG = "#HEADER";
  private static final String ENTITIES_TAG = "#ENTITIES";
  private static final String LEVEL_TAG = "#LEVEL";
  private static final String TAG_DELIMITER = "#";
  private static final String KEY_VAL_SEPARATOR = ":";
  private static final String LEVEL_OBJ_SEPARATOR = " ";
  private static final String MAIN_ENTITY_SYMBOL = "X";
  private static final String EMPTY_SPACE_SYMBOL = ".";
  private static final String WIDTH_SPECIFIER = "levelWidth";
  private static final String HEIGHT_SPECIFIER = "levelHeight";
  private static final int KEY_INDEX = 0;
  private static final int VALUE_INDEX = 1;

  public static Level buildLevel(int levelNumber, double gameWindowHeight) throws FileNotFoundException {
    File levelFile = getLevelFile(levelNumber);

    Map<String,String> headerInfo = getMapFromFile(levelFile, HEADER_TAG);
    Map<String,String> entityInfo = getMapFromFile(levelFile, ENTITIES_TAG);

    int levelHeight = Integer.parseInt(headerInfo.get(HEIGHT_SPECIFIER));
    int levelWidth = Integer.parseInt(headerInfo.get(WIDTH_SPECIFIER));

    EntityList levelEntities = buildEntities(levelFile, entityInfo, levelHeight, levelWidth, gameWindowHeight);

    return new Level(levelNumber, headerInfo,  levelEntities);
  }

  private static File getLevelFile(int levelNumber) {
    FilenameFilter filter = (f, name) -> name.endsWith(LEVEL_FILE_EXTENSION);
    File folder = new File(USERS_PATH_NAME);
    File[] listOfFiles = folder.listFiles(filter);

    assert listOfFiles != null;
    for (File levelFile : listOfFiles){
      if (levelFile.getName().equals(levelNumber + LEVEL_FILE_EXTENSION)) {
        //TODO: remove print statement
        System.out.println(levelFile.getName() + " file was found. Proceeding to Parse Level");
        return levelFile;
      }
    }
    ExceptionFeedback.throwException(new FileNotFoundException(), "File not found");
    return null;
  }

  private static Map<String, String> getMapFromFile(File levelFile, String sectionTag)
      throws FileNotFoundException {

    Map<String, String> sectionMap = new HashMap<>();

    Scanner sc = new Scanner(levelFile);
    sc = moveToSection(sectionTag, sc);

    addDataToMap(sectionMap, sc);

    return sectionMap;
  }

  private static Scanner moveToSection(String sectionTag, Scanner sc) {
    String nextLine = sc.nextLine();
    while (!nextLine.contains(sectionTag)){
      nextLine = sc.nextLine();
    }
    return sc;
  }

  private static void addDataToMap(Map<String, String> sectionMap, Scanner sc) {
    String nextLine = sc.nextLine();
    while (!nextLine.contains(TAG_DELIMITER)){
      String[] sectionLine = nextLine.split(KEY_VAL_SEPARATOR);
      if (sectionLine.length == 2){
        sectionMap.put(sectionLine[KEY_INDEX], sectionLine[VALUE_INDEX]);
      } else {
        ExceptionFeedback.throwException(
            new MalformedInputException(0), "Invalid Level File. Invalid info in section");
      }
      nextLine = sc.nextLine();
    }
  }

  private static EntityList buildEntities(File levelFile, Map<String, String> entityInfo,
      int levelHeight, int width, double gameWindowHeight)
      throws FileNotFoundException {
    EntityList myEntities = new EntityList();

    Scanner sc = new Scanner(levelFile);
    moveToSection(LEVEL_TAG, sc);

    for (int j = 0; j < levelHeight; j++){
      String[] levelLine = sc.nextLine().split(LEVEL_OBJ_SEPARATOR);
      for (int i = 0; i < width; i++){
        String symbol = levelLine[i];
        if (!symbol.equals(EMPTY_SPACE_SYMBOL)){
          String entityFile = entityInfo.get(symbol);
          Entity myEntity = EntityBuilder.getEntity(entityFile);
          double scaleFactor = gameWindowHeight/levelHeight;
          setEntitySize(myEntity, scaleFactor);
          setEntityCoordinates(levelHeight, j, i, myEntity, scaleFactor);
          addNewEntityToEntitiesList(myEntities, symbol, myEntity);
        }
      }
    }
    return myEntities;
  }

  private static void setEntitySize(Entity myEntity, double scaleFactor) {
    double scalingFactor = myEntity.getBoundsInLocal().getWidth()/scaleFactor;
    myEntity.setFitWidth(myEntity.getBoundsInLocal().getWidth()/scalingFactor-0.01);
    myEntity.setFitHeight(myEntity.getBoundsInLocal().getHeight()/scalingFactor-0.01);
  }

  private static void addNewEntityToEntitiesList(EntityList myEntities, String symbol, Entity myEntity) {
    if (symbol.equals(MAIN_ENTITY_SYMBOL)) {
      myEntities.setMainEntity(myEntity);
    }
    myEntities.addEntity(myEntity);
  }

  private static void setEntityCoordinates(int levelHeight, int j, int i, Entity myEntity,
      double scaleFactor) {
    myEntity.setX(getRelativeX(i, scaleFactor));
    double imageHeight = myEntity.getBoundsInLocal().getHeight();
    myEntity.setY(getRelativeY(j, imageHeight, scaleFactor));
  }

  private static double getRelativeY(int j, double imageHeight, double scaleFactor) {
    return (j*scaleFactor) - imageHeight;
  }

  private static double getRelativeX(int i, double scaleFactor) {
    return i*scaleFactor;
  }

}
