package ooga.model.ability;

import ooga.model.entity.Entity;
import ooga.model.physics.Physics;

//todo what is the point of this class
public class Movement extends Ability {

  private static final String PHYSICS_PACKAGE = "ooga.model.physics.";
  //FIXME: this should be leaded in from the entity file
  private static final String PHYSICS = "MarioPhysics";
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

  public void standY(){
    phys.stopVerticalMotion();
  }

  public void standX(){
    bounceX();
    standY();
    System.out.println("standX");
  }

  public double getYVelocity(){
    return phys.getYVelocity();
  }

  public double getXVelocity(){
    return phys.getXVelocity();
  }

  public void bounceY(){
    phys.bounceY();
  }

  public void bounceX(){
    System.out.println("bounccccce x");
    phys.bounceX();
  }

  public void update(Entity entity){
    phys.update(entity);
  }
  //todo combine the above three methods into one that handles key input?
}
