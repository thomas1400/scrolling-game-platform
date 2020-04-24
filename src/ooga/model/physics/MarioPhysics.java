package ooga.model.physics;

public class MarioPhysics extends Physics {

  private static final double myDt = 1/70.0;
  private static final double mySizeScale = 50.0;

  private static final double MY_JUMP_HEIGHT = 3.75 * mySizeScale;
  private static final double MY_TIME_TO_APEX = 4.0;
  private static final double MY_GRAVITY = (2*MY_JUMP_HEIGHT)/Math.pow(MY_TIME_TO_APEX,2);
  private static final double MY_INITIAL_JUMP_VELOCITY = -1 * Math.sqrt(2*MY_GRAVITY*MY_JUMP_HEIGHT);

  private static final double MY_MAX_VERT_VELOCITY = -1 * MY_INITIAL_JUMP_VELOCITY;
  private static final double MY_MAX_HORIZ_VELOCITY = 0.5 * mySizeScale;

  private static final double MY_RUN_ACCELERATION = 3 * mySizeScale;
  private static final double MY_FRICTION_DAMPING = 0.6;
  private static final double MY_REACTIVITY_PERCENT = 3.5;
  private static final double MY_TINY_DISTANCE = MY_MAX_VERT_VELOCITY*myDt;

  public MarioPhysics() {
    super();

    initializeConstants();
  }

  private void initializeConstants() {
    super.dt = myDt;
    super.sizeScale = mySizeScale;

    super.JUMP_HEIGHT = MY_JUMP_HEIGHT;
    super.TIME_TO_APEX = MY_TIME_TO_APEX;
    super.GRAVITY = MY_GRAVITY;
    super.INITIAL_JUMP_VELOCITY = MY_INITIAL_JUMP_VELOCITY;

    super.MAX_VERT_VELOCITY = MY_MAX_VERT_VELOCITY;
    super.MAX_HORIZ_VELOCITY = MY_MAX_HORIZ_VELOCITY;

    super.RUN_ACCELERATION = MY_RUN_ACCELERATION;
    super.FRICTION_DAMPING = MY_FRICTION_DAMPING;
    super.REACTIVITY_PERCENT = MY_REACTIVITY_PERCENT;
    super.TINY_DISTANCE = MY_TINY_DISTANCE;
  }
}
