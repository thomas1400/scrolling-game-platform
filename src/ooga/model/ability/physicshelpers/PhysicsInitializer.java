package ooga.model.ability.physicshelpers;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public final class PhysicsInitializer {

  private static final String DT = "DT";
  private static final String SCALE_SIZE = "SCALE_SIZE";
  private static final String JUMP_HEIGHT = "JUMP_HEIGHT";
  private static final String GRAVITY = "GRAVITY";
  private static final String MAX_VERT_VELOCITY = "MAX_VERT_VELOCITY";
  private static final String MAX_HORIZ_VELOCITY = "MAX_HORIZ_VELOCITY";
  private static final String RUN_ACCELERATION = "RUN_ACCELERATION";
  private static final String INIT_JUMP_VELOCITY = "INIT_JUMP_VELOCITY";
  private static final String TINY_DISTANCE = "TINY_DISTANCE";

  public static Map<String, Double> getConstantsMap(String gameType){
    Map<String, Double> myConstants = new HashMap<>();
    setDefaultConstants(myConstants);
    setGameSpecificConstants(gameType, myConstants);
    scaleAppropriateConstants(myConstants);
    setDerivedConstants(myConstants);
    return myConstants;
  }

  private static void setDefaultConstants(Map<String, Double> myConstants) {
    ResourceBundle defaultPhysicsBundle = ResourceBundle.getBundle("gamedata/defaultphysics");
    for (String constant : defaultPhysicsBundle.keySet()){
      double constantValue = Double.parseDouble(defaultPhysicsBundle.getString(constant));
      myConstants.put(constant, constantValue);
    }
  }

  private static void setGameSpecificConstants(String gameType,
      Map<String, Double> myConstants) {
    ResourceBundle gamePhysicsBundle = ResourceBundle.getBundle("gamedata/" + gameType +
        "/physics/physicsconstants");
    for (String constant : gamePhysicsBundle.keySet()){
      double constantValue = Double.parseDouble(gamePhysicsBundle.getString(constant));
      myConstants.replace(constant, constantValue);
    }
  }

  private static void scaleAppropriateConstants(Map<String, Double> myConstants) {
    String[] constantsToScale = new String[]{JUMP_HEIGHT, MAX_HORIZ_VELOCITY, MAX_VERT_VELOCITY,
        RUN_ACCELERATION};
    for (String constant : constantsToScale){
      double scaledValue = myConstants.get(constant) * myConstants.get(SCALE_SIZE);
      myConstants.replace(constant, scaledValue);
    }
  }

  private static void setDerivedConstants(Map<String, Double> myConstants) {
    if (myConstants.containsKey(INIT_JUMP_VELOCITY)){
      myConstants.replace(INIT_JUMP_VELOCITY, -1* myConstants.get(INIT_JUMP_VELOCITY));
    } else {
      myConstants.put(INIT_JUMP_VELOCITY,
          -1 * Math.sqrt(2 * myConstants.get(GRAVITY) * myConstants.get(JUMP_HEIGHT)));
    }
    myConstants.putIfAbsent(TINY_DISTANCE,
        myConstants.get(MAX_VERT_VELOCITY) * myConstants.get(DT));
  }

}
