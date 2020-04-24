package ooga.controller.levels;

import java.util.Map;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

public class CompleteLevel {

  public static final String SCROLL_TYPE_IDENTIFIER = "scrollType";
  public static final String DEATHS_ALLOWED_IDENTIFIER = "deathsAllowed";
  public static final String LIFE_GAIN_IDENTIFIER = "lifeGain";

  private String myGameType;
  private String myScrollType;
  private int myDeathsAllowed;
  private boolean lifeGainAllowed;
  private Entity myMainEntity;
  private EntityList myEntities;

  /**
   * Simply a package for all relevant Level data. More complex and specific data than is
   * held in a BasicLevel. Filled with Entities by the LevelBuilder and passed to the LevelLoop
   * for usage during game play.
   *
   * Could theoretically be used by a "LevelSaver" class to turn back into a file
   *
   * @param basicLevel the BasicLevel containing the relevant header information
   * @param levelEntities an EntityList used to control all entities
   */
  public CompleteLevel(BasicLevel basicLevel, EntityList levelEntities) {
    myGameType = basicLevel.getGameType();

    Map<String, String> headerInfo = basicLevel.getHeaderInfo();
    myScrollType = headerInfo.get(SCROLL_TYPE_IDENTIFIER);
    myDeathsAllowed = Integer.parseInt(headerInfo.get(DEATHS_ALLOWED_IDENTIFIER));
    lifeGainAllowed = Boolean.parseBoolean(headerInfo.get(LIFE_GAIN_IDENTIFIER));

    myMainEntity = levelEntities.getMainEntity();
    myEntities = levelEntities;

  }

  /**
   * @return the Main Entity from the Entity List for the level
   */
  public Entity getMainEntity() {
    return myMainEntity;
  }

  /**
   * @return the entire EntityList for the level
   */
  public EntityList getEntities(){
    return myEntities;
  }

  /**
   * @return the Scroll Type of the level for assignment of an initial camera manager
   */
  public String getScrollType() {
    return myScrollType;
  }

  /**
   * @return the game type for resource accessing in future classes
   */
  public String getGameType() {
    return myGameType;
  }

  /**
   * @return the number of deaths that a player can have in a single level before the level ends
   * and the user is sent back to the level select screen.
   */
  public int getDeathsAllowed() { return myDeathsAllowed; }

  /**
   * @return whether or not collecting life mushrooms or other items that affect lives will
   * effect your "per level" life number
   */
  public boolean getLifeGainAllowed() { return lifeGainAllowed;  }
}
