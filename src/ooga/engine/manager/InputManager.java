package ooga.engine.manager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.scene.input.KeyEvent;
import ooga.exceptions.ExceptionFeedback;
import ooga.model.entity.Entity;

/**
 * This class is used to process input from the user that impacts the main entity of the game
 * This class uses an associated properties file to contain information regarding the methods associated with each key type and if the method should repeat if the key is held down
 * @author Cayla Schuval
 */
public class InputManager {
  Entity myMainEntity;
  private ResourceBundle myUserInputsResources;
  private Set<String> keysCurrentlyPressed;
  private static final String REPEAT_ACTION = "ONREPEAT";
  private static final String REPEAT = "repeat";
  private static final String EXCEPTION_MESSAGE = "Incorrect method associated with this key";
  private static final String GAME_DATA_FOLDER = "gamedata/";
  private static final String USER_INPUT_INFORMATION = "/userinput/userinput";

  /**
   * @param mainEntity main Entity of the game which reacts to the user input
   * @param gameType String associated with the type of game that is being played and has associated methods for specific keys
   * sets up a resource bundle that contains the necessary information and initializes the set that stores the keys currently being pressed
   */
  public InputManager(Entity mainEntity, String gameType) {
    myMainEntity = mainEntity;
    String UserInputResources = GAME_DATA_FOLDER+gameType+USER_INPUT_INFORMATION;
    myUserInputsResources = ResourceBundle.getBundle(UserInputResources);
    keysCurrentlyPressed = new HashSet<>();
  }

  /**
   * Determines which methods should be invoked while their associated keys are being held down
   */
  public void processInput() {
    for (String str : keysCurrentlyPressed) {
      if (myUserInputsResources.getString(str + REPEAT_ACTION)
          .equals(REPEAT)) {
        invokeMethod(str);
      }
    }
  }

  /**
   * Processes when a key is pressed that impacts the main entity
   * Adds the string associated with the key being pressed into a set if it is not already present and invokes the corresponding method
   * @param keyEvent event containing information regarding which key was pressed
   */
  public void handleKeyPress(KeyEvent keyEvent) {
    String keyCode = keyEvent.getCode().toString();
    if (!keysCurrentlyPressed.contains(keyCode) && myUserInputsResources.containsKey(keyCode)) {
      keysCurrentlyPressed.add(keyCode);
      invokeMethod(keyCode);
    }
  }

  /**
   * Processes when a key is released that impacts the main entity
   * Removes the the string associated with the key that is released from the set
   * @param keyEvent event containing information regarding which key was released
   */
  public void handleKeyRelease(KeyEvent keyEvent) {
    keysCurrentlyPressed.remove(keyEvent.getCode().toString());
  }

  private void invokeMethod(String keyPressed) {
    try {
      String methodName;
      if(myUserInputsResources.containsKey(keyPressed)) {
        methodName = myUserInputsResources.getString(keyPressed);
        try {
          Method m = myMainEntity.getClass().getDeclaredMethod(methodName);
          m.invoke(myMainEntity);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
          ExceptionFeedback.throwBreakingException(e, EXCEPTION_MESSAGE);
        }
      }
    } catch (SecurityException ignored) {
    }
  }



  /**
   * Gets the keyCurrentlyPressed Set
   * Used for testing
   */
  public Set<String> getKeysCurrentlyPressed(){
    return keysCurrentlyPressed;
  }
}
