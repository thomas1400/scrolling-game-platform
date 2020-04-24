package ooga.controller.levels;

import java.util.Map;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

public class CompleteLevel {

  public static final String PHYSICS_IDENTIFIER = "physics";
  public static final String SCROLL_TYPE_IDENTIFIER = "scrollType";
  public static final String DEATHS_ALLOWED_IDENTIFIER = "deathsAllowed";
  public static final String LIFE_GAIN_IDENTIFIER = "lifeGain";
  private String myPhysicsType;
  private String myGameType;
  private String myScrollType;
  private int myDeathsAllowed;
  private boolean lifeGainAllowed;

  private Entity myMainEntity;
  private EntityList myEntities;

  public CompleteLevel(BasicLevel basicLevel, EntityList levelEntities) {

    myGameType = basicLevel.getGameType();

    Map<String, String> headerInfo = basicLevel.getHeaderInfo();
    myPhysicsType = headerInfo.get(PHYSICS_IDENTIFIER);
    myScrollType = headerInfo.get(SCROLL_TYPE_IDENTIFIER);
    myDeathsAllowed = Integer.parseInt(headerInfo.get(DEATHS_ALLOWED_IDENTIFIER));
    lifeGainAllowed = Boolean.parseBoolean(headerInfo.get(LIFE_GAIN_IDENTIFIER));

    myMainEntity = levelEntities.getMainEntity();
    myEntities = levelEntities;

  }

  public Entity getMainEntity() {
    return myMainEntity;
  }

  public EntityList getEntities(){
    return myEntities;
  }

  public String getScrollType() {
    return myScrollType;
  }

  public String getGameType() {
    return myGameType;
  }

  public int getDeathsAllowed() { return myDeathsAllowed; }

  public boolean getLifeGainAllowed() { return lifeGainAllowed;  }
}
