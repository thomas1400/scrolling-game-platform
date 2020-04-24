package ooga.model.ability;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import ooga.exceptions.ExceptionFeedback;
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

  private void adjustForFriction() {
    if (myAcceleration[Y] == 0) {
      myVelocity[X] = myVelocity[X] / (1 + myConstants.get(FRICTION) * myConstants.get(DT));
    }
  }

  //USED FOR REFLECTION
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

  private double getLimitedVelocity(double velocity, double maxVelocity) {
    if (velocity > 0){
      return Math.min(velocity, maxVelocity);
    } else {
      return Math.max(velocity, -1 * maxVelocity);
    }
  }

  public double getYVelocity(){
    return myVelocity[Y];
  }

  public double getXVelocity(){
    return myVelocity[X];
  }

  public void supportX() {
    stopDirectionalMotion(X);
  }

  public void supportY() {
    stopDirectionalMotion(Y);
  }

  private void stopDirectionalMotion(int axis){
    myAcceleration[axis] = 0;
    myVelocity[axis] = 0;
  }

  public void jump() {
    myInputAdjust[Y] -= myConstants.get(TINY_DISTANCE);
    myVelocity[Y] += myConstants.get(INIT_JUMP_VELOCITY);
  }

  public void moveLeft() {
    if (myVelocity[X] < 0) {
      myVelocity[X] = myVelocity[X] - myConstants.get(RUN_ACCELERATION)* myConstants.get(DT);
    } else {
      myVelocity[X] = myVelocity[X] - (myConstants.get(RUN_ACCELERATION) * myConstants.get(REACTIVITY_PERCENT)) * myConstants.get(DT);
    }
  }

  public void moveRight() {
    if (myVelocity[X] > 0) {
      myVelocity[X] = myVelocity[X] + myConstants.get(RUN_ACCELERATION) * myConstants.get(DT);
    } else {
      myVelocity[X] = myVelocity[X] + (myConstants.get(RUN_ACCELERATION) * myConstants.get(REACTIVITY_PERCENT)) * myConstants.get(DT);
    }
  }

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