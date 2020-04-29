package ooga.model.ability;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import ooga.exceptions.ExceptionFeedback;
import ooga.model.ability.physicshelpers.PhysicsState;
import ooga.model.entity.Entity;

public class Physics extends Ability {

  private static final String DT = "DT";
  private static final String MAX_NUM_JUMPS = "MAX_NUM_JUMPS";
  private static final String REACTIVITY_PERCENT = "REACTIVITY_PERCENT";
  private static final String RUN_ACCELERATION = "RUN_ACCELERATION";
  private static final String INIT_JUMP_VELOCITY = "INIT_JUMP_VELOCITY";
  private static final String TINY_DISTANCE = "TINY_DISTANCE";

  PhysicsState myState;
  private Map<String, Double> myConstants;
  private int jumpsSinceLand = 0;

  private static final int X = 0;
  private static final int Y = 1;

  /**
   * Main class associated with controlling entities. Methods are able to be called via standard
   * method calls, or via reflection. Used to update the visual positions of entities in a
   * physical world like manner.
   *
   * @param gameAndPhysicsType used to determine the physics constants
   */
  public Physics(String gameAndPhysicsType) {
    myState = new PhysicsState(gameAndPhysicsType);
    myConstants = myState.getConstants();
  }

  /**
   * Used to update the visual position of an entity based on change in time (and physics
   * constants) as well as the User's input since the last game tick.
   *
   * @param myEntity the entity to have it's physics based position updated
   */
  public void update(Entity myEntity) {
    myState.update(myEntity);
  }

  /**
   * Preforms the appropriate adjustments to the entity when a bounceX command is activated with
   * reflection from the Entity class
   * I.e. makes the entity bounce horizontally or vertically
   */
  private void bounce(){
    if(Math.abs(getYVelocity()) >= Math.abs(getXVelocity())){
      bounceY();
    } else if (Math.abs(getYVelocity()) < Math.abs(getXVelocity())){
      bounceX();
    }
  }

  private void bounceX(){
    myState.setVelocity(X, -1 * myState.getVelocity(X));
  }

  private void bounceY(){
    myState.setVelocity(Y, -1 * myState.getVelocity(Y));
  }

  /**
   * @return the current YVelocity of the entity
   */
  public double getYVelocity(){
    return myState.getVelocity(Y);
  }

  /**
   * @return the current XVelocity of the entity
   */
  public double getXVelocity(){
    return myState.getVelocity(X);
  }

  /**
   * Preforms the appropriate adjustments to the entity when a supportX command is activated
   * I.e. makes the not run through the sides of solid entities
   */
  public void supportX() {
    stopDirectionalMotion(X);
  }

  /**
   * Preforms the appropriate adjustments to the entity when a supportY command is activated.
   * I.e. makes the entity not fall through the entity
   */
  public void supportY() {
    stopDirectionalMotion(Y);
    jumpsSinceLand = 0;
  }

  private void stopDirectionalMotion(int axis){
    myState.setAcceleration(axis, 0);
    myState.setVelocity(axis, 0);
  }

  /**
   * Preforms the appropriate adjustments to the entity when a jump command is activated
   * I.e. makes the entity jump
   */
  public void jump() {
    if (canJumpAgain()) {
      myState.setInputAdjust(Y,
          myState.getInputAdjust(Y) - myConstants.get(TINY_DISTANCE));
      myState.setVelocity(Y,
          myState.getVelocity(Y) + myConstants.get(INIT_JUMP_VELOCITY));
      jumpsSinceLand += 1;
    }
  }

  /**
   * Preforms the appropriate adjustments to the entity when a jumpUp command is activated with
   * use of Reflection
   * I.e. makes the entity jump only if already falling
   */
  public void jumpUp(){
    if (myState.getVelocity(Y) > 0){
      jump();
    }
  }

  private boolean canJumpAgain() {
    return jumpsSinceLand < myConstants.get(MAX_NUM_JUMPS);
  }

  /**
   * Preforms the appropriate adjustments to the entity when a jumpHigh command is activated with
   * use of Reflection
   * I.e. makes the entity jump twice, also only if already falling
   */
  public void jumpHigh(){
    if(myState.getVelocity(Y) > 0){
      jump();
      jump();
    }

  }
  public void moveLeft() {
    if (myState.getVelocity(X) < 0) {
      myState.setVelocity(X,
          myState.getVelocity(X) - myConstants.get(RUN_ACCELERATION)  * myConstants.get(DT));
    } else {
      myState.setVelocity(X,
          myState.getVelocity(X) - (myConstants.get(RUN_ACCELERATION) * myConstants.get(REACTIVITY_PERCENT)) * myConstants.get(DT));
    }
  }

  /**
   * Preforms the appropriate adjustments to the entity when a moveRight command is activated
   * I.e. makes the entity move to the right
   */
  public void moveRight() {
    if (myState.getVelocity(X) > 0) {
      myState.setVelocity(X,
          myState.getVelocity(X) + myConstants.get(RUN_ACCELERATION) * myConstants.get(DT));
    } else {
      myState.setVelocity(X,
          myState.getVelocity(X) + (myConstants.get(RUN_ACCELERATION) * myConstants.get(REACTIVITY_PERCENT)) * myConstants.get(DT));
    }
  }

  /**
   * Calls associated method in the physics class
   * @param methodName string associated with the method to call, derived from resource file
   */
  public void reflectMethod(String methodName){
    try {
      Method method = Physics.class.getDeclaredMethod(methodName);
      method.invoke(Physics.this);
    } catch (NoSuchMethodException e) {
      ExceptionFeedback.throwHandledException(e, "Physics doesn't have the "+methodName+" method, check collision files");
    } catch (IllegalAccessException e) {
      ExceptionFeedback.throwHandledException(e, "Can't call "+methodName+" in Physics class");
    } catch (InvocationTargetException e) {
      ExceptionFeedback.throwHandledException(e, "Failed to invoke "+methodName+" in Physics class");
    }
  }

}