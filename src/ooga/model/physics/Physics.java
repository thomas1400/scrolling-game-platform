package ooga.model.physics;

import ooga.model.entity.Entity;

public class Physics {

  private static final double dt = 1/60.0;
  private static final double sizeScale = 35.0;

  private static final double GRAVITY = 6.0 * 20;
  private static final double INITIAL_JUMP_VELOCITY = -7.0 * sizeScale;
  private static final double MAX_VERT_VELOCITY = 7.0 * sizeScale;
  private static final double MAX_HORIZ_VELOCITY = 2 * sizeScale;
  private static final double RUN_ACCELERATION = 2 * sizeScale;

  private static final int X = 0;
  private static final int Y = 1;

  private double[] myPosition;
  private double[] myVelocity;
  private double[] myAcceleration;

  public Physics() {
    myPosition = new double[]{0, 0};
    myVelocity = new double[]{0, 0};
    myAcceleration = new double[]{0, 0};
  }

  public void update(Entity myEntity) {
    //Get on screen position
    myPosition[X] = myEntity.getX();
    myPosition[Y] = myEntity.getY();

    //Velocity Updates
    myVelocity[X] += myAcceleration[X]*dt;
    myVelocity[Y] += myAcceleration[Y]*dt;

    //Velocity Checks
    myVelocity[X] = getLimitedVelocity(myVelocity[X], MAX_HORIZ_VELOCITY);
    myVelocity[Y] = getLimitedVelocity(myVelocity[Y], MAX_VERT_VELOCITY);

    //Position Updates
    myPosition[X] += myVelocity[X]*dt;
    //myPosition[Y] += myVelocity[Y]*dt;
    myPosition[Y] = tempCheckLandJump();

    //Update Image Position
    myEntity.setX(myPosition[X]);
    myEntity.setY(myPosition[Y]);
  }

  private double tempCheckLandJump() {
    if (myPosition[Y] < 307) {
      myPosition[Y] += myVelocity[Y]*dt;
    } else {
      stopVerticalMotion();
      myPosition[Y] -= 1;
    }
    return myPosition[Y];
  }

  private double getLimitedVelocity(double velocity, double maxVelocity) {
    if (velocity >= 0){
      return Math.min(velocity, maxVelocity);
    } else {
      return Math.max(velocity, -1 * maxVelocity);
    }
  }

  public void jump() {
    myAcceleration[Y] = GRAVITY;
    myVelocity[Y] += INITIAL_JUMP_VELOCITY;
  }
  public void stopHorizMotion() {
    myAcceleration[X] = 0;
    myVelocity[X] = 0;
  }
  public void stopVerticalMotion() {
    myAcceleration[Y] = 0;
    myVelocity[Y] = 0;
  }
  public void moveLeft() {
    myVelocity[X] = myVelocity[X] - RUN_ACCELERATION * dt;
  }
  public void moveRight() {
    myVelocity[X] = myVelocity[X] + RUN_ACCELERATION * dt;
  }
  public void resumeFall() {
    myAcceleration[Y] = GRAVITY;
  }
  public void endFall() { stopVerticalMotion();}

}
