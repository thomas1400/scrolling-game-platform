/*
package ooga.model.physics;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import ooga.model.ability.Ability;
import ooga.model.entity.Entity;

public class Physics extends Ability {

  private static final String DT = "DT";
  private static final String SCALE_SIZE = "SCALE_SIZE";
  private static final String JUMP_HEIGHT = "JUMP_HEIGHT";
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

  private Map<String, Double> myConstants = new HashMap<>();

  private static final int X = 0;
  private static final int Y = 1;

  private double[] myVelocity;
  private double[] myAcceleration;
  private double[] myPosition = new double[]{0, 0};
  private double[] myInputAdjust = new double[] {0,0};

  public REFACTORED_PHYSICS() {
    String gameType = "mario";
    initializeConstants(gameType);
  }

  private void initializeConstants(String gameType) {
    setDefaultConstants();
    setGameSpecificConstants(gameType);
    scaleAppropriateConstants();
    setDerivedConstants();
    setInitialVelocityAndAcceleration();

    //printDebug(myConstants);
  }

  private void printDebug(Map<String, Double> myConstants) {
    for (String constant : myConstants.keySet()){
      System.out.println(constant + " = " + myConstants.get(constant));
    }
  }

  private void setDefaultConstants() {
    ResourceBundle defaultPhysicsBundle = ResourceBundle.getBundle("gamedata/defaultphysics");
    for (String constant : defaultPhysicsBundle.keySet()){
      double constantValue = Double.parseDouble(defaultPhysicsBundle.getString(constant));
      myConstants.put(constant, constantValue);
    }
  }

  private void setGameSpecificConstants(String gameType) {
    ResourceBundle gamePhysicsBundle = ResourceBundle.getBundle("gamedata/" + gameType +
        "/physics/physicsconstants");
    for (String constant : gamePhysicsBundle.keySet()){
      double constantValue = Double.parseDouble(gamePhysicsBundle.getString(constant));
      myConstants.replace(constant, constantValue);
    }
  }

  private void scaleAppropriateConstants() {
    String[] constantsToScale = new String[]{JUMP_HEIGHT, MAX_HORIZ_VELOCITY, MAX_VERT_VELOCITY,
        RUN_ACCELERATION};
    for (String constant : constantsToScale){
      double scaledValue = myConstants.get(constant) * myConstants.get(SCALE_SIZE);
      myConstants.replace(constant, scaledValue);
    }
  }

  private void setDerivedConstants() {
    if (myConstants.containsKey(INIT_JUMP_VELOCITY)){
      myConstants.replace(INIT_JUMP_VELOCITY, -1* myConstants.get(INIT_JUMP_VELOCITY));
    } else {
      myConstants.put(INIT_JUMP_VELOCITY,
          -1 * Math.sqrt(2 * myConstants.get(GRAVITY) * myConstants.get(JUMP_HEIGHT)));
    }
    myConstants.replace(MAX_VERT_VELOCITY, -1 * myConstants.get(MAX_VERT_VELOCITY));
    myConstants.putIfAbsent(TINY_DISTANCE,
        myConstants.get(MAX_VERT_VELOCITY) * myConstants.get(DT));
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
    //Update to use on screen position
    myPosition[X] = myEntity.getX();
    myPosition[Y] = myEntity.getY();

    //Adjust based on inputs
    myPosition[X] += getInputAdjust(X);
    myPosition[Y] += getInputAdjust(Y);

    //Velocity Updates
    myVelocity[X] += myAcceleration[X]*myConstants.get(DT);
    myVelocity[Y] += myAcceleration[Y]*myConstants.get(DT);
    limitVelocities();
    adjustForFriction();

    //Position Updates
    myPosition[X] += myVelocity[X]*myConstants.get(DT);
    myPosition[Y] += myVelocity[Y]*myConstants.get(DT);
    //myPosition[Y] = tempCheckLandJump();

    //Update Image Position
    myEntity.setX(myPosition[X]);
    myEntity.setY(myPosition[Y]);

    //Reset Gravity
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
    //Horiz Velocity Damping b/c of Friction if not in air
    if (myAcceleration[Y] == 0) {
      myVelocity[X] = myVelocity[X] / (1 + myConstants.get(FRICTION) * myConstants.get(DT));
    }
  }

  public void bounceX(){
    myVelocity[X]*=-1;
  }

  public void bounceY(){
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

  public void stopHorizMotion() {
    stopDirectionalMotion(X);
  }

  public void stopVerticalMotion() {
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

}
 */