package ooga.model.data;

import java.util.Map;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

/**
 * Simply a package for all relevant Level data. Generated within the LevelBuilder class and
 * passed to the LevelLoop to retrieve all relevant information.
 *
 * Could theoretically be used by a "LevelSaver" class to turn back into a file
 */
public class Level {

  private int myLevelIndex;
  private String myName;
  private Entity myMainEntity;
  private EntityList myEntities;

  public Level(int index, Map<String,String> headerInfo, EntityList entities){
    myLevelIndex = index;
    myName = headerInfo.get("name");
    myMainEntity = entities.getMainEntity();
    myEntities = entities;
  }

  public int getLevelIndex() {
    return myLevelIndex;
  }

  public String getName(){
    return myName;
  }

  public Entity getMainEntity() {
    return myMainEntity;
  }

  public EntityList getEntities(){
    return myEntities;
  }

}
