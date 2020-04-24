package ooga.engine.manager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.scene.input.KeyEvent;
import ooga.exceptions.ExceptionFeedback;
import ooga.model.entity.Entity;

public class InputManager {
  Entity myMainEntity;
  private ResourceBundle myUserInputsResources;
  private Set<String> keysCurrentlyPressed;
  private static final String REPEAT_ACTION = "ONREPEAT";
  private static final String REPEAT = "repeat";
  private static final String EXCEPTION_MESSAGE = "Incorrect method associated with this key";

  public InputManager(Entity mainEntity, String gameType) {
    myMainEntity = mainEntity;
    String UserInputResources = "gamedata/"+gameType+"/userinput/userinput";
    myUserInputsResources = ResourceBundle.getBundle(UserInputResources);
    keysCurrentlyPressed = new HashSet<>();
  }

  public void processInput() {
    for (String str : keysCurrentlyPressed) {
      if (myUserInputsResources.getString(str + REPEAT_ACTION)
          .equals(REPEAT)) {
        invokeMethod(str);
      }
    }
  }

  public void handleKeyPress(KeyEvent keyEvent) {
    String keyCode = keyEvent.getCode().toString();
    if (!keysCurrentlyPressed.contains(keyCode) && myUserInputsResources.containsKey(keyCode)) {
      keysCurrentlyPressed.add(keyCode);
      invokeMethod(keyCode);
    }
  }

  public void handleKeyRelease(KeyEvent keyEvent) {
    keysCurrentlyPressed.remove(keyEvent.getCode().toString());
  }

  public void invokeMethod(String keyPressed) {
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
}
