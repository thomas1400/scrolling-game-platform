package ooga.model.ability;

import ooga.model.entity.Entity;
import ooga.model.physics.Physics;

public class Movement extends Ability {

  private static final String PHYSICS_PACKAGE = "ooga.model.physics.";
  private static final String PHYSICS = "Physics";
  private Physics phys;

  public Movement(String physicsType){
    //reflection to set the physics Type
    //my idea here is that physics will have enums? maybe?
    phys = (Physics) super.reflect(PHYSICS_PACKAGE, PHYSICS);//fixme change the empty string to physicsType
  }

  //todo this one is only used by the user interactable interfaces
  public void jump(){
    //todo
    phys.jump();
  }

  public void right(){
    //todo
    phys.moveRight();
  }

  public void left(){
    //todo
    phys.moveLeft();
  }

  public void stand(){
    phys.stopVerticalMotion();
    //fixme change so it doesn't stop in air if it hits a brick
  }

  public void bounceY(){
    phys.stopVerticalMotion();
  }

  public void bounceX(){
    //System.out.println("bounce x");
    phys.stopHorizMotion();
  }

  public void update(Entity entity){
    phys.update(entity);
  }
  //todo combine the above three methods into one that handles key input?
}
