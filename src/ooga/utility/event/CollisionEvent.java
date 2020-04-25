package ooga.utility.event;

import ooga.model.behavior.Collidable;

public class CollisionEvent{

  private String typeOfCollision, attackType;
  private Collidable other;

  public CollisionEvent(String whereCollisionOccurred, String attack, Collidable otherEntity){
    typeOfCollision = whereCollisionOccurred;
    attackType = attack;
    other = otherEntity;
  }

  public String getCollisionLocation(){
    return typeOfCollision;
  }

  public String getAttackType(){
    return attackType;
  }

  public Collidable getOther(){
    return other;
  }
}
