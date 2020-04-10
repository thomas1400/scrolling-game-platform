package ooga.model.physics;

import ooga.model.entity.Entity;

public class Physics {

  private static final double dt = 1/60.0;
  private static final double GRAVITY = 6.0*30;
  private static final double INITIAL_JUMP_VELOCITY = -7.0*30;
  private static final double RUN_VELOCITY = 2*30;
  private static final double DAMPING = 1.0;

  private static final int X = 0;
  private static final int Y = 1;

  private Entity myEntity;

  private double[] myPosition;
  private double[] myVelocity;
  private double[] myAcceleration;

  public Physics(Entity entity) {
    myEntity = entity;
    myPosition = new double[]{entity.getX(), entity.getY()};
    myVelocity = new double[]{0, 0};
    myAcceleration = new double[]{0, GRAVITY};
  }

  public void update() {
    myPosition[X] = myEntity.getX();
    myPosition[Y] = myEntity.getY();

    //Vertical Updates
    myVelocity[Y] += myAcceleration[Y]*dt;
    myPosition[Y] += myVelocity[Y]*dt;

    //Horizontal Updates
    myVelocity[X] += myAcceleration[X]*dt;
    myPosition[X] += myVelocity[X]*dt;

    //Update Image Position
    myEntity.setX(myPosition[X]);
    myEntity.setY(myPosition[Y]);
  }

  public void jump() {
    System.out.println("jump");
    myVelocity[Y] += INITIAL_JUMP_VELOCITY;
  }
  public void moveLeft() {
    myVelocity[X] = -1*RUN_VELOCITY;
  }
  public void moveRight() {
    myVelocity[X] = RUN_VELOCITY;
  }
  public void stopForwardMotion() {myVelocity[X] = 0;}
  public void reverseDirection() { myVelocity[X] *= -1; }
  public void beginFall() {
    myAcceleration[Y] = GRAVITY;
  }
  public void endFall() {} {
    //myAcceleration[Y] = 0.0;
  }

}
