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
    if (!keysCurrentlyPressed.contains(keyEvent.getCode().toString())) {
      keysCurrentlyPressed.add(keyEvent.getCode().toString());
      invokeMethod(keyEvent.getCode().toString());
    } else {
      if (myUserInputsResources.getString(keyEvent.getCode().toString() + "ONREPEAT")
          .equals("repeat")) {
        invokeMethod(keyEvent.getCode().toString());
      }
    }
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
          //FIXME
          e.printStackTrace();
        } catch (IllegalAccessException e) {
          //FIXME
          e.printStackTrace();
        } catch (InvocationTargetException e) {
          //FIXME
          e.printStackTrace();
        }
      } catch (SecurityException e) {
        //FIXME
        e.printStackTrace();
      }
    }
  }

  public void invokeMethod(String keyPressed) {
    try {
      String methodName = myUserInputsResources.getString(keyPressed);
      try {
        Method m = myMainEntity.getClass().getDeclaredMethod(methodName);
        m.invoke(myMainEntity);
      } catch (NoSuchMethodException e) {
        //FIXME
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        //FIXME
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        //FIXME
        e.printStackTrace();
      }
    } catch (SecurityException e) {
      //FIXME
      e.printStackTrace();
    }
  }
}
