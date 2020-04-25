package ooga.model.ability.physicshelpers;

import java.util.Map;
import ooga.model.entity.Entity;

public class PhysicsState {

  private static final String DT = "DT";
  private static final String GRAVITY = "GRAVITY";
  private static final String MAX_VERT_VELOCITY = "MAX_VERT_VELOCITY";
  private static final String MAX_HORIZ_VELOCITY = "MAX_HORIZ_VELOCITY";
  private static final String FRICTION = "FRICTION";
  private static final String INIT_X_VEL = "INIT_X_VEL";
  private static final String INIT_Y_VEL = "INIT_Y_VEL";
  private static final String INIT_X_ACCEL = "INIT_X_ACCEL";
  private static final String INIT_Y_ACCEL = "INIT_Y_ACCEL";

  private Map<String, Double> myConstants;

  private static final int X = 0;
  private static final int Y = 1;

  private double[] myVelocity;
  private double[] myAcceleration;
  private double[] myPosition = new double[]{0, 0};
  private double[] myInputAdjust = new double[] {0,0};

  /**
   * Class is meant to hold all relevant information regarding the position and newtonian variables
   * related to the visual position of an Entity. It updates according to the physics of a game
   * described in a resource file.
   * @param gameAndPhysicsType game type needed to initialize constants
   */
  public PhysicsState(String gameAndPhysicsType) {
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
    myPosition[X] += updateRegardingInput(X);
    myPosition[Y] += updateRegardingInput(Y);
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

  private double updateRegardingInput(int axis) {
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
   * @return the constants for an entity's physics
   */
  public Map<String, Double> getConstants() {
    return myConstants;
  }

  /**
   * @param axis the axis about which the velocity is in question
   * @return the entity's current velocity
   */
  public double getVelocity(int axis){
    return myVelocity[axis];
  }

  /**
   * @param axis the axis about which the inputAdjustment is in question
   * @return the entity's current inputAdjustment
   */
  public double getInputAdjust(int axis){
    return myInputAdjust[axis];
  }

  /**
   * @param axis the axis about which the velocity is to be set
   */
  public void setVelocity(int axis, double velocity){
    myVelocity[axis] = velocity;
  }

  /**
   * @param axis the axis about which the acceleration is to be set
   */
  public void setAcceleration(int axis, double acceleration){
    myAcceleration[axis] = acceleration;
  }

  /**
   * @param axis the axis about which the inputAdjust is to be set
   */
  public void setInputAdjust(int axis, double inputAdjust){
    myInputAdjust[axis] = inputAdjust;
  }
}
