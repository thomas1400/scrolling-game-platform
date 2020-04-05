package ooga.model.data;

import ooga.model.entity.EntityList;

public class Level {

  private String myName;
  private User myUser;
  private EntityList myEntities;

  public Level(){

  }

  public Level loadLevel(String filepath) {
    return null;
  }

  public void saveLevel() { }

  public String getName(){
    return myName;
  }

}
