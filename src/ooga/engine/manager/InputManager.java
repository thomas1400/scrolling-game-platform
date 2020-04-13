package ooga.engine.manager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.scene.input.KeyEvent;
import ooga.model.entity.Entity;

public class InputManager {

  Entity myMainEntity;
  private ResourceBundle myUserInputsResources;
  private static final String UserInputResources = "userinput/userinput";
  private Set<String> keysCurrentlyPressed;

  public InputManager(Entity mainEntity) {
    myMainEntity = mainEntity;
    myUserInputsResources = ResourceBundle.getBundle(UserInputResources);
    keysCurrentlyPressed = new HashSet<>();
  }

  public void handleKeyPress(KeyEvent keyEvent) {
      keysCurrentlyPressed.add(keyEvent.getCode().toString());
  }

  public void handleKeyRelease(KeyEvent keyEvent) {
    keysCurrentlyPressed.remove(keyEvent.getCode().toString());
  }

  public void invokeMethods() {
    for (String keyPressed : keysCurrentlyPressed) {
      try {
        String methodName = myUserInputsResources.getString(keyPressed);
        try {
          Method m = myMainEntity.getClass().getDeclaredMethod(methodName);
          m.invoke(myMainEntity);
        } catch (NoSuchMethodException e) {
          e.printStackTrace();
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        } catch (InvocationTargetException e) {
          e.printStackTrace();
        }
      } catch (SecurityException e) {
        e.printStackTrace();
      }
    }
  }

}