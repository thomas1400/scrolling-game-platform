package ooga.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import ooga.controller.data.User;

public final class UserSaver {

  public static void saveUser(User user) {
    if (!user.getName().equals("Default User")) {
      try (OutputStream output = new FileOutputStream(
          "resources/users/" + user.getName() + ".user")) {

        Properties userProperties = new Properties();

        // set the properties value
        setSimpleProperties(user, userProperties);
        setUnlockedLevelsProperty(user, userProperties);

        // save properties to project root folder
        userProperties.store(output, "User properties file for user: " + user.getName());

      } catch (IOException io) {
        io.printStackTrace();
      }
    }
  }

  private static void setSimpleProperties(User user, Properties userProperties) {
    String UserSaverResources = "users/users";
    ResourceBundle myUserSaverResources = ResourceBundle.getBundle(UserSaverResources);

    for (String userProperty : Collections.list(myUserSaverResources.getKeys())) {
      try {
        String methodName = myUserSaverResources.getString(userProperty);
        try {
          Method m = user.getClass().getDeclaredMethod(methodName);
          userProperties.setProperty(userProperty, m.invoke(user) + "");
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
          e.printStackTrace();
        }
      } catch (SecurityException e) {
        e.printStackTrace();
      }
    }
  }

  private static void setUnlockedLevelsProperty(User user, Properties userProperties) {
    String unlockedLevelsString = buildStringFromList(user.getLevelsUnlocked());
    userProperties.setProperty("levelsUnlocked", unlockedLevelsString);
  }

  private static String buildStringFromList(List userPropertyList) {
    if (userPropertyList.isEmpty()) {
      return "";
    }
    StringBuilder newString = new StringBuilder();
    for (Object property : userPropertyList){
      newString.append(property.toString()).append(",");
    }
    return newString.toString().substring(0,newString.length()-1);
  }
}
