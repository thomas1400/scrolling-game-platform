package ooga.controller.data;

import java.util.Map;
import ooga.model.entity.Entity;
import ooga.model.entity.EntityList;

public class CompleteLevel {

  public static final String PHYSICS_IDENTIFIER = "physics";
  public static final String SCROLL_TYPE_IDENTIFIER = "scrollType";
  private String myPhysicsType;
  private String myScrollType;
  private Entity myMainEntity;
  private EntityList myEntities;

  public CompleteLevel(BasicLevel basicLevel, EntityList levelEntities) {

    Map<String, String> headerInfo = basicLevel.getHeaderInfo();
    myPhysicsType = headerInfo.get(PHYSICS_IDENTIFIER);
    myScrollType = headerInfo.get(SCROLL_TYPE_IDENTIFIER);

    myMainEntity = levelEntities.getMainEntity();
    myEntities = levelEntities;

  }

  public Entity getMainEntity() {
    return myMainEntity;
  }

  public EntityList getEntities(){
    return myEntities;
  }

  public String getPhysicsType() {
    return myPhysicsType;
  }

  public String getScrollType() {
    return myScrollType;
  }
}
