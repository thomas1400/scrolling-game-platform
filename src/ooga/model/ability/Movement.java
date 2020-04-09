package ooga.model.ability;

import ooga.model.physics.Physics;

public class Movement extends Ability {

<<<<<<< HEAD
  private static final String PHYSICS_PACKAGE = "ooga.model.physics.";
  private static final String PHYSICS = "Physics";
  private Physics phys;

  public Movement(String physicsType){
    //reflection to set the physics Type
    //my idea here is that physics will have enums? maybe?
    phys = (Physics) super.reflect(PHYSICS_PACKAGE, PHYSICS, physicsType);
  }

  //todo this one is only used by the user interactable interfaces
  public void jump(){
    //todo
  }

  public void right(){
    //todo
  }

  public void left(){
    //todo
  }
  //todo combine the above three methods into one that handles key input?
}
