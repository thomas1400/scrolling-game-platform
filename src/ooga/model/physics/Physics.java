
package ooga.model.physics;

import ooga.model.entity.Entity;

public abstract class Physics {

  protected double dt;
  protected double sizeScale;

  protected double JUMP_HEIGHT;
  protected double TIME_TO_APEX;
  protected double GRAVITY;
  protected double INITIAL_JUMP_VELOCITY;

  protected double MAX_VERT_VELOCITY;
  protected double MAX_HORIZ_VELOCITY;
  protected double RUN_ACCELERATION;
  protected double FRICTION_DAMPING;
  protected double REACTIVITY_PERCENT;
  protected double TINY_DISTANCE;

  private static final int X = 0;
  private static final int Y = 1;

  private double[] myPosition;
  private double[] myVelocity;
  private double[] myAcceleration;
  private double[] myInputAdjust;

  public Physics() {
    myPosition = new double[]{0, 0};
    myVelocity = new double[]{0, 0};
    myAcceleration = new double[]{0, 0};
    myInputAdjust = new double[] {0,0};
  }

  public void update(Entity myEntity) {
    //Update to use on screen position
    myPosition[X] = myEntity.getX();
    myPosition[Y] = myEntity.getY();

    //Adjust based on inputs
    myPosition[X] += getInputAdjust(X);
    myPosition[Y] += getInputAdjust(Y);

    //Velocity Updates
    myVelocity[X] += myAcceleration[X]*dt;
    myVelocity[Y] += myAcceleration[Y]*dt;
    limitVelocities();
    adjustForFriction();

    //Position Updates
    myPosition[X] += myVelocity[X]*dt;
    myPosition[Y] += myVelocity[Y]*dt;
    //myPosition[Y] = tempCheckLandJump();

    //Update Image Position
    myEntity.setX(myPosition[X]);
    myEntity.setY(myPosition[Y]);

    //Reset Gravity
    myAcceleration[Y] = GRAVITY;
  }

  private double getInputAdjust(int axis) {
    double adjustThisTick = myInputAdjust[axis];
    myInputAdjust[axis] = 0;
    return adjustThisTick;
  }

  private void limitVelocities() {
    myVelocity[X] = getLimitedVelocity(myVelocity[X], MAX_HORIZ_VELOCITY);
    myVelocity[Y] = getLimitedVelocity(myVelocity[Y], MAX_VERT_VELOCITY);
  }

  private void adjustForFriction() {
    //Horiz Velocity Damping b/c of Friction if not in air
    if (myAcceleration[Y] == 0) {
      myVelocity[X] = myVelocity[X] / (1 + FRICTION_DAMPING * dt);
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
    myInputAdjust[X] -= TINY_DISTANCE * getDirection(myVelocity[X]);
    stopDirectionalMotion(X);
  }

  public void stopVerticalMotion() {
    stopDirectionalMotion(Y);
  }

  private void stopDirectionalMotion(int axis){
    myAcceleration[axis] = 0;
    myVelocity[axis] = 0;
  }

  private double getDirection(double velocity) {
    return velocity/Math.abs(velocity);
  }

  public void jump() {
    myInputAdjust[Y] -= TINY_DISTANCE;
    myVelocity[Y] += INITIAL_JUMP_VELOCITY;
  }

  public void moveLeft() {
    if (myVelocity[X] < 0) {
      myVelocity[X] = myVelocity[X] - RUN_ACCELERATION * dt;
    } else {
      myVelocity[X] = myVelocity[X] - (RUN_ACCELERATION * REACTIVITY_PERCENT) * dt;
    }
  }
  public void moveRight() {
    if (myVelocity[X] > 0) {
      myVelocity[X] = myVelocity[X] + RUN_ACCELERATION * dt;
    } else {
      myVelocity[X] = myVelocity[X] + (RUN_ACCELERATION * REACTIVITY_PERCENT) * dt;
    }
  }

}