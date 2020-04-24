package ooga.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import ooga.controller.users.User;
import ooga.exceptions.ExceptionFeedback;

public final class UserSaver {

  private static final String USER_PACKAGE = "data/userdata/";

  public static void saveUser(User user) {
    if (!user.getName().equals("Default User")) {
      try (OutputStream output = new FileOutputStream(
          USER_PACKAGE + user.getName() + ".user")) {

        Properties userProperties = new Properties();

        // set the properties value
        setSimpleProperties(user, userProperties);
        setGamesProperty(user, userProperties);
        setUnlockedLevelsProperty(user, userProperties);

        // save properties to project root folder
        userProperties.store(output, "User properties file for user: " + user.getName());

      } catch (IOException io) {
        ExceptionFeedback.throwHandledException(io, "User saving for user " + user.getName() + " "
            + "has failed. Be aware that changes from the previous level may not have been saved.");
      }
    }
  }

  private static void setGamesProperty(User user, Properties userProperties) {
    String gamesString = buildStringFromList(user.getAllGames());
    userProperties.setProperty("games", gamesString);
  }

  private static void setSimpleProperties(User user, Properties userProperties) {
    ResourceBundle myUserSaverResources = ResourceBundle.getBundle("userdata/users");

    for (String userProperty : Collections.list(myUserSaverResources.getKeys())) {
      try {
        String methodName = myUserSaverResources.getString(userProperty);
        try {
          Method m = user.getClass().getDeclaredMethod(methodName);
          userProperties.setProperty(userProperty, m.invoke(user) + "");
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
          ExceptionFeedback.throwBreakingException(e, "Reflection Error when accessing User "
              + "Properties during save. (Check users.properties file)");
        }
      } catch (SecurityException ignore) {
      }
    }
  }

  private static void setUnlockedLevelsProperty(User user, Properties userProperties) {
    for (String game : user.getAllGames()) {
      String unlockedLevelsString = buildStringFromList(user.getLevelsCompleted(game));
      userProperties.setProperty(game + "Levels", unlockedLevelsString);
    }
  }

  private static String buildStringFromList(Set userPropertyList) {
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
