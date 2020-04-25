package ooga.utility.event;

import ooga.model.behavior.Collidible;

public class CollisionEvent{

  private String typeOfCollision, attackType;
  private Collidible other;

  public CollisionEvent(String whereCollisionOccurred, String attack, Collidible otherEntity){
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

  public Collidible getOther(){
    return other;
  }
}
