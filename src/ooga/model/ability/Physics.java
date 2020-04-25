package ooga.model.ability;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import ooga.exceptions.ExceptionFeedback;
import ooga.model.ability.physicshelpers.PhysicsInitializer;
import ooga.model.entity.Entity;

public class Physics extends Ability {

  private static final String DT = "DT";
  private static final String GRAVITY = "GRAVITY";
  private static final String MAX_VERT_VELOCITY = "MAX_VERT_VELOCITY";
  private static final String MAX_HORIZ_VELOCITY = "MAX_HORIZ_VELOCITY";
  private static final String RUN_ACCELERATION = "RUN_ACCELERATION";
  private static final String FRICTION = "FRICTION";
  private static final String REACTIVITY_PERCENT = "REACTIVITY_PERCENT";
  private static final String INIT_X_VEL = "INIT_X_VEL";
  private static final String INIT_Y_VEL = "INIT_Y_VEL";
  private static final String INIT_X_ACCEL = "INIT_X_ACCEL";
  private static final String INIT_Y_ACCEL = "INIT_Y_ACCEL";
  private static final String INIT_JUMP_VELOCITY = "INIT_JUMP_VELOCITY";
  private static final String TINY_DISTANCE = "TINY_DISTANCE";

  private Map<String, Double> myConstants;

  private static final int X = 0;
  private static final int Y = 1;

  private double[] myVelocity;
  private double[] myAcceleration;
  private double[] myPosition = new double[]{0, 0};
  private double[] myInputAdjust = new double[] {0,0};

  public Physics(String gameAndPhysicsType) {
    initializeConstants(gameAndPhysicsType);
  }

  private void initializeConstants(String gameType) {
    myConstants = PhysicsInitializer.getConstantsMap(gameType);
    setInitialVelocityAndAcceleration();
  }

  private void setInitialVelocityAndAcceleration() {
    myConstants.putIfAbsent(INIT_X_VEL, 0.0);
    myConstants.putIfAbsent(INIT_Y_VEL, 0.0);
    myConstants.putIfAbsent(INIT_X_ACCEL, 0.0);
    myConstants.putIfAbsent(INIT_Y_ACCEL, 0.0);

    myVelocity = new double[]{myConstants.get(INIT_X_VEL), myConstants.get(INIT_Y_VEL)};
    myAcceleration = new double[]{myConstants.get(INIT_X_ACCEL), myConstants.get(INIT_Y_ACCEL)};
  }

  /**
   * Used to update the visual position of an entity based on change in time (and physics
   * constants) as well as the User's input since the last game tick.
   *
   * @param myEntity the entity to have it's physics based position updated
   */
  public void update(Entity myEntity) {
    setPositionFromImageView(myEntity);
    adjustPositionFromUserInput();
    updateToNextVelocity();
    updateToNextPosition(myEntity);
    resetGravity();
  }

  private void adjustPositionFromUserInput() {
    myPosition[X] += getInputAdjust(X);
    myPosition[Y] += getInputAdjust(Y);
  }

  private void setPositionFromImageView(Entity myEntity) {
    myPosition[X] = myEntity.getX();
    myPosition[Y] = myEntity.getY();
  }

  private void updateToNextVelocity() {
    myVelocity[X] += myAcceleration[X]*myConstants.get(DT);
    myVelocity[Y] += myAcceleration[Y]*myConstants.get(DT);
    limitVelocities();
    adjustForFriction();
  }

  private void updateToNextPosition(Entity myEntity) {
    myPosition[X] += myVelocity[X]*myConstants.get(DT);
    myPosition[Y] += myVelocity[Y]*myConstants.get(DT);

    myEntity.setX(myPosition[X]);
    myEntity.setY(myPosition[Y]);
  }

  private void resetGravity() {
    myAcceleration[Y] = myConstants.get(GRAVITY);
  }

  private double getInputAdjust(int axis) {
    double adjustThisTick = myInputAdjust[axis];
    myInputAdjust[axis] = 0;
    return adjustThisTick;
  }

  private void limitVelocities() {
    myVelocity[X] = getLimitedVelocity(myVelocity[X], myConstants.get(MAX_HORIZ_VELOCITY));
    myVelocity[Y] = getLimitedVelocity(myVelocity[Y], myConstants.get(MAX_VERT_VELOCITY));
  }

  private double getLimitedVelocity(double velocity, double maxVelocity) {
    if (velocity > 0){
      return Math.min(velocity, maxVelocity);
    } else {
      return Math.max(velocity, -1 * maxVelocity);
    }
  }

  private void adjustForFriction() {
    if (myAcceleration[Y] == 0) {
      myVelocity[X] = myVelocity[X] / (1 + myConstants.get(FRICTION) * myConstants.get(DT));
    }
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
    myVelocity[X]*=-1;
  }

  private void bounceY(){
    myVelocity[Y]*=-1;
  }

  /**
   * @return the current YVelocity of the entity
   */
  public double getYVelocity(){
    return myVelocity[Y];
  }

  /**
   * @return the current XVelocity of the entity
   */
  public double getXVelocity(){
    return myVelocity[X];
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
  }

  private void stopDirectionalMotion(int axis){
    myAcceleration[axis] = 0;
    myVelocity[axis] = 0;
  }

  /**
   * Preforms the appropriate adjustments to the entity when a jump command is activated
   * I.e. makes the entity jump
   */
  public void jump() {
    myInputAdjust[Y] -= myConstants.get(TINY_DISTANCE);
    myVelocity[Y] += myConstants.get(INIT_JUMP_VELOCITY);
  }

  /**
   * Preforms the appropriate adjustments to the entity when a jumpUp command is activated with
   * use of Reflection
   * I.e. makes the entity jump only if already falling
   */
  public void jumpUp(){
    if(myVelocity[Y]>0){
      System.out.println("hi");
      jump();
    }
  }

  /**
   * Preforms the appropriate adjustments to the entity when a moveLeft command is activated
   * I.e. makes the entity move to the left
   */
  public void moveLeft() {
    if (myVelocity[X] < 0) {
      myVelocity[X] = myVelocity[X] - myConstants.get(RUN_ACCELERATION)  * myConstants.get(DT);
    } else {
      myVelocity[X] = myVelocity[X] - (myConstants.get(RUN_ACCELERATION) * myConstants.get(REACTIVITY_PERCENT)) * myConstants.get(DT);
    }
  }

  /**
   * Preforms the appropriate adjustments to the entity when a moveRight command is activated
   * I.e. makes the entity move to the right
   */
  public void moveRight() {
    if (myVelocity[X] > 0) {
      myVelocity[X] = myVelocity[X] + myConstants.get(RUN_ACCELERATION) * myConstants.get(DT);
    } else {
      myVelocity[X] = myVelocity[X] + (myConstants.get(RUN_ACCELERATION) * myConstants.get(REACTIVITY_PERCENT)) * myConstants.get(DT);
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