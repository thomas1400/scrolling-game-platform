
package ooga.model.physics;

import ooga.model.entity.Entity;

public class Physics {

  private static final double dt = 1/60.0;
  private static final double sizeScale = 50.0;

  private static final double JUMP_HEIGHT = 3.75 * sizeScale;
  private static final double TIME_TO_APEX = 4.0;
  private static final double GRAVITY = (2*JUMP_HEIGHT)/Math.pow(TIME_TO_APEX,2);
  private static final double INITIAL_JUMP_VELOCITY = -1 * Math.sqrt(2*GRAVITY*JUMP_HEIGHT);

  private static final double MAX_VERT_VELOCITY = -1 * INITIAL_JUMP_VELOCITY;
  private static final double MAX_HORIZ_VELOCITY = 2.5 * sizeScale;
  private static final double RUN_ACCELERATION = 2.5 * sizeScale;
  private static final double FRICTION_DAMPING = 2.0;

  private static final int X = 0;
  private static final int Y = 1;
  private static final double REACTIVITY_PERCENT = 3.5;
  private static final double TINY_BOUNCE_DISTANCE = 0.1;

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
    limitVelocities();
    adjustForFriction();

    //Position Updates
    myPosition[X] += myVelocity[X]*dt;
    myPosition[Y] += myVelocity[Y]*dt;
    //myPosition[Y] = tempCheckLandJump();

    //Update Image Position
    myEntity.setX(myPosition[X]);
    myEntity.setY(myPosition[Y]);

    //Re-take initial Accel[Y] Value
    myAcceleration[Y] = GRAVITY;
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

  private double tempCheckLandJump() {
    double groundHeight = 300;
    if (myPosition[Y] < groundHeight) {
      myPosition[Y] += myVelocity[Y]*dt;
    } else {
      stopVerticalMotion();
      myPosition[Y] -= 0.1;
    }
    return myPosition[Y];
  }

  private double getLimitedVelocity(double velocity, double maxVelocity) {
    if (velocity > 0){
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
    stopDirectionalMotion(X);
  }

  public void stopVerticalMotion() {
    stopDirectionalMotion(Y);
  }

  private void stopDirectionalMotion(int axis){
    myAcceleration[axis] = 0;
    //myPosition[axis] -= TINY_BOUNCE_DISTANCE * getDirection(myVelocity[axis]);
    myVelocity[axis] = 0;
  }

  private double getDirection(double velocity) {
    return velocity/Math.abs(velocity);
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