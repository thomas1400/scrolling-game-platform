package ooga.utility.event;

import ooga.model.ability.attacktypes.Attack;
import ooga.model.entity.Entity;

public class CollisionEvent{

  private String typeOfCollision, attackType;

  public CollisionEvent(String whereCollisionOccurred, String attack){
    typeOfCollision = whereCollisionOccurred;
    attackType = attack;
  }

  public String getCollisionLocation(){
    return typeOfCollision;
  }

  public String getAttackType(){
    return attackType;
  }
}
