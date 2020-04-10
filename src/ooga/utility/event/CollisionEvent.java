package ooga.utility.event;

import ooga.model.ability.attacktypes.Attack;
import ooga.model.entity.Entity;

public class CollisionEvent{

  private String typeOfCollision;
  private Attack attackType;


  public CollisionEvent(String whereCollisionOccurred, Attack attack){
    typeOfCollision = whereCollisionOccurred;
    attackType = attack;
  }

  public String getTypeOfCollision(){
    return typeOfCollision;
  }

  public Attack getAttackType(){
    return attackType;
  }
}
