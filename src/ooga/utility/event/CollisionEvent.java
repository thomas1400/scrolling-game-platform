package ooga.utility.event;

import ooga.model.entity.Entity;

public class CollisionEvent{

  private String typeOfCollision;
  private String whoCollidedWith;


  public CollisionEvent(String whereCollisionOccurred, String whoCollisionOccurredWith){
    typeOfCollision = whereCollisionOccurred;
    whoCollidedWith = whoCollisionOccurredWith;
  }

  public String getTypeOfCollision(){
    return typeOfCollision;
  }

  public String getWhoCollidedWith(){
    return whoCollidedWith;
  }
}
